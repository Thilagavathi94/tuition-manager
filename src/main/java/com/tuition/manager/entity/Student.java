package com.tuition.manager.entity;

import jakarta.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String studentClass;
    private String subject;
    private String parentMobile;
    private Double monthlyFee;
    private boolean active = true;

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStudentClass() { return studentClass; }
    public void setStudentClass(String studentClass) { this.studentClass = studentClass; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getParentMobile() { return parentMobile; }
    public void setParentMobile(String parentMobile) { this.parentMobile = parentMobile; }

    public Double getMonthlyFee() { return monthlyFee; }
    public void setMonthlyFee(Double monthlyFee) { this.monthlyFee = monthlyFee; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
