package com.emmutua.examify.home.admin;

public class Units {
    // contains the details about the units registered by the admin
    String unitName, unitCode, unitLecturer, semester, role;
    // set setter and getter
    public Units(String unitName, String unitCode, String unitLecturer, String semester, String role) {
        this.unitName = unitName;
        this.unitCode = unitCode;
        this.unitLecturer = unitLecturer;
        this.semester = semester;
        this.role = role;
    }
}
