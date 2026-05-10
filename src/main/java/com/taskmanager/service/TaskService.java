package com.taskmanager.service;

import com.taskmanager.exception.DuplicateTaskException;
import com.taskmanager.exception.TaskNotFoundException;
import com.taskmanager.model.Task;
import com.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task createTask(Task task) {
        if (taskRepository.existsByTitle(task.getTitle())) {
            throw new DuplicateTaskException(task.getTitle());
        }
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (!existing.getTitle().equals(updatedTask.getTitle())
                && taskRepository.existsByTitle(updatedTask.getTitle())) {
            throw new DuplicateTaskException(updatedTask.getTitle());
        }

        existing.setTitle(updatedTask.getTitle());
        existing.setDescription(updatedTask.getDescription());
        existing.setStatus(updatedTask.getStatus());
        existing.setDueDate(updatedTask.getDueDate());

        return taskRepository.save(existing);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }
}
