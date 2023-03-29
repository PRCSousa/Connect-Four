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
                    if (inicial.isWinner(col)) {
                        System.out.println("Ganhaste ! !");
                        break;
                    }
                
                }
                break;

            case "MCTS":

            boolean turn = true;

                Board current = inicial;

                for (int i = 0; i < 42; i++) {

                    if(turn){ // player

                        int col = stdin.nextInt() - 1;
                        Board newB = current.makeMove(col);
                        newB.setParent(current);
                        newB.printBoard();

                        if (newB.isWinner(col)) {
                            System.out.println("O Jogador Ganhou !");
                            break;
                        }

                        current = newB;
                        turn = !turn;

                    }else{ // MCTS
                        MCTS MonteCarlo = new MCTS(current, true, null);
                        Board newB = MonteCarlo.montecarlo();
                        newB.printBoard();
                        current = newB;
                        turn = !turn;
                    }

                }
                break;

                

        }
        stdin.close();
    }
}
