package com.example.SpringSecurity.student;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
public class StudentController {

    private static  final List<Student> STUDENTS = Arrays.asList(
            new Student(1,"brian"),
            new Student(2,"antony"),
            new Student(3,"abel")
    );

    @GetMapping(path = "{studentId}")
    public  Student getStudent(@PathVariable("studentId") Integer studentId){
       return STUDENTS.stream()
              .filter(student -> studentId.equals(student.getStudentId()))
              .findFirst()
              .orElseThrow(() -> new IllegalStateException("student" +studentId + "does nit exist"));
    }
}
