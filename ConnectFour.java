import java.util.Scanner;

public class ConnectFour {
    public static void main(String Args[])
    {
        Scanner stdin = new Scanner(System.in);
        Board inicial = new Board();
        inicial.printBoard();
        for(int i = 0; i < 42; i++){
            int col = stdin.nextInt() - 1;
            inicial = inicial.makeMove(col);
            if(inicial.isWinner(col))
            {
                System.out.println("Ganhaste ! !");
            }
            inicial.printBoard();
            System.out.println("Score: " + inicial.evaluator());
        }
        stdin.close();

    }
}
