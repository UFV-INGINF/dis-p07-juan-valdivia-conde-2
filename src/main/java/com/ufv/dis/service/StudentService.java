package com.ufv.dis.service;

import com.ufv.dis.exception.NotFoundException;
import com.ufv.dis.model.Student;
import com.ufv.dis.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Lógica de negocio de Student. No sabe nada de HTTP.
 */
@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAll() {
        return repository.findAll();
    }

    public Student getById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found: " + id));
    }

    public Student create(Student student) {
        student.setId(repository.nextId());   // el id lo asigna el servidor
        return repository.save(student);
    }

    public Student update(int id, Student student) {
        getById(id);                            // si no existe, lanza 404
        student.setId(id);
        return repository.save(student);
    }

    public void delete(int id) {
        getById(id);                            // si no existe, lanza 404
        repository.deleteById(id);
    }
}
