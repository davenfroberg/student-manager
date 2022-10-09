package persistence;

import model.Course;
import model.Student;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Student s = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNoCourses() {
        JsonReader reader = new JsonReader("./data/testReaderNoCourses.json");
        try {
            Student s = reader.read();
            assertEquals("Test Student", s.getName());
            assertEquals(1, s.getStudentID());
            assertEquals(0, s.getCreditsEnrolled());
            assertEquals(0, s.getTuition());
            assertEquals(0, s.getTuitionPaid());
            assertEquals(0, s.getCourseList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralStudent() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralStudent.json");
        try {
            Student s = reader.read();
            assertEquals("Test Student", s.getName());
            assertEquals(1, s.getStudentID());
            assertEquals(11, s.getCreditsEnrolled());
            assertEquals(1100, s.getTuition());
            assertEquals(200, s.getTuitionPaid());

            ArrayList<Course> courses = s.getCourseList();
            assertEquals("CPSC 210", courses.get(0).getName());
            assertEquals(4, courses.get(0).getCredits());
            assertEquals(100, courses.get(0).calculateAverage());
            assertEquals(1, courses.get(0).getAssignmentList().size());

            assertEquals("ENGL 110", courses.get(1).getName());
            assertEquals(3, courses.get(1).getCredits());
            assertEquals(80, courses.get(1).calculateAverage());
            assertEquals(1, courses.get(1).getAssignmentList().size());

            assertEquals("BIOL 111", courses.get(2).getName());
            assertEquals(4, courses.get(2).getCredits());
            assertEquals(90, courses.get(2).calculateAverage());
            assertEquals(1, courses.get(2).getAssignmentList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
