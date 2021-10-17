package com.microexam.examapi.exeception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.logging.Logger;

@ControllerAdvice
@Slf4j
public class ExamNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(ExamNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String examNotFoundHandler(ExamNotFoundException ex){
        log.error("Exception {} thrown", ex.getMessage());
        return ex.getMessage();
    }
}
