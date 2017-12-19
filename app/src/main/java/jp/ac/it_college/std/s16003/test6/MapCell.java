package jp.ac.it_college.std.s16003.test6;

/**
 * Created by s16003 on 17/10/13.
 */

public class MapCell {
    private int grafic;
    private boolean moveFlag;

    MapCell(int grafic, boolean moveFlag) {
        this.grafic = grafic;
        this.moveFlag = moveFlag;
    }

    public boolean getMoveFlag() {
        return moveFlag;
    }

    public int getGrafic() {
        return grafic;
    }
}
