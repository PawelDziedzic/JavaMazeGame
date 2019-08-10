import javafx.util.Pair;
import javax.swing.*;
import java.awt.*;


public class ImprovedMazePanel extends JPanel{
    boolean hasleft, hasright, hastop, hasbottom, isStart;
    ImprovedMazePanel left, right, top, bottom;
    static int wdth= JMaze.wdth/JMaze.maxX, hght= JMaze.hght/JMaze.maxY;
    static int lineWidth = wdth/10, lineHeight = hght/10;
    Pair<Integer, Integer> coordinates;
    int tolerance;

    ImprovedMazePanel(int x, int y) {
        super();
        setPreferredSize(new Dimension(wdth, hght));
        setLocation(0, 0);
        //setBackground(new Color(1f,0.9f,0.9f));
        setVisible(true);

        coordinates = new Pair<>(x, y);
        hasleft = false;
        hasright = false;
        hastop = false;
        hasbottom = false;
        top = null;
        bottom = null;
        left = null;
        right = null;
        isStart=false;
        tolerance =5;


        revalidate();
    }

    public ImprovedMazePanel getLeft() {
        return left;
    }

    public ImprovedMazePanel getRight() { return right; }

    public ImprovedMazePanel getTop() { return top; }

    public ImprovedMazePanel getBottom() { return bottom; }

    public void setRight(ImprovedMazePanel right) {
        this.right = right;
    }

    public void setLeft(ImprovedMazePanel left) {
        this.left = left;
    }

    public void setTop(ImprovedMazePanel top) {
        this.top = top;
    }

    public void setBottom(ImprovedMazePanel bottom) {
        this.bottom = bottom;
    }

    
}
