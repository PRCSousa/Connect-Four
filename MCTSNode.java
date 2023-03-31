import java.util.PriorityQueue;

public class MCTSNode implements Comparable<MCTSNode>{
    Board board;
    int max; // 1 se for o player, 2 se for a AI
    int v;
    int n;
    MCTSNode parent;
    PriorityQueue<MCTSNode> children;

    MCTSNode(Board b, MCTSNode p){
        board = b;
        max = b.getTurn();
        v = 0;
        n = 0;
        parent = p;
        children = new PriorityQueue<>();
    }

    // Comparar estados pelo UCB
    public int compareTo(MCTSNode b){
        return Double.compare(this.ucb(),b.ucb());
    }

    public double ucb(){
        return (((double)v/(double)n) + (Math.sqrt(2)* (Math.sqrt(Math.log(parent.n)/(double)n))));
    }

    public MCTSNode select(){

        if(children.isEmpty()) return this;
        return children.peek().select();
    }

    public static int randommove() {
        return (int) (Math.random() * 7);
    }

    public int simulate(){
        Board b = new Board(board.getBoard(), board.getTurn()); // creates a copy of the board to simulate a playthrough
        while(b.isFullyExpanded() == 0){ // while the game is not over
            
            
            int c = randommove();
            while(!b.canInsert(c)) c = randommove();


            b = b.makeMove(c);


        }
        return b.isFullyExpanded(); //returns 1 if player wins, 2 if AI wins, 3 if draw
    }

    // Backpropagation sends the result of a simulation to the parent nodes and updates the values of v and n

    public void backpropagation(int winner){
        n++;
        if(max == winner) v++;
        if(parent==null) return;
        parent.backpropagation(winner);
    }

    // Generates all possible children of a node and adds them to the children list

    public void expand(){
        for(int i = 0; i < 7; i++)
        {
            
            if(board.canInsert(i))
            {
                Board b = new Board(board.getBoard(), board.getTurn());
                b = b.makeMove(i);
                b.setMove(i);
                MCTSNode newNode = new MCTSNode(b, this);
                children.add(newNode);
            }
        }
    }

    // Aftet backpropagation, reorders the children list by UCB as they get unsorted

    public void reset(){

        PriorityQueue<MCTSNode> filhos = new PriorityQueue<>();
        while(!children.isEmpty()){
            filhos.add(children.poll());
        }
        children = filhos;

        if(parent != null) parent.reset();
    }

    public Board search(){
        for(int i = 0; i < 30000; i++){
            MCTSNode b = select();
            if(b.board.isFullyExpanded()!=0){
                b.backpropagation(b.board.isFullyExpanded());}
            else{
                b.expand();
                PriorityQueue<MCTSNode> q = new PriorityQueue<>();
                while(!b.children.isEmpty())
                {
                    MCTSNode childNode = b.children.poll();
                    int simVal = childNode.simulate();
                    childNode.backpropagation(simVal);
                    q.add(childNode);
                }
                b.children = q;
            }
            b.reset(); // Aftet backpropagation, reorders the children list by UCB as they get unsorted
        }

        Board b = children.peek().board;

        double min = 1;
        while(!children.isEmpty()){
            MCTSNode filho = children.poll();
            if(((double)filho.v/filho.n) < min){   
                min = (double)filho.v/filho.n;
                b = filho.board;
            }
        }
        return b;

    }

}