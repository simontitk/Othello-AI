public class BoardEvaluator {

    /**
     * Empirically determined values for different positions on a board.
     */
    private final int CORNER_VALUE = 1_000_000;
    private final int EDGE_VALUE = 100;
    private final int DEFAULT_VALUE = 10;
    private final int INNER_EDGE_VALUE = 1;
    private int size = 0;
    private int[][] boardValues;
 
    /**
     * Generates a size x size 2-dimensional integer array, then inserts CORNER_VALUE, EDGE_VALUE, DEFAULT_VALUE and INNER_EDGE_VALUE
     * at the corresponding positions, and lastly returns the resulting array.
     */
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

    /**
     * Extracts the board 2-dimensional array from a GameState object, then calls the generateValues method, if the current board size is 
     * different than the extracted one. Initializes a running sum as 0.
     * Multiplies every pair of corresponding positions in the value array and the board array, with 2's (representing player 2) 
     * being replaced with -1's. Adds the product to the running sum.
     * Returns the sum as an integer, representing the value of the gamestate (positive numbers beneficial for player 1, negatives for player 2).
     */
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
}
        
    


