import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class JRamka extends JFrame implements KeyListener{

    JMaze content;

    public JRamka() {
        super("Labirynt");
        setSize(new Dimension(900, 900));
        setLayout(new FlowLayout());
        setLocation(250, 50);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        addKeyListener(this);

        content = new JMaze();
        add(content);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode())
        {
            case 37: content.moveLeft();break;
            case 38: content.moveUp();break;
            case 39: content.moveRight(); break;
            case 40: content.moveDown();break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}