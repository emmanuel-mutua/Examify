package com.emmutua.examify.home.lecture;

public class StudentModel {
    String studentName;
    String registrationNumber;

    public StudentModel(String studentName, String registrationNumber) {
        this.studentName = studentName;
        this.registrationNumber = registrationNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
