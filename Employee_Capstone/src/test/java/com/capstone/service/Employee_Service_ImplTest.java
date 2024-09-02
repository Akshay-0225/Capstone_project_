package com.capstone.service;

import com.capstone.dto.EmployeeDto;
import com.capstone.entity.Employee;
import com.capstone.exception.IdNotFound;
import com.capstone.repository.EmployeeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class Employee_Service_ImplTest {

    @Mock
    private EmployeeRepo employeeRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private Employee_Service_Impl employeeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateEmployee() {
        EmployeeDto employeeDto = new EmployeeDto();
        Employee employee = new Employee();
        
        when(modelMapper.map(employeeDto, Employee.class)).thenReturn(employee);
        when(employeeRepo.save(employee)).thenReturn(employee);
        when(modelMapper.map(employee, EmployeeDto.class)).thenReturn(employeeDto);

        EmployeeDto result = employeeService.createEmployee(employeeDto);

        assertNotNull(result);
        verify(employeeRepo, times(1)).save(any(Employee.class));
    }

    @Test
    public void testUpdateEmployee_NotFound() {
        Long id = 1L;
        EmployeeDto employeeDto = new EmployeeDto();

        when(employeeRepo.existsById(id)).thenReturn(false);

        assertThrows(IdNotFound.class, () -> employeeService.updateEmployee(id, employeeDto));
    }

    @Test
    public void testGetEmployeeById() {
        Long id = 1L;
        Employee employee = new Employee();
        EmployeeDto employeeDto = new EmployeeDto();

        when(employeeRepo.findById(id)).thenReturn(Optional.of(employee));
        when(modelMapper.map(employee, EmployeeDto.class)).thenReturn(employeeDto);

        EmployeeDto result = employeeService.getEmployeeById(id);

        assertNotNull(result);
        assertEquals(employeeDto, result);
    }
    
    @Test
    public void testDeleteEmployee() {
        when(employeeRepo.existsById(1L)).thenReturn(true);

        String response = employeeService.deleteEmployee(1L);

        assertEquals("Employee is successfully deleted with the ID: 1", response);
    }
    @Test
    public void testDeleteEmployeeNotFound() {
        when(employeeRepo.existsById(1L)).thenReturn(false);

        IdNotFound thrown = assertThrows(IdNotFound.class, () -> employeeService.deleteEmployee(1L));

        assertEquals("Employee with ID 1 not found.", thrown.getMessage());
    }
}
