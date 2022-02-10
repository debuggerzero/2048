package personal.debuggerzero.game;

import personal.debuggerzero.GameFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author DebuggerZero
 */
public class GameStart extends Page {

    private final String GROUND_PATH = "assets\\BackGround.png";
    private final String TITLE_PATH = "assets\\2048.png";
    private final String message = "-点击任意处开始游戏-";
    private final String author = "AUTHOR: ZERO";

    private BufferedImage homeGround;
    private BufferedImage title;

    Timer timer;

    @Override
    public void initPage() throws Exception{
        homeGround = ImageIO.read(new File(GROUND_PATH));
        title = ImageIO.read(new File(TITLE_PATH));
        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();


    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(homeGround, 0, 0 ,null);
        g2.drawImage(title, (GameFrame.WIDTH - title.getWidth()) / 2, GameFrame.HEIGHT / 5, null);
        Font font = new Font("黑体", Font.PLAIN,24);
        g2.setFont(font);
        FontMetrics fm = g.getFontMetrics(font);
        int widthX = (GameFrame.WIDTH - fm.stringWidth(message)) / 2;
        g.drawString(message, widthX, GameFrame.HEIGHT / 5 * 4);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1){
            int result = JOptionPane.showConfirmDialog(
                    null,
                    "<html>按 W、A、S、D 或 ↑、↓、←、→<br>移动方块。<br>按 Esc 重新开始游戏。</html>",
                    "提示", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE
            );
            if (result == JOptionPane.YES_OPTION){
                GameFrame.cardLayout.show(GameFrame.card,"GameMain");
                try {
                    GameFrame.gameMain.initPage();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                timer.stop();
                GameFrame.gameMain.requestFocus(true);
            }
        }
    }

    public GameStart() throws Exception{
        initPage();
    }
}