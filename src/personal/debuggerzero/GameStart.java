package personal.debuggerzero;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author DebuggerZero
 */
public class GameStart extends Page {

    private final String HOME_PAGE_PATH = "assets\\HomePage.png";

    private BufferedImage homeGround;

    @Override
    public void initPage() throws Exception{
        homeGround = ImageIO.read(new File(HOME_PAGE_PATH));
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g.drawImage(homeGround, 0, 0 ,null);
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
                GameFrame.gameMain.requestFocus(true);
            }
        }
    }

    public GameStart() throws Exception{
        initPage();
    }
}