package com.microexam.examapi.service;

import com.microexam.examapi.model.Exam;
import com.microexam.examapi.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    public List<Exam> findAll(){
        return (List<Exam>) examRepository.findAll();
    }

    public Optional<Exam> findByExamId(Long id) { return examRepository.findByExamId(id); }
}
