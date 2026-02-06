package com.tuition.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tuition.manager.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
