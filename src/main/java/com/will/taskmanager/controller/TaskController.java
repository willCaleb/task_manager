package com.will.taskmanager.controller;

import com.will.taskmanager.controller.interfaces.ITaskController;
import com.will.taskmanager.model.dto.TaskDto;
import com.will.taskmanager.model.entity.Task;
import com.will.taskmanager.model.entity.TaskList;
import com.will.taskmanager.service.TaskListService;
import com.will.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TaskController extends AbstractController<Task, TaskDto> implements ITaskController {

    private final TaskService taskService;

    private final TaskListService taskListService;

    @Override
    public TaskDto create(Integer taskListId, TaskDto taskDto) {
        return toDto(taskService.create(taskListId, toEntity(taskDto)));
    }

    @Override
    public void update(Integer taskId, TaskDto taskDto) {
        taskService.update(taskId, toEntity(taskDto));
    }

    @Override
    public List<TaskDto> findAllByList(Integer taskListId, Map<String, Object> filters) {
        TaskList taskList = taskListService.findById(taskListId);
        filters.put("taskList", taskList);
        return toDto(taskService.findAllByTaskList(getSpecificationEqualOrLike(filters)));
    }

    @Override
    public TaskDto findById(Integer taskId) {
        return toDto(taskService.findById(taskId));
    }

    @Override
    public void exclude(Integer taskId) {
        taskService.exclude(taskId);
    }

    @Override
    public void finish(Integer taskId) {
        taskService.finishTask(taskId);
    }
}
