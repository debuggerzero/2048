package personal.debuggerzero;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author DebuggerZero
 */
public class GameFrame {

    //定义窗体大小
    public final static int WIDTH = 600;
    public final static int HEIGHT = 800;

    private final JFrame jFrame = new JFrame("2048");

    private final JPanel card = new JPanel();

    private final CardLayout cardLayout = new CardLayout();

    private GameStart gameStart;
    private GameMain gameMain;

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

    private void mouseEvent(){
        //gameState画板注册鼠标监听事件
        gameStart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                   int result = JOptionPane.showConfirmDialog(
                           gameStart,
                           "<html>按 W、A、S、D 或 ↑、↓、←、→<br>移动方块。<br>按 Esc 重新开始游戏。</html>",
                           "提示", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE
                   );
                   if (result == JOptionPane.YES_OPTION){
                       cardLayout.show(card,"GameMain");
                       gameMain.requestFocus(true);
                   }
                }
            }
        });
    }

    public GameFrame() throws Exception{
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        initCard();
        initFrame();
        mouseEvent();
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