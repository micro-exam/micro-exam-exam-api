package com.microexam.examapi.dto;

import lombok.Data;

import java.sql.Timestamp;


public @Data class ExamDTO {

    private String examName;

    private String examDescription;

    private String language;


    private Timestamp timestamp;
}
