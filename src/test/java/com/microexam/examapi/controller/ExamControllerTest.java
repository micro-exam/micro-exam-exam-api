package com.microexam.examapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microexam.examapi.model.Exam;
import com.microexam.examapi.repository.ExamRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExamController.class)
class ExamControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ExamRepository examRepository;

    Exam exam_one = new Exam(1L, "CS100-Exam1", "Exam1", "Python", Timestamp.from(Instant.now()));
    Exam exam_two = new Exam(2L, "CS114-Exam2", "Exam2", "Java", Timestamp.from(Instant.now().minusSeconds(30)));

    //101, 'CS100-Exam1', 'Exam1', 'Python','2020-01-01 15:10:10 '

    @Test
    public void getAllRecords() throws Exception {
        List<Exam> exams = new ArrayList<>();
        exams.add(exam_one);
        exams.add(exam_two);
        Mockito.when(examRepository.findAll()).thenReturn(exams);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/exams").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    public void getOneRecord() throws Exception {
        Mockito.when(examRepository.findById(exam_one.getExamId())).thenReturn(java.util.Optional.of(exam_one));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/exams/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.examName", is("CS100-Exam1")));

    }

    @Test
    public void createOneRecord() throws Exception {
        Exam exam_to_post = Exam.builder()
                .examName("CS100-Exam1")
                .examDescription("Exam1")
                .language("Python")
                .build();
        Mockito.when(examRepository.save(exam_to_post)).thenReturn(exam_to_post);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/exams")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exam_to_post));
        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());
    }
    @Test
    public void updateRecord() throws Exception {
        Exam updatedRecord = Exam.builder()
                .examId(1l)
                .examName("CS100-ExamUpdated")
                .examDescription("Exam1")
                .language("Python")
                .timestamp(Timestamp.from(Instant.now()))
                .build();

        Mockito.when(examRepository.findById(exam_one.getExamId())).thenReturn(Optional.of(exam_one));
        Mockito.when(examRepository.save(updatedRecord)).thenReturn(updatedRecord);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/exams/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(updatedRecord));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void deleteRecord() throws Exception {
        Mockito.when(examRepository.findById(exam_two.getExamId())).thenReturn(Optional.of(exam_two));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/exams/delete/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

}