package com.taskmanager.service;

import com.taskmanager.exception.DuplicateTaskException;
import com.taskmanager.exception.TaskNotFoundException;
import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task sampleTask;

    @BeforeEach
    void setUp() {
        sampleTask = Task.builder()
                .id(1L)
                .title("Test Görevi")
                .description("Test açıklaması")
                .status(TaskStatus.TODO)
                .dueDate(LocalDate.now().plusDays(7))
                .build();
    }

    @Test
    void getAllTasks_shouldReturnAllTasks() {
        when(taskRepository.findAll()).thenReturn(List.of(sampleTask));
        List<Task> result = taskService.getAllTasks();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Görevi");
    }

    @Test
    void getTaskById_shouldReturnTask_whenExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        Task result = taskService.getTaskById(1L);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void getTaskById_shouldThrow_whenNotFound() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> taskService.getTaskById(99L))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void createTask_shouldSave_whenTitleUnique() {
        when(taskRepository.existsByTitle("Test Görevi")).thenReturn(false);
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);
        Task result = taskService.createTask(sampleTask);
        assertThat(result).isNotNull();
        verify(taskRepository).save(sampleTask);
    }

    @Test
    void createTask_shouldThrow_whenDuplicateTitle() {
        when(taskRepository.existsByTitle("Test Görevi")).thenReturn(true);
        assertThatThrownBy(() -> taskService.createTask(sampleTask))
                .isInstanceOf(DuplicateTaskException.class)
                .hasMessageContaining("Test Görevi");
    }

    @Test
    void deleteTask_shouldDelete_whenExists() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        taskService.deleteTask(1L);
        verify(taskRepository).deleteById(1L);
    }

    @Test
    void deleteTask_shouldThrow_whenNotFound() {
        when(taskRepository.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> taskService.deleteTask(99L))
                .isInstanceOf(TaskNotFoundException.class);
    }
}
