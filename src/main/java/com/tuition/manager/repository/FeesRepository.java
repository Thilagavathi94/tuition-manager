package com.tuition.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tuition.manager.entity.Fees;
import java.util.List;

public interface FeesRepository extends JpaRepository<Fees, Long> {

    @Query("SELECT COALESCE(SUM(f.amount),0) FROM Fees f WHERE f.status='PAID'")
    Double totalCollected();

    @Query("SELECT COALESCE(SUM(f.amount),0) FROM Fees f WHERE f.status='PENDING'")
    Double totalPending();

    @Query("SELECT COUNT(f) FROM Fees f WHERE f.status='PENDING'")
    Long pendingCount();

    @Query("""
        SELECT s.name, f.status, SUM(f.amount)
        FROM Fees f
        JOIN f.student s
        WHERE f.month = :month AND f.year = :year
        GROUP BY s.name, f.status
    """)
    List<Object[]> monthlyFees(int month, int year);

boolean existsByStudentIdAndMonthAndYear(
        Long studentId, int month, int year
);
@Query("""
SELECT f.status
FROM Fees f
WHERE f.student.id = :studentId
AND f.month = :month
AND f.year = :year
""")
String findStatusByStudentMonthYear(
        @Param("studentId") Long studentId,
        @Param("month") int month,
        @Param("year") int year);


@Query("""
SELECT COUNT(f) > 0
FROM Fees f
WHERE f.student.id = :studentId
AND f.month = :month
AND f.year = :year
AND f.status = :status
""")
boolean isFeePaid(
    @Param("studentId") Long studentId,
    @Param("month") int month,
    @Param("year") int year,
    @Param("status") String status
);
List<Fees> findByStudentIdAndMonthAndYear(Long studentId, int month, int year);
@Query("SELECT COALESCE(SUM(f.amount),0) FROM Fees f WHERE f.student.id = :studentId AND f.status = 'PAID'")
double totalPaidByStudent(@Param("studentId") Long studentId);

@Query("SELECT COALESCE(SUM(f.amount),0) FROM Fees f WHERE f.student.id = :studentId AND f.status = 'PENDING'")
double totalPendingByStudent(@Param("studentId") Long studentId);
@Query("""
SELECT COALESCE(SUM(f.amount), 0)
FROM Fees f
WHERE f.student.id = :studentId
AND f.month = :month
AND f.year = :year
AND f.status = 'PAID'
""")
double totalPaidByStudentMonthYear(@Param("studentId") Long studentId,
                                   @Param("month") int month,
                                   @Param("year") int year);

@Query("""
SELECT COALESCE(SUM(f.amount), 0)
FROM Fees f
WHERE f.student.id = :studentId
AND f.month = :month
AND f.year = :year
AND f.status = 'PENDING'
""")
double totalPendingByStudentMonthYear(@Param("studentId") Long studentId,
                                      @Param("month") int month,
                                      @Param("year") int year);






}
