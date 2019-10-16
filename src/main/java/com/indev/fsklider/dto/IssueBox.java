package com.indev.fsklider.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class IssueBox {

   private Issue issue;

    public IssueBox(String description) {
        this.issue = new Issue(description);
    }

    @Data
    public class Issue {
        Integer project_id;
        String subject;
        Integer priority_id;
        Integer tracker_id;
        String description;
        Integer assigned_to_id;

        public Issue( String description) {
            this.project_id = 5;
            this.subject = "Пропущенный звонок от клиента";
            this.priority_id = 3;
            this.tracker_id = 4;
            this.description = description;
            this.assigned_to_id = 1;
        }
    }
}
