package com.emmutua.examify.home.student.StudentGrades;

public class Grades {
    private String studentUid;
    private String studentName;
    private String registrationNumber;
    private String unitName;
    private String unitCode;
    private String unitLecturer;

    private String unitDepartment;
    private String unitStage;
    private Integer unitAssign1Marks;
    private Integer unitAssign2Marks;
    private Integer unitCat1Marks;
    private Integer unitCat2Marks;
    private Integer unitExamMarks;
    private Integer unitTotalMarks;
    Boolean appliedSpecial;

    public Grades(String studentUid, String studentName, String registrationNumber, String unitName, String unitCode, String unitLecturer, String unitDepartment, String unitStage, Integer unitAssign1Marks, Integer unitAssign2Marks, Integer unitCat1Marks, Integer unitCat2Marks, Integer unitExamMarks, Integer unitTotalMarks, Boolean appliedSpecial) {
        this.studentUid = studentUid;
        this.studentName = studentName;
        this.registrationNumber = registrationNumber;
        this.unitName = unitName;
        this.unitCode = unitCode;
        this.unitLecturer = unitLecturer;
        this.unitDepartment = unitDepartment;
        this.unitStage = unitStage;
        this.unitAssign1Marks = unitAssign1Marks;
        this.unitAssign2Marks = unitAssign2Marks;
        this.unitCat1Marks = unitCat1Marks;
        this.unitCat2Marks = unitCat2Marks;
        this.unitExamMarks = unitExamMarks;
        this.unitTotalMarks = unitTotalMarks;
        this.appliedSpecial = appliedSpecial;
    }

    public String getStudentUid() {
        return studentUid;
    }

    public void setStudentUid(String studentUid) {
        this.studentUid = studentUid;
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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitLecturer() {
        return unitLecturer;
    }

    public void setUnitLecturer(String unitLecturer) {
        this.unitLecturer = unitLecturer;
    }

    public String getUnitDepartment() {
        return unitDepartment;
    }

    public void setUnitDepartment(String unitDepartment) {
        this.unitDepartment = unitDepartment;
    }

    public String getUnitStage() {
        return unitStage;
    }

    public void setUnitStage(String unitStage) {
        this.unitStage = unitStage;
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

    public Integer getUnitTotalMarks() {
        return unitTotalMarks;
    }

    public void setUnitTotalMarks(Integer unitTotalMarks) {
        this.unitTotalMarks = unitTotalMarks;
    }

    public Boolean getAppliedSpecial() {
        return appliedSpecial;
    }

    public void setAppliedSpecial(Boolean appliedSpecial) {
        this.appliedSpecial = appliedSpecial;
    }
}
