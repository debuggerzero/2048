package personal.debuggerzero.frame.title;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author DebuggerZero
 */
public class TitleBarRightPanel extends JPanel {

    // jFrame 是需要控制的目标窗口
    public TitleBarRightPanel(JFrame jFrame) {
        //右对齐
        FlowLayout rightFlowLayout = new FlowLayout(FlowLayout.RIGHT);
        rightFlowLayout.setHgap(10);
        this.setLayout(rightFlowLayout);
        // 最小化按钮
        TitleButton min = new TitleButton("—");
        min.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jFrame.setExtendedState(JFrame.ICONIFIED);
            }
        });
        //关闭按钮
        TitleButton close = new TitleButton("×");
        close.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jFrame.dispose();
                System.exit(0);
            }
        });
        this.setBackground(Color.WHITE);
        this.add(min);
        this.add(close);
    }
}

