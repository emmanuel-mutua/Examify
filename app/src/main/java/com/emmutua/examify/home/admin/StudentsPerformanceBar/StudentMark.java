package com.emmutua.examify.home.admin.StudentsPerformanceBar;

public class StudentMark {
String studentName,
        studentRegNo,assignment1Marks,assignment2Marks,cat1Marks,cat2Marks,ExamMarks;
    public StudentMark(String studentName, String studentRegNo, String assignment1Marks, String assignment2Marks, String cat1Marks, String cat2Marks, String examMarks) {
        this.studentName = studentName;
        this.studentRegNo = studentRegNo;
        this.assignment1Marks = assignment1Marks;
        this.assignment2Marks = assignment2Marks;
        this.cat1Marks = cat1Marks;
        this.cat2Marks = cat2Marks;
        ExamMarks = examMarks;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentRegNo() {
        return studentRegNo;
    }

    public void setStudentRegNo(String studentRegNo) {
        this.studentRegNo = studentRegNo;
    }

    public String getAssignment1Marks() {
        return assignment1Marks;
    }

    public void setAssignment1Marks(String assignment1Marks) {
        this.assignment1Marks = assignment1Marks;
    }

    public String getAssignment2Marks() {
        return assignment2Marks;
    }

    public void setAssignment2Marks(String assignment2Marks) {
        this.assignment2Marks = assignment2Marks;
    }

    public String getCat1Marks() {
        return cat1Marks;
    }

    public void setCat1Marks(String cat1Marks) {
        this.cat1Marks = cat1Marks;
    }

    public String getCat2Marks() {
        return cat2Marks;
    }

    public void setCat2Marks(String cat2Marks) {
        this.cat2Marks = cat2Marks;
    }

    public String getExamMarks() {
        return ExamMarks;
    }

    public void setExamMarks(String examMarks) {
        ExamMarks = examMarks;
    }
}
