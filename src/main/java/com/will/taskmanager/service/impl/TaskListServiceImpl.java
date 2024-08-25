package com.will.taskmanager.service.impl;

import com.will.taskmanager.enums.EnumTaskStatus;
import com.will.taskmanager.model.entity.TaskList;
import com.will.taskmanager.repository.TaskListRepository;
import com.will.taskmanager.repository.TaskRepository;
import com.will.taskmanager.service.TaskListService;
import com.will.taskmanager.utils.Utils;
import com.will.taskmanager.validator.TaskListValidator;
import com.will.taskmanager.validator.TaskValidator;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;

    private final TaskListValidator taskListValidator = new TaskListValidator();

    private final TaskValidator taskValidator = new TaskValidator();

    private final TaskRepository taskRepository;

    @PostConstruct
    private void setTaskListValidatorRepository() {
        taskListValidator.setTaskListRepository(taskListRepository);
    }

    @Override
    @Transactional
    public TaskList create(TaskList taskList) {

        taskListValidator.validateInsertOrUpdate(taskList);

        taskList.setCreationDate(new Date());
        onPrepareTasks(taskList);
        return taskListRepository.save(taskList);
    }

    private void onPrepareTasks(TaskList taskList) {
        taskList.getTasks().forEach(task -> {
            task.setStatus(EnumTaskStatus.CREATED);
            taskRepository.save(task);
        });
    }

    @Override
    @Transactional
    public void update(Integer taskListId, TaskList taskList) {

        taskListValidator.validateInsertOrUpdate(taskList);

        TaskList managedTaskList = findById(taskListId);
        managedTaskList.setName(Utils.nvl(taskList.getName(), managedTaskList.getName()));
        managedTaskList.setPriority(Utils.nvl(taskList.getPriority(), taskList.getPriority()));

        taskListRepository.save(managedTaskList);
    }

    @Override
    public TaskList findById(Integer taskListId) {
        return taskListRepository.findById(taskListId).orElseThrow(() -> new RuntimeException("Lista não encontrada!"));
    }

    @Override
    public List<TaskList> findAll(Specification<TaskList> specification) {
        return taskListRepository.findAll(specification);
    }

    @Override
    public void exclude(Integer taskListId) {
        TaskList taskList = findById(taskListId);
        taskListRepository.delete(taskList);
    }

    @Override
    public void finish(Integer taskListId) {
        TaskList taskList = findById(taskListId);

        taskList.getTasks().forEach(task -> {
            task.setStatus(EnumTaskStatus.FINISHED);
            task.setCompletionDate(new Date());
        });

        taskListRepository.save(taskList);
    }
}
