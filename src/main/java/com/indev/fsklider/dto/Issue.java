package com.indev.fsklider.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonRootName(value = "issue")
public class Issue {
   private Integer project_id;
    private String subject;
    private Integer priority_id;
    private Integer tracker_id;
    private String description;
    private Integer assigned_to_id;

    public Issue(String description) {
        this.project_id = 5;
        this.subject = "Пропущенный звонок от клиента";
        this.priority_id = 3;
        this.tracker_id = 4;
        this.description = description;
        this.assigned_to_id = 1;
    }

}
