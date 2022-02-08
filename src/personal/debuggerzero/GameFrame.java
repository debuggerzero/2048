package personal.debuggerzero;

import javax.swing.*;
import java.awt.*;

/**
 * @author DebuggerZero
 */
public class GameFrame {

    //定义窗体大小
    public final static int WIDTH = 600;
    public final static int HEIGHT = 800;

    private final JFrame jFrame = new JFrame("2048");

    public static JPanel card = new JPanel();

    public static CardLayout cardLayout = new CardLayout();

    public static Page gameStart;
    public static Page gameMain;

    private void initFrame(){
        //设置窗体尺寸
        jFrame.setBounds(new Rectangle(WIDTH, HEIGHT));
        //窗体居中
        jFrame.setLocationRelativeTo(null);
        //设置窗体不可改变大小
        jFrame.setResizable(false);
        //关闭窗口事件
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗体可见
        jFrame.setVisible(true);
    }

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

        jFrame.add(card);
    }

    public GameFrame() throws Exception{
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        initCard();
        initFrame();
        SwingUtilities.updateComponentTreeUI(jFrame.getContentPane());
    }

    public static void main(String[] args){
        try {
            new GameFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}