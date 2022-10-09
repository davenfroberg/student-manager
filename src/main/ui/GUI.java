package ui;

import model.EventLog;
import model.Student;

import ui.pages.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.EventListener;

public class GUI extends JFrame implements EventListener {

    private JPanel newUserPage;
    private JPanel mainPage;
    private JPanel existingUserPage;
    private JPanel classesPage;
    private JPanel tuitionPage;
    private JPanel assignmentsPage;

    private CardLayout cards = new CardLayout();
    private StudentManager studentManager = new StudentManager();
    private boolean newUser = false;

    // MODIFIES: this
    // EFFECTS: creates a gui with a size, location, title, minimum size, default close operation, and a card layout;
    //          tries to load the student, initializes the pages, adds them to the gui, and goes the proper new or
    //          existing user page
    public GUI() {
        init();
        try {
            studentManager.loadStudent();
        } catch (Exception e) {
            newUser = true;
        } finally {
            initPages(); //TODO: trying to init a page with no data, throws NULLPOINTEREXCEPTION
        }

        if (newUser) {
            toNewUserPage();
        } else {
            toExistingUserPage();
        }

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets the default properties of this frame
    private void init() {
        setSize(990, 700);
        setLocation(200, 80);
        setTitle("UBC Student Manager v3.0");
        setMinimumSize(new Dimension(990, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                displayEvents();
            }
        });

        setLayout(cards);
    }

    private void displayEvents() {
        for (model.Event event : EventLog.getInstance()) {
            System.out.println(event.getDescription());
        }
    }

    // MODIFIES: this
    // EFFECTS: instantiate and add the page objects
    private void initPages() {
        mainPage = new MainPage(this);
        newUserPage = new NewUserPage(this);
        existingUserPage = new ExistingUserPage(this);
        classesPage = new ClassesPage(this);
        tuitionPage = new TuitionPage(this);
        assignmentsPage = new AssignmentsPage(this);
        addPages();
    }

    // MODIFIES: this
    // EFFECTS: adds the pages to the gui
    private void addPages() {
        add(newUserPage, "NEW USER PAGE");
        add(mainPage, "MAIN PAGE");
        add(existingUserPage, "EXISTING USER PAGE");
        add(classesPage, "CLASSES PAGE");
        add(tuitionPage, "TUITION PAGE");
        add(assignmentsPage, "ASSIGNMENTS PAGE");

    }

    // MODIFIES: student
    // EFFECTS: sets the student to a new student with a name and ID
    public void createStudent(String name, int id) {
        studentManager.setStudent(new Student(name, id));
    }

    // EFFECTS: returns the student object
    public Student getStudent() {
        return (studentManager.getStudent());
    }

    // MODIFIES: this
    // EFFECTS: changes the gui to the main page
    public void toMainPage() {
        remove(mainPage);
        mainPage = new MainPage(this);
        add(mainPage, "MAIN PAGE");
        cards.show(this.getContentPane(), "MAIN PAGE");
    }

    // EFFECTS: changes the gui to the existing user page
    public void toExistingUserPage() {
        cards.show(this.getContentPane(), "EXISTING USER PAGE");
    }

    // EFFECTS: changes the gui to the new user page
    public void toNewUserPage() {
        cards.show(this.getContentPane(), "NEW USER PAGE");
    }

    // MODIFIES: this
    // EFFECTS: changes the gui to the course manager page
    public void toCoursesPage() {
        remove(classesPage);
        classesPage = new ClassesPage(this);
        add(classesPage, "CLASSES PAGE");
        cards.show(this.getContentPane(), "CLASSES PAGE");
    }

    // MODIFIES: this
    // EFFECTS: changes the gui to the tuition manager page
    public void toTuitionPage() {
        remove(tuitionPage);
        tuitionPage = new TuitionPage(this);
        add(tuitionPage, "TUITION PAGE");
        cards.show(this.getContentPane(), "TUITION PAGE");
    }

    // MODIFIES: this
    // EFFECTS: changes the gui to the assignment manager page
    public void toAssignmentsPage() {
        remove(assignmentsPage);
        assignmentsPage = new AssignmentsPage(this);
        add(assignmentsPage, "ASSIGNMENTS PAGE");
        cards.show(this.getContentPane(), "ASSIGNMENTS PAGE");
    }

    // EFFECTS: saves the student
    public void save() {
        studentManager.save();
    }

}
