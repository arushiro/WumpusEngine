
public class Main {
	public static void main(String[] args) {
		try {
			int seed = Const.SEED;
			if (args.length > 0)
				seed = Integer.parseInt(args[0]);
			Game game = new Game(seed, Const.SIZE,Const.WUMPUS, Const.GOLD, Const.HOLE_PROB, Const.ARROW );
			game.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
