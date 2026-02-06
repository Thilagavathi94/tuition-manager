package com.tuition.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tuition.manager.entity.ExamMarks;
import java.util.List;

public interface ExamMarksRepository extends JpaRepository<ExamMarks, Long> {

 

    ExamMarks findByStudentIdAndMonthAndYearAndSubject(
            Long studentId, int month, int year, String subject
    );

    List<ExamMarks> findByMonthAndYearAndSubject(int month, int year, String subject);
    List<ExamMarks> findByStudentIdAndMonthAndYear(Long studentId, int month, int year);

}

