package com.emmutua.examify.home.lecture;

public class StudentModel {
    String studentUid;
    String unitCode;
    String studentName;
    String registrationNumber;
    Integer unitAssign1Marks;
    Integer unitAssign2Marks;
    Integer unitCat1Marks;
    Integer unitCat2Marks;
    Integer unitExamMarks;
    Boolean appliedSpecial;
    public StudentModel(String studentUid, String unitCode,String studentName, String registrationNumber, Integer unitAssign1Marks, Integer unitAssign2Marks, Integer unitCat1Marks, Integer unitCat2Marks, Integer unitExamMarks, Boolean appliedSpecial) {
        this.studentUid = studentUid;
        this.unitCode = unitCode;
        this.studentName = studentName;
        this.registrationNumber = registrationNumber;
        this.unitAssign1Marks = unitAssign1Marks;
        this.unitAssign2Marks = unitAssign2Marks;
        this.unitCat1Marks = unitCat1Marks;
        this.unitCat2Marks = unitCat2Marks;
        this.unitExamMarks = unitExamMarks;
        this.appliedSpecial = appliedSpecial;
    }

    public Boolean getAppliedSpecial() {
        return appliedSpecial;
    }

    public void setAppliedSpecial(Boolean appliedSpecial) {
        this.appliedSpecial = appliedSpecial;
    }

    public String getStudentUid() {
        return studentUid;
    }

    public void setStudentUid(String studentUid) {
        this.studentUid = studentUid;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Integer getUnitAssign1Marks() {
        return unitAssign1Marks;
    }

    public void setUnitAssign1Marks(Integer unitAssign1Marks) {
        this.unitAssign1Marks = unitAssign1Marks;
    }

    public Integer getUnitAssign2Marks() {
        return unitAssign2Marks;
    }

    public void setUnitAssign2Marks(Integer unitAssign2Marks) {
        this.unitAssign2Marks = unitAssign2Marks;
    }

    public Integer getUnitCat1Marks() {
        return unitCat1Marks;
    }

    public void setUnitCat1Marks(Integer unitCat1Marks) {
        this.unitCat1Marks = unitCat1Marks;
    }

    public Integer getUnitCat2Marks() {
        return unitCat2Marks;
    }

    public void setUnitCat2Marks(Integer unitCat2Marks) {
        this.unitCat2Marks = unitCat2Marks;
    }

    public Integer getUnitExamMarks() {
        return unitExamMarks;
    }

    public void setUnitExamMarks(Integer unitExamMarks) {
        this.unitExamMarks = unitExamMarks;
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
