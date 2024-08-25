package com.will.taskmanager.controller;

import com.will.taskmanager.controller.interfaces.ITaskListController;
import com.will.taskmanager.model.dto.TaskListDto;
import com.will.taskmanager.model.entity.TaskList;
import com.will.taskmanager.pattern.OperationsPath;
import com.will.taskmanager.service.TaskListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TaskListController extends AbstractController<TaskList, TaskListDto> implements ITaskListController {

    private final TaskListService taskListService;

    @Override
    public TaskListDto include(TaskListDto taskList) {
        return toDto(taskListService.create(toEntity(taskList)));
    }

    @Override
    public List<TaskListDto> listAll(Map<String, Object> filters) {
        return toDto(taskListService.findAll(getSpecificationEqualOrLike(filters)));
    }

    @Override
    public void update(Integer taskListId, TaskListDto taskListDto) {
        taskListService.update(taskListId, toEntity(taskListDto));
    }

    @Override
    public TaskListDto findOne(Integer taskListId) {
        return  toDto(taskListService.findById(taskListId));
    }

    @Override
    public void exclude(Integer taskListId) {
        taskListService.exclude(taskListId);
    }

    @Override
    public void finish(Integer taskListId) {
        taskListService.finish(taskListId);
    }
}
