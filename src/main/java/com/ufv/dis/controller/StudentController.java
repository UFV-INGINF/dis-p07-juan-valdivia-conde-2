package com.ufv.dis.controller;

import com.ufv.dis.model.Student;
import com.ufv.dis.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Capa HTTP de Student. Recibe peticiones, llama al service y construye
 * la respuesta con el código de estado adecuado. Sin lógica de negocio.
 */
@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Student> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Student getById(@PathVariable("id") int id) {
        return service.getById(id);            // si no existe -> 404 (GlobalExceptionHandler)
    }

    @PostMapping
    public ResponseEntity<Student> create(@Valid @RequestBody Student student) {
        Student created = service.create(student);
        return ResponseEntity
                .created(URI.create("/api/v1/students/" + created.getId()))  // 201 + Location
                .body(created);
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable("id") int id, @Valid @RequestBody Student student) {
        return service.update(id, student);    // 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();  // 204
    }
}
