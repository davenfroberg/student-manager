package ui.pages;

import model.Course;
import ui.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClassesPage extends JPanel {

    private GUI gui;
    private JPanel headerPanel;
    private JPanel centerPanel;
    private JPanel menuPanel;
    private JButton cancelButton;
    private JButton addButton;
    private JButton dropButton;
    private static final Font HEADER_FONT = new Font("Helvetica", Font.BOLD, 25);

    // MODIFIES: this
    // EFFECTS: creates a classes page with a parent and a border layout
    public ClassesPage(GUI parent) {
        this.gui = parent;
        setLayout(new BorderLayout());
        addHeader();
        addMenu();
        addCenter();
    }

    // MODIFIES: this
    // EFFECTS: adds the header panel to the page with a back button
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
    // EFFECTS: adds the center panel to the page and goes to the list of classes
    private void addCenter() {
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        toClassList();
        add(centerPanel, "Center");
    }

    // MODIFIES: this
    // EFFECTS: adds the menu panel with the menu buttons on it to the page
    private void addMenu() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        addMenuOptions();
        add(menuPanel, "East");
    }

    // EFFECTS: returns the class list as a panel
    private JPanel classList() {
        JPanel classesPanel = new JPanel();
        classesPanel.setLayout(new BorderLayout());
        JPanel classList = new JPanel();
        classList.setLayout(new BoxLayout(classList, BoxLayout.Y_AXIS));

        JLabel headerLabel = new JLabel("Currently Enrolled Courses");

        headerLabel.setFont(HEADER_FONT);
        classesPanel.add(headerLabel, "North");
        for (Course course : gui.getStudent().getCourseList()) {
            JLabel label;
            label = new JLabel(course.getName() + ": " + course.getCredits() + " credits");
            label.setFont(new Font("Helvetica", Font.PLAIN, 18));
            classList.add(label);
        }
        JScrollPane classScroll = new JScrollPane(classList);
        classesPanel.add(classScroll, "Center");
        return classesPanel;
    }

    // EFFECTS: returns the add class menu as a panel
    private JPanel addClass() {
        JTextField nameText = new JTextField();
        JTextField creditsText = new JTextField();

        JPanel addClassesPanel = new JPanel();
        addClassesPanel.setLayout(new BorderLayout());

        addClassesPanel.add(addClassHeader(), "North");

        JPanel buttonAndText = new JPanel();
        buttonAndText.setLayout(new BoxLayout(buttonAndText, BoxLayout.Y_AXIS));

        buttonAndText.add(classNameText(nameText));
        buttonAndText.add(creditsText(creditsText));

        JButton submitAdd = new JButton("Add!");
        addSubmitListeners(submitAdd, nameText, creditsText);

        buttonAndText.add(submitAdd);

        addClassesPanel.add(buttonAndText);

        return addClassesPanel;
    }

    // EFFECTS: returns the header for the add class panel as a panel
    private JPanel addClassHeader() {
        JPanel addHeader = new JPanel();
        addHeader.setLayout(new BoxLayout(addHeader, BoxLayout.Y_AXIS));

        JLabel headerLabel = new JLabel("Add Class");
        JLabel headerText = new JLabel("Please type in the course name and credits for the course");
        headerLabel.setFont(HEADER_FONT);
        headerText.setFont(new Font("Helvetica", Font.PLAIN, 18));
        addHeader.add(headerLabel);
        addHeader.add(headerText);
        return addHeader;
    }

    // EFFECTS: labels the text field for the name of the course and returns both as a panel
    private JPanel classNameText(JTextField nameText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        JLabel nameLabel = new JLabel("Course Name:   ");
        panel.add(nameLabel);
        panel.add(nameText);

        return panel;
    }

    // EFFECTS: labels the text field for the credits of the course and returns both as a panel
    private JPanel creditsText(JTextField creditsText) {
        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        JLabel nameLabel = new JLabel("Course Credits: ");
        panel.add(nameLabel);
        panel.add(creditsText);

        return panel;
    }

    // EFFECTS: adds an action listener to the button that adds the class with the provided name and credits
    private void addSubmitListeners(JButton button, JTextField nameText, JTextField creditsText) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.getStudent().addCourse(new Course(nameText.getText(), Integer.parseInt(creditsText.getText())),
                        false);
                toClassList();
            }
        });
    }

    // EFFECTS: returns the drop class menu as a panel
    private JPanel dropClass() {
        JPanel dropClassesPanel = new JPanel();
        dropClassesPanel.setLayout(new BorderLayout());

        JPanel classList = new JPanel();
        classList.setLayout(new BoxLayout(classList, BoxLayout.Y_AXIS));

        dropClassesPanel.add(dropClassHeader(), "North");

        JPanel buttonAndBoxes = new JPanel();
        buttonAndBoxes.setLayout(new BoxLayout(buttonAndBoxes, BoxLayout.Y_AXIS));

        JPanel courseCheckBoxes = new JPanel();
        courseCheckBoxes.setLayout(new BoxLayout(courseCheckBoxes, BoxLayout.Y_AXIS));

        addAllBoxes(courseCheckBoxes);

        JButton submitDrop = new JButton("Drop!");
        addDropListener(submitDrop, courseCheckBoxes);

        buttonAndBoxes.add(courseCheckBoxes);
        buttonAndBoxes.add(submitDrop);

        dropClassesPanel.add(buttonAndBoxes, "Center");

        return dropClassesPanel;
    }

    // EFFECTS: returns the header for the drop class menu as a panel
    private JPanel dropClassHeader() {
        JPanel dropHeader = new JPanel();
        dropHeader.setLayout(new BoxLayout(dropHeader, BoxLayout.Y_AXIS));

        JLabel headerLabel = new JLabel("Drop Class");
        JLabel headerText = new JLabel("Select all the classes you would like to drop");
        headerLabel.setFont(HEADER_FONT);
        headerText.setFont(new Font("Helvetica", Font.PLAIN, 18));
        dropHeader.add(headerLabel);
        dropHeader.add(headerText);
        return dropHeader;
    }

    // EFFECTS: adds check boxes for each of the courses to the provided panel
    private void addAllBoxes(JPanel courseCheckBoxes) {
        for (Course course : gui.getStudent().getCourseList()) {
            JCheckBox box;
            box = new JCheckBox(course.getName());
            box.setFont(new Font("Helvetica", Font.PLAIN, 15));
            courseCheckBoxes.add(box);
        }
    }

    // EFFECTS: adds an action listener to the button that drops the selected courses
    private void addDropListener(JButton button, JPanel boxes) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int size = gui.getStudent().getCourseList().size();
                for (int i = 0; i < size; i++) {
                    JCheckBox myCheckBox = (JCheckBox) boxes.getComponent(i);
                    if (myCheckBox.isSelected()) {
                        gui.getStudent().removeCourse(myCheckBox.getText());
                    }
                }
                toClassList();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: adds the menu options to the menu panel
    private void addMenuOptions() {
        cancelButton = new JButton("Cancel");
        addButton = new JButton("Add a course");
        dropButton = new JButton("Drop a course");
        if (gui.getStudent().getCourseList().isEmpty()) {
            dropButton.setEnabled(false);
        }

        addActionListeners();
        cancelButton.setVisible(false);
        menuPanel.add(cancelButton);
        menuPanel.add(addButton);
        menuPanel.add(dropButton);
    }

    // MODIFIES: this
    // EFFECTS: adds action listeners to each of the menu buttons
    private void addActionListeners() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toAddClass();
            }
        });
        dropButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toDropClass();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toClassList();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: changes the main panel to the class list and hides the cancel button
    private void toClassList() {
        remove(centerPanel);
        centerPanel = classList();
        add(centerPanel, "Center");
        cancelButton.setVisible(false);
        dropButton.setVisible(true);
        addButton.setVisible(true);
        repaint();
        revalidate();
    }

    // MODIFIES: this
    // EFFECTS: changes the main panel to the add class menu and hides the add/drop class buttons and shows the cancel
    //          button
    private void toAddClass() {
        remove(centerPanel);
        centerPanel = addClass();
        add(centerPanel, "Center");
        cancelButton.setVisible(true);
        dropButton.setVisible(false);
        addButton.setVisible(false);
        repaint();
        revalidate();
    }

    // MODIFIES: this
    // EFFECTS: changes the main panel to the drop class menu and hides the add/drop class buttons and shows the cancel
    //          button
    private void toDropClass() {
        remove(centerPanel);
        centerPanel = dropClass();
        add(centerPanel, "Center");
        cancelButton.setVisible(true);
        dropButton.setVisible(false);
        addButton.setVisible(false);
        repaint();
        revalidate();
    }
}
