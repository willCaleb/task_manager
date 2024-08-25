package com.will.taskmanager.service;

import com.will.taskmanager.model.entity.Task;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface TaskService {

    Task create(Integer taskListId, Task task);

    void update(Integer taskId, Task task);

    Task findById(Integer taskId);

    List<Task> findAllByTaskList(Specification<Task> spec);

    void finishTask(Integer taskId);

    void exclude(Integer taskId);

}
