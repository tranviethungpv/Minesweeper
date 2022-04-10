package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuBar extends JFrame implements ActionListener {

    private aboutGame aboutGameFrame = new aboutGame();
    private aboutStudent1 aboutStudent1Frame = new aboutStudent1();
    private aboutStudent2 aboutStudent2Frame = new aboutStudent2();
    private Law LawFrame = new Law();
    private JMenuBar menuBar = new JMenuBar();
    private JMenuItem guide = new JMenuItem("Luật chơi");
    private JMenu menu = new JMenu("About");
    private JMenu aboutDev = new JMenu("Sinh viên");
    private JMenuItem aboutGame = new JMenuItem("Game");
    private JMenuItem student1 = new JMenuItem("Trần Việt Hưng");
    private JMenuItem student2 = new JMenuItem("La Cảnh Kỳ");

    public MenuBar() {
        menuBar.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        menuBar.add(guide);
        aboutDev.add(student1);
        aboutDev.add(student2);
        menu.add(aboutGame);
        menu.add(aboutDev);
        menuBar.add(menu);
        guide.addActionListener(this);
        aboutGame.addActionListener(this);
        student1.addActionListener(this);
        student2.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("Game")) {
            aboutGameFrame.showAboutGame();
        }
        if (e.getActionCommand().equalsIgnoreCase("Trần Việt Hưng")) {
            aboutStudent1Frame.showAboutStudent1();
        }
        if (e.getActionCommand().equalsIgnoreCase("La Cảnh Kỳ")) {
            aboutStudent2Frame.showAboutStudent2();
        }
        if (e.getActionCommand().equalsIgnoreCase("Luật chơi")) {
            LawFrame.showLaw();
        }
    }

    public JMenuBar getmenuBar() {
        return this.menuBar;
    }
}
