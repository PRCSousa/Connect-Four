import java.util.Scanner;

public class ConnectFour {
    public static void main(String Args[]) {
        Scanner stdin = new Scanner(System.in);
        Board inicial = new Board();

        switch (Args[0]) {

            case "PVP":
                inicial.printBoard();

                for (int i = 0; i < 42; i++) {
                    System.out.print("\n      Player " + (i%2 + 1) + " move: ");

                    int col = stdin.nextInt() - 1;

                    while(!inicial.validMove(col)) {
                        System.out.print(" Invalid move, try again: ");
                        col = stdin.nextInt() - 1;
                    }

                    inicial = inicial.makeMove(col);

                    inicial.printBoard();

                    if (inicial.isWinner()) {
                        System.out.println("Player " + (i % 2 + 1) + " wins !");
                        break;
                    }
                }
                break;

            case "MCTS":
                inicial.printBoard();

                Board current = inicial;

                for (int i = 0; i < 42; i++) {

                    if (i % 2 == 0) { // player

                        System.out.print("\n      Player " + (i%2 + 1) + " move: ");
                        int col = stdin.nextInt() - 1;

                        while(!inicial.validMove(col)) {
                            System.out.print(" Invalid move, try again: ");
                            col = stdin.nextInt() - 1;
                        }
                        Board newB = current.makeMove(col);
                        newB.printBoard();

                        if (newB.isWinner()) {
                            System.out.println("You won !");
                            break;
                        }

                        current = newB;

                    } else { // MCTS
                        MCTSNode MonteCarlo = new MCTSNode(current, null);
                        Board newB = MonteCarlo.search();
                        System.out.println("\n        MCTS move: " + (newB.getMove() + 1));
                        newB.printBoard();
                        if (newB.isWinner()) {
                            System.out.println("Monte Carlo Tree Search won !");
                            break;
                        }
                        current = newB;
                    }
                }
                break;

            case "MM":
                inicial.printBoard();   

                Board state = inicial;

                for (int i = 0; i < 42; i++) {

                    if (i % 2 == 0) { // player

                        System.out.print("\n      Player " + (i%2 + 1) + " move: ");
                        int col = stdin.nextInt() - 1;

                        while(!inicial.validMove(col)) {
                            System.out.print(" Invalid move, try again: ");
                            col = stdin.nextInt() - 1;
                        }
                        Board newB = state.makeMove(col);
                        newB.printBoard();

                        if (newB.isWinner()) {
                            System.out.println("You won !");
                            break;
                        }

                        state = newB;

                    } else { // MinMax
                        MinMax mm = new MinMax();
                        int move = mm.minmax(state);
                        Board newstate=state.makeMove(move);
                        System.out.println("\n       Minmax move: " + (move + 1));
                        newstate.printBoard();

                        if (newstate.isWinner()) {
                            System.out.println("O MinMax Ganhou !");
                            break;
                        }

                        state = newstate;

                    }

                }
                break;

            case "AB":
                inicial.printBoard();

                Board ab = inicial;

                for (int i = 0; i < 42; i++) {

                    if (i % 2 == 0) { // player

                        System.out.print("\n      Player " + (i%2 + 1) + " move: ");
                        int col = stdin.nextInt() - 1;

                        while(!inicial.validMove(col)) {
                            System.out.print(" Invalid move, try again: ");
                            col = stdin.nextInt() - 1;
                        }
                        Board newB = ab.makeMove(col);
                        newB.printBoard();

                        if (newB.isWinner()) {
                            System.out.println("You won !");
                            break;
                        }

                        ab = newB;

                    } else { // MinMax
                        MinMaxAB mm = new MinMaxAB();
                        int move = mm.minmax(ab);
                        Board newstate=ab.makeMove(move);
                        System.out.println("\n    Alpha-Beta move: " + (move + 1));

                        newstate.printBoard();

                        if (newstate.isWinner()) {
                            System.out.println("Alpha-Beta won !");
                            break;
                        }

                        ab = newstate;

                    }

                }
                break;

                default:
                    System.out.println("Invalid Argument");
                    System.out.println("Usage: java ConnectFour [PVP | MCTS | MM | AB]");
                    break;

        }
        stdin.close();
    }
}