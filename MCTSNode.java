import java.util.PriorityQueue;
import java.util.Random;

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
        return Double.compare(b.ucb(),ucb());
    }

    public double ucb(){
        return v/(double)n + Math.sqrt(2)* Math.sqrt(Math.log(parent.n)/(double)n);
    }

    public MCTSNode select(){

        if(children.isEmpty()) return this;
        return children.peek().select();
    }

    public static int randommove() {
        Random rand = new Random();
        int randomNum = rand.nextInt(7);
        return randomNum;
    }

    public int simulate(){

        Board b = new Board(board.getBoard(), board.getTurn()); // crio uma cópia do board deste node para simular jogos
        while(b.isFullyExpanded() == 0){ //enquanto não houver vencedor ou empate, joga
            
            
            int c = randommove();
            while(!b.canInsert(c)) c = randommove();


            b = b.makeMove(c);


        }
        return b.isFullyExpanded();
    }

    public void backpropagation(int winner){
        n++;
        if(max == winner) v++;
        if(parent==null) return;
        parent.backpropagation(winner);
    }

    public void expand(){
        for(int i = 0; i < 7; i++)
        {
            Board b = new Board(board.getBoard(), board.getTurn());
            if(board.canInsert(i))
            {
                b = b.makeMove(i);
                MCTSNode newNode = new MCTSNode(b, this);
                children.add(newNode);
            }
        }
    }

    public Board search(){
        for(int i = 0; i < 10000; i++){
            MCTSNode b = select();
            if(b.board.isFullyExpanded()!=0){
                b.backpropagation(b.board.isFullyExpanded());}
            else{
                b.expand();
                PriorityQueue<MCTSNode> q = new PriorityQueue<>(); // Lista para os filhos derivados de b
                while(!b.children.isEmpty())
                {
                    MCTSNode childNode = b.children.poll();
                    int simVal = childNode.simulate();
                    childNode.backpropagation(simVal);
                    q.add(childNode);
                }
                b.children = q;
            }
        }

        Board b = children.peek().board;

        double min = 2;
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