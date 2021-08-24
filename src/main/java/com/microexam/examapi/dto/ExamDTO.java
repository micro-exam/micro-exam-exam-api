package com.microexam.examapi.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Date;


public @Data class ExamDTO {

    private String examName;

    private String examDescription;

    private String language;


    private Timestamp timestamp;
}
