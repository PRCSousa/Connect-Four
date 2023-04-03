import java.util.ArrayList;

public class MCTSNode implements Comparable<MCTSNode>{
    Board board;
    int player;
    int visits;
    int wins;
    MCTSNode parent;
    ArrayList<MCTSNode> children;

    public MCTSNode(Board tab, MCTSNode par){
        board = tab;
        player = tab.getTurn();
        visits = 0;
        wins = 0;
        parent = par;
        children = new ArrayList<MCTSNode>();
    }

    public double ucb(){
        if(visits == 0){
            return Integer.MAX_VALUE;
        }
        return (double)wins/visits + Math.sqrt(2*Math.log(parent.visits)/visits);
    }

    public int compareTo(MCTSNode other){
        return -1*Double.compare(this.ucb(),other.ucb());
    }

}
