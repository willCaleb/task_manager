package com.will.taskmanager.validator;

import com.will.taskmanager.exception.CustomException;
import com.will.taskmanager.model.entity.Task;
import com.will.taskmanager.utils.Utils;

public class TaskValidator {

    public static void validateInsertOrUpdate(Task task) {
        validateTitle(task);
    }

    private static void validateTitle(Task task) {
        if (Utils.isEmpty(task.getTitle())) {
            throw new CustomException("Título é obrigatório!");
        }
        if (task.getTitle().length() < 5) {
            throw new CustomException("O título deve ter pelo menos 5 caracteres!");
        }
    }

}
