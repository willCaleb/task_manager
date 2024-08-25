package com.will.taskmanager.validator;

import com.will.taskmanager.exception.CustomException;
import com.will.taskmanager.model.entity.Task;
import com.will.taskmanager.repository.TaskRepository;
import com.will.taskmanager.utils.Utils;

import java.util.List;

public class TaskValidator {

    private TaskRepository taskRepository;

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void validateInsertOrUpdate(Task task) {
        validateTitle(task);
    }

    private void validateTitle(Task task) {
        validateEmptyTitle(task);
        validateTitleSize(task);
        validateTitleDuplicity(task);
    }

    private void validateTitleDuplicity(Task task) {
        List<Task> existingTitles = taskRepository.findAllByTitleAndTaskList(task.getTitle(), task.getTaskList());

        if(!existingTitles.isEmpty()) {
            throw new CustomException("Já existe uma tarefa com esse o título " + task.getTitle() + " definido nessa lista!");
        }
    }

    private static void validateTitleSize(Task task) {
        if (task.getTitle().length() < 5) {
            throw new CustomException("O título deve ter pelo menos 5 caracteres!");
        }
    }

    private static void validateEmptyTitle(Task task) {
        if (Utils.isEmpty(task.getTitle())) {
            throw new CustomException("Título é obrigatório!");
        }
    }

}
