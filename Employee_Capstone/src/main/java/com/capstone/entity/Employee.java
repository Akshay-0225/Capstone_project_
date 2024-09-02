package com.capstone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; 
    private String email;
    
    private Long phoneNumber;
    
//    @NotBlank(message = "Job role cannot be empty")
    private String jobRole;
    
//    @Max(value = 10000000L, message = "Salary is too high. Enter a proper value.")
//    @Min(value = 999L, message = "Salary is too low. Enter a proper value.")
    private Double salary;
    
//    @Size(min = 2, max = 30, message = "Department name must be between 2 and 30 characters")
    private String departmentCode;
    
    private Long taskId;
    
    private Long performanceId;
}
