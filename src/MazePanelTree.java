
public class MazePanelTree {

    SingleMazePanel root;

    MazePanelTree()
    {
        root = null;
    }

    public boolean insert(int x, int y)
    {
        if(root == null)
        {
            root = new SingleMazePanel(x,y);
            root.isStart = true;
            return true;
        }
        else
        {
            return root.insert(x,y);
        }
    }

    SingleMazePanel find(int x, int y)
    {
        return root.find(x,y);
    }

}
