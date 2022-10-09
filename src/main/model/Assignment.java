package model;

import org.json.JSONObject;
import persistence.Writable;

public class Assignment implements Writable {

    private String title;
    private double grade;

    public Assignment(String title, double grade) {
        this.title = title;
        this.grade = grade;
    }

    public Assignment(String title, Course course) {
        this.title = title;
        this.grade = -1;
    }

    public String getTitle() {
        return title;
    }

    public double getGrade() {
        return grade;
    }



    // EFFECTS: returns all information about the assignment as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("grade", grade);
        return json;
    }
}
