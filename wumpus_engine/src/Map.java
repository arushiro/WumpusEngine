import java.util.LinkedList;
import java.util.Random;

public class Map {
	private int startX;
	private int startY;
	private Tile[][] map;
	private int size;

	public Map(int seed, int size, int wumpus, int gold, int prob) {
		Random rnd = new Random(seed);
		map = new Tile[size][size];
		this.size = size;
		
		// 何も置かれていないタイルをリストに追加
		LinkedList<Integer> list = new LinkedList<Integer>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				map[i][j] = Tile.NONE;
				list.add(j * size + i);
			}
		}

		// 初期位置の決定
		int n = rnd.nextInt(list.size());
		map[list.get(n) % size][list.get(n) / size] = Tile.UPSTAIRS;
		startX = list.get(n) % size;
		startY = list.get(n) / size;
		list.remove(n);

		// Wumpusの配置
		for (int i = 0; i < wumpus; i++) {
			n = rnd.nextInt(list.size());
			map[list.get(n) % size][list.get(n) / size] = Tile.WUMPUS;
			list.remove(n);
		}

		// 金貨の配置
		for (int i = 0; i < gold; i++) {
			n = rnd.nextInt(list.size());
			map[list.get(n) % size][list.get(n) / size] = Tile.GOLD;
			list.remove(n);
		}

		// 残った空きタイルに穴を配置
		for (int i : list)
			if (rnd.nextInt(100) < prob)
				map[i % size][i / size] = Tile.HOLE;
	}

	// 初期位置のX座標を返す
	public int getStartX() {
		return startX;
	}

	// 初期位置のY座標を返す
	public int getStartY() {
		return startY;
	}

	// 指定された座標のタイルを返す
	// 範囲外であればTile.STONEが返される
	public Tile getTile(int x, int y) {
		if (x >= size || x < 0 || y >= size || y < 0)
			return Tile.STONE;
		return map[x][y];
	}

	// 指定された座標に金貨があれば金貨を消してtrueを返す
	// そうでなければfalseを返す
	public boolean pickUp(int x, int y) {
		if (getTile(x, y) == Tile.GOLD) {
			map[x][y] = Tile.NONE;
			return true;
		}
		return false;
	}
	
	public boolean hit(int x, int y) {
		if (getTile(x, y) == Tile.WUMPUS) {
			map[x][y] = Tile.NONE;
			return true;
		}
		return false;
	}
}
