package com.ufv.dis.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufv.dis.model.Subject;
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
 * Capa de datos para Subject (persistencia JSON). Te la damos hecha:
 * es idéntica en estructura a StudentRepository.
 */
@Repository
public class SubjectRepository {

    private final ObjectMapper mapper = new ObjectMapper();
    private final List<Subject> data = new ArrayList<>();

    @Value("${dis.subjects.file:./data/subjects.json}")
    private String filePath;

    @PostConstruct
    public void load() {
        Path path = Path.of(filePath);
        if (Files.exists(path)) {
            try {
                List<Subject> loaded = mapper.readValue(Files.readAllBytes(path),
                        new TypeReference<List<Subject>>() {});
                data.clear();
                data.addAll(loaded);
            } catch (IOException e) {
                throw new UncheckedIOException("No se pudo leer " + filePath, e);
            }
        }
    }

    public List<Subject> findAll() {
        return new ArrayList<>(data);
    }

    public Optional<Subject> findById(int id) {
        return data.stream().filter(s -> s.getId() != null && s.getId() == id).findFirst();
    }

    public Subject save(Subject subject) {
        data.removeIf(s -> s.getId() != null && s.getId().equals(subject.getId()));
        data.add(subject);
        persist();
        return subject;
    }

    public void deleteById(int id) {
        data.removeIf(s -> s.getId() != null && s.getId() == id);
        persist();
    }

    public int nextId() {
        return data.stream().filter(s -> s.getId() != null)
                .mapToInt(Subject::getId).max().orElse(0) + 1;
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
