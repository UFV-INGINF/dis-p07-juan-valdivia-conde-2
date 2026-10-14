package com.ufv.dis.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * Modelo de asignatura. El id lo asigna el servidor al crear.
 */
public class Subject {

    private Integer id;

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    @Min(1)
    @Max(12)
    private int credits;

    public Subject() { }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
}
