package com.raza.springboot.controller;

import com.raza.springboot.bean.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("students")
public class StudentController {
    List<Student> students = new ArrayList<>();

    //http://localhost:8080/student
    @GetMapping("student")
    public ResponseEntity<Student> getStudent() {
        Student student = new Student(1, "Aasif", "Raza");
        return ResponseEntity.ok().header("custom-header", "aasif").body(student);
    }

    @PostConstruct
    public void init() {
//        students = new ArrayList<>();
        students.add(new Student(1, "Aamir", "Raza"));
        students.add(new Student(2, "Aasif", "Raza"));
        students.add(new Student(3, "Kashif", "Raza"));
    }

    //http://localhost:8080/students
    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {

        return ResponseEntity.ok(students);
    }

    //http://localhost:8080/students/1
    @GetMapping("{id}")
    public Student getStudentById(@PathVariable("id") int id) {
        return this.students.stream().filter(t -> t.getId() == id).findFirst().get();
    }

    //spring boot rest api with RequestParam
    //http://localhost:8080/students?id=1
    @GetMapping("query")
    public ResponseEntity<Student> studentRequestVariable(@RequestParam int id, @RequestParam String firstName, @RequestParam String lastName) {
//        return this.students.stream().filter(t->t.getId() == id).findFirst().get();
        return ResponseEntity.ok(new Student(id, firstName, lastName));
    }

    //@PostMapping and @RequestBody
    //@PutMapping
    @PutMapping("{id}/update") // Assuming the endpoint path requires an ID parameter
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student updatedStudent) {
        // Find the existing student in the list based on the ID
        Optional<Student> existingStudent = this.students.stream()
                .filter(student -> student.getId() == id)
                .findFirst();

        if (existingStudent.isPresent()) {
            Student studentToUpdate = existingStudent.get();
            studentToUpdate.setFirstName(updatedStudent.getFirstName());
            studentToUpdate.setLastName(updatedStudent.getLastName());

            return ResponseEntity.ok(studentToUpdate);
        }
        return ResponseEntity.ok(null);


    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        System.out.println("Id : " + id);
        this.students = this.students.stream().filter(t -> t.getId() != id).collect(Collectors.toList());
        return ResponseEntity.ok("Successfully deleted student");
    }
}