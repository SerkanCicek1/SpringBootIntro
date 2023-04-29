package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students") // http://localhost:8080/students
public class StudentController {

    // !!! Logger
    Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    // !!! Butun ogrencileri getirelim
    @GetMapping // http://localhost:8080/students/ + GET
    public ResponseEntity<List<Student>>  getAll(){
        List<Student> students = studentService.getAll();

        return ResponseEntity.ok(students); // List<Student> + HTTP-StatusCode(200)
    }

    // !!! Create new student
    @PostMapping // http://localhost:8080/students/ + POST + JSON
    public ResponseEntity<Map<String,String>> createStudent( @Valid @RequestBody Student student){
        studentService.createStudent(student);

        Map<String,String> map = new HashMap<>();
        map.put("message", "Student is created successfuly");
        map.put("status", "true");

        return new ResponseEntity<>(map, HttpStatus.CREATED); // 201
    }

    //!!! Get a Student by ID via RequestParam
    @GetMapping("/query") // http://localhost:8080/students/query?id=1
    public ResponseEntity<Student> getStudent(@RequestParam("id") Long id){

        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(student); //  return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //!!! Get a Student by ID via PathVariable
    @GetMapping("{id}") // http://localhost:8080/students/1
    public ResponseEntity<Student> getStudentWithPath(@PathVariable("id") Long id ){
        Student student = studentService.findStudent(id);
        return ResponseEntity.ok(student);

    }

    //!!! Delete Student with id
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable("id") Long id){
        studentService.deleteStudent(id);

        Map<String,String> map = new HashMap<>();
        map.put("message", "Student is deleted successfuly");
        map.put("status", "true");

        return new ResponseEntity<>(map, HttpStatus.OK); // return ResponseEntity.ok(map);
    }

    // !!! Update Student
    @PutMapping("{id}")  // http://localhost:8080/students/1  ---> endPoint + id + JSON + HTTP-Method
    public ResponseEntity<Map<String,String>> updateStudent(
            @PathVariable Long id, @RequestBody StudentDTO studentDTO ) {
        studentService.updateStudent(id,studentDTO);

        Map<String,String> map = new HashMap<>();
        map.put("message", "Student is updated successfuly");
        map.put("status", "true");

        return new ResponseEntity<>(map, HttpStatus.OK);

    }

    //!!! pageable
    @GetMapping("/page") // http://localhost:8080/students/page?page=1&size=2&sort=name&direction=ASC
    public ResponseEntity<Page<Student>> getAllWithPage(
            @RequestParam("page") int page, // kacinci sayfa gelsin
            @RequestParam("size") int size, // sayfa basi kac urun
            @RequestParam("sort") String prop, // hangi field a gore siralanacak
            @RequestParam("direction") Sort.Direction direction // siralama turu

    ) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(direction,prop));
        Page<Student> studentPage = studentService.getAllWithPage(pageable);

        return ResponseEntity.ok(studentPage);

    }

    // !!! Get By LastName
    @GetMapping("/querylastname")
    public ResponseEntity<List<Student>> getStudentByLastName(@RequestParam("lastName") String lastName) {
        List<Student> list = studentService.findStudent(lastName);
        return ResponseEntity.ok(list);
    }

    // !!! get All Student By Grade ( JPQL )
    @GetMapping("/grade/{grade}") // http://localhost:8080/students/grade/75  + GET
    public ResponseEntity<List<Student>> getStudentsEqualsGrade(@PathVariable("grade") Integer grade) {

        List<Student> list = studentService.findAllEqualsGrade(grade);

        return ResponseEntity.ok(list);
    }

    // !!! DB den direk DTO olarak datami almak istersem ??
    @GetMapping("/query/dto") // http://localhost:8080/students/query/dto?id=1   + GET
    public ResponseEntity<StudentDTO> getStudentDTO(@RequestParam("id") Long id) {
        StudentDTO studentDTO = studentService.findStudentDTOById(id);

        return  ResponseEntity.ok(studentDTO);

    }

    // !!! view
    @GetMapping("/welcome") // http://localhost:8080/students/welcome  + GET
    public String welcome(HttpServletRequest request){

        logger.warn("------------------- Welcome{}", request.getServletPath());
        return "Welcome to Student Controller";
    }

}