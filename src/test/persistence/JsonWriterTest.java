package persistence;

import model.Assignment;
import model.Course;
import model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {

    Student s;

    @BeforeEach
    void setup(){
        s = new Student("Test Student2", 2);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterNoCourses() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterNoCourses.json");
            writer.open();
            writer.write(s);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNoCourses.json");
            s = reader.read();
            assertEquals("Test Student2", s.getName());
            assertEquals(2, s.getStudentID());
            assertEquals(0, s.getTuition());
            assertEquals(0, s.getTuitionPaid());
            assertEquals(0, s.getCourseList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralStudent() {
        try {
            Course course1 = new Course("CPSC 110", 4);
            Course course2 = new Course("CHEM 121", 4);
            Course course3 = new Course("ENGL 111", 3);
            course1.addAssignment(new Assignment("Test Assignment",90), true);
            course2.addAssignment(new Assignment("Test Assignment2", 100), true);
            course3.addAssignment(new Assignment("Test Assignment3", 80), true);
            s.addCourse(course1, true);
            s.addCourse(course2, true);
            s.addCourse(course3, true);
            s.payTuition(100);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralStudent.json");
            writer.open();
            writer.write(s);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralStudent.json");
            s = reader.read();

            assertEquals("Test Student2", s.getName());
            assertEquals(2, s.getStudentID());
            assertEquals(11, s.getCreditsEnrolled());
            assertEquals(1100, s.getTuition());
            assertEquals(100, s.getTuitionPaid());

            ArrayList<Course> courses = s.getCourseList();
            assertEquals("CPSC 110", courses.get(0).getName());
            assertEquals(4, courses.get(0).getCredits());
            assertEquals(90, courses.get(0).calculateAverage());
            assertEquals(1, courses.get(0).getAssignmentList().size());

            assertEquals("CHEM 121", courses.get(1).getName());
            assertEquals(4, courses.get(1).getCredits());
            assertEquals(100, courses.get(1).calculateAverage());
            assertEquals(1, courses.get(1).getAssignmentList().size());

            assertEquals("ENGL 111", courses.get(2).getName());
            assertEquals(3, courses.get(2).getCredits());
            assertEquals(80, courses.get(2).calculateAverage());
            assertEquals(1, courses.get(2).getAssignmentList().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
