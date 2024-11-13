package com.vision.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vision.task.model.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
class TaskApplicationTests {

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	private final WebApplicationContext webApplicationContext;


	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
		this.objectMapper = new ObjectMapper();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void addEmployeeReturnsCreated() throws Exception {
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setEmail("ahmed@gmail.com");
		employeeDto.setFirstName("Ahmed");
		employeeDto.setLastName("Hussein");
		employeeDto.setDepartment("CS");
		employeeDto.setSalary(2000);
		String jsonRequest = objectMapper.writeValueAsString(employeeDto);

		mockMvc.perform(post("/employee/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.success").value(true));
	}

	@Test
	void addEmployeeMissingLastNameReturnsBadRequest() throws Exception {
		EmployeeDto invalidEmployeeDto = new EmployeeDto();
		invalidEmployeeDto.setEmail("ahmed@gmail.com");
		invalidEmployeeDto.setFirstName("Ahmed");
		invalidEmployeeDto.setDepartment("CS");
		invalidEmployeeDto.setSalary(2000);
		String jsonRequest = objectMapper.writeValueAsString(invalidEmployeeDto);

		mockMvc.perform(post("/employee/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonRequest))
				.andExpect(status().isBadRequest());
	}

}
