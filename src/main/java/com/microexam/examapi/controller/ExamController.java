package com.microexam.examapi.controller;

import com.microexam.examapi.model.Exam;
import com.microexam.examapi.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;

@RestController
public class ExamController {
    @Autowired
    private ExamRepository examRepository;

    @GetMapping("/exams")
    public List<Exam> findExams(){
        return examRepository.findAll();
    }

    @GetMapping("/exams/{id}")
    public ResponseEntity<?> findExam(@PathVariable Long id){
        return examRepository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
        }


    @PostMapping(path = "exams",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createExam(@RequestBody Exam newExam) {
        Exam exam = examRepository.save(newExam);
        return new ResponseEntity<>(exam, HttpStatus.CREATED);
    }

}


