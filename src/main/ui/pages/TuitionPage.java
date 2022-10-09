package ui.pages;

import model.Course;
import ui.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TuitionPage extends JPanel {

    private GUI gui;
    private JPanel headerPanel;
    private JPanel menuPanel;
    private JPanel centerPanel;

    private JButton payButton;
    private JButton cancelButton;
    private JButton submitPay;
    private JTextField amountToPayText;
    private boolean warned;
    private JLabel warningLabel;
    private Font headerFont = new Font("Helvetica", Font.BOLD, 25);


    // MODIFIES: this
    // EFFECTS: creates a tuition page with a parent and a border layout
    public TuitionPage(GUI parent) {
        this.gui = parent;
        setLayout(new BorderLayout());
        addHeader();
        addMenu();
        addCenter();
    }

    // MODIFIES: this
    // EFFECTS: instantiates the header panel, adds a back button to it, and adds the panel to the page
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
    // EFFECTS: instantiates the menu panel and adds menu buttons to the panel and adds the panel to the page
    private void addMenu() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        addMenuButtons();
        add(menuPanel, "East");
    }

    // MODIFIES: this
    // EFFECTS: instantiates the menu buttons and adds them to the menu panel; cancel button is not visible by default
    private void addMenuButtons() {
        payButton = new JButton("Pay tuition");
        if (gui.getStudent().getCourseList().isEmpty()) {
            payButton.setEnabled(false);
        }

        cancelButton = new JButton("Cancel");
        cancelButton.setVisible(false);
        addMenuListeners();
        menuPanel.add(payButton);
        menuPanel.add(cancelButton);
    }

    // MODIFIES: this
    // EFFECTS: adds action listeners to each of the menu buttons
    private void addMenuListeners() {
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toPayTuition();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toTuitionList();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: instantiates the center panel and sets it to the tuition list
    private void addCenter() {
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        toTuitionList();
    }

    // EFFECTS: returns the list of classes and their tuition as a panel
    private JPanel tuitionList() {
        JPanel tuitionList = new JPanel();
        tuitionList.setLayout(new BorderLayout());
        JPanel classList = new JPanel();
        classList.setLayout(new BoxLayout(classList, BoxLayout.Y_AXIS));

        JLabel headerLabel = new JLabel("Tuition by Course");

        headerLabel.setFont(headerFont);
        tuitionList.add(headerLabel, "North");
        for (Course course : gui.getStudent().getCourseList()) {
            JLabel label;
            label = new JLabel(course.getName() + ": $" + (course.getCredits() * gui.getStudent().pricePerCredit));
            label.setFont(new Font("Helvetica", Font.PLAIN, 18));
            classList.add(label);
        }
        JScrollPane classScroll = new JScrollPane(classList);
        tuitionList.add(classScroll, "Center");
        tuitionList.add(addTotals(), "South");

        return tuitionList;
    }

    // EFFECTS: returns a summary of total tuition as a panel
    private JPanel addTotals() {
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.Y_AXIS));
        JLabel totalTuiton = new JLabel("Total: $" + gui.getStudent().getTuition());
        JLabel tuitionPaid = new JLabel("Total Paid: $" + gui.getStudent().getTuitionPaid());
        JLabel tuitionDue = new JLabel("Total Due: $" + gui.getStudent().getTuitionDue());
        totalPanel.add(totalTuiton);
        totalPanel.add(tuitionPaid);
        totalPanel.add(tuitionDue);
        return totalPanel;
    }

    // EFFECTS: returns the menu to pay the tuition as a panel
    private JPanel payTuition() {
        JPanel payTuition = new JPanel();
        payTuition.setLayout(new BoxLayout(payTuition, BoxLayout.Y_AXIS));
        payTuition.add(amountToPay());
        submitPay = new JButton("Pay!");
        addSubmitListener();
        payTuition.add(submitPay);
        payTuition.add(addTotals());
        warningLabel = new JLabel("Warning! You are about to pay more than what is due. Press the pay button again"
                + " to proceed");
        warningLabel.setFont(new Font("Helvetica", Font.BOLD, 15));
        warningLabel.setForeground(new Color(255, 0, 0));
        warningLabel.setVisible(false);
        payTuition.add(warningLabel);
        return payTuition;
    }

    // EFFECTS: adds an action listener to the button that confirms payment, which warns the user when the payment is
    //          greater than what they owe
    private void addSubmitListener() {
        submitPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int amount = Integer.parseInt(amountToPayText.getText());
                if (amount > gui.getStudent().getTuitionDue() && !warned) {
                    warningLabel.setVisible(true);
                    warned = true;
                } else {
                    warningLabel.setVisible(false);
                    warned = false;
                    gui.getStudent().payTuition(amount);
                    toTuitionList();
                }
            }
        });

    }

    // MODIFIES: this
    // EFFECTS: returns the amount to pay as a labeled text field
    private JPanel amountToPay() {
        JPanel amountToPayPanel = new JPanel();
        amountToPayPanel.setLayout(new BoxLayout(amountToPayPanel, BoxLayout.X_AXIS));
        amountToPayText = new JTextField();
        JLabel amountToPayLabel = new JLabel("How much would you like to pay? $");
        amountToPayPanel.add(amountToPayLabel);
        amountToPayPanel.add(amountToPayText);
        return amountToPayPanel;
    }

    // MODIFIES: this
    // EFFECTS: changes the center panel to the tuition list, shows the pay tuition button and hides the cancel button
    private void toTuitionList() {
        remove(centerPanel);
        centerPanel = tuitionList();
        add(centerPanel, "Center");
        payButton.setVisible(true);
        cancelButton.setVisible(false);
        repaint();
        revalidate();
    }

    // MODIFIES: this
    // EFFECTS: changes the center panel to the pay tuition menu, shows the cancel button and hides the pay tuition
    //          button
    private void toPayTuition() {
        remove(centerPanel);
        centerPanel = payTuition();
        add(centerPanel, "Center");
        payButton.setVisible(false);
        cancelButton.setVisible(true);
        repaint();
        revalidate();
    }
}
