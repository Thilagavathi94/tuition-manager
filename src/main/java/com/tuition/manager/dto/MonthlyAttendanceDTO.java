package com.tuition.manager.dto;

public class MonthlyAttendanceDTO {

    private String studentName;
    private int present;
    private int absent;

    public MonthlyAttendanceDTO(String studentName, int present, int absent) {
        this.studentName = studentName;
        this.present = present;
        this.absent = absent;
    }

    public String getStudentName() { return studentName; }
    public int getPresent() { return present; }
    public int getAbsent() { return absent; }
}
