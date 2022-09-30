package com.nchas.thameem;

public class StudentDetails {

    public  String name,dob,department,phoneNo,studphone;
    public int roomno;
public  String uniqueID;
    public StudentDetails(){}

    public StudentDetails(String uniqueID, String name, String dob, String department, int roomno, String phoneNo, String studphone) {
        this.name=name;
        this.dob=dob;
        this.uniqueID = uniqueID;
        this.department=department;
        this.roomno=roomno;
        this.phoneNo=phoneNo;
        this.studphone=studphone;



    }

    public String getName() {
        return name;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public int getRoomno() {
        return roomno;
    }

    public String getDob() {
        return dob;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getDepartment() {
        return department;
    }

    public String getStudphone() {
        return studphone;
    }
}
