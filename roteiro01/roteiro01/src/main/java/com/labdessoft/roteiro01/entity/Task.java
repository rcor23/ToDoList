package com.labdessoft.roteiro01.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Todos os detalhes sobre uma tarefa.")
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(name = "Descrição da tarefa deve possuir pelo menos 10 caracteres")
    @Size(min = 10, message = "Descrição da tarefa deve possuir pelo menos 10 caracteres")
    private String description;

    private Boolean completed = false;

    @Schema(name = "Tipo da tarefa")
    private taskType type;

    @Schema(name = "Prioridade da tarefa")
    private taskPriority priority;

    @Schema(name = "Data prevista de conclusão da tarefa")
    private LocalDate dueDate;

    public void setDueDate(LocalDate dueDate) {
        if (type == taskType.DATA && dueDate != null) {
            LocalDate currentDate = LocalDate.now();
            if (dueDate.isBefore(currentDate)) {
                throw new IllegalArgumentException("A data prevista de execução deve ser igual ou superior à data atual");
            }
        }
        this.dueDate = dueDate;
    }


    public String getStatus() {
        switch (type) {
            case DATA:
                if (completed) {
                    return "Concluída";
                } else if (dueDate != null && LocalDate.now().isAfter(dueDate)) {
                    long daysOverdue = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
                    return daysOverdue + " dias de atraso";
                } else {
                    return "Prevista";
                }
            case PRAZO:
                if (completed) {
                    return "Concluída";
                } else if (dueDate != null && LocalDate.now().isAfter(dueDate)) {
                    long daysOverdue = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
                    return daysOverdue + " dias de atraso";
                } else {
                    return "Prevista";
                }
            case LIVRE:
                return completed ? "Concluída" : "Prevista";
            default:
                return "Desconhecido";
        }
    }

    @Override
    public String toString() {
        return "Task [id=" + id + ", description=" + description + ", completed=" +
                completed + ", type=" + type + ", priority=" + priority + "]";
    }
}
