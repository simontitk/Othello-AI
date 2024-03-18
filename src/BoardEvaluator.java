public class BoardEvaluator {

    private int[][] boardValues = {
        {1000, 50, 70, 70, 70, 70, 50, 1000},
        {50, 1, 3, 2, 2, 3, 1, 50},
        {70, 3, 5, 4, 4, 5, 3, 70},
        {70, 2, 4, 5, 5, 4, 2, 70},
        {70, 2, 4, 5, 5, 4, 2, 70},
        {70, 3, 5, 4, 4, 5, 3, 70},
        {50, 1, 3, 2, 2, 3, 1, 50},
        {1000, 50, 70, 70, 70, 70, 50, 1000},
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
        
    


