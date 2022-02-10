package personal.debuggerzero.game;

import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author DebuggerZero
 */
public class GameMain extends Page {

    private final String GROUND_PATH = "assets\\BackGround.png";
    private final String SCORE_BOX_PATH = "assets\\ScoreBox.png";

    private BufferedImage backGround;
    private BufferedImage scoreBox;
    private BufferedImage bestScoreBox;

    private final int BACKGROUND_X = 0;
    private final int BACKGROUND_Y = 0;

    private final int MAP_X = 43;
    private final int MAX_Y = 230;

    private final int SCORE_BOX_X = 43;
    private final int SCORE_BOX_Y = 35;

    private final int BEST_SCORE_BOX_X = 301;
    private final int BEST_SCORE_BOX_Y = 35;

    private int score;
    private int bestScore;

    private final int LINE = 4;
    private final int ROW = 4;

    private Check[][] check = new Check[LINE][ROW];

    private Storage save = new Storage();

    private Timer timer;

    enum Move {
        up, down, left, right
    }

    Queue<Move> moveList = new LinkedList<>();

    private void initGame(){
        score = 0;
        try {
            bestScore = save.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < LINE; i++){
            for (int j = 0; j < ROW; j++){
                check[i][j] = new Check();
            }
        }
        createCheck();
        timer.start();
    }

    private void createCheck(){
        ArrayList<Check> list = getEmptyChecks();
        if (!list.isEmpty()){
            Random random = new Random();
            int index = random.nextInt(list.size());
            Check check = list.get(index);
            check.value = (random.nextInt(4) % 4 != 0) ? 2 : 4;
        }
    }

    private ArrayList<Check> getEmptyChecks(){
        ArrayList<Check> list = new ArrayList<>();
        for (int i = 0; i < LINE; i++){
            for (int j = 0; j < ROW; j++){
                if (check[i][j].value == 0){
                    list.add(check[i][j]);
                }
            }
        }
        return list;
    }

    private boolean gameOver(){
        ArrayList<Check> list = getEmptyChecks();
        if (list.isEmpty()){
            for (int i = 0; i < LINE - 1; i++){
                for (int j = 0; j < ROW - 1; j++){
                    if (check[i][j].value == check[i+1][j].value || check[i][j].value == check[i][j + 1].value){
                        return false;
                    }
                }
            }
            for (int i = 0; i < LINE - 1; i++){
                if(check[LINE - 1][i].value == check[LINE - 1][i + 1].value){
                    return false;
                }
            }
            for (int i = 0; i < ROW - 1; i++){
                if(check[i][ROW - 1].value == check[i + 1][ROW -1].value){
                    return false;
                }
            }
        }
        return list.isEmpty();
    }

    //绘制方块
    private BufferedImage checkPaint(int value){
        BufferedImage checkImage = new BufferedImage(Check.WIDTH, Check.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = checkImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Check.getColor(value));
        g.fillOval(0,0, Check.WIDTH, Check.HEIGHT);
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

    //绘制得分框
    private BufferedImage boxPaint(BufferedImage image, String text, int score){
        BufferedImage scoreImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scoreImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(image,0,0,null);
        g.setColor(Color.WHITE);
        Font font=new Font("黑体",Font.PLAIN,35);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics(font);
        int widthX = (scoreImage.getWidth() - fm.stringWidth(text)) / 2;
        g.drawString(text, widthX, 65);
        widthX = (scoreImage.getWidth() - fm.stringWidth(Integer.toString(score))) / 2;
        g.drawString(Integer.toString(score), widthX, 125);
        g.dispose();
        return scoreImage;
    }

    @Override
    public void initPage() throws Exception {
        backGround = ImageIO.read(new File(GROUND_PATH));
        scoreBox = ImageIO.read(new File(SCORE_BOX_PATH));
        bestScoreBox = ImageIO.read(new File(SCORE_BOX_PATH));
        timer = new Timer(10, this);
        initGame();
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //绘制游戏背景
        g2.drawImage(backGround,BACKGROUND_X,BACKGROUND_Y,null);
        //绘制方块区域
        g2.setColor(new Color(255, 255, 255, 50));
        g2.fillRoundRect(MAP_X, MAX_Y, 504, 504, 35,35);

        //绘制得分框
        g2.drawImage(boxPaint(scoreBox, "得分", score), SCORE_BOX_X, SCORE_BOX_Y, null);
        //绘制最佳记录框
        g2.drawImage(boxPaint(bestScoreBox, "最高记录", bestScore), BEST_SCORE_BOX_X, BEST_SCORE_BOX_Y, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //重绘界面
        this.repaint();

        //判断游戏是否结束
        if (gameOver()){
            String message;
            timer.stop();
            if (bestScore < score){
                try {
                    save.write(score);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                message = "<html>恭喜你破纪录了！！<br>请点击确定重新开始...</html>";
            }
            else {
                message = "游戏结束，请点击确定重新开始...";
            }
            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "提示",
                    JOptionPane.PLAIN_MESSAGE
            );
            initGame();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            moveList.offer(Move.up);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            moveList.offer(Move.down);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            moveList.offer(Move.right);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            moveList.offer(Move.left);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            timer.stop();
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "请点击是重新开始...",
                    "提示",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );
            if (result == JOptionPane.YES_OPTION){
                initGame();
            }
            else {
                timer.start();
            }
        }
    }

    //构造函数
    public GameMain(){}
}