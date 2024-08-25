package com.will.taskmanager.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.will.taskmanager.enums.EnumPriority;
import com.will.taskmanager.enums.EnumTaskStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class TaskDto extends AbstractDto{

    private Integer id;

    private String title;

    private String description;

    private EnumTaskStatus status;

    private Date creationDate;

    private Date expectedCompletionDate;

    private Date completionDate;

    private EnumPriority priority;

    @JsonIgnore
    private TaskListDto taskList;
}
