package com.studentManagement.controller;

import com.studentManagement.model.Student;
import com.studentManagement.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private MockMvc mockMvc;
    private Student student;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
        student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setAge(20);
        student.setStudentClass("10th Grade");
        student.setPhoneNumber("1234567890");
    }

    @Test
    void addStudent() throws Exception {
        when(studentService.addStudent(any(Student.class))).thenReturn(student);
        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\",\"age\":20,\"studentClass\":\"10th Grade\",\"phoneNumber\":\"1234567890\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void getAllStudents() throws Exception {
        when(studentService.getAllStudents()).thenReturn(Arrays.asList(student));
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void getStudentById() throws Exception {
        when(studentService.getStudentById(1L)).thenReturn(Optional.of(student));
        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void getStudentsByName() throws Exception {
        when(studentService.getStudentsByName("John Doe")).thenReturn(Arrays.asList(student));
        mockMvc.perform(get("/api/students/search").param("name", "John Doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void updateStudent() throws Exception {
        when(studentService.updateStudent(anyLong(), any(Student.class))).thenReturn(student);
        mockMvc.perform(put("/api/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\",\"age\":20,\"studentClass\":\"10th Grade\",\"phoneNumber\":\"1234567890\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void deleteStudent() throws Exception {
        doNothing().when(studentService).deleteStudent(1L);
        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isNoContent());
    }
}