package com.capstone.service;

import com.capstone.dto.TaskDto;
import com.capstone.entity.Task;
import com.capstone.exception.IdNotFound;
import com.capstone.repository.TaskRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepo taskRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private TaskDto taskDto;

    @BeforeEach
    void setUp() {
        task = new Task(1L, "Title", "Description", "Pending");
        taskDto = new TaskDto(1L, "Title", "Description", "Pending");
    }

    @Test
    void createTaskTest() {
        when(taskRepo.save(any(Task.class))).thenReturn(task);
        when(modelMapper.map(any(TaskDto.class), eq(Task.class))).thenReturn(task);
        when(modelMapper.map(any(Task.class), eq(TaskDto.class))).thenReturn(taskDto);

        TaskDto result = taskService.createTask(taskDto);

        assertNotNull(result);
        assertEquals(taskDto.getTitle(), result.getTitle());
        verify(taskRepo, times(1)).save(any(Task.class));
    }

    @Test
    void getAllTasksTest() {
        List<Task> tasks = Arrays.asList(task);
        when(taskRepo.findAll()).thenReturn(tasks);
        when(modelMapper.map(any(Task.class), eq(TaskDto.class))).thenReturn(taskDto);

        List<TaskDto> result = taskService.getAllTasks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(taskDto.getTitle(), result.get(0).getTitle());
        verify(taskRepo, times(1)).findAll();
    }

    @Test
    void getTaskByIdTest() {
        when(taskRepo.findById(1L)).thenReturn(Optional.of(task));
        when(modelMapper.map(any(Task.class), eq(TaskDto.class))).thenReturn(taskDto);

        TaskDto result = taskService.getTaskById(1L);

        assertNotNull(result);
        assertEquals(taskDto.getTitle(), result.getTitle());
        verify(taskRepo, times(1)).findById(1L);
    }

    @Test
    void getTaskByIdNotFoundTest() {
        when(taskRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IdNotFound.class, () -> taskService.getTaskById(1L));
        verify(taskRepo, times(1)).findById(1L);
    }

    @Test
    void updateTaskTest() {
        when(taskRepo.existsById(1L)).thenReturn(true);
        when(taskRepo.save(any(Task.class))).thenReturn(task);
        when(modelMapper.map(any(TaskDto.class), eq(Task.class))).thenReturn(task);
        when(modelMapper.map(any(Task.class), eq(TaskDto.class))).thenReturn(taskDto);

        TaskDto result = taskService.updateTask(1L, taskDto);

        assertNotNull(result);
        assertEquals(taskDto.getTitle(), result.getTitle());
        verify(taskRepo, times(1)).existsById(1L);
        verify(taskRepo, times(1)).save(any(Task.class));
    }

    @Test
    void deleteTaskTest() {
        when(taskRepo.existsById(1L)).thenReturn(true);

        String result = taskService.deleteTask(1L);

        assertEquals("Task successfully deleted with ID 1", result);
        verify(taskRepo, times(1)).deleteById(1L);
    }

    @Test
    void deleteTaskNotFoundTest() {
        when(taskRepo.existsById(1L)).thenReturn(false);

        assertThrows(IdNotFound.class, () -> taskService.deleteTask(1L));
        verify(taskRepo, times(1)).existsById(1L);
    }
}
