package com.emmutua.examify.home.admin.homeBar.units;

// unitModel Class
public class UnitModel {
    String unitName, unitCode, unitLecturer, unitSemesterStage;

    public UnitModel(String unitName, String unitCode, String unitLecturer, String unitSemesterStage) {
        this.unitName = unitName;
        this.unitCode = unitCode;
        this.unitLecturer = unitLecturer;
        this.unitSemesterStage = unitSemesterStage;
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

    public String getUnitSemesterStage() {
        return unitSemesterStage;
    }

    public void setUnitSemesterStage(String unitSemesterStage) {
        this.unitSemesterStage = unitSemesterStage;
    }
}
