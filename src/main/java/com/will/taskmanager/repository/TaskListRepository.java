package com.will.taskmanager.repository;

import com.will.taskmanager.model.entity.Task;
import com.will.taskmanager.model.entity.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskListRepository extends JpaRepository<TaskList, Integer>, JpaSpecificationExecutor<TaskList> {
}
