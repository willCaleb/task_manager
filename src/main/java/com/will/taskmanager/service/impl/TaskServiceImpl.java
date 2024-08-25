package com.will.taskmanager.service.impl;

import com.will.taskmanager.enums.EnumPriority;
import com.will.taskmanager.enums.EnumTaskStatus;
import com.will.taskmanager.exception.CustomException;
import com.will.taskmanager.model.entity.Task;
import com.will.taskmanager.model.entity.TaskList;
import com.will.taskmanager.repository.TaskRepository;
import com.will.taskmanager.service.TaskListService;
import com.will.taskmanager.service.TaskService;
import com.will.taskmanager.utils.Utils;
import com.will.taskmanager.validator.TaskValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskListService taskListService;

    @Override
    public Task create(Integer taskListId, Task task) {
        TaskList taskList = taskListService.findById(taskListId);
        TaskValidator.validateInsertOrUpdate(task);
        task.setTaskList(taskList);
        task.setStatus(EnumTaskStatus.CREATED);
        task.setCreationDate(new Date());
        task.setPriority(Utils.nvl(task.getPriority(), EnumPriority.AVERAGE));
        return taskRepository.save(task);
    }

    @Override
    public void update(Integer taskId, Task task) {
        Task managedTask = findById(taskId);

        managedTask.setPriority(Utils.nvl(task.getPriority(), managedTask.getPriority()));
        managedTask.setStatus(Utils.nvl(task.getStatus(), managedTask.getStatus()));
        managedTask.setDescription(Utils.nvl(task.getDescription(), managedTask.getDescription()));
        managedTask.setTitle(Utils.nvl(task.getTitle(), managedTask.getTitle()));
        managedTask.setExpectedCompletionDate(Utils.nvl(task.getCompletionDate(), managedTask.getCompletionDate()));

        taskRepository.save(managedTask);
    }

    @Override
    public Task findById(Integer taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new CustomException("Tarefa não encontrada!"));
    }

    @Override
    public List<Task> findAllByTaskList(Specification<Task> spec) {
        return taskRepository.findAll(spec);
    }

    @Override
    public void finishTask(Integer taskId) {
        Task task = findById(taskId);

        task.setStatus(EnumTaskStatus.FINISHED);
        task.setCompletionDate(new Date());

        taskRepository.save(task);
    }

    @Override
    public void exclude(Integer taskId) {
        Task task = findById(taskId);
        taskRepository.delete(task);
    }
}
