package com.hrms.hrms.user.DTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String username;
    private String role;
    private Long employeeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

