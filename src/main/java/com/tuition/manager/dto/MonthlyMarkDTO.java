package com.tuition.manager.dto;

public class MonthlyMarkDTO {
    

    private String studentName;
    private long present;
    private long absent;
    private String feeStatus;

    public MonthlyMarkDTO(String studentName, long present, long absent, String feeStatus) {
        this.studentName = studentName;
        this.present = present;
        this.absent = absent;
        this.feeStatus = feeStatus;
    }


    // getters
    public String getStudentName() {
        return studentName;
    }   
    public long getPresent() {
        return present;
    }
    public long getAbsent() {
        return absent;
    }
    public String getFeeStatus() {
        return feeStatus;
    }
    
}


