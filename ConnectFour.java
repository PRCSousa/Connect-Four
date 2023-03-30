import java.util.Scanner;

public class ConnectFour {
    public static void main(String Args[]) {
        Scanner stdin = new Scanner(System.in);
        Board inicial = new Board();

        switch (Args[0]) {

            case "PvP":
                for (int i = 0; i < 42; i++) {
                    int col = stdin.nextInt() - 1;
                    inicial = inicial.makeMove(col);
                    inicial.printBoard();
                    if (inicial.isWinner()) {
                        System.out.println("Ganhaste ! !");
                        break;
                    }

                }
                break;

            case "MCTS":

                Board current = inicial;

                for (int i = 0; i < 42; i++) {

                    if (i % 2 == 0) { // player

                        int col = stdin.nextInt() - 1;
                        Board newB = current.makeMove(col);
                        newB.printBoard();

                        if (newB.isWinner()) {
                            System.out.println("O Jogador Ganhou !");
                            break;
                        }

                        current = newB;

                    } else { // MCTS
                        MCTSNode MonteCarlo = new MCTSNode(current, null);
                        Board newB = MonteCarlo.search();
                        newB.printBoard();
                        if (newB.isWinner()) {
                            System.out.println("O Mt. Carlos Ganhou !");
                            break;
                        }
                        current = newB;
                    }
                    System.out.println("\n");

                }
                break;

            case "MinMax":

                Board state = inicial;

                for (int i = 0; i < 42; i++) {

                    if (i % 2 == 0) { // player

                        int col = stdin.nextInt() - 1;
                        Board newstate = state.makeMove(col);
                        newstate.printBoard();

                        if (newstate.isWinner()) {
                            System.out.println("O Jogador Ganhou !");
                            break;
                        }

                        state = newstate;

                    } else { // MinMax
                        int d = state.getDepth();
                        MinMax mm = new MinMax(state, d);
                        Board newstate = state.makeMove((mm.minmax(state, 0, true)));
                        newstate.printBoard();

                        if (newstate.isWinner()) {
                            System.out.println("\nO MinMax Ganhou !");
                            break;
                        }

                        state = newstate;
                    }

                }
                break;

        }
        stdin.close();
    }
}