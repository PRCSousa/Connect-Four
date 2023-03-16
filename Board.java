public class Board {
    public int[][] board = new int[6][7];

    int turn; // true = player 1, false = player 2

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

    public int changeTurn() {
        if(turn == 1) return 2;
        return 1;
    }

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
            if (i < 0 || i > 6) continue;
            if (board[h][i] == turn){
                pontos++;
            }else{
                break;
            }
        }
    }

}
