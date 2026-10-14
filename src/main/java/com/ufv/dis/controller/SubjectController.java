package com.ufv.dis.controller;

import com.ufv.dis.model.Subject;
import com.ufv.dis.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
/**
 * Capa HTTP de Subject.
 *
 * TODO (P7 · núcleo): implementa el CRUD REST de asignaturas reflejando
 * StudentController. Debe exponer:
 *   GET    /api/v1/subjects        -> 200, lista
 *   GET    /api/v1/subjects/{id}   -> 200 | 404
 *   POST   /api/v1/subjects        -> 201 + header Location
 *   PUT    /api/v1/subjects/{id}   -> 200 | 404
 *   DELETE /api/v1/subjects/{id}   -> 204 | 404
 * Recuerda validar el cuerpo con @Valid.
 */
@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    private final SubjectService service;

    public SubjectController(SubjectService service) {
        this.service = service;
    }

    @GetMapping
    public List<Subject> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Subject getById(@PathVariable("id") int id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<Subject> create(@Valid @RequestBody Subject subject) {
        Subject created = service.create(subject);
        return ResponseEntity
                .created(URI.create("/api/v1/subjects/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public Subject update(@PathVariable("id") int id,
                        @Valid @RequestBody Subject subject) {
        return service.update(id, subject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Subject> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Subject getById(@PathVariable("id") int id) {
        return service.getById(id);            // si no existe -> 404 (GlobalExceptionHandler)
    }

    @PostMapping
    public ResponseEntity<Subject> create(@Valid @RequestBody Subject subject) {
        Subject created = service.create(subject);
        return ResponseEntity
                .created(URI.create("/api/v1/subjects/" + created.getId()))  // 201 + Location
                .body(created);
    }

    @PutMapping("/{id}")
    public Subject update(@PathVariable("id") int id, @Valid @RequestBody Subject subject) {
        return service.update(id, subject);    // 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();  // 204
    }
}
