package personal.debuggerzero;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @author DebuggerZero
 */
public class GameStart extends JPanel {

    private final String HOME_PAGE_PATH = "assets\\HomePage.png";

    private Image gameStart;
    private Image homeGround;

    public GameStart() throws Exception{
        homeGround = ImageIO.read(new File(HOME_PAGE_PATH));
    }

    private void pagePaint(Graphics2D g){
        g.drawImage(homeGround, 0, 0 ,null);
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        pagePaint(g2);
    }

    @Override
    public void update(Graphics g){
        if (gameStart == null){
            gameStart = this.createImage(GameFrame.WIDTH, GameFrame.HEIGHT);
        }
        Graphics gg = gameStart.getGraphics();
        paint(gg);
        g.drawImage(gameStart, 0, 0, null);
    }
}