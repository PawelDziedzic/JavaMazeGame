import javafx.util.Pair;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections.*;


public class JMaze extends JPanel {

    ImprovedMazePanel[][] arrayOfPanels;
    ArrayList<Pair<Integer, Integer>> listaPar;
    ArrayList<Pair<Integer, Integer>> pastListaPar;
    String[][] cheatArray;
    ArrayList<Pair<Integer,Integer>> errorList;
    static int maxX, maxY;
    int playerX, playerY;
    int iterCounter;

    static int wdth, hght;
    ArrayList<Integer> orderOfCalls;

    JMaze() {
        super();
        maxX = 29;
        maxY = 29;
        listaPar = new ArrayList<>();
        pastListaPar = new ArrayList<>();
        errorList = new ArrayList<>();
        arrayOfPanels = new ImprovedMazePanel[maxX+1][maxY+1];
        wdth = 600;
        hght = 600;
        iterCounter =0;
        orderOfCalls = new ArrayList<>();
        orderOfCalls.add(0);
        orderOfCalls.add(1);
        orderOfCalls.add(2);
        orderOfCalls.add(3);

        setPreferredSize(new Dimension(wdth, hght));
        setLayout(new GridLayout(maxY+1,maxX+1));
        setBackground(new Color(1f,0f,1f));
        CreateGoodMaze();
        for (int i = 0; i <= maxY; i++) {
            for (int j = 0; j <= maxX; j++) {
                //testArray[j][i].revalidate();
               // add(testArray[j][i]);
                arrayOfPanels[j][i].revalidate();
                add(arrayOfPanels[j][i]);
            }
        }


        revalidate();
    }

    void CreateGoodMaze()
    {
        java.util.Collections.shuffle(orderOfCalls);

        Random generator = new Random();
        Integer x0;
        x0 = (int) (generator.nextDouble() * maxX/2);
        Integer y0;
        y0 = (int) (generator.nextDouble() * maxY/2)+maxY/2;
        playerX = x0;
        playerY = y0;

        pastListaPar.add(new Pair<>(x0, y0));
        arrayOfPanels[x0][y0] = new ImprovedMazePanel(x0,y0);
        arrayOfPanels[x0][y0].isStart = true;

        do {
            if (!pastListaPar.contains(new Pair<>(x0 + 1, y0)) && x0 < maxX) {
                listaPar.add(new Pair<>(x0 + 1, y0));
                pastListaPar.add(new Pair<>(x0 + 1, y0));
            }
            if (!pastListaPar.contains(new Pair<>(x0 - 1, y0)) && x0 >= 1) {
                listaPar.add(new Pair<>(x0 - 1, y0));
                pastListaPar.add(new Pair<>(x0 - 1, y0));
            }
            if (!pastListaPar.contains(new Pair<>(x0, y0 + 1)) && y0 < maxY) {
                listaPar.add(new Pair<>(x0, y0 + 1));
                pastListaPar.add(new Pair<>(x0, y0 + 1));
            }
            if (!pastListaPar.contains(new Pair<>(x0, y0 - 1)) && y0 >= 1) {
                listaPar.add(new Pair<>(x0, y0 - 1));
                pastListaPar.add(new Pair<>(x0, y0 - 1));
            }

            //randomly add one of them to maze, remove it from pool
            int id;
            id = (int) (generator.nextDouble() * (listaPar.size()));
            x0 = listaPar.get(id).getKey();
            y0 = listaPar.get(id).getValue();

            int orderCount =0;
            while(orderCount<4 && !insertIntoArray(arrayOfPanels, x0,y0, orderOfCalls.get(orderCount))){
                orderCount++;
            }

            if(orderCount>3)
                break;
            else
            {
                java.util.Collections.shuffle(orderOfCalls);
                listaPar.remove(id);
            }

        }while(listaPar.size()>0);
    }


    boolean insertIntoArray(ImprovedMazePanel[][] arr, Integer x0, Integer y0, Integer dir)
    {
        switch(dir)
        {
            case 0:
                if(x0>0 && arr[x0-1][y0]!=null)
                {

                    ImprovedMazePanel imp = new ImprovedMazePanel(x0,y0);
                    arr[x0-1][y0].setRight(imp);
                    imp.setLeft(arr[x0-1][y0]);
                    arr[x0][y0] = imp;
                    return true;
                }; break;
            case 1:
                if(y0>0 && arr[x0][y0-1]!=null)
                {

                    ImprovedMazePanel imp = new ImprovedMazePanel(x0,y0);
                    arr[x0][y0-1].setBottom(imp);
                    imp.setTop(arr[x0][y0-1]);
                    arr[x0][y0] = imp;
                    return true;
                }; break;
            case 2:
                if(x0<maxX && arr[x0+1][y0]!=null)
                {

                    ImprovedMazePanel imp = new ImprovedMazePanel(x0,y0);
                    arr[x0+1][y0].setLeft(imp);
                    imp.setRight(arr[x0+1][y0]);
                    arr[x0][y0] = imp;
                    return true;
                }; break;
            case 3:
                if(y0<maxY && arr[x0][y0+1]!=null)
                {

                    ImprovedMazePanel imp = new ImprovedMazePanel(x0,y0);
                    arr[x0][y0+1].setTop(imp);
                    imp.setBottom(arr[x0][y0+1]);
                    arr[x0][y0] = imp;
                    return true;
                }; break;
                default: break;
        }
        return false;
    }

    void moveUp()
    {
        if(arrayOfPanels[playerX][playerY].top!=null) {
            arrayOfPanels[playerX][playerY].isStart = false;
            arrayOfPanels[playerX][playerY].repaint();
            playerY--;
            arrayOfPanels[playerX][playerY].isStart = true;
            arrayOfPanels[playerX][playerY].repaint();
        }
        if(playerX==maxX&&playerY==0)
            youWin();
        revalidate();
    }
    void moveDown()
    {
        if(arrayOfPanels[playerX][playerY].bottom!=null) {
            arrayOfPanels[playerX][playerY].isStart = false;
            arrayOfPanels[playerX][playerY].repaint();
            playerY++;
            arrayOfPanels[playerX][playerY].isStart = true;
            arrayOfPanels[playerX][playerY].repaint();
        }
        revalidate();
    }
    void moveLeft()
    {
        if(arrayOfPanels[playerX][playerY].left!=null) {
            arrayOfPanels[playerX][playerY].isStart = false;
            arrayOfPanels[playerX][playerY].repaint();
            playerX--;
            arrayOfPanels[playerX][playerY].isStart = true;
            arrayOfPanels[playerX][playerY].repaint();
        }
        revalidate();
    }
    void moveRight()
    {
        if(arrayOfPanels[playerX][playerY].right!=null) {
            arrayOfPanels[playerX][playerY].isStart = false;
            arrayOfPanels[playerX][playerY].repaint();
            playerX++;
            arrayOfPanels[playerX][playerY].isStart = true;
            arrayOfPanels[playerX][playerY].repaint();
        }
        if(playerX==maxX&&playerY==0)
            youWin();
        revalidate();
    }

    void youWin()
    {
        removeAll();
        revalidate();
        setLayout(new GridLayout(1,1));
        TextArea text = new TextArea("WYGRANA!");
        text.setPreferredSize(new Dimension(400,400));
        add(text);
    }
}
