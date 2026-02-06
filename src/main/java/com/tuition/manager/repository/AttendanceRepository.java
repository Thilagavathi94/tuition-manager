package com.tuition.manager.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tuition.manager.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Attendance findByStudentIdAndDate(Long studentId, LocalDate date);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.status = 'PRESENT'")
    Long totalPresent();

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.status = 'ABSENT'")
    Long totalAbsent();

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.date = CURRENT_DATE AND a.status = 'PRESENT'")
    Long todayPresent();

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.date = CURRENT_DATE AND a.status = 'ABSENT'")
    Long todayAbsent();

    @Query("""
        SELECT a.student.name,
        SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END),
        SUM(CASE WHEN a.status = 'ABSENT' THEN 1 ELSE 0 END)
        FROM Attendance a
        WHERE MONTH(a.date) = :month AND YEAR(a.date) = :year
        GROUP BY a.student.name
    """)
    List<Object[]> monthlyAttendance(
            @Param("month") int month,
            @Param("year") int year
    );

    @Query("""
        SELECT COUNT(a)
        FROM Attendance a
        WHERE a.student.id = :studentId
        AND MONTH(a.date) = :month
        AND YEAR(a.date) = :year
        AND a.status = :status
    """)
    long countByStudentMonthYearAndStatus(
            @Param("studentId") Long studentId,
            @Param("month") int month,
            @Param("year") int year,
            @Param("status") String status
    );

    @Query("""
        SELECT a FROM Attendance a
        WHERE a.student.id = :studentId
        AND MONTH(a.date) = :month
        AND YEAR(a.date) = :year
    """)
    List<Attendance> findMonthlyAttendance(
            @Param("studentId") Long studentId,
            @Param("month") int month,
            @Param("year") int year
    );
}
