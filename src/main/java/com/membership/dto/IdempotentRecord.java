package com.membership.dto;

import java.time.LocalDateTime;
import com.membership.dao.IdempotencyStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdempotentRecord {

    @NotBlank
    private String requestHash;

    private IdempotencyStatus status; 
      // IN_PROGRESS, COMPLETED
    @NotBlank
    private String response;      
    // serialized response
    @NotBlank
    private LocalDateTime createdAt;
}
