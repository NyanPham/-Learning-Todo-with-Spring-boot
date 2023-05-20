package nhanpham.basictodo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import nhanpham.basictodo.model.Task;

@Repository
public interface TaskRepository extends MongoRepository<Task, ObjectId> {
}
