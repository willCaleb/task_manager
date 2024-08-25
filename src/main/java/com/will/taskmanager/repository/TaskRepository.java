package com.will.taskmanager.repository;

import com.will.taskmanager.model.entity.Task;
import com.will.taskmanager.model.entity.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {

    List<Task> findAllByTitleAndTaskList(String title, TaskList taskList);

}
