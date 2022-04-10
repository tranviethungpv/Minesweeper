package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;
import Interface.ICommon;
import Interface.ITrans;
import Model.Board;
import Model.Square;

public class BoardPanel extends JPanel implements ICommon {

    private Label[][] label_Square;
    private ITrans listener;
    private int unopenedSquare;

    public BoardPanel() {
        initComp();
        addComp();
        addEvent();
    }

    @Override
    public void initComp() {
        setLayout(new GridLayout(Board.Rows, Board.Columns));
    }

    @Override
    public void addComp() {
        Border border = BorderFactory.createLineBorder(Color.gray, 1);
        label_Square = new Label[Board.Rows][Board.Columns];
        for (Label[] label_Square1 : label_Square) {
            for (int j = 0; j < label_Square[0].length; j++) {
                label_Square1[j] = new Label();
                label_Square1[j].setOpaque(true);
                label_Square1[j].setBackground(new Color(173, 173, 173));
                label_Square1[j].setBorder(border);
                label_Square1[j].setHorizontalAlignment(JLabel.CENTER);
                label_Square1[j].setVerticalAlignment(JLabel.CENTER);
                add(label_Square1[j]);
            }
        }
    }

    @Override
    public void addEvent() {
        for (int i = 0; i < label_Square.length; i++) {
            for (int j = 0; j < label_Square[0].length; j++) {
                label_Square[i][j].x = i;
                label_Square[i][j].y = j;
                label_Square[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        Label label = (Label) e.getComponent();
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            listener.play(label.x, label.y);
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            listener.target(label.x, label.y);
                        }
                    }
                });
            }
        }
    }

    public void addListener(ITrans event) {
        listener = event;
    }

    // cập nhật hiển thị
    public void updateBoard() {
        Font font = new Font("VNI", Font.PLAIN, 20);
        unopenedSquare = 0;
        Square[][] listSquare = listener.getListSquare();
        for (int i = 0; i < listSquare.length; i++) {
            for (int j = 0; j < listSquare[0].length; j++) {
                label_Square[i][j].setFont(font);
                if (!listSquare[i][j].isOpen()) {
                    label_Square[i][j].setBackground(new Color(173, 173, 173));
                    label_Square[i][j].setForeground(Color.black);
                    unopenedSquare++;
                    if (!listSquare[i][j].isTarget()) {
                        label_Square[i][j].setText("");
                    } else {
                        label_Square[i][j].setText("\uD83D\uDEA9"); // ki tu 'flag'
                    }
                } else {
                    if (listSquare[i][j].isHasMine()) {
                        label_Square[i][j].setText("\uD83D\uDCA3"); // ki tu 'bomb'
                    } else {
                        int numMineAround = listSquare[i][j].getNumMineAround();
                        if (numMineAround == 0) {
                            label_Square[i][j].setText("");
                        } else {
                            label_Square[i][j].setText(numMineAround + "");
                            // đặt màu số cho dễ nhìn
                            switch (numMineAround) {
                                case 1 ->
                                    label_Square[i][j].setForeground(new Color(128, 128, 128));
                                case 2 ->
                                    label_Square[i][j].setForeground(new Color(255, 0, 0));
                                case 3 ->
                                    label_Square[i][j].setForeground(new Color(0, 204, 0));
                                case 4 ->
                                    label_Square[i][j].setForeground(new Color(102, 0, 255));
                                case 5 ->
                                    label_Square[i][j].setForeground(new Color(128, 128, 128));
                                case 6 ->
                                    label_Square[i][j].setForeground(new Color(255, 0, 0));
                                case 7 ->
                                    label_Square[i][j].setForeground(new Color(0, 204, 0));
                                case 8 ->
                                    label_Square[i][j].setForeground(new Color(102, 0, 255));
                            }
                        }
                    }
                    label_Square[i][j].setBackground(Color.white);
                }
            }
        }
    }

    // tạo class con để mở rộng thuộc tính cho lớp JLabel
    // giúp lưu được chỉ số hàng, cột của label đó ở trong GridLayout
    // vì ko thể truyền giá trị i, j vào bên trong phương thức addMouseListener
    private class Label extends JLabel {

        private int x;
        private int y;
    }

    public int getUnopenedSquare() {
        return unopenedSquare;
    }
}
