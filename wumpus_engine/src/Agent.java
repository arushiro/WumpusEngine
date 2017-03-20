import java.util.HashMap;
import java.util.Random;

public class Agent {
	// Wumpus World
	//
	// 1. 概要
	// ここは穴や怪物Wumpusなど様々な危険がいっぱいのダンジョン！
	// あなたはこのダンジョンに貴重な金貨を求め、弓矢を携えやってきた！
	// しかし、ダンジョンは暗く目の前に何があるかもわからない……
	// 穴に吹き込む風や、Wumpusの臭いなどの情報を駆使して、
	// Wumpusを倒しダンジョンに落ちている金貨を無事に持ち帰ろう！
	//
	// 2. 目的
	// N*Nのマスで構成されたダンジョンに落ちている金貨を拾い、Wumpusを倒し無事にダンジョンを脱出してください。
	//
	// 3. 流れ
	// このゲームはターン進行をするゲームです。
	// 各ターンあなたはダンジョン内での行動を決める必要があります。
	// 行動の生成はこのファイル(Agent.java)のDoメソッドによって行われます。
	// Agent.javaを書き換えることによってより良い行動を生成しましょう。
	// 行動の結果、ダンジョン内で死亡する、もしくは無事に脱出した場合にゲームは終了します。
	// 各ターンの行動を決めるためのヒントとして、穴が隣接しているかどうかなどの情報が渡されます。
	//
	// 4. 行動
	// ターンに出来る行動は以下の11項目です。
	//
	// 何もしない
	//   Action.NONE : 何もしない。
	//
	// 移動
	//   指定した方向に1マス進みます。進んだ先が穴,Wumpusの場合あなたは死にます。壁の場合その場にとどまります。
	//   Action.UP : 上に1マス進みます。
	//   Action.DOWN : 下に1マス進みます。
	//   Action.LEFT : 左に1マス進みます。
	//   Action.Right : 右に1マス進みます。
	//
	// 矢を放つ
	//   指定した方向に矢を放ちます。矢の残数が0の場合何も起こりません。放たれた矢は壁に当たるまで進みます。途中でWumpusに当たった場合Wumpusは消滅します。
	//   Action.SHOOT_UP : 上に矢を放ちます。
	//   Action.SHOOT_DOWN : 下に矢を放ちます。
	//   Action.SHOOT_LEFT : 左に矢を放ちます。
	//   Action.SHOOT_Right : 右に矢を放ちます。
	//
	// 拾う
	//   Action.PICKUP : 現在地に金貨があれば拾います。なければ何も起こりません。
	//
	// 脱出する/自殺する
	//   Action.END : 現在地が入り口であれば脱出します。階段でなければ探索を諦めて自殺します。
	//
	// 5. 情報
	// 各ターンに渡される情報です。
	// 情報は複数渡されることがあります。(穴が隣接している かつ Wumpusが隣接している など)
	// 情報は以下の5項目です。
	//   Status.WALL : 前のターンに移動した結果、壁に衝突して移動できなかった。
	//   Status.HOLE : 現在位置の4方のいずれかに穴が1つ以上存在している。
	//   Status.GOLD : 現在位置に金貨が存在している。
	//   Status.WUMPUS : 現在位置の4方のいずれかにWumpusが存在している。
	//   Status.HIT : 前のターンに放った矢がWumpusに当たった。
	//
	// 6. ターンの進め方
	// 毎ターンこのクラスのDoメソッドが呼ばれます。
	// Doメソッドには5.で紹介した状況を格納しているHashMap<Status, Boolean>であるsensorが渡されます。
	// sensor.get(Status.GOLD)とすると、現在位置に金貨が存在しているならtrue、なければfalseが返ってきます。
	// この結果を元にして何かしらの処理を行ったのち、EnumであるActionのうち1つを返すことで行動を起こします。
	// return Action.UP; とすると上に1マス進むという行動を起こします。
	//
	// 7. ゲームの点数
	// 以下の行動で点数が入ります。
	//
	// ゲーム中に入る得点
	//   1ターン経過 : -1
	//   矢を放つ : 0
	//   金貨を拾う : 100
	//   Wumpusを倒す : 200
	//
	// ゲーム終了時に入る得点
	//   脱出
	//     脱出する(入り口でAction.Endを行う) : 100
	//     金貨を全て拾った状態で脱出する : 1000
	//     Wunpusを全て倒した状態で脱出する : 1000
	//
	//   死亡
	//     穴に落ちる : -200
	//     Wumpusに捕まる : -200
	//     自殺する(入り口以外でAction.Endを行う/1000ターンを超過する) : 0
	//
	// 8. 結果の閲覧
	// 終了時にリザルトファイルを書き出し、wumpusviewerで開いてください。

