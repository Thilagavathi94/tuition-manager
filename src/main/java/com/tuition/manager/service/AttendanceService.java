package com.tuition.manager.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuition.manager.dto.MonthlyAttendanceDTO;
import com.tuition.manager.dto.MonthlyCombinedReportDTO;
import com.tuition.manager.entity.Attendance;
import com.tuition.manager.repository.AttendanceRepository;
import com.tuition.manager.repository.FeesRepository;
import com.tuition.manager.repository.StudentRepository;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private FeesRepository feesRepository;
    @Autowired
private StudentRepository studentRepository;

       // ✅ ADD THIS METHOD
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }
    public void saveAttendance(Long studentId, LocalDate date, String status) {

    Attendance attendance = attendanceRepository
            .findByStudentIdAndDate(studentId, date);

    if (attendance == null) {
        attendance = new Attendance();
        attendance.setStudent(studentRepository.findById(studentId).orElseThrow());
        attendance.setDate(date);
    }

    attendance.setStatus(status);
    attendanceRepository.save(attendance);
}


    // ================= MONTHLY ATTENDANCE =================
    public List<MonthlyAttendanceDTO> getMonthlyAttendance(int month, int year) {

        List<Object[]> rows = attendanceRepository.monthlyAttendance(month, year);
        List<MonthlyAttendanceDTO> result = new ArrayList<>();

        for (Object[] row : rows) {
            MonthlyAttendanceDTO dto = new MonthlyAttendanceDTO(
                    (String) row[0],
                    ((Number) row[1]).intValue(),
                    ((Number) row[2]).intValue()
            );
            result.add(dto);
        }
        return result;
    }

    // ================= COMBINED REPORT =================
   public List<MonthlyCombinedReportDTO> getMonthlyCombinedReport(int month, int year) {

    String monthName = java.time.Month.of(month)
            .getDisplayName(java.time.format.TextStyle.SHORT, java.util.Locale.ENGLISH);

    String feeMonth = monthName + "-" + year; // Feb-2026

    List<Object[]> attendance = attendanceRepository.monthlyAttendance(month, year);
    List<Object[]> fees =feesRepository.monthlyFees(month, year);


    List<MonthlyCombinedReportDTO> result = new ArrayList<>();

    for (Object[] row : attendance) {
        MonthlyCombinedReportDTO dto = new MonthlyCombinedReportDTO();
        dto.setStudentName((String) row[0]);
        dto.setPresentCount(((Number) row[1]).intValue());
        dto.setAbsentCount(((Number) row[2]).intValue());
        dto.setPaidAmount(0);
        dto.setPendingAmount(0);
        result.add(dto);
    }

    for (Object[] row : fees) {
        String student = (String) row[0];
        String status = (String) row[1];
        Double amount = ((Number) row[2]).doubleValue();

        result.stream()
                .filter(r -> r.getStudentName().equals(student))
                .findFirst()
                .ifPresent(r -> {
                    if ("PAID".equals(status)) r.setPaidAmount(amount);
                    if ("PENDING".equals(status)) r.setPendingAmount(amount);
                });
    }

    return result;
}


}
