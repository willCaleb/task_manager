package com.will.taskmanager.model.entity;

import com.will.taskmanager.enums.EnumPriority;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "task_lists")
public class TaskList extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "priority")
    @Convert(converter = EnumPriority.EnumConverter.class)
    private EnumPriority priority;

    @Column(name = "creation_date")
    private Date creationDate;

    @OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Task> tasks;
}
