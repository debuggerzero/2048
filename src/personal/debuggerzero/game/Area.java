package personal.debuggerzero.game;

/**
 * @author DebuggerZero
 */
public class Area {

    public final static int WIDTH = 120;
    public final static int HEIGHT = 120;

    int value;
    int x;
    int y;

    public Area(int x, int y, int value){
        this.x = x;
        this.y = y;
        this.value = value;
    }
}
