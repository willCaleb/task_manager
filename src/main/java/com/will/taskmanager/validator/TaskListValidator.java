package com.will.taskmanager.validator;

import com.will.taskmanager.exception.CustomException;
import com.will.taskmanager.model.entity.TaskList;
import com.will.taskmanager.repository.TaskListRepository;

import java.util.List;

public class TaskListValidator {

    private TaskListRepository taskListRepository;

    public void setTaskListRepository(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    private void validateDuplicatedTitle(TaskList taskList) {
        List<TaskList> allByName = taskListRepository.findAllByName(taskList.getName());
        if (!allByName.isEmpty()) {
            throw new CustomException("Já existe uma lista de tarefas com o nome " + taskList.getName() + "!");
        }
    }

    private void validateNameMinSize(TaskList taskList) {
        if (taskList.getName().length() < 5) {
            throw new CustomException("O nome da lista de tarefas deve ter no mínimo 5 caracteres!");
        }
    }

    public void validateInsertOrUpdate(TaskList taskList) {
        validateDuplicatedTitle(taskList);
        validateNameMinSize(taskList);
    }

}
