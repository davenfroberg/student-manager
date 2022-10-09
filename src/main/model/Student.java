package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

public class Student implements Writable {

    public final int pricePerCredit = 100;
    private String name;
    private int studentID;
    private int tuition;
    private int tuitionPaid;
    private ArrayList<Course> courseList;

    public Student(String name, int studentID) {
        this.name = name;
        this.studentID = studentID;
        this.courseList = new ArrayList<>();
        this.tuition = 0;
        this.tuitionPaid = 0;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: increase tuition paid by amount
    public void payTuition(int amount) {
        tuitionPaid += amount;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    //EFFECTS: increase tuition by amount
    public void addTuition(int amount) {
        tuition += amount;
    }

    // REQUIRES amount > 0
    // MODIFIES: this
    // EFFECTS: decrease the tuition by amount
    public void removeTuition(int amount) {
        tuition -= amount;
    }

    // MODIFIES: this
    // EFFECTS: add the provided course to the course list if a course with the same name is not already in list, add
    //          tuition and return true, otherwise do nothing
    public boolean addCourse(Course course, boolean fromJson) {
        ArrayList<String> courseNames = new ArrayList<>();

        for (Course courseInList : courseList) {
            courseNames.add(courseInList.getName());
        }

        if (!courseNames.contains(course.getName())) {
            courseList.add(course);
            addTuition(course.getCredits() * pricePerCredit);
            if (!fromJson) {
                EventLog.getInstance().logEvent(new Event("Course added to the student's schedule"));
            }
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: remove the course from the course list if a course with the same name is already in list, reduce
    //          tuition, and return true otherwise return false
    public boolean removeCourse(String courseName) {
        for (int i = 0; i < courseList.size(); i++) {
            if (courseList.get(i).getName().equals(courseName)) {
                removeTuition(courseList.get(i).getCredits() * pricePerCredit);
                courseList.remove(i);
                EventLog.getInstance().logEvent(new Event("Course removed from the student's schedule"));
                return true;
            }
        }
        return false;
    }

    // EFFECTS: return the course object with the given name
    public Course getCourse(String courseName) {
        for (Course course : courseList) {
            if (course.getName() == courseName) {
                return course;
            }
        }
        return null;
    }

    // REQUIRES: must have at least one course with a grade
    // EFFECTS: return the credit weighted average of all the courses in a student's course list
    public double getAverage() {
        int totalCredits = 0;
        double totalPercent = 0;

        for (Course course : courseList) {
            if (course.calculateAverage() == -1) {
                continue;
            }
            totalCredits += course.getCredits();
            totalPercent += course.getCredits() * course.calculateAverage();
        }

        if (totalCredits == 0) {
            return -1.0;
        }

        return (double)Math.round((totalPercent / totalCredits) * 100d) / 100d;
    }

    // EFFECTS: return the current number of credits the student is enrolled in
    public int getCreditsEnrolled() {
        int credits = 0;
        for (Course course : courseList) {
            credits += course.getCredits();
        }
        return credits;
    }

    public ArrayList<Course> getCourseList() {
        return this.courseList;
    }

    public String getName() {
        return this.name;
    }

    public int getStudentID() {
        return this.studentID;
    }

    public int getTuition() {
        return this.tuition;
    }

    public int getTuitionPaid() {
        return this.tuitionPaid;
    }

    // EFFECTS: return the tuition that has yet to be paid
    public int getTuitionDue() {
        return this.tuition - this.tuitionPaid;
    }

    // EFFECTS: returns all information about the student as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("studentID", studentID);
        json.put("tuition", tuition);
        json.put("tuitionPaid", tuitionPaid);
        json.put("courses", coursesToJson());
        return json;
    }

    // EFFECTS: returns this student's courses as a JSON array
    private JSONArray coursesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Course c : courseList) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
