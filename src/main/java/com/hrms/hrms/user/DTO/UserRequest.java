package com.hrms.hrms.user.DTO;

import lombok.Data;

@Data
public class UserRequest {

    private String username;
    private String password;
    private String role;
    private Long employeeId;   // only ID, not full Employee object
}
