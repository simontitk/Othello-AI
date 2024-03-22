import java.util.ArrayList;
import java.util.List;

public class HooliganAI  implements IOthelloAI {

    private BoardEvaluator evaluator;
    private int maxDepth;

    /**
     * Private class representing a (move, value) 2-tuple. 
     */
    private static class MoveValue {
        private Position move;
        private int value;
        private MoveValue(Position move, int value) {
            this.move = move;
            this.value = value;
        }
    }

    /**
     * Initialize the AI instance. Since we were advised not to modify the GameState and the Othello classes, 
     * the parameters are directly initialized in the constructor, not passed in as arguments.
     */
    public HooliganAI() {
        this.evaluator = new BoardEvaluator();
        this.maxDepth = 6;
    }   

    /**
     * Determine the maximal depth using the getMaxDepth method, then storing the result in a class field, 
     * so that it's not recalculated with every call.
     * Initializes depth, alpha and beta values.
     * Calls the maxValue or minValue methods, depending on whether the agent is playing as player 1 or 2.
     * Returns the agent's move of choice. 
     */
    @Override
    public Position decideMove(GameState s) {
        this.maxDepth = getMaxDepth(s.getBoard().length);
        int depth = 0;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        MoveValue mv = (s.getPlayerInTurn() == 1) ? this.maxValue(s, depth, alpha, beta) : this.minValue(s, depth, alpha, beta);
        return mv.move;
    }

    /**
     * Simple switch statement to dynamically determine the depth of the search as a function of the board size.
     */
    private int getMaxDepth(int boardSize) {
        switch (boardSize) {
            case 4: return 12;
            case 6: return 10;
            case 8: return 6;
            case 10: return 4;
            case 12: return 4;
            default: return 2;
        }
    }

    /**
     * Initializes a MoveValue pair, which is then later updated as the evaluation progresses.
     * If the search is at its maximal depth, or if the gamestate has no more legal moves,
     * the BoardEvaluator instance evaluates the current position and returns the MoveValue pair.
     * Otherwise, it iterates through the moves returned by the getMovesToCheck method.
     * For each iteration, the move is performed and the new gamestate, alongside with alpha, beta and depth
     * is passed into the opposing minValue/maxValue function.
     * Throughout the iterations the current best move and its resulting value is kept track of.
     * Alpha and beta are updated based on the best move, and are used for pruning the search tree, if the opponent
     * finds a move more favourable to them.
     */
    private MoveValue maxValue(GameState s, int depth, int alpha, int beta) {
        MoveValue mv = new MoveValue(new Position(-1, -1), Integer.MIN_VALUE);
        List<Position> movesToCheck = getMovesToCheck(s);
        if (depth == this.maxDepth || movesToCheck.size() == 0) {
            mv.value = this.evaluator.evaluate(s);
            return mv;
        }
        for (Position move: movesToCheck) {
            GameState newState = new GameState(s.getBoard(), s.getPlayerInTurn());
            newState.insertToken(move);
            MoveValue newMv = minValue(newState, depth+1, alpha, beta);
            if (newMv.value >= mv.value) {
                mv.value = newMv.value;
                mv.move = move;
                alpha = Math.max(alpha, mv.value);
            }
            if (mv.value >= beta) {
                return mv;
            }
        }
        return mv;
    }

    /**
     * Initializes a MoveValue pair, which is then later updated as the evaluation progresses.
     * If the search is at its maximal depth, or if the gamestate has no more legal moves,
     * the BoardEvaluator instance evaluates the current position and returns the MoveValue pair.
     * Otherwise, it iterates through the moves returned by the getMovesToCheck method.
     * For each iteration, the move is performed and the new gamestate, alongside with alpha, beta and depth
     * is passed into the opposing minValue/maxValue function.
     * Throughout the iterations the current best move and its resulting value is kept track of.
     * Alpha and beta are updated based on the best move, and are used for pruning the search tree, if the opponent
     * finds a move more favourable to them.
     */
    private MoveValue minValue(GameState s, int depth,  int alpha, int beta) {
        MoveValue mv = new MoveValue(new Position(-1, -1), Integer.MAX_VALUE);
        List<Position> movesToCheck = getMovesToCheck(s);
        if (depth == this.maxDepth || movesToCheck.size() == 0) {
            mv.value = this.evaluator.evaluate(s);
            return mv;
        }
        for (Position move: movesToCheck) {
            GameState newState = new GameState(s.getBoard(), s.getPlayerInTurn());
            newState.insertToken(move);
            MoveValue newMv = this.maxValue(newState, depth+1, alpha, beta);
            if (newMv.value <= mv.value) {
                mv.value = newMv.value;
                mv.move = move;
                beta = Math.min(beta, mv.value);
            }
            if (mv.value <= alpha) {
                return mv;
            }
        }
        return mv;
    }
    
    /**
     * Extracts the list of legal moves from a given gamestate, then filters these into a new array using the isCorner method, to
     * only keep the corner posititons. If any of these is found, then the new list is returned, otherwise the original one is.
     * In case of a 4x4 board, its possible to check the entire game tree (max 12 moves ahead), 
     * so the list containing all legal moves is returned by default.
     */
    private List<Position> getMovesToCheck(GameState s) {
        int boardSize = s.getBoard().length;
        List<Position> legalMoves = s.legalMoves();
        if (boardSize == 4) {
            return legalMoves;
        }
        List<Position> cornerMoves = new ArrayList<>();
        for (Position move : legalMoves) {
            if (isCorner(move, boardSize)) cornerMoves.add(move);
        }
        return cornerMoves.size() == 0 ? legalMoves : cornerMoves;
    }

    /**
     * Determines whether a position is a corner in the context of a given board size.
     */
    private boolean isCorner(Position position, int boardSize) {
        return (
            position.row == 0 && position.col == 0 ||
            position.row == 0 && position.col == boardSize-1 ||
            position.row == boardSize-1 && position.col == 0 ||
            position.row == boardSize-1 && position.col == boardSize-1
        );
    }
}