package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import Interface.ICommon;
import Interface.ITrans;
import Model.Board;
import javax.swing.JOptionPane;
import View.saveInfo;

public class ControlPanel extends JPanel implements ICommon {

    private JLabel label_unopenedSquare;
    private JLabel label_Notify;
    private JButton button_Restart;
    private ITrans listener;

    public ControlPanel() {
        initComp();
        addComp();
        addEvent();
    }

    @Override
    public void initComp() {
        setLayout(null);
    }

    @Override
    public void addComp() {
        Font font = new Font("VNI", Font.PLAIN, 20);

        label_unopenedSquare = new JLabel();
        label_unopenedSquare.setFont(font);
        label_unopenedSquare.setText("Số ô chưa mở: " + Board.Rows * Board.Columns);
        label_unopenedSquare.setBounds(8, 5, 230, 40);
        add(label_unopenedSquare);

        label_Notify = new JLabel();
        label_Notify.setFont(font);
        label_Notify.setBounds(230, 5, 185, 40);
        add(label_Notify);

        button_Restart = new JButton();
        button_Restart.setFont(font);
        button_Restart.setText("NEW GAME");
        button_Restart.setBounds(430, 5, 150, 40);
        add(button_Restart);
    }

    @Override
    public void addEvent() {
        button_Restart.addActionListener((ActionEvent e) -> {
            listener.restart();
            label_unopenedSquare.setText("Số ô chưa mở: " + Board.Rows * Board.Columns);
            label_Notify.setText("");
        });
    }

    public void addListener(ITrans event) {
        listener = event;
    }

    public void updateStatus(int numSquareClosed) {
        label_unopenedSquare.setText("Số ô chưa mở: " + numSquareClosed);
        if (numSquareClosed == Board.Mines) {
            label_Notify.setText("THẮNG");
            label_Notify.setForeground(Color.blue);
            saveInfo.setEndTime(System.currentTimeMillis());
            JOptionPane.showMessageDialog(null, "End Game! Total time is " + String.valueOf((double) (saveInfo.getEndTime() - saveInfo.getStartTime()) / 1000) + "s");
            saveInfo.setStatus("WIN");
            saveInfo.showsaveInfo();
        } else if (numSquareClosed == 0) {
            label_Notify.setText("THUA");
            label_Notify.setForeground(Color.red);
        }
    }
}
