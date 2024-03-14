public class BoardEvaluator {

    private int[][] boardValues = {
        {10, 6, 7, 7, 7, 7, 6, 10},
        {6, 1, 3, 2, 2, 3, 1, 6},
        {7, 3, 5, 4, 4, 5, 3, 7},
        {7, 2, 4, 5, 5, 4, 2, 7},
        {7, 2, 4, 5, 5, 4, 2, 7},
        {7, 3, 5, 4, 4, 5, 3, 7},
        {6, 1, 3, 2, 2, 3, 1, 6},
        {10, 6, 7, 7, 7, 7, 6, 10},
    };

    public int evaluate(GameState s) {
        int[][] board = s.getBoard();
        int score = 0;
        for (int col = 0 ; col < 8; col++) {
            for (int row = 0; row < 8; row++ ){
                    score += this.boardValues[col][row] * (board[col][row] == 2 ? -1 : board[col][row]);
            }
        }
        return score;
    }
}
        
    


