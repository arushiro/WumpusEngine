import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Game {
	// ���݈ʒu��x,y�A�}�b�v�̑傫���A���_�A��̐��A���݂��E�������AWumpus��|������
	private int x, y, size, point, arrow, pickup, hit, wumpus, gold;
	// �Q�[���I���t���O
	private boolean isEnd;
	// �s���̋L�^
	private Logger logger;
	// �}�b�v�Ǘ�
	private Map map;
	// �G�[�W�F���g
	private Agent agent;
	// �Z���T�[
	private HashMap<Status, Boolean> sensor;

	public Game(int seed, int size, int wumpus, int gold, int prob, int arrow) {
		this.size = size;
		logger = new Logger(seed, size, arrow, wumpus, gold, prob);
		map = new Map(seed, size, wumpus, gold, prob);
		agent = new Agent(arrow, size);
		point = 0;
		this.arrow = arrow;
		this.wumpus = wumpus;
		this.gold = gold;
		x = map.getStartX();
		y = map.getStartY();
		isEnd = false;
		pickup = 0;
		hit = 0;

		// �Z���T�[�̏�����
		sensor = new HashMap<Status, Boolean>();
		sensor.put(Status.WALL, false);
		sensor.put(Status.HOLE, false);
		sensor.put(Status.GOLD, false);
		sensor.put(Status.HIT, false);
		sensor.put(Status.WUMPUS, false);

		// ����Wumpus�����͂ɂ�������Z���T�[���X�V����
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (Math.abs(i + j) == 1) {
					sensorUpdate(i, j);
				}
			}
		}

		// ������Ԃ̏o��
		if (Const.DEBUG) {
			System.out.println("MAP : " + seed);
			for (int j = 0; j < size; j++) {
				for (int i = 0; i < size; i++) {
					System.out.print(map.getTile(i, j).toString().charAt(0));
				}
				System.out.println();
			}
			for (Status s : sensor.keySet()) {
				System.out.println(s.toString() + " : " + sensor.get(s).toString());
			}
			System.out.println("----");
		}
	}

	public void run() throws IOException {
		int turn = 0;

		// �^�[������(���C�����[�v)
		for (; turn < Const.MAX_TURN && !isEnd; turn++) {
			// �^�[���o�߂ɂ����_/���_
			point += Const.POINT_TURN;

			// �A�N�V�����̎擾
			Action action = agent.Do(sensor);

			// �A�N�V�����̋L�^
			logger.add(action);

			// �Z���T�[�̏�����
			sensor = new HashMap<Status, Boolean>();
			sensor.put(Status.WALL, false);
			sensor.put(Status.HOLE, false);
			sensor.put(Status.GOLD, false);
			sensor.put(Status.HIT, false);
			sensor.put(Status.WUMPUS, false);

			// �A�N�V���������s
			switch (action) {

			// �ړ�
			case UP:
				move(0, -1);
				break;
			case DOWN:
				move(0, 1);
				break;
			case LEFT:
				move(-1, 0);
				break;
			case RIGHT:
				move(1, 0);
				break;

			// ������
			case SHOOT_UP:
				if (arrow > 0) {
					shoot(x, y, 0, -1);
					point += Const.POINT_ARROW;
					arrow--;
				}
				break;
			case SHOOT_DOWN:
				if (arrow > 0) {
					shoot(x, y, 0, 1);
					point += Const.POINT_ARROW;
					arrow--;
				}
				break;
			case SHOOT_LEFT:
				if (arrow > 0) {
					shoot(x, y, -1, 0);
					point += Const.POINT_ARROW;
					arrow--;
				}
				break;
			case SHOOT_RIGHT:
				if (arrow > 0) {
					shoot(x, y, 1, 0);
					point += Const.POINT_ARROW;
					arrow--;
				}
				break;

			// ���݂��E��
			case PICKUP:
				if (map.pickUp(x, y)) {
					pickup++;
					point += Const.POINT_PICKUP;
				}
				break;

			// �Q�[���I��
			case END:
				if (map.getTile(x, y) == Tile.UPSTAIRS) {
					if (pickup == gold)
						point += Const.POINT_ESCAPE_GOLD;
					if (hit == wumpus)
						point += Const.POINT_ESCAPE_WUMPUS;

					point += Const.POINT_ESCAPE;
				} else {
					point += Const.POINT_SUICIDE;
				}
				isEnd = true;
				break;

			// �������Ȃ�
			case NONE:
				point += Const.POINT_SUICIDE;
				break;

			default:
				isEnd = true;
				break;
			}

			// ����Wumpus�����͂ɂ�������Z���T�[���X�V����
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if (Math.abs(i + j) == 1) {
						sensorUpdate(i, j);
					}
				}
			}

			// �����ɋ��݂���������Z���T�[���X�V����
			if (map.getTile(x, y) == Tile.GOLD)
				sensor.put(Status.GOLD, true);

			// �f�o�b�O�p�̏����o��
			if (Const.DEBUG) {
				System.out.println("Turn : " + turn);
				System.out.println("Point : " + point);
				System.out.println("Arrow Left : " + arrow);
				System.out.println("Action : " + action.toString());

				// �}�b�v�̏o��
				for (int j = 0; j < size; j++) {
					for (int i = 0; i < size; i++) {
						if (i == x && j == y)
							System.out.print("A");
						else
							System.out.print(map.getTile(i, j).toString().charAt(0));
					}
					System.out.println();
				}

				// �Z���T�[�̏o��
				for (Status s : sensor.keySet()) {
					System.out.println(s.toString() + " : " + sensor.get(s).toString());
				}
				System.out.println("----");
			}
		}

		// �ő�^�[�����߂����玩�E
		if (turn == Const.MAX_TURN && !isEnd) {
			point += Const.POINT_SUICIDE;
		}

		// �_���̏o��
		if (Const.DEBUG) {
			System.out.print("Point : ");
		}
		System.out.println(point);

		if (Const.DEBUG) {
			try (Scanner sc = new Scanner(System.in)) {
				System.out.println("----");
				System.out.print("Do you create a result file? (y/N) : ");
				if (sc.nextLine().trim().toLowerCase().equals("y")) {
					System.out.print("File Name (Default : " + Const.FILE_NAME + ") : ");
					String str = sc.nextLine().trim();
					if (str.isEmpty())
						logger.print();
					else
						logger.print(str);
				}
			}
		}
	}

	// �ړ�����
	private void move(int dx, int dy) {
		point += Const.POINT_TURN;
		switch (map.getTile(x + dx, y + dy)) {
		case NONE:
		case GOLD:
		case UPSTAIRS:
			break;
		case HOLE:
			point += Const.POINT_HOLE;
			isEnd = true;
			break;
		case WUMPUS:
			point += Const.POINT_WUMPUS;
			isEnd = true;
			break;
		case STONE:
			x -= dx;
			y -= dy;
			sensor.put(Status.WALL, true);
			break;
		default:
			break;
		}
		x += dx;
		y += dy;
	}

	// ���������
	private void shoot(int x, int y, int dx, int dy) {
		if (arrow > 0) {
			switch (map.getTile(x + dx, y + dy)) {
			case NONE:
			case GOLD:
			case UPSTAIRS:
			case HOLE:
				shoot(x + dx, y + dy, dx, dy);
				break;
			case WUMPUS:
				map.hit(x + dx, y + dy);
				sensor.put(Status.HIT, true);
				hit++;
				point += Const.POINT_HIT;
				break;
			case STONE:
				break;
			default:
				break;
			}
		}
	}

	// ���͂̃Z���T�[�̍X�V����(Wumpus��Hole�p)
	private void sensorUpdate(int dx, int dy) {
		switch (map.getTile(x + dx, y + dy)) {
		case NONE:
		case GOLD:
		case UPSTAIRS:
			break;
		case HOLE:
			sensor.put(Status.HOLE, true);
			break;
		case WUMPUS:
			sensor.put(Status.WUMPUS, true);
			break;
		case STONE:
			break;
		default:
			break;
		}
	}

}
