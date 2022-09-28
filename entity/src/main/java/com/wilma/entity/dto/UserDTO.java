package com.wilma.entity.dto;

import lombok.Data;

@Data
public class UserDTO {
    //All
    private String userType;
    private Integer userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;

    //Educator/Student
    private String staffId;
    private String studentID;
    private String discipline;

    //Partner
    private String businessName;
    private String abn;

}
