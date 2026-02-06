package com.tuition.manager.controller;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tuition.manager.entity.Fees;
import com.tuition.manager.entity.Student;
import com.tuition.manager.repository.FeesRepository;
import com.tuition.manager.repository.StudentRepository;
import java.util.List;


@Controller
@RequestMapping("/fees")
public class FeesController {

    @Autowired
    private FeesRepository feesRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public String feesPage(Model model) {
        model.addAttribute("feesList", feesRepository.findAll());
        model.addAttribute("students", studentRepository.findAll());
         model.addAttribute("months", List.of(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    ));
    
    model.addAttribute("currentYear", LocalDate.now().getYear());

        return "fees";
    }

 @PostMapping("/add")
public String addFee(
        @RequestParam Long studentId,
        @RequestParam int month,
        @RequestParam int year) {

    Student student = studentRepository.findById(studentId).orElse(null);

    if (student != null) {

        // 🔒 Prevent duplicate fee for same month
        if (feesRepository.existsByStudentIdAndMonthAndYear(studentId, month, year)) {
            return "redirect:/fees";
        }

        Fees fees = new Fees();
        fees.setStudent(student);
        fees.setMonth(month);
        fees.setYear(year);
        fees.setAmount(student.getMonthlyFee());
        fees.setStatus("PENDING");

        feesRepository.save(fees);
    }
    return "redirect:/fees";
}
@GetMapping("/pay/{id}")
public String markPaid(@PathVariable Long id) {

    Fees fee = feesRepository.findById(id).orElse(null);

    if (fee != null) {
        fee.setStatus("PAID");
        fee.setPaidDate(LocalDate.now());

        // ✅ Generate receipt number
        fee.setReceiptNo("RCPT-" + System.currentTimeMillis());

        feesRepository.save(fee);
    }
    return "redirect:/fees";
}
@GetMapping("/receipt/{id}")
public String viewReceipt(@PathVariable Long id, Model model) {

    Fees fee = feesRepository.findById(id).orElse(null);

    if (fee == null || !"PAID".equals(fee.getStatus())) {
        return "redirect:/fees";
    }

    model.addAttribute("fee", fee);
    return "receipt";
}




}
