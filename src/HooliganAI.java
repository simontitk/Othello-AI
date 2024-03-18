import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HooliganAI  implements IOthelloAI {

    private BoardEvaluator evaluator;
    private int MAX_DEPTH;
    private int turns;
    private String date;
    private boolean isLogging;
    private boolean isPruning;

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
        this.MAX_DEPTH = 4;
        this.turns = 0;
        this.isLogging = false;
        this.isPruning = true;
        var date = LocalDateTime.now();
        this.date = "" + date.getMonthValue() + "-" + date.getDayOfMonth() + "-" + date.toLocalTime().format(DateTimeFormatter.ofPattern("HH-mm"));
    }   


    @Override
    public Position decideMove(GameState s) {
        int depth = 0;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        long t0 = System.currentTimeMillis();
        MoveValue mv = (s.getPlayerInTurn() == 1) ? this.maxValue(s, depth, alpha, beta) : this.minValue(s, depth, alpha, beta);
        long t1 = System.currentTimeMillis();
        this.turns++;
        if (isLogging) {
            try {
                FileWriter writer = new FileWriter(String.format("logs/log_%s_d%d.csv", this.date, this.MAX_DEPTH), true);
                writer.append(String.format("%d,%d,%d\n", this.turns, (t1-t0), s.legalMoves().size()));
                writer.close();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        return mv.move;
    }


    private MoveValue maxValue(GameState s, int depth, int alpha, int beta) {
        MoveValue mv = new MoveValue(new Position(-1, -1), Integer.MIN_VALUE);
        if (depth == this.MAX_DEPTH) {
            mv.value = this.evaluator.evaluate(s);
            return mv;
        }
        for (Position move: s.legalMoves()) {
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
        if (depth == this.MAX_DEPTH) {
            mv.value = this.evaluator.evaluate(s);
            return mv;
        }
        for (Position move: s.legalMoves()) {
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


    private static void printBoard(GameState s, int depth) {
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
    }
}