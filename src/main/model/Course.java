package model;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

public class Course implements Writable {

    private String courseName;
    private int credits;
    private ArrayList<Assignment> assignmentList;

    public Course(String name, int credits) {
        this.courseName = name;
        this.credits = credits;
        this.assignmentList = new ArrayList<Assignment>();
    }

    // REQUIRES: must not exist an assignment with the same name in the assignment list
    // MODIFIES: this
    // EFFECTS: adds an assignment to the course's assignment list
    public boolean addAssignment(Assignment assignment, boolean fromJson) {
        ArrayList<String> assignmentTitles = new ArrayList<String>();

        for (Assignment assignmentInList : assignmentList) {
            assignmentTitles.add(assignmentInList.getTitle());
        }

        if (!assignmentTitles.contains(assignment.getTitle())) {
            assignmentList.add(assignment);
            if (!fromJson) {
                EventLog.getInstance().logEvent(new Event("Assignment added to " + getName()));
            }
            return true;
        }

        return false;
    }

    // MODIFIES: this
    // EFFECTS: removes an assignment with assignmentTitle from the course's assignment list if it exists and return
    //          true, else do nothing and return false
    public boolean removeAssignment(String assignmentTitle) {
        for (int i = 0; i < assignmentList.size(); i++) {
            if (assignmentList.get(i).getTitle().equals(assignmentTitle)) {
                assignmentList.remove(i);
                EventLog.getInstance().logEvent(new Event("Removed assignment from " + getName()));
                return true;
            }
        }
        return false;

    }

    // REQUIRES: must have at least one assignment grade
    // EFFECTS: return the average of all the assignments currently graded in this course,
    //          ignoring assignments without a grade
    public double calculateAverage() {
        int totalAssignments = 0;
        double totalPercent = 0;

        for (Assignment assignment : assignmentList) {
            if (assignment.getGrade() == -1) {
                continue;
            }
            totalAssignments++;
            totalPercent += assignment.getGrade();
        }

        if (totalAssignments == 0) {
            return -1.0;
        }

        return (double)Math.round((totalPercent / totalAssignments) * 100d) / 100d;
    }

    public int getCredits() {
        return credits;
    }

    public String getName() {
        return courseName;
    }

    public ArrayList<Assignment> getAssignmentList() {
        return assignmentList;
    }

    // EFFECTS: returns all information about the course as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("courseName", courseName);
        json.put("credits", credits);
        json.put("assignments", assignmentsToJson());
        return json;
    }

    // EFFECTS: returns this course's assignments as a JSON array
    private JSONArray assignmentsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Assignment a : assignmentList) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }
}
