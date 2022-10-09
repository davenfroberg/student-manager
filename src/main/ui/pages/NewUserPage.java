package ui.pages;

import ui.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewUserPage extends JPanel {

    private JPanel contentPanel = new JPanel();
    private JTextField nameText;
    private JTextField idText;
    public static final Font HEADER_FONT = new Font("Helvetica", Font.BOLD, 15);
    private GUI gui;

    // MODIFIES: this
    // EFFECTS: creates a new user page panel with a parent and a grid layout
    public NewUserPage(GUI parent) {
        this.gui = parent;
        setLayout(new GridLayout(3,3));
        init();
        setVisible(true);
    }

    // EFFECTS: initialize the page
    private void init() {
        initTextFields();
        addPanels();
        addContent();
    }

    // MODIFIES: this
    // EFFECTS: instantiate the text field objects
    private void initTextFields() {
        nameText = new JTextField();
        idText = new JTextField();
    }

    // MODIFIES: this
    // EFFECTS: adds the panels to the page
    private void addPanels() {
        addEmptyPanels(4);
        JPanel alignX = new JPanel();
        alignX.setLayout(new BoxLayout(alignX, BoxLayout.X_AXIS));
        alignX.add(contentPanel);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        add(alignX);
        addEmptyPanels(4);
    }

    // MODIFIES: this
    // REQUIRES: x > 0
    // EFFECTS: adds x number of empty panels to the page
    private void addEmptyPanels(int x) {
        for (int i = 0; i < x; i++) {
            add(new JPanel());
        }
    }

    // MODIFIES: this
    // EFFECTS: adds the content to the page
    private void addContent() {
        JLabel welcomeLabel = new JLabel("Welcome to the UBC Student Manager v3.0!");
        welcomeLabel.setFont(HEADER_FONT);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(welcomeLabel);

        JLabel promptLabel = new JLabel("Please enter your name and student ID:");
        promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(promptLabel);

        JPanel nameTextComponents = addTitledTextField("Name:",180, nameText);
        nameTextComponents.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(nameTextComponents);

        JPanel idTextComponents = addTitledTextField("Student ID:",150, idText);
        idTextComponents.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(idTextComponents);

        addSubmitButton();
    }

    // EFFECTS: adds a label to the given text field and returns it as a panel
    private JPanel addTitledTextField(String title, int width, JTextField field) {
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        JLabel nameLabel = new JLabel(title);
        namePanel.add(nameLabel);
        field.setMaximumSize(new Dimension(width, 25));
        namePanel.add(field);
        return namePanel;
    }

    // MODIFIES: this
    // EFFECTS: adds a button to submit the new profile
    private void addSubmitButton() {
        JButton submitButton = new JButton("Submit");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(submitButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    gui.createStudent(nameText.getText(), Integer.parseInt(idText.getText()));
                    gui.toMainPage();
                } catch (NumberFormatException exception) {
                    //
                }
            }
        });
    }


}
