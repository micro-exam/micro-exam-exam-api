package com.microexam.examapi.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="exams")
@Entity
@Builder
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long examId;

    private String examName;

    private String examDescription;

    private String language;

    @CreationTimestamp
    private Timestamp timestamp;




}