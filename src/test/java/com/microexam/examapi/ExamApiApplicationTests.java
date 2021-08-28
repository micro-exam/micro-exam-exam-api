package com.microexam.examapi;

import com.microexam.examapi.controller.ExamController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ExamApiApplicationTests {

	@Autowired
	ExamController examController;


	@Test
	void contextLoads() {
		assertThat(examController).isNotNull();
	}

}
