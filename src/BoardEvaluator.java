public class BoardEvaluator {

    private final int CORNER_VALUE = 1_000_000;
    private final int EDGE_VALUE = 100;
    private final int DEFAULT_VALUE = 10;
    private final int INNER_EDGE_VALUE = 1;
    private int size = 0;
    private int[][] boardValues;
 

    public int[][] generateValues(int size) {
        int[][] scores = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((i==0) || (j==0) || (i==size-1) || (j==size-1)) scores[i][j] = EDGE_VALUE;
                else if ((i==1) || (j==1) || (i==size-2) || (j==size-2)) scores[i][j] = INNER_EDGE_VALUE;
                else scores[i][j] = DEFAULT_VALUE;
            }
        }
        scores[0][0] = CORNER_VALUE;
        scores[0][size-1] = CORNER_VALUE;
        scores[size-1][0] = CORNER_VALUE;
        scores[size-1][size-1] = CORNER_VALUE;

        return scores;
    }

    public int evaluate(GameState s) {
        int[][] board = s.getBoard();
        int boardSize = board.length;
        if (boardSize != this.size) {
            this.boardValues = this.generateValues(boardSize);
            this.size = boardSize;
        }

        int score = 0;
        for (int col = 0 ; col < this.size; col++) {
            for (int row = 0; row < this.size; row++ ){
                    score += this.boardValues[col][row] * (board[col][row] == 2 ? -1 : board[col][row]);
            }
        }
        return score;
    }

/*     private void printBoard(GameState s, int depth) {
        System.out.println("board at depth " + depth + " :");
        int[][]board = s.getBoard();
        String res = " | 0-1-2-3-4-5-6-7-\n";
        for (int i=0; i<board.length; i++) {
            res += i + "| ";
            for (int j = 0; j < board.length; j++) {
                res+= board[i][j] + "-";
            }
            res+= "\n";
        }
        System.out.println(res + "----------------");
        System.out.println("legal moves: " + s.legalMoves());
    } */
}
        
    


