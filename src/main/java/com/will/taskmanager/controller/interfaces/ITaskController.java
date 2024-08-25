package com.will.taskmanager.controller.interfaces;

import com.will.taskmanager.model.dto.TaskDto;
import com.will.taskmanager.pattern.OperationsParam;
import com.will.taskmanager.pattern.OperationsPath;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(ITaskController.PATH)
public interface ITaskController{

    String PATH = ITaskListController.PATH + OperationsPath.PARENT_ID + "/task";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TaskDto create(@PathVariable(OperationsParam.PARENT_ID) Integer taskListId, @RequestBody TaskDto taskDto);

    @PutMapping(OperationsPath.ID)
    void update(@PathVariable(OperationsParam.ID) Integer taskId, @RequestBody TaskDto taskDto);

    @GetMapping("/list")
    List<TaskDto> findAllByList(@PathVariable(OperationsParam.PARENT_ID) Integer taskListId, @RequestParam Map<String, Object> filters);

    @GetMapping(OperationsPath.ID)
    TaskDto findById(@PathVariable(OperationsParam.ID) Integer taskId);

    @DeleteMapping(OperationsPath.ID)
    void exclude(@PathVariable(OperationsParam.ID) Integer taskId);

    @PutMapping(OperationsPath.ID + "/finish")
    void finish(@PathVariable(OperationsParam.ID) Integer taskId);
}
