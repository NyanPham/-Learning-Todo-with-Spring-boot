package nhanpham.basictodo.service;

import java.util.Date;
import java.util.Optional;

import javax.swing.text.html.Option;

import java.util.Collection;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import nhanpham.basictodo.model.Task;
import nhanpham.basictodo.model.TaskToUpsert;
import nhanpham.basictodo.repository.TaskRepository;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Collection<Task> findTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(String title, String description, Date dueDate, Boolean isCompleted) {
        return taskRepository.insert(new Task(title, description, dueDate, isCompleted));
    }

    public Optional<Task> getTask(ObjectId id) {
        return taskRepository.findById(id);
    }

    public Task updateTask(ObjectId id, TaskToUpsert taskToUpdate) {
        Query updateQuery = new Query();
        updateQuery.addCriteria(Criteria.where("id").is(id));

        Update update = new Update();

        if (taskToUpdate.getTitle() != null)
            update.set("title", taskToUpdate.getTitle());

        if (taskToUpdate.getDescription() != null)
            update.set("description", taskToUpdate.getDescription());

        if (taskToUpdate.getDueDate() != null)
            update.set("dueDate", taskToUpdate.getDueDate());

        if (taskToUpdate.getIsCompleted() != null)
            update.set("isCompleted", taskToUpdate.getIsCompleted());

        Task updatedTask = mongoTemplate.findAndModify(updateQuery, update, new FindAndModifyOptions().returnNew(true),
                Task.class);

        return updatedTask;
    }

    public Boolean deleteTask(ObjectId id) {
        try {
            taskRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
}
