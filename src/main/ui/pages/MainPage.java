package ui.pages;

import ui.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainPage extends JPanel {

    private static final Font HEADER_FONT = new Font("Helvetica", Font.BOLD, 20);
    private static final Font SUBHEADER_FONT = new Font("Helvetica", Font.PLAIN, 15);
    private static final Color HEADER_COLOR = new Color(12, 16, 44);
    private static final Font TITLE_FONT = new Font("Helvetica", Font.BOLD, 13);

    private JPanel mainPanel = new JPanel();
    private JPanel classesPanel = new JPanel();
    private JPanel headerPanel = new JPanel(); // maybe do this using a grid layout?, 2 rows, 1 column

    private GUI gui;

    // MODIFIES: this
    // EFFECTS: creates a main page panel with a parent and a border layout
    public MainPage(GUI parent) {
        this.gui = parent;
        setLayout(new BorderLayout());
        init();
        setVisible(true);
    }

    // EFFECTS: initializes the panel
    private void init() {
        addPanels();
        addHeader();
        addMain();
    }

    // MODIFIES: this
    // EFFECTS: adds the header and main panels to the page and sets their layouts
    private void addPanels() {
        add(headerPanel, "North");
        add(mainPanel, "Center");
        mainPanel.setBorder(new TitledBorder(""));
        mainPanel.setLayout(new GridLayout(1, 2));

    }

    // MODIFIES: this
    // EFFECTS: sets header layout and tries to add content to header
    private void addHeader() {
        headerPanel.setLayout(new GridLayout(1, 3));
        try {
            addWelcomeToHeader();
        } catch (IOException e) {
            System.out.println("Image Error");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds content to the header
    private void addWelcomeToHeader() throws IOException {
        JPanel wordsAndImage = new JPanel();

        wordsAndImage.setLayout(new BoxLayout(wordsAndImage, BoxLayout.X_AXIS));

        BufferedImage myPicture = ImageIO.read(new File("./assets/ubc-logo.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        wordsAndImage.add(picLabel);

        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        JLabel welcomeNameLabel = new JLabel("   Welcome " + gui.getStudent().getName() + "!");
        JLabel welcomeIdLabel = new JLabel("     Student ID: " + gui.getStudent().getStudentID());
        welcomeNameLabel.setFont(HEADER_FONT);
        welcomeNameLabel.setForeground(HEADER_COLOR);
        welcomeIdLabel.setFont(SUBHEADER_FONT);
        welcomeIdLabel.setForeground(HEADER_COLOR);
        welcomePanel.add(welcomeNameLabel);
        welcomePanel.add(welcomeIdLabel);

        wordsAndImage.add(welcomePanel);

        headerPanel.add(wordsAndImage);
    }

    // MODIFIES: this
    //EFFECTS: adds the quick facts to the main panel
    private void addQuickFacts() {
        JPanel quickFactsPanel = new JPanel();
        quickFactsPanel.setLayout(new BoxLayout(quickFactsPanel, BoxLayout.Y_AXIS));
        JLabel quickFactsHeader = new JLabel("Quick Facts");
        JPanel factsText = addFactsText();

        quickFactsHeader.setFont(HEADER_FONT);
        quickFactsHeader.setForeground(HEADER_COLOR);
        quickFactsPanel.add(quickFactsHeader);
        quickFactsPanel.add(factsText);
        mainPanel.add(quickFactsPanel);

    }

    // REQUIRES: there must already be a student created
    // EFFECTS: returns a panel with the body text of the quick facts
    private JPanel addFactsText() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel tuitionLabel = new JLabel("Tuition Due: $" + gui.getStudent().getTuitionDue());

        JLabel creditsEnrolledLabel = new JLabel("Credits Enrolled: " + gui.getStudent().getCreditsEnrolled()
                + " credits");

        JLabel currentAverageLabel;
        if (gui.getStudent().getAverage() != -1) {
            currentAverageLabel = new JLabel("Current Average: " + gui.getStudent().getAverage() + "%");
        } else {
            currentAverageLabel = new JLabel("Current Average: N/A");
        }

        tuitionLabel.setFont(SUBHEADER_FONT);
        creditsEnrolledLabel.setFont(SUBHEADER_FONT);
        currentAverageLabel.setFont(SUBHEADER_FONT);
        tuitionLabel.setForeground(HEADER_COLOR);
        creditsEnrolledLabel.setForeground(HEADER_COLOR);
        currentAverageLabel.setForeground(HEADER_COLOR);

        panel.add(tuitionLabel);
        panel.add(creditsEnrolledLabel);
        panel.add(currentAverageLabel);
        return panel;
    }

    // EFFECTS: adds content to the main panel
    private void addMain() {
        addQuickFacts();
        addMenuButtons();

    }

    // MODIFIES: this
    // EFFECTS: adds the menu buttons to the main panel
    private void addMenuButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        addButtons(buttonPanel);

        mainPanel.add(buttonPanel);
    }

    // EFFECTS: adds the specific buttons and their button listeners to the button panel
    private void addButtons(JPanel buttonPanel) {
        addButton(buttonPanel, "Manage courses", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gui.toCoursesPage();
            }
        });
        addButton(buttonPanel, "Manage tuition", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gui.toTuitionPage();
            }
        });
        addButton(buttonPanel, "Manage assignments", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gui.toAssignmentsPage();
            }
        });
        addButton(buttonPanel, "Save", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gui.save();
            }
        });
    }

    // EFFECTS: adds a new button object to the button panel
    private void addButton(JPanel buttonPanel, String title, ActionListener listener) {
        JButton button = new JButton(title);
        button.addActionListener(listener);
        buttonPanel.add(button);
    }
}
