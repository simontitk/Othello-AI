import java.util.ArrayList;

public class HooliganAI  implements IOthelloAI {

    private BoardEvaluator evaluator;
    private int MAX_DEPTH;

    private static class MoveValue {
        private Position move;
        private int value;
        private MoveValue(Position move, int value) {
            this.move = move;
            this.value = value;
        }
    }

    public HooliganAI() {
        this.evaluator = new BoardEvaluator();
        this.MAX_DEPTH = 5;
    }


    @Override
    public Position decideMove(GameState s) {
        int depth = 0;
        MoveValue mv = (s.getPlayerInTurn() == 1) ? this.maxValue(s, depth) : this.minValue(s, depth);
        return mv.move;
    }


    private MoveValue maxValue(GameState s, int depth) {
        MoveValue mv = new MoveValue(new Position(-1, -1), Integer.MIN_VALUE);
        if (depth == this.MAX_DEPTH) {
            mv.value = this.evaluator.evaluate(s);
            return mv;
        }
        int[][] board = s.getBoard();
        ArrayList<Position> moves = s.legalMoves();
        for (Position move: moves) {
            GameState newState = new GameState(board, s.getPlayerInTurn());
            newState.insertToken(move);
            MoveValue newMv = minValue(newState, depth+1);
            if (newMv.value >= mv.value) {
                mv.value = newMv.value;
                mv.move = move;
            }
        }
        return mv;
    }


    private MoveValue minValue(GameState s, int depth) {
        MoveValue mv = new MoveValue(new Position(-1, -1), Integer.MAX_VALUE);
        if (depth == this.MAX_DEPTH) {
            mv.value = this.evaluator.evaluate(s);
            return mv;
        }
        int[][] board = s.getBoard();
        ArrayList<Position> moves = s.legalMoves();
        for (Position move: moves) {
            GameState newState = new GameState(board, s.getPlayerInTurn());
            newState.insertToken(move);
            MoveValue newMv = this.maxValue(newState, depth+1);
            if (newMv.value <= mv.value) {
                mv.value = newMv.value;
                mv.move = move;
            }
        }
        return mv;
    }
}