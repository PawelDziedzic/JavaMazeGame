import javafx.util.Pair;
import javax.swing.*;
import java.awt.*;


public class SingleMazePanel extends JPanel {

    boolean hasleft, hasright, hastop, hasbottom, isStart;
    SingleMazePanel left, right, top, bottom;
    static int wdth= JMaze.wdth/JMaze.maxX, hght= JMaze.hght/JMaze.maxY;
    static int lineWidth = wdth/10, lineHeight = hght/10;
    Pair<Integer, Integer> coordinates;
    int tolerance;

    SingleMazePanel(int x, int y) {
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

    public SingleMazePanel getLeft() {
        return left;
    }

    public SingleMazePanel getRight() { return right; }

    public SingleMazePanel getTop() { return top; }

    public SingleMazePanel getBottom() { return bottom; }

    public void setRight(SingleMazePanel right) {
        this.right = right;
    }

    public void setLeft(SingleMazePanel left) {
        this.left = left;
    }

    public void setTop(SingleMazePanel top) {
        this.top = top;
    }

    public void setBottom(SingleMazePanel bottom) {
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

    void showText() {
        System.out.println(coordinates);
        if (top != null)
            System.out.print(" Top: " + top.coordinates);
        if (bottom != null)
            System.out.print(" Bottom: " + bottom.coordinates);
        if (left != null)
            System.out.print(" Left: " + left.coordinates);
        if (right != null)
            System.out.print(" Right: " + right.coordinates);
        System.out.println();
    }

    boolean insert(int x, int y) {
        if (insertNear( x, y)) {
            return true;
        }
        if (bottom != null&&y+tolerance>=coordinates.getValue())
            if (bottom.insertDown( x, y))
                return true;
        if (left != null&&x<=coordinates.getKey()+tolerance)
            if (left.insertLeft( x, y))
                return true;
        if (right != null&&x+tolerance>=coordinates.getKey())
            if (right.insertRight( x, y))
                return true;
        if (top != null&&y<=coordinates.getValue()+tolerance)
            if (top.insertUp( x, y))
                return true;
        return false;
    }

    boolean insertUp(int x, int y)
    {
        if (insertNear( x, y))
            return true;
        if (right != null&&x+tolerance>=coordinates.getKey())
            if (right.insertRight( x, y))
                return true;
        if (top != null&&y<=coordinates.getValue()+tolerance)
            if (top.insertUp( x, y))
                return true;
        if (left != null&&x<=coordinates.getKey()+tolerance)
            if (left.insertLeft( x, y))
                return true;

        return false;
    }

    boolean insertDown(int x, int y) {
        if (insertNear( x, y))
            return true;
        if (left != null&&x<=coordinates.getKey()+tolerance)
            if (left.insertLeft( x, y))
                return true;
        if (bottom != null&&y+tolerance>=coordinates.getValue())
            if (bottom.insertDown( x, y))
                return true;
        if (right != null&&x+tolerance>=coordinates.getKey())
            if (right.insertRight( x, y))
                return true;

        return false;
    }

    boolean insertLeft(int x, int y) {
        if (insertNear( x, y))
            return true;
        if (top != null&&y<=coordinates.getValue()+tolerance)
            if (top.insertUp( x, y))
                return true;
        if (bottom != null&&y+tolerance>=coordinates.getValue())
            if (bottom.insertDown( x, y))
                return true;
        if (left != null&&x<=coordinates.getKey()+tolerance)
            if (left.insertLeft( x, y))
                return true;

        return false;
    }

    boolean insertRight(int x, int y) {
        if (insertNear( x, y))
            return true;
        if (bottom != null&&y+tolerance>=coordinates.getValue())
            if (bottom.insertDown( x, y))
                return true;
        if (right != null&&x+tolerance>=coordinates.getKey())
            if (right.insertRight( x, y))
                return true;
        if (top != null&&y<=coordinates.getValue()+tolerance)
            if (top.insertUp( x, y))
                return true;

        return false;
    }

    boolean insertNear(int x, int y) {
        if (isBottom( x, y)) {
            setBottom(new SingleMazePanel( x, y));
            getBottom().setTop(this);
            return true;
        } else if (isTop( x, y)) {
            setTop(new SingleMazePanel( x, y));
            getTop().setBottom(this);
            return true;
        } else if (isLeft( x, y)) {
            setLeft(new SingleMazePanel( x, y));
            getLeft().setRight(this);
            return true;
        } else if (isRight( x, y)) {
            setRight(new SingleMazePanel( x, y));
            getRight().setLeft(this);
            return true;
        } else
            return false;
    }

    boolean isLeft(int x, int y) {
        return y==coordinates.getValue() && x + 1 == coordinates.getKey();
    }

    boolean isRight(int x, int y) {
        return y==coordinates.getValue()&& x == coordinates.getKey() + 1;
    }

    boolean isTop(int x, int y) {
        return y + 1 == coordinates.getValue() && x==coordinates.getKey();
    }

    boolean isBottom(int x, int y) {
        return y == coordinates.getValue() + 1 && x==coordinates.getKey();
    }

    SingleMazePanel find(int x, int y) {
        SingleMazePanel temp;
        if (x==coordinates.getKey() && y==coordinates.getValue())
            return this;
        if (bottom != null&&y+tolerance>=coordinates.getValue()) {
            temp = bottom.findDown( x, y);
            if ( temp!= null)
                return temp;
        }
        if (left != null&&x<=coordinates.getKey()+tolerance) {
            temp = left.findLeft( x, y);
            if (temp != null)
                return temp;
        }
        if (right != null&&x+tolerance>=coordinates.getKey()) {
            temp = right.findRight( x, y);
            if (temp != null)
                return temp;
        }
        if (top != null&&y<=coordinates.getValue()+tolerance) {
            temp = top.findUp( x, y);
            if (temp != null)
                return temp;
        }

        return null;
    }

    SingleMazePanel findUp(int x, int y) {
        SingleMazePanel temp;
        if (x==coordinates.getKey() && y==coordinates.getValue())
            return this;
        if (right != null&&x+tolerance>=coordinates.getKey()) {
            temp = right.findRight( x, y);
            if (temp != null)
                return temp;
        }
        if (top != null&&y<=coordinates.getValue()+tolerance) {
            temp = top.findUp( x, y);
            if (temp != null)
                return temp;
        }
        if (left != null&&x<=coordinates.getKey()+tolerance) {
            temp = left.findLeft( x, y);
            if (temp != null)
                return temp;
        }

        return null;
    }

    SingleMazePanel findDown(int x, int y) {
        SingleMazePanel temp;
        if (x==coordinates.getKey() && y==coordinates.getValue())
            return this;
        if (left != null&&x<=coordinates.getKey()+tolerance) {
            temp = left.findLeft( x, y);
            if (temp != null)
                return temp;
        }
        if (bottom != null&&y+tolerance>=coordinates.getValue()) {
            temp = bottom.findDown( x, y);
            if ( temp!= null)
                return temp;
        }
        if (right != null&&x+tolerance>=coordinates.getKey()) {
            temp = right.findRight( x, y);
            if (temp != null)
                return temp;
        }

        return null;
    }

    SingleMazePanel findLeft(int x, int y) {
        SingleMazePanel temp;
        if (x==coordinates.getKey() && y==coordinates.getValue())
            return this;
        if (top != null&&y<=coordinates.getValue()+tolerance) {
            temp = top.findUp( x, y);
            if (temp != null)
                return temp;
        }
        if (bottom != null&&y+tolerance>=coordinates.getValue()) {
            temp = bottom.findDown( x, y);
            if ( temp!= null)
                return temp;
        }
        if (left != null&&x<=coordinates.getKey()+tolerance) {
            temp = left.findLeft( x, y);
            if (temp != null)
                return temp;
        }

        return null;
    }

    SingleMazePanel findRight(int x, int y) {
        SingleMazePanel temp;
        if (x==coordinates.getKey() && y==coordinates.getValue())
            return this;
        if (bottom != null&&y+tolerance>=coordinates.getValue()) {
            temp = bottom.findDown( x, y);
            if ( temp!= null)
                return temp;
        }
        if (right != null&&x+tolerance>=coordinates.getKey()) {
            temp = right.findRight( x, y);
            if (temp != null)
                return temp;
        }
        if (top != null&&y<=coordinates.getValue()+tolerance) {
            temp = top.findUp( x, y);
            if (temp != null)
                return temp;
        }

        return null;
    }

    void unicode() {
        if (bottom != null) {
            if (top != null) {
                if (left != null) {
                    if (right != null)
                        System.out.print('\u256C');
                    else
                        System.out.print('\u2563');
                } else {
                    if (right != null)
                        System.out.print('\u2560');
                    else
                        System.out.print('\u2551');
                }
            } else {
                if (left != null) {
                    if (right != null)
                        System.out.print('\u2566');
                    else
                        System.out.print('\u2557');
                } else {
                    if (right != null)
                        System.out.print('\u2554');
                    else
                        System.out.print('\u2565');
                }
            }
        } else {
            if (top != null) {
                if (left != null) {
                    if (right != null)
                        System.out.print('\u2569');
                    else
                        System.out.print('\u255D');
                } else {
                    if (right != null)
                        System.out.print('\u255A');
                    else
                        System.out.print('\u2568');
                }
            } else {
                if (left != null) {
                    if (right != null)
                        System.out.print('\u2550');
                    else
                        System.out.print('\u2561');
                } else {
                    if (right != null)
                        System.out.print('\u255E');
                    else
                        System.out.print('\u25A1');
                }
            }
        }
    }
}
