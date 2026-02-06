package com.tuition.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tuition.manager.repository.AttendanceRepository;
import com.tuition.manager.repository.FeesRepository;
import com.tuition.manager.repository.StudentRepository;

@Controller
public class DashboardController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FeesRepository feesRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;
  @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {

        // Students & Fees
        model.addAttribute("studentCount", studentRepository.count());
        model.addAttribute("totalCollected", feesRepository.totalCollected());
        model.addAttribute("totalPending", feesRepository.totalPending());
       model.addAttribute(
    "pendingCount",
    feesRepository.pendingCount() != null ? feesRepository.pendingCount() : 0
);


        // Attendance (NULL SAFE)
        model.addAttribute("todayPresent",
                attendanceRepository.todayPresent() != null
                        ? attendanceRepository.todayPresent() : 0);

        model.addAttribute("todayAbsent",
                attendanceRepository.todayAbsent() != null
                        ? attendanceRepository.todayAbsent() : 0);

        model.addAttribute("totalPresent",
                attendanceRepository.totalPresent() != null
                        ? attendanceRepository.totalPresent() : 0);

        model.addAttribute("totalAbsent",
                attendanceRepository.totalAbsent() != null
                        ? attendanceRepository.totalAbsent() : 0);

        return "dashboard";
    }
}
