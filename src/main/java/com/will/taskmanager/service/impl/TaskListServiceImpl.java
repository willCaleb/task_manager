package com.will.taskmanager.service.impl;

import com.will.taskmanager.enums.EnumTaskStatus;
import com.will.taskmanager.model.entity.TaskList;
import com.will.taskmanager.repository.TaskListRepository;
import com.will.taskmanager.service.TaskListService;
import com.will.taskmanager.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;

    @Override
    public TaskList create(TaskList taskList) {
        taskList.setCreationDate(new Date());
        onPrepareTasks(taskList);
        return taskListRepository.save(taskList);
    }

    private void onPrepareTasks(TaskList taskList) {
        taskList.getTasks().forEach(task -> {
            task.setTaskList(taskList);
            task.setStatus(EnumTaskStatus.CREATED);
        });
    }

    @Override
    public void update(Integer taskListId, TaskList taskList) {
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
