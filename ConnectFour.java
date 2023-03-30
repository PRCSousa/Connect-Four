import java.util.Scanner;

public class ConnectFour {
    public static void main(String Args[]) {
        Scanner stdin = new Scanner(System.in);
        Board inicial = new Board();
        inicial.printBoard();

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

                        System.out.print("Player move: ");
                        int col = stdin.nextInt() - 1;
                        System.out.println("\n");
                        Board newstate = state.makeMove(col);
                        newstate.printBoard();

                        if (newstate.isWinner()) {
                            System.out.println("O Jogador Ganhou !");
                            break;
                        }

                        state = newstate;

                    } else { // MinMax
                        MinMax mm = new MinMax();
                        int move = mm.minmax(state);
                        Board newstate=state.makeMove(move);
                        System.out.println("Minmax move: " + (move + 1));
                        System.out.println("\n");
                        newstate.printBoard();

                        if (newstate.isWinner()) {
                            System.out.println("\nO MinMax Ganhou !");
                            break;
                        }

                        state = newstate;

                    }

                }
                break;

            case "MinMaxAB":

                Board ab = inicial;

                for (int i = 0; i < 42; i++) {

                    if (i % 2 == 0) { // player

                        System.out.print("Player move: ");
                        int col = stdin.nextInt() - 1;
                        System.out.println("\n");
                        Board newstate = ab.makeMove(col);
                        newstate.printBoard();

                        if (newstate.isWinner()) {
                            System.out.println("O Jogador Ganhou !");
                            break;
                        }

                        ab = newstate;

                    } else { // MinMax
                        MinMaxAB mm = new MinMaxAB();
                        int move = mm.minmax(ab);
                        Board newstate=ab.makeMove(move);
                        System.out.println("Alpha-Beta move: " + (move + 1));
                        System.out.println("\n");
                        newstate.printBoard();

                        if (newstate.isWinner()) {
                            System.out.println("\nO Alpha-Beta Ganhou !");
                            break;
                        }

                        ab = newstate;

                    }

                }
                break;

        }
        stdin.close();
    }
}