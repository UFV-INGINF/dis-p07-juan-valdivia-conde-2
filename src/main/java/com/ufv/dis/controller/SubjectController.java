package com.ufv.dis.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufv.dis.model.Subject;
import com.ufv.dis.service.SubjectService;

import jakarta.validation.Valid;
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

/* Delete this method
    @GetMapping
    public List<Subject> getAll() {
        return service.getAll();
    }
*/

/* Delete this method
    @GetMapping("/{id}")
    public Subject getById(@PathVariable("id") int id) {
        return service.getById(id);            // si no existe -> 404 (GlobalExceptionHandler)
    }

*/

/* Delete this method
    @PostMapping
    public ResponseEntity<Subject> create(@Valid @RequestBody Subject subject) {
        Subject created = service.create(subject);
        return ResponseEntity
                .created(URI.create("/api/v1/subjects/" + created.getId()))  // 201 + Location
                .body(created);
    }

*/

/* Delete this method
    @PutMapping("/{id}")
    public Subject update(@PathVariable("id") int id, @Valid @RequestBody Subject subject) {
        return service.update(id, subject);    // 200
    }

*/

/* Delete this method
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();  // 204
    }
    */
}
