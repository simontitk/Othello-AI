import java.util.ArrayList;
import java.util.Random;

public class RandomAI implements IOthelloAI {

    
	public Position decideMove(GameState s){
		ArrayList<Position> moves = s.legalMoves();
		if ( !moves.isEmpty() ) {
            Random r = new Random();
			return moves.get(r.nextInt(moves.size()));
        }
		else
			return new Position(-1,-1);
	}
	
}

    
