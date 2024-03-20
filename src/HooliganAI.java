import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HooliganAI  implements IOthelloAI {

    private BoardEvaluator evaluator;
    private int maxDepth;

// fields used for testing and logging
    private int turns;
    private String date;
    private boolean isLogging;
    private boolean isPruning;    
// fields used for testing and logging

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
        this.maxDepth = 6;

// fields used for testing and logging
        this.turns = 0;
        this.isLogging = true;
        this.isPruning = true;
        var date = LocalDateTime.now();
        this.date = (
            date.getMonthValue() + "-" + 
            date.getDayOfMonth() + "-" + 
            date.toLocalTime().format(DateTimeFormatter.ofPattern("HH-mm"))
        );
// fields used for testing and logging
    }   


    @Override
    public Position decideMove(GameState s) {
        this.maxDepth = getMaxDepth(s.getBoard().length);
        int depth = 0;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        long t0 = System.currentTimeMillis();
        MoveValue mv = (s.getPlayerInTurn() == 1) ? this.maxValue(s, depth, alpha, beta) : this.minValue(s, depth, alpha, beta);
        long t1 = System.currentTimeMillis();
    
// logging logic 
        if (isLogging) {
            this.turns++;
            try {
                FileWriter writer = new FileWriter(String.format("logs/log_%s_d%d.csv", this.date, this.maxDepth), true);
                writer.append(String.format("%d,%d,%d\n", this.turns, (t1-t0), s.legalMoves().size()));
                writer.close();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
// logging logic 
        return mv.move;
    }

    private int getMaxDepth(int boardSize) {
        switch (boardSize) {
            case 4: return 12;
            case 6: return 10;
            case 8: return 6;
            case 10: return 4;
            default: return 2;
        }
    }


    private MoveValue maxValue(GameState s, int depth, int alpha, int beta) {
        MoveValue mv = new MoveValue(new Position(-1, -1), Integer.MIN_VALUE);
        if (depth == this.maxDepth) {
            mv.value = this.evaluator.evaluate(s);
            return mv;
        }
        for (Position move: movesToCheck(s)) {
            GameState newState = new GameState(s.getBoard(), s.getPlayerInTurn());
            newState.insertToken(move);
            MoveValue newMv = minValue(newState, depth+1, alpha, beta);
            if (newMv.value >= mv.value) {
                mv.value = newMv.value;
                mv.move = move;
                alpha = Math.max(alpha, mv.value);
            }
            if (isPruning && mv.value >= beta) {
                return mv;
            }
        }
        return mv;
    }


    private MoveValue minValue(GameState s, int depth,  int alpha, int beta) {
        MoveValue mv = new MoveValue(new Position(-1, -1), Integer.MAX_VALUE);
        if (depth == this.maxDepth) {
            mv.value = this.evaluator.evaluate(s);
            return mv;
        }
        for (Position move: movesToCheck(s)) {
            GameState newState = new GameState(s.getBoard(), s.getPlayerInTurn());
            newState.insertToken(move);
            MoveValue newMv = this.maxValue(newState, depth+1, alpha, beta);
            if (newMv.value <= mv.value) {
                mv.value = newMv.value;
                mv.move = move;
                beta = Math.min(beta, mv.value);
            }
            if (isPruning && mv.value <= alpha) {
                return mv;
            }
        }
        return mv;
    }

    
    private List<Position> movesToCheck(GameState s) {
        int boardSize = s.getBoard().length;
        List<Position> legalMoves = s.legalMoves();
        if (boardSize == 4) {
            return legalMoves; // in case of a 4x4 board, its possible to check the entire game tree (max 12 moves ahead)
        }
        List<Position> cornerMoves = new ArrayList<>();
        for (Position move : legalMoves) {
            if (isCorner(move, boardSize)) cornerMoves.add(move);
        }
        return cornerMoves.size() == 0 ? legalMoves : cornerMoves;
    }


    private boolean isCorner(Position position, int boardSize) {
        return (
            position.row == 0 && position.col == 0 ||
            position.row == 0 && position.col == boardSize-1 ||
            position.row == boardSize-1 && position.col == 0 ||
            position.row == boardSize-1 && position.col == boardSize-1
        );
    }
}