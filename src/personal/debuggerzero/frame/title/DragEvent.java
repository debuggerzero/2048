package personal.debuggerzero.frame.title;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * @author DebuggerZero
 */
public class DragEvent {
    public static void initDragEvent(Component moveComponent, Component dragTarget){
        final boolean[] isDragging = new boolean[1];
        final int[] x = new int[1];
        final int[] y = new int[1];

        dragTarget.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isDragging[0] = true;
                x[0] = e.getX();
                y[0] = e.getY();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                isDragging[0] = false;
            }
        });

        dragTarget.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                try {
                    JFrame jFrame = ((JFrame) moveComponent);
                    int state =  jFrame.getExtendedState();

                    if(state == JFrame.MAXIMIZED_BOTH){
                        int sideWidth = moveComponent.getWidth() - e.getComponent().getWidth();
                        jFrame.setExtendedState(JFrame.NORMAL);
                        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
                        double rate = ((double) (e.getXOnScreen() - sideWidth)/ (double) width);
                        int resultX = (int) ((moveComponent.getWidth()-sideWidth)   * rate);
                        x[0] = resultX;
                        moveComponent.setLocation(e.getXOnScreen() - resultX ,y[0]);

                    }else{
                        if (isDragging[0]) {
                            int left = moveComponent.getLocation().x;
                            int top = moveComponent.getLocation().y;
                            moveComponent.setLocation(left + e.getX() - x[0], top + e.getY() - y[0]);
                        }
                    }
                }catch (Exception e1){
                    e1.printStackTrace();
                }

            }
        });
    }

}

