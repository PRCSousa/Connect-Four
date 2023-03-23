public class Board {
    public int[][] board = new int[6][7];

    int turn; // 1 = player 1, 2 = player 2

    public Board() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = 0;
            }
        }
        
        turn = 1;
    }

    public Board(int[][] mat, int op) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = mat[i][j];
            }
        }
        turn = op;
    }

    public void printBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                // print 1's as X's and 0's as O's, if its empty print a '-'
                if (board[i][j] == 1) {
                    System.out.print("X");
                } else if (board[i][j] == 2) {
                    System.out.print("O");
                } else {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
        System.out.println("1234567");
    }

    public int[][] getBoard(){return board;}

    public int changeTurn() {
        if(turn == 1) return 2;
        return 1;
    }

    public int getTurn(){return turn;}

    public Board makeMove(int column) {

        if (column < 0 || column > 6 || board[0][column] != 0) {
            System.out.println("Invalid move");
            return null;
        } else {

            if (turn == 1) {
                for (int i = 5; i >= 0; i--) {
                    if (board[i][column] == 0) {
                        board[i][column] = 1;
                        break;
                    }
                }
            } else {
                for (int i = 5; i >= 0; i--) {
                    if (board[i][column] == 0) {
                        board[i][column] = 2;
                        break;
                    }
                }
            }

            Board temp = new Board(board, changeTurn());
            return temp;
        }
    }

    public boolean isWinner(int column)
    {
        int h = 0;
        for(int i = 0; i <= 5; i++)
        {
            if(board[i][column] == turn) h = i;
        }

        int pontos = 1;
        // Verificar na horizontal
        for(int i = column - 1; i < (column - 3); i--)
        {
            if (i < 0) continue;
            if (board[h][i] == turn){
                pontos++;
            }else{
                break;
            }
        }
        
        return false;
    }


    public int evaluator(Board state, int moveCol)
    {
        Board temp = state.makeMove(moveCol);
        int[] mat[] = temp.getBoard(); 
        int player = temp.getTurn();
        // The point is to return a integer value of the score of a given position.
        // Based on R. L. Rivest, Game Tree Searching by Min/Max Approximation, AI 34 [1988], pp. 77-96
        // The Rules:

        // A win by X returns +512 points, a win by O returns -512 points
        if(temp.isWinner(moveCol))
        {
            if(temp.getTurn() == 1){
                return 512;
            } else{
                return -512;
            }
        }

        // For every 4 line segment, we sum the score of them, in every diagonal, horizontal and vertical

        int util = 0;

        // -50 for three Os, no Xs,
        // -10 for two Os, no Xs,
        // - 1 for one O, no Xs,
        // 0 for no tokens, or mixed Xs and Os,
        // 1 for one X, no Os,
        // 10 for two Xs, no Os,
        // 50 for three Xs, no Os

        // Beggining by the horizontals, we need to count 4 lines per board line, hence:
        for(int h = 0; h < 6; h++)
        {
            for(int u = 0; u < 4; u++)
            {
                // Count x's and o's on the 4 segment
                int x = 0;
                int o = 0;
                for(int i = u; i < u + 4; i++)
                {
                    if(mat[h][i] == 1) x++;
                    if(mat[h][i] == 1) o++;

                }

                // Verify the score and add to the sum
                if(x == 3 && o == 0) util += 50;
                if(x == 2 && o == 0) util += 10; 
                if(x == 1 && o == 0) util += 1;
                if(x == 0 && o == 1) util -= 1;
                if(x == 0 && o == 2) util -= 10;
                if(x == 0 && o == 3) util -= 50;

            }

            // Now I need to do to diagonals and verticals
        }

        return util;
    }

}
