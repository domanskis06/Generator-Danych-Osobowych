package main.java.generator;

import main.java.generator.gui.MainController;
import main.java.generator.gui.MainView;

import javax.swing.*;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            MainView view = new MainView();
            new MainController(view);
            view.setVisible(true);
        });
    }
}