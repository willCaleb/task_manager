package com.will.taskmanager.repository;

import com.will.taskmanager.model.entity.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaskListRepository extends JpaRepository<TaskList, Integer>, JpaSpecificationExecutor<TaskList> {

    List<TaskList> findAllByName(String title);

}
