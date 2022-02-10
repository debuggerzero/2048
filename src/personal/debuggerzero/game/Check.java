package personal.debuggerzero.game;

import java.awt.*;

/**
 * @author DebuggerZero
 */
public class Check extends Map {
    //定义方格的WIDTH, HEIGHT
    public final static int WIDTH = 120;
    public final static int HEIGHT = 120;

    //移动步长
    public double stepSize;

    public Check(){
        value = 0;
    }

    public static Color getColor(int value){
        switch (value) {
            case 0:
                return new Color(0, 0, 0, 0);
            case 2:
                return new Color(238, 228, 218);
            case 4:
                return new Color(237, 224, 200);
            case 8:
                return new Color(242, 177, 121);
            case 16:
                return new Color(245, 149, 99);
            case 32:
                return new Color(246, 124, 95);
            case 64:
                return new Color(246, 94, 59);
            case 128:
                return new Color(237, 207,114);
            case 256:
                return new Color(237, 204, 97);
            case 512:
                return new Color(237,200,80);
            case 1024:
                return new Color(237,197,63);
            case 2048:
                return new Color(237, 194,46);
            case 4096:
                return new Color(101,218,146);
            case 8192:
                return new Color(90, 188,101);
            default:
                return new Color(36,140,81);
        }
    }

    public static Font getFont(int value) {
        if (value < 16) {
            return new Font("宋体", Font.BOLD, 46);
        }
        if (value < 128) {
            return new Font("黑体", Font.BOLD, 40);
        }
        if (value < 1024) {
            return new Font("黑体", Font.BOLD, 34);
        }
        if (value < 16384) {
            return new Font("黑体", Font.BOLD, 28);
        }
        return new Font("黑体", Font.BOLD, 22);
    }
}