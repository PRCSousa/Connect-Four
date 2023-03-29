import java.util.PriorityQueue; 

public class MCTS implements Comparable<MCTS>{
    Board board;
    boolean max;
    int v;
    int n;
    MCTS parent;
    PriorityQueue<MCTS> childs;

    MCTS(Board b, boolean m, MCTS p){
        board = b;
        max = m;
        v = 0;
        n = 0;
        parent = p;
        childs = new PriorityQueue<>();
    }




    public double ucb(){
        return v/(double)n + Math.sqrt(2)* Math.sqrt(Math.log(parent.n)/(double)n);
    }

    public int compareTo(MCTS b){
        return Double.compare(b.ucb(),ucb());
    }

    public MCTS select(){
        if(childs.isEmpty()){return this;}
        return childs.peek().select();
    }

    public void expand(){
        for(int j = 0;j<7;j++){
            if(board.canInsert(j)){
                Board bo = board.makeMove(j);
                bo.changeTurn();
                MCTS b = new MCTS(bo, !max, this);
                childs.add(b);
            }
        }
    }

    public int randommove(){
        return (int)(Math.random() * 7);
    }

    public int rollout(){
        Board b = new Board(board.getBoard(), board.getTurn());
        while(b.isTerminal() == false){
            int c = randommove();
            while(b.validMove(c) != true)
            {
                c = randommove();
            }
            b = b.makeMove(c);
            b.changeTurn();
        }
        return b.isFullyExpanded();
    }

    public void backpropagation(int winner){
        n++;
        if(max && winner == 512){v++;}
        if(!max && winner == -512){v++;}
        if(parent==null){return;}
        parent.backpropagation(winner);
    }

    public void reset(){
        PriorityQueue<MCTS> filhos = new PriorityQueue<>();
        while(!childs.isEmpty()){
            filhos.add(childs.poll());
        }
        childs = filhos;
        if(parent==null){return;}
        parent.reset();
    }

    public Board montecarlo(){
        for(int k=0;k<100;k++){
            MCTS b = select();
            if(b.board.isFullyExpanded()!=0){
                b.backpropagation(b.board.isFullyExpanded());}
            else{
                b.expand();
                PriorityQueue<MCTS> filhos = new PriorityQueue<>();
                while(!b.childs.isEmpty()){
                    MCTS bo = b.childs.poll();
                    int a = bo.rollout();
                    bo.backpropagation(a);
                    filhos.add(bo);}
                b.childs = filhos;
            }
            b.reset();
        }
        Board b = childs.peek().board;
        double min = 2;
        while(!childs.isEmpty()){
            MCTS filho = childs.poll();
            if((double)filho.v/filho.n<min){   
                min = (double)filho.v/filho.n;
                b = filho.board;
            }
        }
        return b;
    }

}
