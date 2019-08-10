import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class JMaze extends JPanel {

    SingleMazePanel[][] testArray;
    ArrayList<Pair<Integer, Integer>> listaPar;
    ArrayList<Pair<Integer, Integer>> pastListaPar;
    String[][] cheatArray;
    ArrayList<Pair<Integer,Integer>> errorList;
    static int maxX, maxY;
    int playerX, playerY;
    int iterCounter;

    static int wdth, hght;

    JMaze() {
        super();
        maxX = 19;
        maxY = 19;
        listaPar = new ArrayList<>();
        pastListaPar = new ArrayList<>();
        errorList = new ArrayList<>();
        testArray = new SingleMazePanel[maxX+1][maxY+1];
        wdth = 600;
        hght = 600;
        iterCounter =0;
        setPreferredSize(new Dimension(wdth, hght));
        setLayout(new GridLayout(maxY+1,maxX+1));
        setBackground(new Color(1f,0f,1f));
        CreateMaze();
        for (int i = 0; i <= maxY; i++) {
            for (int j = 0; j <= maxX; j++) {
                testArray[j][i].revalidate();
                add(testArray[j][i]);
            }
        }
        revalidate();
    }

    void CreateGoodMaze()
    {
        Random generator = new Random();
        Integer x0;
        x0 = (int) (generator.nextDouble() * maxX);
        Integer y0;
        y0 = (int) (generator.nextDouble() * maxY);
        playerX = x0;
        playerY = y0;

        pastListaPar.add(new Pair<>(x0, y0));
        MazePanelTree MPT = new MazePanelTree();

        MPT.insert(x0,y0);

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

        }while(listaPar.size()>0);
    }

    void CreateMaze() {
        Random generator = new Random();
        Integer x0;
        x0 = (int) (generator.nextDouble() * maxX);
        Integer y0;
        y0 = (int) (generator.nextDouble() * maxY);
        playerX = x0;
        playerY = y0;

        pastListaPar.add(new Pair<>(x0, y0));
        MazePanelTree MPT = new MazePanelTree();
        boolean succesValue=MPT.insert(x0,y0);
        do {

            if (succesValue) {
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
            }

            //randomly add one of them to maze, remove it from pool
            int id;
            id = (int) (generator.nextDouble() * (listaPar.size()));
            x0 = listaPar.get(id).getKey();
            y0 = listaPar.get(id).getValue();
            succesValue = MPT.insert(x0,y0);
            if (succesValue) {
                listaPar.remove(id);
            }
        } while (listaPar.size() > 0);

        for (int i = 0; i <= maxX; i++) {
            for (int j = 0; j <= maxY; j++) {
                testArray[i][j] = MPT.find(i,j);
            }
        }

        /*
        for (int i = 0; i <= maxY; i++) {
            for (int j = 0; j <= maxX; j++) {
                if (testArray[j][i] != null)
                    testArray[j][i].unicode();
                else
                    System.out.print(" ");
            }
            System.out.println();
        }*/
    }

    void addMaze() {
        for (int i = 0; i <= maxY; i++) {
            for (int j = 0; j <= maxX; j++) {
                    add(testArray[j][i]);
            }
        }
    }

    void moveUp()
    {
        if(testArray[playerX][playerY].top!=null) {
            testArray[playerX][playerY].isStart = false;
            testArray[playerX][playerY].repaint();
            playerY--;
            testArray[playerX][playerY].isStart = true;
            testArray[playerX][playerY].repaint();
        }
        if(playerX==maxX&&playerY==0)
            youWin();
        revalidate();
    }
    void moveDown()
    {
        if(testArray[playerX][playerY].bottom!=null) {
            testArray[playerX][playerY].isStart = false;
            testArray[playerX][playerY].repaint();
            playerY++;
            testArray[playerX][playerY].isStart = true;
            testArray[playerX][playerY].repaint();
        }
        revalidate();
    }
    void moveLeft()
    {
        if(testArray[playerX][playerY].left!=null) {
            testArray[playerX][playerY].isStart = false;
            testArray[playerX][playerY].repaint();
            playerX--;
            testArray[playerX][playerY].isStart = true;
            testArray[playerX][playerY].repaint();
        }
        revalidate();
    }
    void moveRight()
    {
        if(testArray[playerX][playerY].right!=null) {
            testArray[playerX][playerY].isStart = false;
            testArray[playerX][playerY].repaint();
            playerX++;
            testArray[playerX][playerY].isStart = true;
            testArray[playerX][playerY].repaint();
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
