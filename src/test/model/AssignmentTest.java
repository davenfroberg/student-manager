package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssignmentTest {

    Assignment assignment;
    Course course;

    @BeforeEach
    public void setup() {
        course = new Course("CPSC 210", 4);
        assignment = new Assignment("Test Assignment", 50);
    }

    @Test
    public void constructorOneTest() {
        assertEquals("Test Assignment", assignment.getTitle());
        assertEquals(50, assignment.getGrade());
    }

    @Test
    public void constructorTwoTest() {
        Assignment assignment2 = new Assignment("Test Assignment2", course);
        assertEquals("Test Assignment2", assignment2.getTitle());
        assertEquals(-1, assignment2.getGrade());
    }
}
