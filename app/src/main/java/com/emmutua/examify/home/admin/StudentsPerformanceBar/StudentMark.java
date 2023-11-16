package com.emmutua.examify.home.admin.StudentsPerformanceBar;

public class StudentMark {
String studentName, studentRegNo,unitCode,unitName;
Integer assignment1Marks,assignment2Marks,cat1Marks,cat2Marks,ExamMarks;
    public StudentMark(String studentName,String unitName,String unitCode, String studentRegNo, Integer assignment1Marks, Integer assignment2Marks, Integer cat1Marks, Integer cat2Marks, Integer examMarks) {
        this.studentName = studentName;
        this.studentRegNo = studentRegNo;
        this.unitName = unitName;
        this.unitCode = unitCode;
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

    public Integer getAssignment1Marks() {
        return assignment1Marks;
    }

    public void setAssignment1Marks(Integer assignment1Marks) {
        this.assignment1Marks = assignment1Marks;
    }

    public Integer getAssignment2Marks() {
        return assignment2Marks;
    }

    public void setAssignment2Marks(Integer assignment2Marks) {
        this.assignment2Marks = assignment2Marks;
    }

    public Integer getCat1Marks() {
        return cat1Marks;
    }

    public void setCat1Marks(Integer cat1Marks) {
        this.cat1Marks = cat1Marks;
    }

    public Integer getCat2Marks() {
        return cat2Marks;
    }

    public void setCat2Marks(Integer cat2Marks) {
        this.cat2Marks = cat2Marks;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getExamMarks() {
        return ExamMarks;
    }

    public void setExamMarks(Integer examMarks) {
        ExamMarks = examMarks;
    }
}
