package com.microexam.examapi.exeception;

public class ExamNotFoundException extends  RuntimeException{
    public ExamNotFoundException(Long id) {
        super("Could not find exam " + id);
    }
}

