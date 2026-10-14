package com.ufv.dis.service;

import com.ufv.dis.model.Subject;
import com.ufv.dis.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Lógica de negocio de Subject.
 *
 * TODO (P7 · núcleo): implementa estos métodos REFLEJANDO StudentService.
 * Cuando no exista el recurso, lanza NotFoundException (se traducirá en 404).
 */
@Service
public class SubjectService {

    private final SubjectRepository repository;

    public SubjectService(SubjectRepository repository) {
        this.repository = repository;
    }

    public List<Subject> getAll() {
        // TODO: devuelve todas las asignaturas
        return repository.findAll();
    }

    public Subject getById(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Subject not found: " + id)); 
    }

    public Subject create(Subject subject) {
        subject.setId(repository.nextId()); // el id lo asigna el servidor
        return repository.save(subject);
    }

    public Subject update(int id, Subject subject) {
        getById(id);                        // si no existe, lanza 404
        subject.setId(id);
        return repository.save(subject);
    }

    public void delete(int id) {
        getById(id);                        // si no existe, lanza 404
        repository.deleteById(id);
    }
}