import java.lang.Math;

public class MinMax {
    Board board;
    int depth;
    int value;

    MinMax(Board b, int d) {
        board = b;
        depth = d;
    }

    public int minmax() {
        int minVal = 512;
        int move = 0;

        for (int i = 0; i < 7; i++) {
            if (board.canInsert(i)) {
                Board b = new Board(board.getBoard(), board.getTurn());
                b = b.makeMove(i);
                int heurVal = minmax(b, 0, true);
                if (heurVal < minVal) {
                    minVal = heurVal;
                    move = i;
                }
            }
        }
        return move;
    }

    public int minmax(Board state, int depth, boolean isMax) {
        int value = 0;

        if (depth == 0 || state.isTerminal())
            return state.evaluator();

        if (isMax) {
            value = -512;
            for (int i = 0; i < 7; i++) {
                if (state.canInsert(i)) {
                    Board child = new Board(state.getBoard(), state.getTurn());
                    child = child.makeMove(i);
                    value = Math.max(value, minmax(child, depth + 1, false));
                    return value;
                }
            }
        }

        else {
            value = 512;
            for (int i = 0; i < 7; i++) {
                if (state.canInsert(i)) {
                    Board child = new Board(state.getBoard(), state.getTurn());
                    child = child.makeMove(i);
                    value = Math.min(value, minmax(child, depth + 1, true));
                    return value;
                }
            }
        }

        return value;
    }

}