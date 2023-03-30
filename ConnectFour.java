import java.util.Scanner;

public class ConnectFour {
    public static void main(String Args[]) {
        Scanner stdin = new Scanner(System.in);
        Board inicial = new Board();

        switch ("MCTS") {

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

                    if(i%2 == 0){ // player

                        int col = stdin.nextInt() - 1;
                        Board newB = current.makeMove(col);
                        newB.setParent(current);
                        newB.printBoard();

                        if (newB.isWinner()) {
                            System.out.println("O Jogador Ganhou !");
                            break;
                        }

                        current = newB;

                    }else{ // MCTS
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
                turn = true;

                Board state = inicial;

                for (int i = 0; i < 42; i++) {

                    if(turn){ // player

                        int col = stdin.nextInt() - 1;
                        Board newstate = state.makeMove(col);
                        newstate.setParent(state);
                        newstate.printBoard();

                        if (newstate.isWinner(col)) {
                            System.out.println("O Jogador Ganhou !");
                            break;
                    }

                    state = newstate;
                    turn = !turn;

                }else{ // MinMax
                    int d=state.getDepth();
                    MinMax mm=new MinMax(state,d);
                    state.makeMove((mm.minmax(state, 0, true)));
                    state.printBoard();
                    //newstate = state;
                    turn = !turn;
                }

            }
                break;

        }
        stdin.close();
    }
}
