package nhanpham.basictodo.Task;

import java.util.Date;
import java.util.Optional;

import java.util.Collection;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<Collection<Task>> getAllTasks(
            @CurrentSecurityContext(expression = "authentication") Authentication auth) {
                
        return new ResponseEntity<Collection<Task>>(taskService.findTasks(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskToUpsertDao taskToCreate) {
        return new ResponseEntity<Task>(
                taskService.createTask(taskToCreate.getTitle(), taskToCreate.getDescription(),
                        taskToCreate.getDueDate(), taskToCreate.getIsCompleted()),
                HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Task>> getTask(@PathVariable("id") ObjectId id) {
        return new ResponseEntity<Optional<Task>>(taskService.getTask(id), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") ObjectId id,
            @RequestBody TaskToUpsertDao taskToUpsert) {
        return new ResponseEntity<Task>(taskService.updateTask(id, taskToUpsert), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteTask(@PathVariable("id") ObjectId id) {
        return new ResponseEntity<Boolean>(taskService.deleteTask(id), HttpStatus.GONE);
    }
}