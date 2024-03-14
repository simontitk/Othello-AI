import java.util.ArrayList;

public class HooliganAI  implements IOthelloAI {

    private BoardEvaluator evaluator;
    private int MAX_DEPTH;

    public HooliganAI() {
        this.evaluator = new BoardEvaluator();
        this.MAX_DEPTH = 1;
    }

    @Override
    public Position decideMove(GameState s) {
        int depth = 1;
        int playerToMove = s.getPlayerInTurn();
        Position bestMove = new Position(-1, -1);
        ArrayList<Position> moves = s.legalMoves();
        int startValue = Integer.MIN_VALUE;
        
        for (var move: moves) {
            int[][] currentBoard = s.getBoard();
            GameState newState = new GameState(currentBoard, playerToMove);
            newState.insertToken(move);
            if (depth == this.MAX_DEPTH) {
                int value = this.evaluator.evaluate(newState);
                if (value >= startValue) {
                    startValue = value;
                    bestMove = move;
                }
            }             
        }

        return bestMove;
    }
    
}
