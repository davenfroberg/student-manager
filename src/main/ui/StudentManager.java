// Save method of this code came from the Workshop example
// "JsonSerializationDemo" by Paul Carter, 2020
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

package ui;

import model.Student;
import persistence.*;

import java.io.FileNotFoundException;

public class StudentManager {

    private Student student;
    private static final String SAVE_FILE = "./data/student.json";
    private JsonReader reader;
    private JsonWriter writer;

    // EFFECTS: runs the student manager application
    public StudentManager() {
        reader = new JsonReader(SAVE_FILE);
        writer = new JsonWriter(SAVE_FILE);
    }

    // MODIFIES: this
    // EFFECTS: loads the student from the save file
    public void loadStudent() throws Exception {
        student = reader.read();
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    // (Carter 2020)
    // EFFECTS: saves the current student
    public void save() {
        try {
            writer.open();
            writer.write(student);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + SAVE_FILE);
        }
    }
}
