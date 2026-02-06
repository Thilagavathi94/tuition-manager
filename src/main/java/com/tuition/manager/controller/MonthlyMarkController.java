package com.tuition.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tuition.manager.entity.ExamMarks;
import com.tuition.manager.repository.ExamMarksRepository;
import com.tuition.manager.repository.StudentRepository;

import java.util.List;

@Controller
@RequestMapping("/monthly-mark")
public class MonthlyMarkController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ExamMarksRepository examMarksRepository;

    @GetMapping
    public String page(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String subject,
            Model model
    ) {
        model.addAttribute("students", studentRepository.findAll());

        // ✅ If filters present → filter
        if (month != null && year != null && subject != null && !subject.isEmpty()) {
            model.addAttribute("marksList",
                    examMarksRepository.findByMonthAndYearAndSubject(month, year, subject));
        }
        // ✅ Else → show all saved marks
        else {
            model.addAttribute("marksList", examMarksRepository.findAll());
        }

        return "monthly-mark";
    }

    @PostMapping("/save")
    public String save(
            @RequestParam Long studentId,
            @RequestParam int month,
            @RequestParam int year,
            @RequestParam String subject,
            @RequestParam int marks
    ) {
        ExamMarks em = examMarksRepository
                .findByStudentIdAndMonthAndYearAndSubject(studentId, month, year, subject);

        if (em == null) {
            em = new ExamMarks();
            em.setStudent(studentRepository.findById(studentId).orElseThrow());
            em.setMonth(month);
            em.setYear(year);
            em.setSubject(subject);
        }

        em.setMarks(marks);
        examMarksRepository.save(em);

        return "redirect:/monthly-mark?month=" + month + "&year=" + year + "&subject=" + subject;
    }
}
