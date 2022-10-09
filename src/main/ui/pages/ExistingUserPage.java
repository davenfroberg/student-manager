package ui.pages;

import ui.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExistingUserPage extends JPanel {

    private GUI gui;
    private JPanel contentPanel = new JPanel();
    public static final Font HEADER_FONT = new Font("Helvetica", Font.BOLD, 15);

    // MODIFIES: this
    // EFFECTS: creates an existing user page with a parent and a grid layout
    public ExistingUserPage(GUI parent) {
        this.gui = parent;
        setLayout(new GridLayout(3,3));
        init();
        setVisible(true);
    }

    // EFFECTS: initializes the page
    private void init() {
        addPanels();
        addContent();
    }

    // MODIFIES: this
    // EFFECTS: adds all the panels to the page
    private void addPanels() {
        addEmptyPanels(4);
        JPanel alignX = new JPanel();
        alignX.setLayout(new BoxLayout(alignX, BoxLayout.X_AXIS));
        alignX.add(contentPanel);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        add(alignX);
        addEmptyPanels(4);
    }

    // REQUIRES: x > 0
    // MODIFIES: this
    // EFFECTS: adds x number of empty panels to the page
    private void addEmptyPanels(int x) {
        for (int i = 0; i < x; i++) {
            add(new JPanel());
        }
    }

    // MODIFIES: this
    // EFFECTS: adds user prompt content to the page
    private void addContent() {
        JLabel welcomeLabel = new JLabel("Welcome to the UBC Student Manager v3.0!");
        welcomeLabel.setFont(HEADER_FONT);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(welcomeLabel);

        JLabel promptLabel = new JLabel("Would you like to load the following student profile?");
        promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(promptLabel);

        JLabel nameLabel = new JLabel("Name: " + gui.getStudent().getName());
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(nameLabel);

        JLabel idLabel = new JLabel("Student ID: " + gui.getStudent().getStudentID());
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(idLabel);

        addButtons();
    }

    // MODIFIES: this
    // EFFECTS: adds the yes and no buttons to the page
    private void addButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        addYesButton(buttonPanel);
        addNoButton(buttonPanel);
        contentPanel.add(buttonPanel);
    }

    // EFFECTS: adds a button that says yes and brings the user to the main page
    private void addYesButton(JPanel panel) {
        JButton yesButton = new JButton("Yes");
        yesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(yesButton);
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.toMainPage();
            }
        });
    }

    // EFFECTS: adds a button that says no and brings the user to the new user page
    private void addNoButton(JPanel panel) {
        JButton noButton = new JButton("No");
        noButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(noButton);
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.toNewUserPage();
            }
        });
    }
}
