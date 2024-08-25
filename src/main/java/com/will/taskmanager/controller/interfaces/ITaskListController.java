package com.will.taskmanager.controller.interfaces;

import com.will.taskmanager.model.dto.TaskListDto;
import com.will.taskmanager.model.entity.TaskList;
import com.will.taskmanager.pattern.OperationsParam;
import com.will.taskmanager.pattern.OperationsPath;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RequestMapping(ITaskListController.PATH)
public interface ITaskListController {

    String PATH = "/task-list";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TaskListDto include(@RequestBody TaskListDto taskList);

    @GetMapping
    List<TaskListDto> listAll(@RequestParam Map<String, Object> filters);

    @PutMapping(OperationsPath.ID)
    void update(@PathVariable(OperationsParam.ID) Integer taskListId, @RequestBody TaskListDto taskListDto);

    @GetMapping(OperationsPath.ID)
    TaskListDto findOne(@PathVariable(OperationsParam.ID) Integer taskListId);

    @DeleteMapping(OperationsPath.ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void exclude(@PathVariable(OperationsParam.ID) Integer taskListId);

    @PutMapping(OperationsPath.ID + "/finish")
    void finish(@PathVariable(OperationsParam.ID) Integer taskListId);

}
