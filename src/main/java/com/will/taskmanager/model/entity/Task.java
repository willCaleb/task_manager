package com.will.taskmanager.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.will.taskmanager.enums.EnumPriority;
import com.will.taskmanager.enums.EnumTaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "task")
public class Task extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Convert(converter = EnumTaskStatus.EnumConverter.class)
    private EnumTaskStatus status;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "expected_completion_date")
    private Date expectedCompletionDate;

    @Column(name = "completion_date")
    private Date completionDate;

    @Column(name = "priority")
    private EnumPriority priority;

    @ManyToOne
    @JoinColumn(name = "task_list_id", referencedColumnName = "id")
    private TaskList taskList;
}
