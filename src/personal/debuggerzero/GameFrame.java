package personal.debuggerzero;

import personal.debuggerzero.frame.MyFrame;
import personal.debuggerzero.game.GameMain;
import personal.debuggerzero.game.GameStart;
import personal.debuggerzero.frame.panel.Page;

import javax.swing.*;
import java.awt.*;

/**
 * @author DebuggerZero
 */
public class GameFrame {

    //定义窗体大小
    public final static int WIDTH = 600;
    public final static int HEIGHT = 800;

    private MyFrame myFrame = new MyFrame(WIDTH, HEIGHT);

    public static JPanel card = new JPanel();

    public static CardLayout cardLayout = new CardLayout();

    public static Page gameStart;
    public static Page gameMain;

    private void initCard() {
        //设置 JPanel 组件的布局
        card.setLayout(cardLayout);
        try {
            gameStart = new GameStart();
            gameMain = new GameMain();
        } catch (Exception e) {
            e.printStackTrace();
        }

        card.add("GameStart", gameStart);
        card.add("GameMain", gameMain);

        myFrame.add(card);
    }

    public GameFrame() throws Exception{
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        initCard();
        SwingUtilities.updateComponentTreeUI(myFrame.getContentPane());
    }

    public static void main(String[] args){
        try {
            new GameFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}