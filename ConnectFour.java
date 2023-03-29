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
                    if (inicial.isWinner(col)) {
                        System.out.println("Ganhaste ! !");
                        break;
                    }
                
                }
                break;
                

        }
        stdin.close();
    }
}
