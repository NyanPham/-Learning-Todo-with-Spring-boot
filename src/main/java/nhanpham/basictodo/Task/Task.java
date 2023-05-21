package nhanpham.basictodo.Task;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nhanpham.basictodo.User.User;

@Document(collection = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Task {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private Date dueDate;
    @NonNull
    private Boolean isCompleted;

    private ObjectId userId;

    public Task(String title, String description, Date dueDate, Boolean isCompleted, ObjectId userId) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
        this.userId = userId;
    }
}
