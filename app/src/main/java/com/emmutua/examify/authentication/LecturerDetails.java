package com.emmutua.examify.authentication;

public class LecturerDetails {
    String uid, fullName, unitCode, unitName;
    Boolean isAssigned;

    public LecturerDetails(String uid, String fullName, String unitCode, String  unitName, Boolean isAssigned) {
        this.uid = uid;
        this.fullName = fullName;
        this.unitCode = unitCode;
        this.unitName = unitName;
        this.isAssigned = isAssigned;
    }

    public Boolean getAssigned() {
        return isAssigned;
    }

    public void setAssigned(Boolean assigned) {
        isAssigned = assigned;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
}
