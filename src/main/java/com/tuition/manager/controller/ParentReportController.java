package com.tuition.manager.controller;

import com.tuition.manager.entity.Student;
import com.tuition.manager.repository.StudentRepository;
import com.tuition.manager.service.ParentReportPdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/parent-report")
public class ParentReportController {

    @Autowired
    private ParentReportPdfService pdfService;

    @Autowired
    private StudentRepository studentRepo;

    @GetMapping
    public String page(Model model) {
        model.addAttribute("students", studentRepo.findAll());
        return "parent-report";
    }

    @PostMapping("/download")
    public ResponseEntity<byte[]> download(
            @RequestParam Long studentId,
            @RequestParam int month,
            @RequestParam int year
    ) {
        Student student = studentRepo.findById(studentId).orElseThrow();
        byte[] pdf = pdfService.generateReport(student, month, year);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=parent-report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
