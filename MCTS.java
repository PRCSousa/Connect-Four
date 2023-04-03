
import java.util.LinkedList;
import java.util.PriorityQueue;


public class MCTS {
    MCTSNode root;
    public MCTS(Board br){
        this.root = new MCTSNode(br, null);
    }

    public MCTSNode select(){

        MCTSNode node = root;

        while(node.children.size() > 0)
        {
            PriorityQueue<MCTSNode> pq = new PriorityQueue<MCTSNode>();

            pq.addAll(node.children);

            LinkedList<MCTSNode> max = new LinkedList<MCTSNode>();

            double max_ucb = pq.peek().ucb();

            while(pq.peek() != null && pq.peek().ucb() == max_ucb){
                max.addFirst(pq.poll());
            }
            // return a random node with max ucb
            int index = (int)(Math.random()*max.size());
            for(int i = 0; i < index; i++){
                max.removeFirst();
            }
            node = max.removeFirst();

            if (node.visits == 0) {
                return node;
            }
        }
        return node;

    }

    public MCTSNode expand(MCTSNode node){
        if(node.board.isFullyExpanded() != 0){
            return null;
        }

        for(int i = 0; i < 7; i++)
        {
            
            if(node.board.canInsert(i))
            {
                Board b = new Board(node.board.getBoard(), node.player);
                b = b.makeMove(i);
                b.setMove(i);
                MCTSNode newNode = new MCTSNode(b, node);
          //      System.out.println("expanded playert" + newNode.player);
                node.children.add(newNode);
            }
        }

        // return a random child
        return node.children.get((int)(Math.random()*node.children.size()));
    }

    public int simulate(MCTSNode node){

        Board b = node.board;
        //System.out.println("simulating player" + b.getTurn() + "skmlef" + node.player);
        while(b.isFullyExpanded() == 0){

            int move = (int)(Math.random()*7);
            if(b.canInsert(move)){
                b = b.makeMove(move);
                b.setMove(move);
            }
        }
        //System.out.println("who won" + b.isFullyExpanded());
        return b.isFullyExpanded();
    }

    public void backpropagate(MCTSNode node, int result){
            node.visits++;

            if(node.player == result){
                node.wins++;
            }
          //  System.out.println("backpropagating player" + node.player + " " + node.wins + " " + node.visits);
            node.children.sort(null);

            if(node.parent != null){
                backpropagate(node.parent, result);
            }


    }

    public int run(){
        //System.out.println(root.player);
        for(int i = 0; i < 100000; i++){

            MCTSNode node = select();

            if(node.board.isFullyExpanded() != 0){
                backpropagate(node, node.board.isFullyExpanded());
            }else{

                MCTSNode child = expand(node);
                int val = simulate(child);
                backpropagate(child, val);
            }
        }

        // return the best move from root
        MCTSNode best = root.children.get(0);
    
        // for every child of root, print status
        for (MCTSNode child : root.children) {
            System.out.println("Move: " + (child.board.getMove() + 1) + " | W = " + child.wins + " / N = " + child.visits + " | UCT = " + child.ucb());
        }
        return best.board.getMove();
    }
}
