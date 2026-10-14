package com.ufv.dis.model;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de estudiante. Las anotaciones declaran la validación de FORMATO.
 * El id lo asigna el servidor al crear, por eso no es obligatorio en la entrada.
 */
public class Student {

    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private double grade;

    private List<String> tags = new ArrayList<>();

    public Student() { }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public double getGrade() { return grade; }
    public void setGrade(double grade) { this.grade = grade; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}
