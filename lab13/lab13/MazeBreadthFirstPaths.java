import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */

public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int x;
    private int y;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        x = maze.xyTo1D(sourceX, sourceY);
        y = maze.xyTo1D(targetX, targetY);
        distTo[x] = 0;
        edgeTo[x] = x;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        Queue<Integer> newQueue = new LinkedList<>();
        newQueue.add(x);
        while (!newQueue.isEmpty()) {
            int removed = newQueue.remove();
            marked[removed] = true;
            announce();
            if (removed == y) {
                targetFound = true;
                return;
            }
            for (int w : maze.adj(removed)) {
                if (!marked[w]) {
                    edgeTo[w] = removed;
                    announce();
                    distTo[w] = distTo[removed] + 1;
                    newQueue.add(w);
                }
            }
        }
    }

    @Override
    public void solve() {
        bfs();
    }
}

