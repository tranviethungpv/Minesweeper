package Interface;

import Model.Square;

public interface ITrans {

    Square[][] getListSquare();

    void play(int x, int y);

    void target(int x, int y);

    void restart();
}
