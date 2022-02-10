package personal.debuggerzero.frame.title;

import javax.swing.*;
import java.awt.*;

/**
 * @author DebuggerZero
 */
public class TitleBarPanel extends JPanel {
    public TitleBarLeftPanel titleBarLeftPanel = new TitleBarLeftPanel("2048");

    public TitleBarPanel(JFrame jframe) {

        this.setLayout(new BorderLayout());
        //给父窗体初始化拖拽事件
        DragEvent.initDragEvent(jframe,this);
        //添加右界面
        this.add(new TitleBarRightPanel(jframe), BorderLayout.EAST);
        //添加左界面
        this.add(titleBarLeftPanel, BorderLayout.WEST);
        //设置高度
        this.setPreferredSize(new Dimension(0, 36));
        //设置背景
        this.setBackground(Color.WHITE);
    }

    public TitleBarLeftPanel getTitleBarLeftPanel() {
        return titleBarLeftPanel;
    }
}


