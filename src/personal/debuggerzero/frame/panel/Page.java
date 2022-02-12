package personal.debuggerzero.frame.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Debugger
 */
public abstract class Page extends JPanel implements ActionListener, MouseListener, KeyListener {

    public Page(){
        super(true);
        addMouseListener(this);
        addKeyListener(this);
    }

    public abstract void initPage() throws Exception;

    @Override
    public abstract void paint(Graphics g);

    @Override
    public void actionPerformed(ActionEvent e){}

    @Override
    public void mouseClicked(MouseEvent e){}

    @Override
    public void mousePressed(MouseEvent e){}

    @Override
    public void mouseReleased(MouseEvent e){}

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent e){}

    @Override
    public void keyTyped(KeyEvent e){}

    @Override
    public void keyPressed(KeyEvent e){}

    @Override
    public void keyReleased(KeyEvent e){}
}
