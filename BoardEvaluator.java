import java.util.Map;

public class BoardEvaluator {
    
    private static Map<Position, Integer> positionValues;

    public BoardEvaluator() {
        this.positionValues = Map.ofEntries(
            Map.entry(new Position(0, 0), 8),
            Map.entry(new Position(0, 1), 6),
            Map.entry(new Position(0, 2), 7),
            Map.entry(new Position(0, 3), 7),
            Map.entry(new Position(0, 4), 7),
            Map.entry(new Position(0, 5), 7),
            Map.entry(new Position(0, 6), 6),
            Map.entry(new Position(0, 7), 8),
            Map.entry(new Position(1, 0), 6),
            Map.entry(new Position(1, 1), 1),
            Map.entry(new Position(1, 2), 3),
            Map.entry(new Position(1, 3), 2),
            Map.entry(new Position(1, 4), 2),
            Map.entry(new Position(1, 5), 3),
            Map.entry(new Position(1, 6), 1),
            Map.entry(new Position(1, 7), 6),
            Map.entry(new Position(2, 0), 7),
            Map.entry(new Position(2, 1), 3),
            Map.entry(new Position(2, 2), 5),
            Map.entry(new Position(2, 3), 4),
            Map.entry(new Position(2, 4), 4),
            Map.entry(new Position(2, 5), 5),
            Map.entry(new Position(2, 6), 3),
            Map.entry(new Position(2, 7), 7),
            Map.entry(new Position(3, 0), 7),
            Map.entry(new Position(3, 1), 2),
            Map.entry(new Position(3, 2), 4),
            Map.entry(new Position(3, 3), 5),
            Map.entry(new Position(3, 4), 5),
            Map.entry(new Position(3, 5), 4),
            Map.entry(new Position(3, 6), 2),
            Map.entry(new Position(3, 7), 7),
            Map.entry(new Position(4, 0), 7),
            Map.entry(new Position(4, 1), 2),
            Map.entry(new Position(4, 2), 4),
            Map.entry(new Position(4, 3), 5),
            Map.entry(new Position(4, 4), 5),
            Map.entry(new Position(4, 5), 4),
            Map.entry(new Position(4, 6), 2),
            Map.entry(new Position(4, 7), 7),
            Map.entry(new Position(5, 0), 7),
            Map.entry(new Position(5, 1), 3),
            Map.entry(new Position(5, 2), 5),
            Map.entry(new Position(5, 3), 4),
            Map.entry(new Position(5, 4), 4),
            Map.entry(new Position(5, 5), 5),
            Map.entry(new Position(5, 6), 3),
            Map.entry(new Position(5, 7), 7),
            Map.entry(new Position(6, 0), 6),
            Map.entry(new Position(6, 1), 1),
            Map.entry(new Position(6, 2), 3),
            Map.entry(new Position(6, 3), 2),
            Map.entry(new Position(6, 4), 2),
            Map.entry(new Position(6, 5), 3),
            Map.entry(new Position(6, 6), 1),
            Map.entry(new Position(6, 7), 6),
            Map.entry(new Position(7, 0), 8),
            Map.entry(new Position(7, 1), 6),
            Map.entry(new Position(7, 2), 7),
            Map.entry(new Position(7, 3), 7),
            Map.entry(new Position(7, 4), 7),
            Map.entry(new Position(7, 5), 7),
            Map.entry(new Position(7, 6), 6),
            Map.entry(new Position(7, 7), 8)
        );
    }

    public int evaluate(GameState s) {
        int[][] board = s.getBoard();

        // do mathy stuff
        return 0;
    }
}
        
    


