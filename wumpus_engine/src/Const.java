
// 定数宣言
public class Const {
	// デバッグ用出力を出すかどうか
	// 集計の際にfalseにするのでtrueのままで構いません
	public static final boolean DEBUG = true;
	
	// シード値
	// マップ生成に使われるシード値です
	// このプログラムの第一引数に数値が渡っていると代わりにその数値をシード値として使います
	// 第一引数が数値でない場合はNumberFormatExceptionを投げます
	public static final int SEED = 2000;
	
	// ダンジョンの大きさ (SIZE * SIZE) : 5
	public static final int SIZE = 10;
	
	// ターンの最大値 : 1000
	public static final int MAX_TURN = 1000;
	
	// 穴の開いている確率(%) : 20
	public static final int HOLE_PROB = 20;
	
	// 矢の本数 : 1 
	public static final int ARROW = 1;
	
	// Wumpusの数 : 1
	public static final int WUMPUS = 1;
	
	// 金貨の数 : 1
	public static final int GOLD = 1;
	
	// 1ターン経過 : -1
	public static final int POINT_TURN = -1;
	
	// 矢を放つ : 0
	public static final int POINT_ARROW = -50;
	
	// 金貨を拾う : 100
	public static final int POINT_PICKUP = 100;
	
	// Wumpusを倒す : 200
	public static final int POINT_HIT = 200;
	
	// 脱出したときに金貨を持っている : 1000
	public static final int POINT_ESCAPE_GOLD = 1000;
	
	// 脱出する : 50
	public static final int POINT_ESCAPE = 50;
	
	// 脱出したときにWumpusを倒している : 1000
	public static final int POINT_ESCAPE_WUMPUS = 1000;
	
	// 穴に落ちる : -200
	public static final int POINT_HOLE = -200;
	
	// Wumpusに捕まる : -200
	public static final int POINT_WUMPUS = -200;
	
	// 自殺する : 0
	public static final int POINT_SUICIDE = 0;
	
	// アウトプットファイル名 : "result.out"
	public static final String FILE_NAME = "result.out";
}
