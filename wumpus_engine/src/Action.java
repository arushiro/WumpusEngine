
// ターンに取れる行動のEnum
public enum Action {
	NONE, // 何もしない
	UP, DOWN, LEFT, RIGHT, // 移動
	SHOOT_UP, SHOOT_DOWN, SHOOT_LEFT, SHOOT_RIGHT, // 矢を放つ
	PICKUP, // 金貨を拾う
	END // ゲーム終了の宣言
}
