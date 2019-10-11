package com.indev.fsklider.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Issue {
    Integer projectId;
    String subject;
    Integer priorityId;
    Integer trackerId;
    String description;
    Integer assignedToId;

    public Issue( String description) {
        this.projectId = 5;
        this.subject = "Входящий звонок";
        this.priorityId = 1;
        this.trackerId = 2;
        this.description = description;
        this.assignedToId = 1;
    }
}
