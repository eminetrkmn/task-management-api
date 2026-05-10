package com.taskmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Başlık boş olamaz")
    @Column(nullable = false, unique = true)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Durum boş olamaz")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Column(name = "due_date")
    private LocalDate dueDate;
}
