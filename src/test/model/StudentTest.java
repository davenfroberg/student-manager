package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    Student student;

    @BeforeEach
    public void setup() {
        student = new Student("Daven", 123456);
    }

    @Test
    public void constructorTest() {
        assertEquals("Daven", student.getName());
        assertEquals(123456, student.getStudentID());
        assertEquals(0, student.getTuitionPaid());
        assertEquals(0, student.getTuition());
        assertEquals(0, student.getTuitionDue());
    }

    @Test
    public void payTuitionTest() {
        assertEquals(0, student.getTuitionPaid());
        student.payTuition(500);
        assertEquals(500, student.getTuitionPaid());

    }

    @Test
    public void addTuitionTest() {
        assertEquals(0, student.getTuition());
        student.addTuition(500);
        assertEquals(500, student.getTuition());
    }

    @Test
    public void removeTuitionTest() {
        assertEquals(0, student.getTuition());
        student.addTuition(500);
        student.removeTuition(250);
        assertEquals(250, student.getTuition());
    }

    @Test
    public void getTuitionDueNoPaymentTest() {
        assertEquals(0, student.getTuitionDue());
        student.addTuition(500);
        assertEquals(500, student.getTuitionDue());
    }

    @Test
    public void getTuitionDuePartialPaymentTest() {
        assertEquals(0, student.getTuitionDue());
        student.addTuition(500);
        assertEquals(500, student.getTuitionDue());
        student.payTuition(200);
        assertEquals(300, student.getTuitionDue());
    }

    @Test
    public void getTuitionDueOverpaymentTest() {
        assertEquals(0, student.getTuitionDue());
        student.addTuition(500);
        assertEquals(500, student.getTuitionDue());
        student.payTuition(1000);
        assertEquals(-500, student.getTuitionDue());
    }

    @Test
    public void addCourseNotAlreadyInListTest() {
        assertEquals(0, student.getCourseList().size());
        assertTrue(student.addCourse(new Course("CPSC 210", 4), false));
        assertEquals(1, student.getCourseList().size());
        assertEquals(400, student.getTuition());
    }

    @Test
    public void addCourseAlreadyInListTest() {
        Course course1 = new Course("CPSC 210", 4);
        assertEquals(0, student.getCourseList().size());
        assertTrue(student.addCourse(course1, false));
        assertEquals(1, student.getCourseList().size());
        assertEquals(400, student.getTuition());
        assertFalse(student.addCourse(course1, false));
        assertEquals(1, student.getCourseList().size());
        assertEquals(400, student.getTuition());
    }

    @Test
    public void addCourseAlreadyInListSameNameDiffCreditsTest() {
        Course course1 = new Course("CPSC 210", 4);
        Course course2 = new Course("CPSC 210", 3);
        assertEquals(0, student.getCourseList().size());
        assertTrue(student.addCourse(course1, false));
        assertEquals(400, student.getTuition());
        assertEquals(1, student.getCourseList().size());
        assertFalse(student.addCourse(course2, false));
        assertEquals(400, student.getTuition());
        assertEquals(1, student.getCourseList().size());
        assertEquals(course1, student.getCourseList().get(0));
    }

    @Test
    public void addTwoCoursesNotAlreadyInListTest() {
        Course course1 = new Course("CPSC 210", 4);
        Course course2 = new Course("CPSC 110", 4);
        assertEquals(0, student.getCourseList().size());
        assertTrue(student.addCourse(course1, false));
        assertEquals(400, student.getTuition());
        assertEquals(1, student.getCourseList().size());
        assertTrue(student.addCourse(course2, false));
        assertEquals(800, student.getTuition());
        assertEquals(2, student.getCourseList().size());
        assertEquals(course1, student.getCourseList().get(0));
        assertEquals(course2, student.getCourseList().get(1));
    }

    @Test
    public void removeCourseAlreadyInListTest() {
        Course course1 = new Course("CPSC 210", 4);
        assertEquals(0, student.getCourseList().size());
        assertTrue(student.addCourse(course1, false));
        assertEquals(400, student.getTuition());
        assertEquals(1, student.getCourseList().size());
        assertEquals(course1, student.getCourseList().get(0));
        assertTrue(student.removeCourse("CPSC 210"));
        assertEquals(0, student.getTuition());
        assertEquals(0, student.getCourseList().size());
    }

    @Test
    public void removeCourseNotAlreadyInListTest() {
        Course course1 = new Course("CPSC 210", 4);
        Course course2 = new Course("CPSC 110", 4);
        assertEquals(0, student.getCourseList().size());
        assertTrue(student.addCourse(course1, false));
        assertEquals(400, student.getTuition());
        assertEquals(1, student.getCourseList().size());
        assertEquals(course1, student.getCourseList().get(0));
        assertFalse(student.removeCourse("CPSC 110"));
        assertEquals(400, student.getTuition());
        assertEquals(1, student.getCourseList().size());
        assertEquals(course1, student.getCourseList().get(0));
    }

    @Test
    public void removeSameCourseTwiceTest() {
        Course course1 = new Course("CPSC 210", 4);
        Course course2 = new Course("CPSC 110", 4);
        assertTrue(student.addCourse(course1, false));
        assertTrue(student.addCourse(course2, false));
        assertEquals(800, student.getTuition());
        assertTrue(student.removeCourse("CPSC 210"));
        assertEquals(400, student.getTuition());
        assertEquals(1, student.getCourseList().size());
        assertEquals(course2, student.getCourseList().get(0));
        assertFalse(student.removeCourse("CPSC 210"));
        assertEquals(400, student.getTuition());
        assertEquals(1, student.getCourseList().size());
        assertEquals(course2, student.getCourseList().get(0));
    }

    @Test
    public void getAverageNoGradesTest() {
        Course course1 = new Course("CPSC 210", 4);
        student.addCourse(course1, false);
        assertEquals(-1, student.getAverage());
    }

    @Test
    public void getAverageOneCourseTest() {
        Course course1 = new Course("CPSC 210", 4);
        course1.addAssignment(new Assignment("Test", 80.00), false);
        student.addCourse(course1, false);
        assertEquals(80.00, student.getAverage());
    }

    @Test
    public void getAverageTwoCourseSameCreditsTest() {
        Course course1 = new Course("CPSC 210", 4);
        Course course2 = new Course("CPSC 110", 4);
        course1.addAssignment(new Assignment("Test", 90), false);
        course2.addAssignment(new Assignment("Test", 80), false);
        student.addCourse(course1, false);
        student.addCourse(course2, false);
        assertEquals(85.00, student.getAverage());
    }

    @Test
    public void getAverageTwoCourseDifferentCreditsTest() {
        Course course1 = new Course("CPSC 210", 4);
        Course course2 = new Course("ENGL 110", 3);
        course1.addAssignment(new Assignment("Test", 80.00), false);
        course2.addAssignment(new Assignment("Test", 90.00), false);
        student.addCourse(course1, false);
        student.addCourse(course2, false);
        assertEquals(84.29, student.getAverage());
    }

    @Test
    public void getCreditsNoCoursesTest() {
        assertEquals(0, student.getCreditsEnrolled());
    }

    @Test
    public void getCreditsOneCourseTest() {
        Course course1 = new Course("CPSC 210", 4);
        student.addCourse(course1, false);
        assertEquals(4, student.getCreditsEnrolled());
    }

    @Test
    public void getCreditsTwoCoursesTest() {
        Course course1 = new Course("CPSC 210", 4);
        Course course2 = new Course("ENGL 110", 3);
        student.addCourse(course1, false);
        student.addCourse(course2, false);
        assertEquals(7, student.getCreditsEnrolled());
    }

    @Test
    public void getCourseInListTest() {
        Course course1 = new Course("CPSC 210", 4);
        Course course2 = new Course("ENGL 110", 3);
        student.addCourse(course1, false);
        student.addCourse(course2, false);
        assertEquals(course1, student.getCourse("CPSC 210"));
    }

    @Test
    public void getCourseNotInListTest() {
        Course course1 = new Course("CPSC 210", 4);
        Course course2 = new Course("ENGL 110", 3);
        student.addCourse(course1, false);
        student.addCourse(course2, false);
        assertNull(student.getCourse("CPSC 110"));
    }
}
