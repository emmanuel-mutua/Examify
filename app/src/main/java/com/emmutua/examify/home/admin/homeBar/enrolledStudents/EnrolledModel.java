package com.emmutua.examify.home.admin.homeBar.enrolledStudents;

// create model EnrolledModel
// package com.emmutua.examify.home.admin.HomeBar.EnrolledStudents;
public class EnrolledModel {
    String studentName, StudentRegNo;


    public EnrolledModel(String studentName, String studentRegNo) {
        this.studentName = studentName;
        StudentRegNo = studentRegNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentRegNo() {
        return StudentRegNo;
    }

    public void setStudentRegNo(String studentRegNo) {
        StudentRegNo = studentRegNo;
    }
}
