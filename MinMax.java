import java.lang.Math;
import java.util.ArrayList;
import java.util.Stack;

public class MinMax{
    Board board;
    int depth;
    Stack<Board> children;

    MinMax(Board b, int d){
        board=b;
        depth=d;
    }

    //Using DFS Algorithm to Complete the Minimax Tree
    public static Stack<Board> rewind(Board c) {
        Stack<Board> solution = new Stack<Board>();
        while (c != null) {
            solution.push(c);
            c = c.getParent();
        }
        return solution;
    }

    public Stack<Board> definechild() {
        board.setDepth(0);
        children = new Stack<Board>();
        children.push(board);

        if (board.isTerminal())
            return rewind(board);

        while (!children.empty()) {
            Board currState = children.pop();
            int depth = currState.getDepth();
            for (int i=0; i<7; i++) {

                Board newNode = new Board(currState.getBoard(), currState.getTurn());
                newNode = newNode.makeMove(i);

                if (newNode == null)
                    continue;

                newNode.setParent(currState);
                newNode.setDepth(depth + 1);

                if (newNode.isTerminal()){
                    System.out.println(newNode.getDepth());
                    return rewind(newNode);
                }

                if (!children.contains(newNode) && depth < 42) {
                    children.push(newNode);
                }
            }
        }
        return null;
    }

    public int minmax(Board state, int depth, boolean isMax){
        int value;
        Board child=new Board();
        child.setParent(state);

        if(depth==0 || state.isTerminal()) 
            return state.evaluator();
        
        if(isMax){
            value=-512;
            for(Board i : children){
                value=Math.max(value, minmax(i, i.getDepth()-1, false));
            } 
            return value;
        }
        else{
            value=512;
            for(Board i:children){
                value=Math.min(value, minmax(i, i.getDepth()-1, true));
            }
            return value;
        }
    }
    
}
