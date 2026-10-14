package com.ufv.dis.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufv.dis.model.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Capa de datos para Student: persiste la lista en un archivo JSON.
 * No sabe nada de HTTP ni de reglas de negocio.
 */
@Repository
public class StudentRepository {

    private final ObjectMapper mapper = new ObjectMapper();
    private final List<Student> data = new ArrayList<>();

    @Value("${dis.students.file:./data/students.json}")
    private String filePath;

    @PostConstruct
    public void load() {
        Path path = Path.of(filePath);
        if (Files.exists(path)) {
            try {
                List<Student> loaded = mapper.readValue(Files.readAllBytes(path),
                        new TypeReference<List<Student>>() {});
                data.clear();
                data.addAll(loaded);
            } catch (IOException e) {
                throw new UncheckedIOException("No se pudo leer " + filePath, e);
            }
        }
    }

    public List<Student> findAll() {
        return new ArrayList<>(data);
    }

    public Optional<Student> findById(int id) {
        return data.stream().filter(s -> s.getId() != null && s.getId() == id).findFirst();
    }

    public Student save(Student student) {
        data.removeIf(s -> s.getId() != null && s.getId().equals(student.getId()));
        data.add(student);
        persist();
        return student;
    }

    public void deleteById(int id) {
        data.removeIf(s -> s.getId() != null && s.getId() == id);
        persist();
    }

    public int nextId() {
        return data.stream().filter(s -> s.getId() != null)
                .mapToInt(Student::getId).max().orElse(0) + 1;
    }

    private void persist() {
        Path path = Path.of(filePath);
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            mapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), data);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo escribir " + filePath, e);
        }
    }
}
