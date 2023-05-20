package nhanpham.basictodo.model;

import java.util.Date;

public class TaskToUpsert {
    private String title;
    private String description;
    private Date dueDate;
    private Boolean isCompleted;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public TaskToUpsert(String title, String description, Date dueDate, Boolean isCompleted) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;

        if (isCompleted == null) {
            this.isCompleted = false;
        } else {
            this.isCompleted = isCompleted;
        }
    }
}
