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

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,lineWidth,lineHeight);
        g.fillRect(0,9*lineHeight,lineWidth,lineHeight);
        g.fillRect(9*lineWidth,0,lineWidth,lineHeight);
        g.fillRect(9*lineWidth,9*lineHeight,lineWidth,lineHeight);
        if(isStart){
            g.setColor(Color.RED);
            g.fillRect(2*lineWidth,2*lineHeight,6*lineWidth,6*lineHeight);
            g.setColor(Color.BLACK);
        }
        if(coordinates.getKey()==JMaze.maxX&&coordinates.getValue()==0)
            g.drawRect(2*lineWidth,2*lineHeight,6*lineWidth,6*lineHeight);
        if(bottom==null)
            g.fillRect(lineWidth,9*lineHeight,8*lineWidth,lineHeight);
        if(top==null)
            g.fillRect(lineWidth,0,8*lineWidth,lineHeight);
        if(left==null)
            g.fillRect(0,lineHeight,lineWidth,8*lineHeight);
        if(right==null)
            g.fillRect(9*lineWidth,lineHeight,lineWidth,8*lineHeight);

    }

    void insertUp(ImprovedMazePanel imp)
    {
        top = imp;
        hastop = true;
    }

    void insertDown(ImprovedMazePanel imp)
    {
        bottom = imp;
        hasbottom = true;
    }

    void insertLeft(ImprovedMazePanel imp)
    {
        left = imp;
        hasleft = true;
    }

    void insertRight(ImprovedMazePanel imp)
    {
        right = imp;
        hasright = true;
    }
}
