package personal.debuggerzero.game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author DebuggerZero
 */
public class Check extends Area {
    //移动步长
    public int stepSizeX;
    public int stepSizeY;

    public Check(int x, int y, int value) {
        super(x, y, value);
        stepSizeX = 0;
        stepSizeY = 0;
    }

    public BufferedImage checkPaint(){
        BufferedImage checkImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = checkImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(getColor());
        g.fillOval(0,0, WIDTH, HEIGHT);
        Color color = value == 0 ? new Color(0,0,0,0) : value <= 4 ? Color.BLACK:Color.WHITE;
        g.setColor(color);
        Font font= Check.getFont(value);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics(font);
        int widthX = (checkImage.getWidth() - fm.stringWidth(Integer.toString(value))) / 2;
        int widthY = (checkImage.getHeight() - fm.getDescent() + fm.getAscent()) / 2;
        g.drawString(Integer.toString(value), widthX, widthY);
        g.dispose();
        return checkImage;
    }

    public Color getColor(){
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