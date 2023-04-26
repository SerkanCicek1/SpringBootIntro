package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/students") // http://localhost:8080/students
public class StudentController {

    @Autowired
    private StudentService studentService;

    // !!! Butun ogrencileri getirelim
    @GetMapping // http://localhost:8080/students + GET
    public ResponseEntity<List<Student>>  getAll(){
        List<Student> students = studentService.getAll();

        return ResponseEntity.ok(students); // List<Student> + HTTP-StatusCode(200)

    }

}