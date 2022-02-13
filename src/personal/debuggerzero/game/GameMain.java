package personal.debuggerzero.game;

import personal.debuggerzero.frame.panel.Page;
import personal.debuggerzero.game.storage.Storage;

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

    private Storage save = new Storage();

    private Timer timer;

    enum Direction {
        up, down, left, right
    }

    private Area[] area = new Area[LINE * ROW];
    private HashMap<Integer, Check> checkList = new HashMap<>();
    private Queue<Direction> moveList = new LinkedList<>();

    private final int MOVE_NUMBER = 8;
    private int moveNumber = 0;
    private boolean moveFlag = false;
    private Direction direction;

    protected void initGame(){
        score = 0;
        try {
            bestScore = save.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < LINE * ROW; i++){
            area[i] = new Area((i % 4) * (Area.WIDTH + 8), (i / 4) * (Area.HEIGHT + 8), 0);
        }
        moveList.clear();
        checkList.clear();
        createCheck();
        timer.start();
    }

    protected void createCheck(){
        ArrayList<Area> list = getEmptyArea();
        if (!list.isEmpty()){
            Random random = new Random();
            int index = random.nextInt(list.size());
            Area area = list.get(index);
            area.value = (random.nextInt(4) % 4 != 0) ? 2 : 4;
            int cindex = area.x / (Area.WIDTH + 8) + area.y / (Area.HEIGHT + 8) * 4;
            checkList.put(cindex, new Check(area.x, area.y, area.value));
        }
    }

    protected ArrayList<Area> getEmptyArea(){
        ArrayList<Area> list = new ArrayList<>();
        for (int i = 0; i < LINE * ROW; i++){
            if (area[i].value == 0){
                list.add(area[i]);
            }
        }
        return list;
    }

    protected boolean gameOver(){
        ArrayList<Area> list = getEmptyArea();
        if (list.isEmpty()){
            for (int i = 0; i < LINE - 1; i++){
                for (int j = 0; j < ROW - 1; j++){
                    if (area[i + j * 4].value == area[i + (j + 1) * 4].value || area[i * 4 + j].value == area[i * 4 + j + 1].value){
                        return false;
                    }
                }
            }
            for (int i = 12; i < LINE * ROW - 1; i++){
                if(area[i].value == area[i + 1].value){
                    return false;
                }
            }
            for (int i = 3; i < LINE * ROW - 1; i += LINE){
                if(area[i].value == area[i + 4].value){
                    return false;
                }
            }
        }
        return list.isEmpty();
    }

    protected BufferedImage mapPaint(HashMap<Integer, Check> checkList){
        BufferedImage map = new BufferedImage(Check.WIDTH * 4 + 24, Check.HEIGHT * 4 + 24, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = map.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //绘制方块区域
        g.setColor(new Color(255, 255, 255, 50));
        g.fillRoundRect(0, 0, Check.WIDTH * 4 + 24, Check.HEIGHT * 4 + 24, 35,35);
        for (Integer i : checkList.keySet()) {
            g.drawImage(checkPaint(checkList.get(i)), checkList.get(i).x, checkList.get(i).y, null);
        }
        return  map;
    }

    protected BufferedImage checkPaint(Check check){
        BufferedImage checkImage = new BufferedImage(Check.WIDTH, Check.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = checkImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(check.getColor());
        g.fillOval(0,0, Check.WIDTH, Check.HEIGHT);
        Color color = check.value == 0 ? new Color(0,0,0,0) : check.value <= 4 ? Color.BLACK:Color.WHITE;
        g.setColor(color);
        Font font= Check.getFont(check.value);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics(font);
        int widthX = (checkImage.getWidth() - fm.stringWidth(Integer.toString(check.value))) / 2;
        int widthY = (checkImage.getHeight() - fm.getDescent() + fm.getAscent()) / 2;
        g.drawString(Integer.toString(check.value), widthX, widthY);
        g.dispose();
        return checkImage;
    }

    protected BufferedImage boxPaint(BufferedImage image, String text, int score){
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
        //绘制地图
        g2.drawImage(mapPaint(checkList), MAP_X, MAX_Y, null);
        //绘制得分框
        g2.drawImage(boxPaint(scoreBox, "得分", score), SCORE_BOX_X, SCORE_BOX_Y, null);
        //绘制最佳记录框
        g2.drawImage(boxPaint(bestScoreBox, "最高记录", bestScore), BEST_SCORE_BOX_X, BEST_SCORE_BOX_Y, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //重绘界面
        this.repaint();

        if (!moveList.isEmpty()){
            if (moveNumber == 0){
                direction = moveList.poll();
                moveFlag = true;
            }
        }
        if (moveFlag && moveNumber < MOVE_NUMBER){
            moveNumber = moveAnimation(direction, moveNumber);
        }
        else if (moveFlag && moveNumber == MOVE_NUMBER){
            moveFlag = false;
            moveNumber = 0;
            createCheck();
            for(Integer i: checkList.keySet()){
                checkList.get(i).stepSizeX = 0;
                checkList.get(i).stepSizeY = 0;
            }
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
    }

    protected int moveAnimation(Direction direction, int number){
        if (number == 0){
            move(direction);
        }
        switch (direction){
            case up:
                for (Integer i : checkList.keySet()){
                    checkList.get(i).y -= checkList.get(i).stepSizeY;
                }
                break;
            case down:
                for (Integer i : checkList.keySet()){
                    checkList.get(i).y += checkList.get(i).stepSizeY;
                }
                break;
            case left:
                for (Integer i : checkList.keySet()){
                    checkList.get(i).x -= checkList.get(i).stepSizeX;
                }
                break;
            case right:
                for (Integer i : checkList.keySet()){
                    checkList.get(i).x += checkList.get(i).stepSizeX;
                }
                break;
        }
        return ++number;
    }

    protected void move(Direction direction){
        switch (direction){
            case up:
                moveUp();
                break;
            case down:
                moveDown();
                break;
            case left:
                moveLeft();
                break;
            case right:
                moveRight();
                break;
        }
    }

    protected void moveUp(){
        boolean flag;
        for (int x = 0; x < LINE; x++) {
            for (int y = 1, index = 0; y < ROW; y++) {
                flag = false;
                if (area[x + y * 4].value > 0) {
                    if (area[x + y * 4].value == area[x + index * 4].value) {
                        area[x + y * 4].value = 0;
                        checkList.get(x + y * 4).value = area[x + index * 4].value <<= 1;
                        score += area[x + index * 4].value;
                        flag = true;
                    } else if (area[x + index * 4].value == 0 || area[x + (++index) * 4].value == 0) {
                        area[x + index * 4].value = area[x + y * 4].value;
                        area[x + y * 4].value = 0;
                        flag = true;
                    }
                }
                if (flag) {
                    checkList.put(x + index * 4, checkList.remove(x + y * 4));
                    checkList.get(x + index * 4).stepSizeY = (checkList.get(x + index * 4).y - area[x + index * 4].y) / MOVE_NUMBER;
                }
            }
        }
    }

    protected void moveDown(){
        boolean flag;
        for (int x = LINE - 1; x >= 0; x--){
            for (int y = ROW - 2, index = ROW - 1; y >=0 ; y--){
                flag = false;
                if (area[x + y * 4].value > 0) {
                    if (area[x + y * 4].value == area[x + index * 4].value) {
                        checkList.get(x + y * 4).value = area[x + index * 4].value <<= 1;
                        area[x + y * 4].value = 0;
                        score += area[x + index * 4].value;
                        flag = true;
                    } else if (area[x + index * 4].value == 0 || area[x + (--index) * 4].value == 0) {
                        area[x + index * 4].value = area[x + y * 4].value;
                        area[x + y * 4].value = 0;
                        flag = true;
                    }
                }
                if (flag) {
                    checkList.put(x + index * 4, checkList.remove(x + y * 4));
                    checkList.get(x + index * 4).stepSizeY = (area[x + index * 4].y - checkList.get(x + index * 4).y) / MOVE_NUMBER;
                }
            }
        }
    }

    protected void moveLeft(){
        boolean flag;
        for (int x = 0; x < LINE; x++){
            for (int y = 1, index = 0; y < ROW; y++){
                flag = false;
                if (area[x * 4 + y].value > 0) {
                    if (area[x * 4 + y].value == area[index + x * 4].value) {
                        checkList.get(x * 4 + y).value = area[index + x * 4].value <<= 1;
                        area[x * 4 + y].value = 0;
                        score += area[index + x * 4].value;
                        flag = true;
                    } else if (area[index + x * 4].value == 0) {
                        area[index + x * 4].value = area[x * 4 + y].value;
                        area[x * 4 + y].value = 0;
                        flag = true;
                    } else if (area[(++index) + x * 4].value == 0) {
                        area[index + x * 4].value = area[x * 4 + y].value;
                        area[x * 4 + y].value = 0;
                        flag = true;
                    }
                }
                if (flag) {
                    checkList.put(index + x * 4, checkList.remove(x * 4 + y));
                    checkList.get(index + x * 4).stepSizeX = (checkList.get(index + x * 4).x - area[index + x * 4].x) / MOVE_NUMBER;
                }
            }
        }
    }

    protected void moveRight(){
        boolean flag;
        for (int x = LINE - 1; x >= 0; x--){
            for (int y = ROW - 2, index = ROW - 1; y >=0 ; y--){
                flag = false;
                if (area[x * 4 + y].value > 0) {
                    if (area[x * 4 + y].value == area[index + x * 4].value) {
                        checkList.get(x * 4 + y).value = area[index + x * 4].value <<= 1;
                        area[x * 4 + y].value = 0;
                        score += area[index + x * 4].value;
                        flag = true;
                    } else if (area[index + x * 4].value == 0 || area[(--index) + x * 4].value == 0) {
                        area[index + x * 4].value = area[x * 4 + y].value;
                        area[x * 4 + y].value = 0;
                        flag = true;
                    }
                }
                if (flag) {
                    checkList.put(index + x * 4, checkList.remove(x * 4 + y));
                    checkList.get(index + x * 4).stepSizeX = (area[index + x * 4].x - checkList.get(index + x * 4).x) / MOVE_NUMBER;
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            moveList.offer(Direction.up);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            moveList.offer(Direction.down);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            moveList.offer(Direction.right);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            moveList.offer(Direction.left);
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