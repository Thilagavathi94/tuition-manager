package com.tuition.manager.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Fees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;
    @Column(unique = true)
private String receiptNo;


    private int month; 
    private int year;     // FEB-2026
    private Double amount;
    private String status;     // PAID / PENDING
    private LocalDate paidDate;
        private LocalDate feeDate;

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getPaidDate() { return paidDate; }
    public void setPaidDate(LocalDate paidDate) { this.paidDate = paidDate; }
    public int getYear() {  return year;}
    public void setYear(int year) { this.year = year;}
       public String getReceiptNo() { return receiptNo; }
    public void setReceiptNo(String receiptNo) { this.receiptNo = receiptNo;    
    }
}
