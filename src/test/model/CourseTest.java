package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {
    Course course;

    @BeforeEach
    public void setup() {
        course = new Course("CPSC 210", 4);
    }

    @Test
    public void constructorTest() {
        assertEquals("CPSC 210", course.getName());
        assertEquals(4, course.getCredits());
        assertEquals(0, course.getAssignmentList().size());
    }

    @Test
    public void addAssignmentNotAlreadyInListNoGradeYetTest() {
        Assignment assignment1 = new Assignment("Test Assignment", -1);
        assertTrue(course.addAssignment(assignment1, false));
        assertEquals(1, course.getAssignmentList().size());
        assertEquals(assignment1, course.getAssignmentList().get(0));
    }

    @Test
    public void addAssignmentNotAlreadyInListWithGradeTest() {
        Assignment assignment1 = new Assignment("Test Assignment", 90);
        assertTrue(course.addAssignment(assignment1, false));
        assertEquals(1, course.getAssignmentList().size());
        assertEquals(assignment1, course.getAssignmentList().get(0));
    }

    @Test
    public void addAssignmentAlreadyInListTest() {
        Assignment assignment1 = new Assignment("Test Assignment", 90);
        Assignment assignment2 = new Assignment("Test Assignment", 80);
        assertTrue(course.addAssignment(assignment1, false));
        assertEquals(1, course.getAssignmentList().size());
        assertEquals(assignment1, course.getAssignmentList().get(0));
        assertFalse(course.addAssignment(assignment2, false));
        assertEquals(1, course.getAssignmentList().size());
        assertEquals(assignment1, course.getAssignmentList().get(0));
    }

    @Test
    public void addTwoAssignmentsNotAlreadyInListTest() {
        Assignment assignment1 = new Assignment("Test Assignment", 100);
        Assignment assignment2 = new Assignment("Test Assignment2", 90);
        assertTrue(course.addAssignment(assignment1, false));
        assertTrue(course.addAssignment(assignment2, false));
        assertEquals(2, course.getAssignmentList().size());
        assertEquals(assignment1, course.getAssignmentList().get(0));
        assertEquals(assignment2, course.getAssignmentList().get(1));
    }

    @Test
    public void removeAssignmentEmptyListTest() {
        assertEquals(0, course.getAssignmentList().size());
        assertFalse(course.removeAssignment("Test Assignment"));
        assertEquals(0, course.getAssignmentList().size());
    }

    @Test
    public void removeAssignmentAlreadyInListTest() {
        Assignment assignment1 = new Assignment("Test Assignment", -1);
        course.addAssignment(assignment1, false);
        assertTrue(course.removeAssignment("Test Assignment"));
        assertEquals(0, course.getAssignmentList().size());
    }

    @Test
    public void removeAssignmentNotAlreadyInListTest() {
        Assignment assignment1 = new Assignment("Test Assignment", -1);
        course.addAssignment(assignment1, false);
        assertFalse(course.removeAssignment("Test Assignment2"));
        assertEquals(1, course.getAssignmentList().size());
        assertEquals(assignment1, course.getAssignmentList().get(0));
    }

    @Test
    public void removeSameAssignmentTwiceTest() {
        Assignment assignment1 = new Assignment("Test Assignment", -1);
        course.addAssignment(assignment1, false);
        assertTrue(course.removeAssignment("Test Assignment"));
        assertEquals(0, course.getAssignmentList().size());
        assertFalse(course.removeAssignment("Test Assignment"));
        assertEquals(0, course.getAssignmentList().size());
    }

    @Test
    public void calculateAverageOneAssignmentNoGradeTest() {
        Assignment assignment1 = new Assignment("Test Assignment", -1);
        course.addAssignment(assignment1, false);
        assertEquals(-1, course.calculateAverage());
    }

    @Test
    public void calculateAverageOneAssignmentWithGradeTest() {
        Assignment assignment1 = new Assignment("Test Assignment", 90);
        course.addAssignment(assignment1, false);
        assertEquals(90, course.calculateAverage());
    }

    @Test
    public void calculateAverageTwoAssignmentsWithGradesTest() {
        Assignment assignment1 = new Assignment("Test Assignment", 90);
        Assignment assignment2 = new Assignment("Test Assignment2", 100);
        course.addAssignment(assignment1, false);
        course.addAssignment(assignment2, false);
        assertEquals(95, course.calculateAverage());
    }

    @Test
    public void calculateAverageTwoAssignmentsOneWithOneWithoutGradeTest() {
        Assignment assignment1 = new Assignment("Test Assignment", 90);
        Assignment assignment2 = new Assignment("Test Assignment2", -1);
        course.addAssignment(assignment1, false);
        course.addAssignment(assignment2, false);
        assertEquals(90, course.calculateAverage());
    }
}