	// 矢の本数
	private int arrow;
	// 部屋の大きさ (size * sizeの正方形)
	private int size;

	private Random rnd;

	// 相対位置
	private int x, y;

	private class Flag {
		public boolean breeze;
		public boolean stench;
		public boolean qHole;
		public boolean qWumpus;
		public boolean isHole;
		public boolean isWumpus;
		public boolean isNone;
		public boolean isWall;
	}

	private Flag[][] map;

	private Action last;

	// コンストラクタ
	public Agent(int arrow, int size) {
		this.arrow = arrow;
		this.size = size;
		last = Action.NONE;
		rnd = new Random(2000);

		x = size; y = size;

		map = new Flag[size * 2 - 1][size * 2 - 1];
		for (int i = 0; i < size * 2 - 1; i++) {
			for (int j = 0; j < size * 2 - 1; j++) {
				map[i][j] = new Flag();
			}
		}
	}

	// 毎ターン呼ばれる関数
	// EnumであるActionを返すことにより行動を起こします。
	// 現在の状況(前のターンに起きたこと)はsensorに格納されています。
	// 状況の一覧：
	public Action Do(HashMap<Status, Boolean> sensor) {
		Action action = Action.NONE;

		if (sensor.get(Status.GOLD))
			action = Action.PICKUP;
		else
			action = Action.values()[rnd.nextInt(4) + 1];
		
		if (sensor.get(Status.WALL))
			action = Action.SHOOT_UP;

		last = action;
		return action;
	}

	/*
	public void update(HashMap<Status, Boolean> sensor) {
		if (sensor.get(Status.WALL)) {
			switch (last) {
			case UP:
				for (int i = 0; i < size * 2 - 1; i++) {
					for (int j = y - 1; j >= 0; j--) {
						map[i][j].isWall = true;
					}
				}
				break;
			case DOWN:
				for (int i = 0; i < size * 2 - 1; i++) {
					for (int j = y + 1; j < size * 2 - 1; j++) {
						map[i][j].isWall = true;
					}
				}
				break;
			case LEFT:
				for (int j = 0; j < size * 2 - 1; j++) {
					for (int i = x - 1; i >= 0; i--) {
						map[i][j].isWall = true;
					}
				}
				break;
			case RIGHT:
				for (int j = 0; j < size * 2 - 1; j++) {
					for (int i = x + 1; i < size * 2 - 1; i++) {
						map[i][j].isWall = true;
					}
				}
				break;
			default:
				break;
			}
		}
		if (sensor.get(Status.WUMPUS)) {
			int n = 0;
			int lx = 0;
			int ly = 0;
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (Math.abs(i + j) == 1) {
						if (x + i >= 0 && x + i < size * 2 - 1 && y + j >= 0 && y + j < size * 2 - 1) {
							if (!map[x + i][y + j].isNone) {
								n++;
								lx = x + i;
								ly = y + i;
								map[x + i][y + j].qWumpus = true;
							}
						}
					}
				}
			}
			if (n == 1)
				map[lx][ly].isWumpus = true;
		}
	}
	*/
}
