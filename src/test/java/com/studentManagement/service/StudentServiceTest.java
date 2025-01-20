package com.studentManagement.service;

import com.studentManagement.model.Student;
import com.studentManagement.repository.StudentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setAge(20);
        student.setStudentClass("10th Grade");
        student.setPhoneNumber("1234567890");
    }

    @Test
    void addStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        Student createdStudent = studentService.addStudent(student);
        assertNotNull(createdStudent);
        assertEquals(student.getName(), createdStudent.getName());
    }

    @Test
    void getAllStudents() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student));
        List<Student> students = studentService.getAllStudents();
        assertFalse(students.isEmpty());
        assertEquals(1, students.size());
    }

    @Test
    void getStudentById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Optional<Student> foundStudent = studentService.getStudentById(1L);
        assertTrue(foundStudent.isPresent());
        assertEquals(student.getName(), foundStudent.get().getName());
    }

    @Test
    void getStudentsByName() {
        when(studentRepository.findByName("John Doe")).thenReturn(Arrays.asList(student));
        List<Student> students = studentService.getStudentsByName("John Doe");
        assertFalse(students.isEmpty());
        assertEquals(1, students.size());
    }

    @Test
    void updateStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        Student updatedStudent = studentService.updateStudent(1L, student);
        assertNotNull(updatedStudent);
        assertEquals(student.getName(), updatedStudent.getName());
    }

    @Test
    void deleteStudent() {
        doNothing().when(studentRepository).deleteById(1L);
        studentService.deleteStudent(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }
}