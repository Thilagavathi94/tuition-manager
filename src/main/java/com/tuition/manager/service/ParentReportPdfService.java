package com.tuition.manager.service;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.tuition.manager.entity.ExamMarks;
import com.tuition.manager.entity.Student;
import com.tuition.manager.repository.AttendanceRepository;
import com.tuition.manager.repository.ExamMarksRepository;
import com.tuition.manager.repository.FeesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ParentReportPdfService {

    @Autowired
    private AttendanceRepository attendanceRepo;

    @Autowired
    private ExamMarksRepository marksRepo;

    @Autowired
    private FeesRepository feesRepo;

    public byte[] generateReport(Student student, int month, int year) {

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document doc = new Document(pdf);

            // Header
            doc.add(new Paragraph("HC TECH SOLUTIONS - TUITION CENTRE")
                    .setBold()
                    .setFontSize(18)
                    .setFontColor(ColorConstants.BLUE)
                    .setTextAlignment(TextAlignment.CENTER));

            doc.add(new Paragraph("Monthly Parent Report")
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));

          LineSeparator line = new LineSeparator(null);
doc.add(line);

            doc.add(new Paragraph(" "));

            // Student Info
            doc.add(new Paragraph("Student Name : " + student.getName()).setBold());
            doc.add(new Paragraph("Class : " + student.getStudentClass()));
            doc.add(new Paragraph("Month / Year : " + month + " / " + year));
            doc.add(new Paragraph(" "));

            // Attendance
            long present = attendanceRepo.countByStudentMonthYearAndStatus(student.getId(), month, year, "PRESENT");
            long absent = attendanceRepo.countByStudentMonthYearAndStatus(student.getId(), month, year, "ABSENT");

            doc.add(new Paragraph("Attendance Summary").setBold());
            doc.add(new Paragraph("Present Days : " + present));
            doc.add(new Paragraph("Absent Days : " + absent));
            doc.add(new Paragraph(" "));

            // Marks
            List<ExamMarks> marksList =
                    marksRepo.findByStudentIdAndMonthAndYear(student.getId(), month, year);

            doc.add(new Paragraph("Monthly Exam Marks").setBold());

            Table table = new Table(2);
            table.addHeaderCell("Subject");
            table.addHeaderCell("Marks");

            for (ExamMarks m : marksList) {
                table.addCell(m.getSubject());
                table.addCell(String.valueOf(m.getMarks()));
            }

            doc.add(table);
            doc.add(new Paragraph(" "));

            // Fees
           double paid = feesRepo.totalPaidByStudentMonthYear(student.getId(), month, year);
double pending = feesRepo.totalPendingByStudentMonthYear(student.getId(), month, year);

doc.add(new Paragraph("Fees Summary").setBold());
doc.add(new Paragraph("Paid Amount : " + paid));
doc.add(new Paragraph("Pending Amount : " + pending));


            doc.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("PDF Generation Failed", e);
        }
    }
}
