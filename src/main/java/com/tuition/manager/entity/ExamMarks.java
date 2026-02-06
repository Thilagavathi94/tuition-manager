package com.tuition.manager.entity;

import jakarta.persistence.*;

@Entity
@Table(
     name = "exam_marks",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"student_id", "month", "year", "subject"}
    )
)
public class ExamMarks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    private int month;
    private int year;

    private int marks;
    private String subject;


    // ---------- getters & setters ----------

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
    public String getSubject() {
    return subject;
}

public void setSubject(String subject) {
    this.subject = subject;
}

}
