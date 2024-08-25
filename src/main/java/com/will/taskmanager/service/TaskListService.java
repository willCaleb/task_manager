package com.will.taskmanager.service;

import com.will.taskmanager.model.entity.TaskList;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface TaskListService {

    TaskList create(TaskList taskList);

    void update(Integer taskListId, TaskList taskList);

    TaskList findById(Integer id);

    List<TaskList> findAll(Specification<TaskList> specification);

    void exclude(Integer taskListId);

    void finish(Integer taskListId);
}
