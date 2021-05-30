package pl.edu.pg.student.cybersecurity.Layouts;

import pl.edu.pg.student.cybersecurity.System.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowApp extends JFrame {

    private User user;

    private CardLayout cardLayout = new CardLayout();
    private JPanel panelCont = new JPanel();

    private JPanel welcomePanel;
    private JPanel loginPanel;
    private JPanel registerPanel;
    private JPanel dashboardPanel;

    public WindowApp() {

        setSize(new Dimension(500, 400));

        welcomePanel = new WelcomPanel(this);
        loginPanel =  new LoginPanel(this);
        registerPanel = new RegisterPanel(this);
        dashboardPanel = new DashboardPanel(this);

        panelCont.setLayout(cardLayout);

        panelCont.add(loginPanel, "LoginPanel");
        panelCont.add(welcomePanel, "WelcomPanel");
        panelCont.add(registerPanel, "RegisterPanel");
        panelCont.add(dashboardPanel, "DashboardPanel");

        cardLayout.show(panelCont,"WelcomPanel");

        add(panelCont);

        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("CyberSecurity - version 1.0");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public JPanel getDashboardPanel() {
        return dashboardPanel;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getPanelCont() {
        return panelCont;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
