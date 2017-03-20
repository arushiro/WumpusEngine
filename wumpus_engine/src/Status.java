
// センサー情報のEnum
public enum Status {
	WALL, // 壁に衝突した
	HOLE, // 穴に隣接している
	GOLD, // 金貨の真上にいる
	WUMPUS, // Wumpusに隣接している
	HIT // 前のターンに矢を放った結果、Wumpusを倒した
}
