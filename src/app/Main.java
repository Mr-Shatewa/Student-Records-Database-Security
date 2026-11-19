package app;

import javax.swing.SwingUtilities;

import gui.LoginFrame;

import gui.DashboardFrame;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DashboardFrame();
            }
        });
    }
}
