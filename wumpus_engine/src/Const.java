
// �萔�錾
public class Const {
	// �f�o�b�O�p�o�͂��o�����ǂ���
	// �W�v�̍ۂ�false�ɂ���̂�true�̂܂܂ō\���܂���
	public static final boolean DEBUG = true;
	
	// �V�[�h�l
	// �}�b�v�����Ɏg����V�[�h�l�ł�
	// ���̃v���O�����̑������ɐ��l���n���Ă���Ƒ���ɂ��̐��l���V�[�h�l�Ƃ��Ďg���܂�
	// �����������l�łȂ��ꍇ��NumberFormatException�𓊂��܂�
	public static final int SEED = 2000;
	
	// �_���W�����̑傫�� (SIZE * SIZE) : 5
	public static final int SIZE = 10;
	
	// �^�[���̍ő�l : 1000
	public static final int MAX_TURN = 1000;
	
	// ���̊J���Ă���m��(%) : 20
	public static final int HOLE_PROB = 20;
	
	// ��̖{�� : 1 
	public static final int ARROW = 1;
	
	// Wumpus�̐� : 1
	public static final int WUMPUS = 1;
	
	// ���݂̐� : 1
	public static final int GOLD = 1;
	
	// 1�^�[���o�� : -1
	public static final int POINT_TURN = -1;
	
	// ������ : 0
	public static final int POINT_ARROW = -50;
	
	// ���݂��E�� : 100
	public static final int POINT_PICKUP = 100;
	
	// Wumpus��|�� : 200
	public static final int POINT_HIT = 200;
	
	// �E�o�����Ƃ��ɋ��݂������Ă��� : 1000
	public static final int POINT_ESCAPE_GOLD = 1000;
	
	// �E�o���� : 50
	public static final int POINT_ESCAPE = 50;
	
	// �E�o�����Ƃ���Wumpus��|���Ă��� : 1000
	public static final int POINT_ESCAPE_WUMPUS = 1000;
	
	// ���ɗ����� : -200
	public static final int POINT_HOLE = -200;
	
	// Wumpus�ɕ߂܂� : -200
	public static final int POINT_WUMPUS = -200;
	
	// ���E���� : 0
	public static final int POINT_SUICIDE = 0;
	
	// �A�E�g�v�b�g�t�@�C���� : "result.out"
	public static final String FILE_NAME = "result.out";
}
