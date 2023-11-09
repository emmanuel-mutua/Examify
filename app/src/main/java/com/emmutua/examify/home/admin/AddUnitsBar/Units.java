package com.emmutua.examify.home.admin.AddUnitsBar;

public class Units {
    // contains the details about the units registered by the admin
    String unitName, unitCode, unitLecturer, semester, role;

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

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // set setter and getter
    public Units(String unitName, String unitCode, String unitLecturer, String semester, String role) {
        this.unitName = unitName;
        this.unitCode = unitCode;
        this.unitLecturer = unitLecturer;
        this.semester = semester;
        this.role = role;
    }

}
