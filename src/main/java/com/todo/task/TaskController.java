package com.todo.task;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    //fetch data
    @GetMapping
    public List<TaskItem> getTasks() {
        return taskRepository.findAll();
    }

    //add data
    @PostMapping("/add")
    public TaskItem addTask(@Valid @RequestBody TaskItem taskItem) {
        return taskRepository.save(taskItem);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTask(@PathVariable long id) {
        boolean exist = taskRepository.existsById(id);
        if (exist) {
            TaskItem task = taskRepository.getById(id);
            boolean done = task.isDone();
            task.setDone(!done);
            taskRepository.save(task);
            return new ResponseEntity<>("Task is updated", HttpStatus.OK);

        }
        return new ResponseEntity<>("Task not exist", HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable long id) {
        boolean exist = taskRepository.existsById(id);
        if (exist) {
            taskRepository.deleteById(id);
            return new ResponseEntity<>("Task is deleted", HttpStatus.OK);

        }
        return new ResponseEntity<>("Task not exist", HttpStatus.BAD_REQUEST);
    }
}
