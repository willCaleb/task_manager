package com.will.taskmanager.model.dto;

import com.will.taskmanager.enums.EnumPriority;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class TaskListDto extends AbstractDto{

    private Integer id;

    private String name;

    private EnumPriority priority;

    private Date creationDate;

    private List<TaskDto> tasks;
}
