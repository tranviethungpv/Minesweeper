package Controller;

import java.util.Calendar;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import View.BoardPanel;
import View.ControlPanel;
import View.MenuBar;
import View.saveInfo;
import Model.Board;
import Model.Square;
import Interface.ICommon;
import Interface.ITrans;
import java.util.Date;

public class Gui extends JFrame implements ICommon, ITrans {

    private static final String TITLE = "MineSweeper";
    public static final int FRAME_WIDTH = 614;
    public static final int FRAME_HEIGHT = 630;
    private BoardPanel boardPanel;
    private ControlPanel controlPanel;
    private Board board;
    private final MenuBar menuBar = new MenuBar();
    private static final saveInfo saveInfo = new saveInfo();

    public Gui() {
        board = new Board();
        saveInfo.setDate(Calendar.getInstance().getTime());
        saveInfo.setStartTime(System.currentTimeMillis());
        initComp();
        addComp();
        addEvent();
    }

    @Override
    public void initComp() {
        setJMenuBar(menuBar.getmenuBar());
        setTitle(TITLE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(null);
    }

    @Override
    public void addComp() {
        boardPanel = new BoardPanel();
        boardPanel.setBounds(0, 70, 600, 500);
        add(boardPanel);
        boardPanel.addListener(this);
        controlPanel = new ControlPanel();
        controlPanel.setBounds(0, 15, 600, 100);
        add(controlPanel);
        controlPanel.addListener(this);
    }

    @Override
    public void addEvent() {
        WindowListener wd = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int kq = JOptionPane.showConfirmDialog(Gui.this, "Do you want to quit?",
                        "Alert", JOptionPane.YES_NO_OPTION);
                if (kq == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        };
        addWindowListener(wd);
    }

    @Override
    public Square[][] getListSquare() {
        return board.getListSquare();
    }

    @Override
    public void play(int x, int y) {
        boolean check = board.play(x, y);
        if (!check) {
            board.showAllSquares();
            saveInfo.setEndTime(System.currentTimeMillis());
            JOptionPane.showMessageDialog(null, "End Game! Total time is " + String.valueOf((double) (saveInfo.getEndTime() - saveInfo.getStartTime()) / 1000) + "s");
            saveInfo.setStatus("LOSE");
            saveInfo.showsaveInfo();
        }
        boardPanel.updateBoard();
        // cập nhật số ô chưa mở vào controlPanel
        int numSquareClosed = boardPanel.getUnopenedSquare();
        controlPanel.updateStatus(numSquareClosed);
    }

    @Override
    public void target(int x, int y) {
        board.target(x, y);
        boardPanel.updateBoard();
    }

    @Override
    public void restart() {
        saveInfo.setDate(Calendar.getInstance().getTime());
        saveInfo.setStartTime(System.currentTimeMillis());
        saveInfo.setName(null);
        saveInfo.setStatus(null);
        board = new Board();
        boardPanel.updateBoard();
    }
}
