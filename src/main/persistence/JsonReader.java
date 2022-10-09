// All methods use portions of code which came from the Workshop example
// "JsonSerializationDemo" by Paul Carter, 2020
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

package persistence;

import model.Assignment;
import model.Course;
import model.Student;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads student from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Student read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStudent(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses student from JSON object and returns it
    private Student parseStudent(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int studentID = jsonObject.getInt("studentID");
        int tuitionPaid = jsonObject.getInt("tuitionPaid");
        Student s = new Student(name, studentID);
        addCourses(s, jsonObject);
        s.payTuition(tuitionPaid);
        return s;
    }

    // MODIFIES: student
    // EFFECTS: parses courses from JSON object and adds them to student
    private void addCourses(Student s, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("courses");
        for (Object json : jsonArray) {
            JSONObject nextCourse = (JSONObject) json;
            addCourse(s, nextCourse);
        }
    }

    // MODIFIES: student
    // EFFECTS: parses course from JSON object and adds it to student
    private void addCourse(Student s, JSONObject jsonObject) {
        String name = jsonObject.getString("courseName");
        int credits = jsonObject.getInt("credits");
        Course course = new Course(name, credits);
        addAssignments(course, jsonObject);
        s.addCourse(course, true);

    }

    // MODIFIES: student
    // EFFECTS: parses assignments from JSON object and adds them to student
    private void addAssignments(Course c, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("assignments");
        for (Object json : jsonArray) {
            JSONObject nextAssignment = (JSONObject) json;
            addAssignment(c, nextAssignment);
        }
    }

    // MODIFIES: student
    // EFFECTS: parses assignment from JSON object and adds it to student
    private void addAssignment(Course c, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        double grade = jsonObject.getInt("grade");
        Assignment assignment = new Assignment(title, grade);
        c.addAssignment(assignment, true);
    }

}
