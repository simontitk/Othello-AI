public class test {

    public static void main(String[] args) {
        BoardEvaluator evaluator = new BoardEvaluator();

        var g = new GameState(8, 1);
        System.out.println(g.legalMoves());
        System.out.println(evaluator.evaluate(g));
    }
    
}
