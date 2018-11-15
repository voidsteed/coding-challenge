import java.util.ArrayList;
import java.util.Stack;

/**
 *  @author Josh Hug
 */

class Node{
    int location;
    int prev;
    int distance;
    Node(int l, int p, int d) {
        location = l;
        prev = p;
        distance = d;
    }
}

public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int source;
    private Maze maze;
    private boolean cycleFound = false;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        source = maze.xyTo1D(1, 1);
    }

    private void cycleDetector() {
        Node current;
        Stack<Node> stack = new Stack<>();
        stack.add(new Node(source, source, 0));
        ArrayList<Integer> backTrack = new ArrayList<>();
        marked[source] = true;
        while (!cycleFound) {
            current = stack.pop();
            backTrack.add(current.location);
            marked[current.location] = true;
            for (int i : maze.adj(current.location)){
                if (marked[i] && current.prev != i) {
                    edgeTo[i] = current.location;
                    // this is the drawing part
                    boolean display[] = new boolean[maze.V()];
                    display[i] =true;
                    cycleFound = true;
                    announce();

                    int k=backTrack.size()-1;
                    while(i!=backTrack.get(k)&&k>0){

                        edgeTo[backTrack.get(k)] = backTrack.get(k-1);
                        display[backTrack.get(k)] =true;
                        k--;
                    }
                    marked = display;
                    announce();
                    return;
                }else if (current.prev != i) {
                    stack.add(new Node(i, current.location, current.distance + 1));
                }
            }
            if (stack.isEmpty()){
                return;
            }
        }
    }


    @Override
    public void solve() {
        cycleDetector();
    }
}