package personal.debuggerzero.frame;

import personal.debuggerzero.frame.panel.DropShadowPanel;
import personal.debuggerzero.frame.title.TitleBarPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author DebuggerZero
 */
public class MyFrame extends JFrame {

    DropShadowPanel dropShadowPanel = new DropShadowPanel();
    TitleBarPanel titleBarPanel;

    public MyFrame(int width, int height){
        titleBarPanel = new TitleBarPanel(this);
        //设置窗体尺寸
        this.setSize(width, height + 36);
        //设置窗体布局管理器
        this.setLayout(new BorderLayout());
        //为窗体添加阴影
        dropShadowPanel.add(titleBarPanel, BorderLayout.NORTH);
        //添加标题栏
        super.add(dropShadowPanel);
        //设置窗体无边框
        this.setUndecorated(true);
        //设置透明背景
        this.setBackground(new Color(255,255,255,0));
        //窗体居中
        this.setLocationRelativeTo(null);
        //设置窗口可见
        this.setVisible(true);
    }

    @Override
    public Component add(Component comp) {
        dropShadowPanel.add(comp);
        return comp;
    }
}