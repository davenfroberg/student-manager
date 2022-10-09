package ui.pages;

import model.Assignment;
import model.Course;
import ui.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AssignmentsPage extends JPanel {

    private GUI gui;
    private JPanel headerPanel;
    private JPanel menuPanel;
    private JPanel centerPanel;
    private JButton addButton;
    private JButton removeButton;
    private JButton cancelButton;
    private JTextField assignmentNameText;
    private JTextField assignmentGradeText;
    private JPanel allAssignmentBoxes;
    private JPanel classRadioButtons;

    private Font headerFont = new Font("Helvetica", Font.BOLD, 25);

    // MODIFIES: this
    // EFFECTS: creates an assignment page with a parent and a border layout
    public AssignmentsPage(GUI parent) {
        this.gui = parent;
        setLayout(new BorderLayout());
        addHeader();
        addMenu();
        addCenter();
    }

    // MODIFIES: this
    // EFFECTS: adds a header with a back button to the page
    private void addHeader() {
        headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        add(headerPanel, "North");
        JButton backButton = new JButton("Main Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.toMainPage();
            }
        });
        headerPanel.add(backButton);
    }

    // MODIFIES: this
    // EFFECTS: instantiates the menu panel, adds buttons to it and adds it to the page
    private void addMenu() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        addMenuOptions();
        add(menuPanel, "East");
    }

    // MODIFIES: this
    // EFFECTS: instantiates all the menu buttons and adds them to the menu panel; the cancel button is not visible by
    //          default
    private void addMenuOptions() {
        addButton = new JButton("Add Assignment");
        removeButton = new JButton("Remove Assignment");
        cancelButton = new JButton("Cancel");
        cancelButton.setVisible(false);
        addMenuListeners();
        menuPanel.add(addButton);
        menuPanel.add(removeButton);
        menuPanel.add(cancelButton);

    }

    // MODIFIES: this
    // EFFECTS: adds action listeners to the menu buttons
    private void addMenuListeners() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toAddAssignment();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toRemoveAssignment();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toAssignmentList();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: instantiates the center panel, adds it to the page, and changes the center panel to the assignment list
    private void addCenter() {
        centerPanel = new JPanel();
        toAssignmentList();
        add(centerPanel, "Center");
    }

    // EFFECTS: returns the list of all assignments with their respective classes as a panel
    private JPanel assignmentList() {
        JPanel assignmentsPanel = new JPanel();
        assignmentsPanel.setLayout(new BorderLayout());

        JPanel assignmentsList = new JPanel();
        assignmentsList.setLayout(new BoxLayout(assignmentsList, BoxLayout.Y_AXIS));

        JLabel headerLabel = new JLabel("Assignments in Registered Courses");
        headerLabel.setFont(headerFont);
        assignmentsPanel.add(headerLabel, "North");
        for (Course course : gui.getStudent().getCourseList()) {
            assignmentsList.add(courseWithAssignments(course));
        }

        JScrollPane assignmentsScroll = new JScrollPane(assignmentsList);
        assignmentsPanel.add(assignmentsScroll, "Center");

        return assignmentsPanel;
    }

    // EFFECTS: returns the name of the provided course and its assignments as a panel
    private JPanel courseWithAssignments(Course course) {
        JPanel courseWithAssignments = new JPanel();
        courseWithAssignments.setLayout(new BoxLayout(courseWithAssignments, BoxLayout.Y_AXIS));
        JLabel courseName;
        if (course.calculateAverage() != -1) {
            courseName = new JLabel(course.getName() + " : " + course.calculateAverage() + "%");
        } else {
            courseName = new JLabel(course.getName() + " : N/A");
        }
        courseName.setFont(new Font("Helvetica", Font.BOLD, 17));
        courseWithAssignments.add(courseName);
        for (Assignment assignment : course.getAssignmentList()) {
            JLabel assignmentName;
            if (assignment.getGrade() != -1) {
                assignmentName = new JLabel("    - " + assignment.getTitle() + ": "
                        + assignment.getGrade() + "%");
            } else {
                assignmentName = new JLabel("    - " + assignment.getTitle() + ": N/A");
            }
            assignmentName.setFont(new Font("Helvetica", Font.PLAIN, 14));
            assignmentName.setForeground(new Color(40, 40, 40));
            courseWithAssignments.add(assignmentName);
        }

        return courseWithAssignments;
    }

    // EFFECTS: returns the add assignment menu as a panel
    private JPanel addAssignment() {
        JPanel addAssignmentPanel = new JPanel();
        addAssignmentPanel.setLayout(new BorderLayout());

        addAssignmentPanel.add(addAssignmentHeader(), "North");
        JPanel addAssignment = new JPanel();
        addAssignment.setLayout(new BoxLayout(addAssignment, BoxLayout.Y_AXIS));
        addAssignment.add(assignmentNameTextEntry());
        addAssignment.add(assignmentGradeTextEntry());
        JLabel classText = new JLabel("Which class would you like to add this assignment to?");
        addAssignment.add(classText);
        addAssignment.add(addToClassButtons());
        addAssignment.add(submitAddAssignment());
        addAssignmentPanel.add(addAssignment, "Center");

        return addAssignmentPanel;
    }

    // EFFECTS: returns the header for the add assignment menu as a panel
    private JPanel addAssignmentHeader() {
        JPanel addHeader = new JPanel();
        addHeader.setLayout(new BoxLayout(addHeader, BoxLayout.Y_AXIS));

        JLabel headerLabel = new JLabel("Add Assignment");
        JLabel headerText = new JLabel("Please type in the assignment name and grade "
                + "and select the course to add it to");
        headerLabel.setFont(headerFont);
        headerText.setFont(new Font("Helvetica", Font.PLAIN, 18));
        addHeader.add(headerLabel);
        addHeader.add(headerText);
        return addHeader;
    }

    // MODIFIES: this
    // EFFECTS: adds a title to the text field for the name of the assignment and returns both as a panel
    private JPanel assignmentNameTextEntry() {
        JPanel textEntry = new JPanel();
        textEntry.setLayout(new BoxLayout(textEntry, BoxLayout.X_AXIS));
        JLabel nameEntry = new JLabel("What is the name of the assignment?");
        textEntry.add(nameEntry);
        assignmentNameText = new JTextField();
        textEntry.add(assignmentNameText);
        return textEntry;
    }

    // MODIFIES: this
    // EFFECTS: adds a title to the text field for the grade of the assignment and returns both as a panel
    private JPanel assignmentGradeTextEntry() {
        JPanel textEntry = new JPanel();
        textEntry.setLayout(new BoxLayout(textEntry, BoxLayout.X_AXIS));
        JLabel gradeEntry = new JLabel("What is the grade of the assignment? (-1 if no grade)");
        textEntry.add(gradeEntry);
        assignmentGradeText = new JTextField();
        textEntry.add(assignmentGradeText);
        return textEntry;
    }

    // MODIFIES: this
    // EFFECTS: returns the list of classes that you can add an assignment to as panel of radio buttons
    private JPanel addToClassButtons() {
        classRadioButtons = new JPanel();
        classRadioButtons.setLayout(new BoxLayout(classRadioButtons, BoxLayout.Y_AXIS));

        ButtonGroup group = new ButtonGroup();
        for (Course course : gui.getStudent().getCourseList()) {
            JRadioButton button = new JRadioButton(course.getName());
            group.add(button);
            classRadioButtons.add(button);
        }
        return classRadioButtons;
    }

    // EFFECTS: returns the button which adds the assignment with its given name and grade as a panel and adds an action
    //          listener to the button
    private JPanel submitAddAssignment() {
        JPanel buttonPanel = new JPanel();
        JButton submit = new JButton("Add!");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewAssignment();
                toAssignmentList();
            }
        });
        buttonPanel.add(submit);
        return buttonPanel;
    }


    // MODIFIES: gui.getStudent();
    // EFFECTS: creates a new assignment in the provided class that the student is enrolled in
    private void createNewAssignment() {
        String name = assignmentNameText.getText();
        double grade = Double.parseDouble(assignmentGradeText.getText());
        String className = getSelectedClass();
        gui.getStudent().getCourse(className).addAssignment(new Assignment(name, grade), false);
    }

    // REQUIRES: selected class must be in the student's class list
    // EFFECTS: returns the name of the class that is selected by a radio button
    private String getSelectedClass() {
        for (int i = 0; i < gui.getStudent().getCourseList().size(); i++) {
            JRadioButton currentButton = (JRadioButton) classRadioButtons.getComponent(i);
            if (currentButton.isSelected()) {
                return currentButton.getText();
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: returns the remove assignment menu as a panel
    private JPanel removeAssignment() {
        JPanel removeAssignmentPanel = new JPanel();
        removeAssignmentPanel.setLayout(new BorderLayout());
        removeAssignmentPanel.add(removeAssignmentHeader(), "North");
        JPanel removeCenterPanel = new JPanel();
        removeCenterPanel.setLayout(new BoxLayout(removeCenterPanel, BoxLayout.Y_AXIS));
        removeAssignmentPanel.add(removeCenterPanel, "Center");
        allAssignmentBoxes = new JPanel();
        allAssignmentBoxes.setLayout(new BoxLayout(allAssignmentBoxes, BoxLayout.Y_AXIS));
        for (Course course : gui.getStudent().getCourseList()) {
            allAssignmentBoxes.add(titleAndAssignmentBoxes(course));
        }
        JScrollPane boxesScroll = new JScrollPane(allAssignmentBoxes);
        removeCenterPanel.add(boxesScroll);
        JButton removeButton = new JButton("Remove!");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelectedAssignments();
                toAssignmentList();
            }
        });
        removeCenterPanel.add(removeButton);
        return removeAssignmentPanel;
    }

    // EFFECTS: returns the name of the course and its assignments as check boxes as a panel
    private JPanel titleAndAssignmentBoxes(Course course) {
        JPanel titleAndBoxes = new JPanel();
        titleAndBoxes.setLayout(new BoxLayout(titleAndBoxes, BoxLayout.Y_AXIS));
        JLabel courseName = new JLabel(course.getName());
        courseName.setFont(new Font("Helvetica", Font.BOLD, 17));
        titleAndBoxes.add(courseName);
        JPanel assignmentBoxes = new JPanel();
        assignmentBoxes.setLayout(new BoxLayout(assignmentBoxes, BoxLayout.Y_AXIS));
        for (Assignment assignment : course.getAssignmentList()) {
            assignmentBoxes.add(new JCheckBox(assignment.getTitle()));
        }
        titleAndBoxes.add(assignmentBoxes);
        return titleAndBoxes;
    }

    // MODIFIES: course
    // EFFECTS: removes the selected assignments from its respective course
    private void removeSelectedAssignments() {
        for (int i = 0; i < gui.getStudent().getCourseList().size(); i++) {
            JPanel currentBoxesPanel = (JPanel) allAssignmentBoxes.getComponent(i);
            JLabel courseLabel = (JLabel) currentBoxesPanel.getComponent(0);
            JPanel justBoxesPanel = (JPanel) currentBoxesPanel.getComponent(1);
            String courseName = courseLabel.getText();
            Course course = gui.getStudent().getCourse(courseName);
            int numAssignments = course.getAssignmentList().size();
            for (int k = 0; k < numAssignments; k++) {
                JCheckBox currentBox = (JCheckBox) justBoxesPanel.getComponent(k);
                if (currentBox.isSelected()) {
                    course.removeAssignment(currentBox.getText());
                }
            }
        }
    }

    // EFFECTS: returns the header for the remove assignment menu as a panel
    private JPanel removeAssignmentHeader() {
        JPanel removeHeader = new JPanel();
        removeHeader.setLayout(new BoxLayout(removeHeader, BoxLayout.Y_AXIS));

        JLabel headerLabel = new JLabel("Remove Assignment");
        JLabel headerText = new JLabel("Please select the assignment you would like to remove");
        headerLabel.setFont(headerFont);
        headerText.setFont(new Font("Helvetica", Font.PLAIN, 18));
        removeHeader.add(headerLabel);
        removeHeader.add(headerText);
        return removeHeader;
    }

    // MODIFIES: this
    // EFFECTS: changes the center panel to the list of assignments, hides the cancel button, and shows the add and
    //          remove assignment buttons
    private void toAssignmentList() {
        remove(centerPanel);
        centerPanel = assignmentList();
        add(centerPanel);
        removeButton.setVisible(true);
        addButton.setVisible(true);
        cancelButton.setVisible(false);
        repaint();
        revalidate();
    }

    // MODIFIES: this
    // EFFECTS: changes the center panel to the add assignment menu, shows the cancel button, and hides the add and
    //          remove assignment buttons
    private void toAddAssignment() {
        remove(centerPanel);
        centerPanel = addAssignment();
        add(centerPanel);
        removeButton.setVisible(false);
        addButton.setVisible(false);
        cancelButton.setVisible(true);
        repaint();
        revalidate();
    }

    // MODIFIES: this
    // EFFECTS: changes the center panel to the remove assignment menu, shows the cancel button, and hides the add and
    //          remove assignment buttons
    private void toRemoveAssignment() {
        remove(centerPanel);
        centerPanel = removeAssignment();
        add(centerPanel);
        removeButton.setVisible(false);
        addButton.setVisible(false);
        cancelButton.setVisible(true);
        repaint();
        revalidate();
    }

}
