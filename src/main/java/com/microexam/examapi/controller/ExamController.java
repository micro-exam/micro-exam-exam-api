package com.microexam.examapi.controller;

import com.microexam.examapi.dto.ExamDTO;
import com.microexam.examapi.exeception.ExamNotFoundException;
import com.microexam.examapi.model.Exam;
import com.microexam.examapi.repository.ExamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/exams")
@Slf4j
public class ExamController {
    @Autowired
    private ExamRepository examRepository;

    @GetMapping()
    public List<Exam> findExams(){
        return examRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findExam(@PathVariable Long id){
        return examRepository.findById(id).map(ResponseEntity::ok)
                .orElseThrow(() -> new ExamNotFoundException(id));
        }


    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createExam(@RequestBody Exam newExam) {
        Exam exam = examRepository.save(newExam);
        return new ResponseEntity<>(exam, HttpStatus.CREATED);
    }

   @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteExam(@PathVariable Long id) {
       Optional<Exam> exam = Optional.ofNullable(examRepository.findById(id).orElseThrow(() -> new ExamNotFoundException(id)));

       if (exam.isPresent()) {
           examRepository.delete(exam.get());
           log.info("Exam that was deleted {}", exam.toString());
           return ResponseEntity.status(HttpStatus.ACCEPTED).build();
       } else {
           log.error("Exam was not found with exam id of {}", id);
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }
   }

   @PutMapping("/{id}")
    public ResponseEntity<?> updateExam(@RequestBody ExamDTO examDTO, @PathVariable Long id){
       Optional<Exam> exam = Optional.ofNullable(examRepository.findById(id).orElseThrow(() -> new ExamNotFoundException(id)));
       if(exam.isEmpty()) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }
       else {
           BeanUtils.copyProperties(examDTO, exam.get());
           exam.get().setTimestamp(Timestamp.from(Instant.now()));
       }
       final Exam updatedExam = examRepository.save(exam.get());
       return ResponseEntity.ok(updatedExam);
   }

}


