import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Logger {
	private int seed, size, arrow, wumpus, gold, prob;
	private ArrayList<Action> list;

	// コンストラクタ
	public Logger(int seed, int size, int arrow, int wumpus, int gold, int prob) {
		this.seed = seed;
		this.size = size;
		this.arrow = arrow;
		this.wumpus = wumpus;
		this.gold = gold;
		this.prob = prob;
		list = new ArrayList<Action>();
	}

	// 行動記録
	public void add(Action action) {
		list.add(action);
	}

	// ログの書き出し
	public void print(String str) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(str)));
		String output = "";
		output += Integer.toString(seed) + ",";
		output += Integer.toString(size) + ",";
		output += Integer.toString(arrow) + ",";
		output += Integer.toString(wumpus) + ",";
		output += Integer.toString(gold) + ",";
		output += Integer.toString(prob) + ",";
		for (Action action : list)
			output += Integer.toString(action.ordinal()) + ",";
		output = output.substring(0, output.length() - 1);
		bw.write(output);
		bw.newLine();
		bw.close();
	}

	public void print() throws IOException {
		print(Const.FILE_NAME);
	}
}
