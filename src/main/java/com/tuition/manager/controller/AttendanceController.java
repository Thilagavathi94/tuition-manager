package com.tuition.manager.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tuition.manager.entity.Attendance;
import com.tuition.manager.service.AttendanceService;
import com.tuition.manager.service.StudentService;


@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;
       @Autowired
    private StudentService studentService;

  @GetMapping
    public String attendancePage(Model model) {

        model.addAttribute("attendance", new Attendance());   // form binding
        model.addAttribute("students", studentService.getAllStudents()); // dropdown
        model.addAttribute("attendanceList",
                attendanceService.getAllAttendance()); // table

        return "attendance";
    }
     @PostMapping("/save")
    public String saveAttendance(
            @RequestParam Long studentId,
            @RequestParam String status,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        attendanceService.saveAttendance(studentId, date, status);
        return "redirect:/attendance";
    }

    @GetMapping("/monthly-report")
    public String monthlyAttendanceReport(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            Model model) {

        if (month != null && year != null) {
            model.addAttribute(
                "reports",
                attendanceService.getMonthlyAttendance(month, year)
            );
        }

        model.addAttribute("month", month);
        model.addAttribute("year", year);

        return "monthly-attendance";
    }

    @GetMapping("/monthly-combined-report")
    public String monthlyCombinedReport(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            Model model) {

        if (month != null && year != null) {
            model.addAttribute(
                "reports",
                attendanceService.getMonthlyCombinedReport(month, year)
            );
        }

        model.addAttribute("month", month);
        model.addAttribute("year", year);

        return "monthly-combined-report";
    }
}

