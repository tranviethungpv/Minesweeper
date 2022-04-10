package Model;

import java.util.Random;

public class Board {

    public static int Rows = 9;
    public static int Columns = 9;
    public static int Mines = Rows * Columns / 10;

    private Square[][] square;

    public Board() {
        square = new Square[Rows][Columns];
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square[0].length; j++) {
                square[i][j] = new Square();
            }
        }

        // đặt mìn vào các ô ngẫu nhiên
        for (int i = 0; i < Mines; i++) {
            int x = randomMines(Rows);      //chọn ngẫu nhiên toạ độ của mìn
            int y = randomMines(Columns);

            // nếu có mìn rồi thì đặt ngẫu nhiên vào ô khác
            while (square[x][y].isHasMine()) {
                x = randomMines(Rows);
                y = randomMines(Columns);
            }
            square[x][y].setHasMine(true);
        }

        // ghi số lượng mìn xung quanh vào ô
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square[0].length; j++) { //xét ô có toạ độ [i][j]
                int count = 0;
                if (square[i][j].isHasMine() == true) { //nếu ô có mìn thì bỏ qua luôn
                    continue;
                }
                // nếu ô không có mìn thì duyệt các ô xung quanh ô đó
                for (int x = -1; x <= 1; x++) { //duyệt hàng ngang
                    for (int y = -1; y <= 1; y++) { //duyệt hàng dọc
                        int xpos = i + x;   //tung độ của ô xung quanh
                        int ypos = j + y;   //hoành độ của ô xung quanh
                        if (xpos < 0 || xpos > Rows - 1 || ypos < 0 || ypos > Columns - 1) {
                            continue; //nếu xpos < 0, tức là ô [i][j] nằm ở cột trái ngoài cùng
                            //nếu ypox < 0, tức là ô [i][j] nằm ở hàng dưới cùng
                            //nếu xpos > Rows - 1, tức là ô [i][j] nằm ở cạnh phải ngoài cùng
                            //nếu ypox > Columns - 1, tức là ô [i][j] nằm ở hàng trên cùng
                            //các trường hợp này thì continue, tức là chỉ có 5 ô xung quanh còn lại để duyệt
                        }
                        if (square[xpos][ypos].isHasMine() == true) {
                            count++; //nếu ô vừa duyệt có mìn thì tăng đếm để ghi vào ô [i][j] đang xét
                        }
                    }
                }
                square[i][j].setNumMineAround(count);
            }
        }
    }

    private int randomMines(int range) {
        Random rd = new Random();
        return rd.nextInt(range);
    }

    public Square[][] getListSquare() {
        return square;
    }

    public boolean play(int x, int y) {
        //thuật toán loang để mở các ô không chứa mìn xung quanh
        if (!square[x][y].isOpen()) {
            square[x][y].setOpen(true); //mở ô [x][y]
            if (square[x][y].isHasMine()) {
                return false;
            }
            if (square[x][y].getNumMineAround() == 0) { //nếu ô[x][y] có 0 mìn xung quanh, thực hiện duyệt tất cả các ô xung quanh
                for (int m = -1; m <= 1; m++) { //duyệt hàng ngang
                    for (int n = -1; n <= 1; n++) { //duyệt hàng dọc
                        int mpos = x + m;   //tung độ của ô xung quanh
                        int npos = y + n;   //hoành độ của ô xung quanh
                        if (mpos < 0 || mpos > Rows - 1 || npos < 0 || npos > Columns - 1) {
                            continue; //nếu mpos < 0, tức là ô [x][y] nằm ở cột trái ngoài cùng
                            //nếu npox < 0, tức là ô [x][y] nằm ở hàng dưới cùng
                            //nếu mpos > Rows - 1, tức là ô [x][y] nằm ở cạnh phải ngoài cùng
                            //nếu npox > Columns - 1, tức là ô [x][y] nằm ở hàng trên cùng
                            //các trường hợp này thì continue, tức là chỉ có 5 ô xung quanh còn lại để duyệt
                        }
                        play(mpos, npos); //gọi đệ quy
                    }
                }
            }
        }
        return true;
    }

    public void target(int x, int y) {
        if (!square[x][y].isOpen()) {
            if (!square[x][y].isTarget()) {
                square[x][y].setTarget(true);
            } else {
                square[x][y].setTarget(false);
            }
        }
    }

    public void showAllSquares() {
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square[0].length; j++) {
                square[i][j].setOpen(true);
            }
        }
    }
}
