package jp.tnw.game23;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game_Main implements MouseListener, MouseMotionListener, KeyListener {

	// グローバル変数（どこでも使えるよ)
	final float FrameTime = 1.0f / 60.0f;// 0.031f;
	final int X_Size = 960;// ｹﾞｰﾑｳｨﾝﾄﾞの横ｻｲｽﾞ
	final int Y_Size = 540;// ｹﾞｰﾑｳｨﾝﾄﾞの縦ｻｲｽﾞ
	JFrame Wind = new JFrame("STG作成");// JFrame の初期化(ﾒﾆｭｰﾊﾞｰの表示ﾀｲﾄﾙ
	BufferStrategy offimage;// ﾀﾞﾌﾞﾙﾊﾞｯﾌｧでちらつき防止
	Insets sz;// ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ

	Font f = new Font("Default", Font.BOLD, 24);// 使用するフォントクラス宣言

	BufferedImage PNG;// 読み込むフィールド宣言

	// 部品化したｸﾗｽを使うためにインスタンスを作る--------------
	Haikei Hk = new Haikei();
	Jiki Jk = new Jiki();
	// Jtama Jtm =new Jtama();
	Jtama2 Jtm2 = new Jtama2();// 継承された自機弾2
	Jtama3 Jtm3 = new Jtama3();// 継承された自機弾3
	Jtama4 Jtm4 = new Jtama4();// 継承された自機弾4
	Jtama5 Jtm5 = new Jtama5();// 継承された自機弾5
	Jtama6 Jtm6 = new Jtama6();// 継承された自機弾6
	Jtama7 Jtm7 = new Jtama7();// 継承された自機弾7
	Jtama8 Jtm8 = new Jtama8();// 継承された自機弾7
	Zako_01 Zk = new Zako_01();
	Zako_02 Zk2 = new Zako_02();
	Zako_03 Zk3 = new Zako_03();
	Zako_04 Zk4 = new Zako_04();
	Zako_05 Zk5 = new Zako_05();
	Zako_06 Zk6 = new Zako_06();
	Zako_07 Zk7 = new Zako_07();
	Zako_08 Zk8 = new Zako_08();
	Zako_09 Zk9 = new Zako_09();
	Bomb_Class Bom = new Bomb_Class();
	Item_01 It_01 = new Item_01();
	
	GameOver Go =new GameOver();
	enemyAttcak eA =new enemyAttcak();

	int mx, my;// マウスの座標の保存
	// ----------------------------------
	// 初期化用の関数(コンストラクタ)
	// ・Window生成
	// ・Window大きさの指定
	// ・Windowの場所
	// ----------------------------------

	Game_Main() {
		// Windowの初期化---------------------
		Wind.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 閉じﾎﾞﾀﾝ許可
		Wind.setBackground(new Color(0, 0, 0));// 色指定
		Wind.setResizable(false);// ｻｲｽﾞ変更不可false
		Wind.setVisible(true);// 表示or非表示

		sz = Wind.getInsets();// ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ
		Wind.setSize(X_Size + sz.left + sz.right, Y_Size + sz.top + sz.bottom);// ｳｨﾝﾄﾞｳのｻｲｽﾞ
		Wind.setLocationRelativeTo(null);// 中央に表示

		// ちらつき防止の設定(ﾀﾞﾌﾞﾙﾊﾞｯﾌｧﾘﾝｸﾞ)-------------------------------------
		Wind.setIgnoreRepaint(true);// JFrameの標準書き換え処理無効
		Wind.createBufferStrategy(2);// 2でﾀﾞﾌﾞﾙ
		offimage = Wind.getBufferStrategy();
		// ﾏｳｽｲﾍﾞﾝﾄ初期化をGame_Mainのｺﾝｽﾄﾗｸﾀで実装---------------------
		Wind.addMouseMotionListener(this);
		Wind.addMouseListener(this);
		Wind.addKeyListener(this);

		// ファイルの読み込み
		Hk.load();
		Jk.load();
		// Jtm.load ();
		Jtm2.load();
		Jtm3.load();
		Jtm4.load();
		Jtm5.load();
		Jtm6.load();
		Jtm7.load();
		Jtm8.load();
		Zk.load();
		Zk2.load();
		Zk3.load();
		Zk4.load();
		Zk5.load();
		Zk6.load();
		Zk7.load();
		Zk8.load();
		Zk9.load();
		Bom.Load();
		It_01.load();
		Go.load();
		eA.load();

		// クラス変数へ代入
		// Jtm.Jk=Jk;//アドレスのコピー
		Jtm2.Jk = Jk;// アドレスのコピー
		Jtm3.Jk = Jk;// アドレスのコピー
		Jtm4.Jk = Jk;// アドレスのコピー
		Jtm5.Jk = Jk;// アドレスのコピー
		Jtm6.Jk = Jk;// アドレスのコピー
		Jtm7.Jk = Jk;// アドレスのコピー
		Jtm8.Jk = Jk;// アドレスのコピー
		
		Jk.Hk =Hk;

		Jk.Jtm[0] = Jtm2;
		Jk.Jtm[1] = Jtm3;
		Jk.Jtm[2] = Jtm4;
		Jk.Jtm[3] = Jtm5;
		Jk.Jtm[4] = Jtm6;
		Jk.Jtm[5] = Jtm7;
		Jk.Jtm[6] = Jtm8;
		It_01.Jk = Jk;

		Zk.It_01 = It_01;
		It_01.Zk = Zk;
		Zk.Jk = Jk;
		Zk.Bom = Bom;
		Zk.Jtm[0] = Jtm2;
		Zk.Jtm[0] = Jtm2;
		Zk.Jtm[1] = Jtm3;
		Zk.Jtm[2] = Jtm4;
		Zk.Jtm[3] = Jtm5;
		Zk.Jtm[4] = Jtm6;
		Zk.Jtm[5] = Jtm7;
		Zk.Jtm[6] = Jtm8;

		Zk2.It_01 = It_01;
		It_01.Zk2 = Zk2;
		Zk2.Jk = Jk;
		Zk2.Bom = Bom;
		Zk2.Jtm[0] = Jtm2;
		Zk2.Jtm[0] = Jtm2;
		Zk2.Jtm[1] = Jtm3;
		Zk2.Jtm[2] = Jtm4;
		Zk2.Jtm[3] = Jtm5;
		Zk2.Jtm[4] = Jtm6;
		Zk2.Jtm[5] = Jtm7;
		Zk2.Jtm[6] = Jtm8;

		Zk3.It_01 = It_01;
		It_01.Zk3 = Zk3;
		Zk3.Jk = Jk;
		Zk3.Bom = Bom;
		Zk3.Jtm[0] = Jtm2;
		Zk3.Jtm[0] = Jtm2;
		Zk3.Jtm[1] = Jtm3;
		Zk3.Jtm[2] = Jtm4;
		Zk3.Jtm[3] = Jtm5;
		Zk3.Jtm[4] = Jtm6;
		Zk3.Jtm[5] = Jtm7;
		Zk3.Jtm[6] = Jtm8;

		Zk4.It_01 = It_01;
		It_01.Zk4 = Zk4;
		Zk4.Jk = Jk;
		Zk4.Bom = Bom;
		Zk4.Jtm[0] = Jtm2;
		Zk4.Jtm[0] = Jtm2;
		Zk4.Jtm[1] = Jtm3;
		Zk4.Jtm[2] = Jtm4;
		Zk4.Jtm[3] = Jtm5;
		Zk4.Jtm[4] = Jtm6;
		Zk4.Jtm[5] = Jtm7;
		Zk4.Jtm[6] = Jtm8;

		Zk5.It_01 = It_01;
		It_01.Zk5 = Zk5;
		Zk5.Jk = Jk;
		Zk5.Bom = Bom;
		Zk5.Jtm[0] = Jtm2;
		Zk5.Jtm[0] = Jtm2;
		Zk5.Jtm[1] = Jtm3;
		Zk5.Jtm[2] = Jtm4;
		Zk5.Jtm[3] = Jtm5;
		Zk5.Jtm[4] = Jtm6;
		Zk5.Jtm[5] = Jtm7;
		Zk5.Jtm[6] = Jtm8;

		Zk6.It_01 = It_01;
		It_01.Zk6 = Zk6;
		Zk6.Jk = Jk;
		Zk6.Bom = Bom;
		Zk6.Jtm[0] = Jtm2;
		Zk6.Jtm[0] = Jtm2;
		Zk6.Jtm[1] = Jtm3;
		Zk6.Jtm[2] = Jtm4;
		Zk6.Jtm[3] = Jtm5;
		Zk6.Jtm[4] = Jtm6;
		Zk6.Jtm[5] = Jtm7;
		Zk6.Jtm[6] = Jtm8;

		Zk7.It_01 = It_01;
		It_01.Zk7 = Zk7;
		Zk7.Jk = Jk;
		Zk7.Bom = Bom;
		Zk7.Jtm[0] = Jtm2;
		Zk7.Jtm[0] = Jtm2;
		Zk7.Jtm[1] = Jtm3;
		Zk7.Jtm[2] = Jtm4;
		Zk7.Jtm[3] = Jtm5;
		Zk7.Jtm[4] = Jtm6;
		Zk7.Jtm[5] = Jtm7;
		Zk7.Jtm[6] = Jtm8;

		Zk8.It_01 = It_01;
		It_01.Zk8 = Zk8;
		Zk8.Jk = Jk;
		Zk8.Bom = Bom;
		Zk8.Jtm[0] = Jtm2;
		Zk8.Jtm[0] = Jtm2;
		Zk8.Jtm[1] = Jtm3;
		Zk8.Jtm[2] = Jtm4;
		Zk8.Jtm[3] = Jtm5;
		Zk8.Jtm[4] = Jtm6;
		Zk8.Jtm[5] = Jtm7;
		Zk8.Jtm[6] = Jtm8;

		Zk9.It_01 = It_01;
		It_01.Zk9 = Zk9;
		Zk9.Jk = Jk;
		Zk9.Bom = Bom;
		Zk9.Jtm[0] = Jtm2;
		Zk9.Jtm[0] = Jtm2;
		Zk9.Jtm[1] = Jtm3;
		Zk9.Jtm[2] = Jtm4;
		Zk9.Jtm[3] = Jtm5;
		Zk9.Jtm[4] = Jtm6;
		Zk9.Jtm[5] = Jtm7;
		Zk9.Jtm[6] = Jtm8;
		
		Go.Jk=Jk;
		Go.Bom=Bom;

		try {
			PNG = ImageIO.read(getClass().getResource(""));// ファイル名

		} catch (IOException e) {// 読み込みエラーの場合
			// e.printStackTrace();
		}

		// ﾀｲﾏｰﾀｽｸ起動----------------------------
		Timer TM = new Timer();// ﾀｲﾏｰｸﾗｽの実体化
		TM.schedule(new timer_TSK(), 17, 17);// 17ms後から 17msおきに
		// どこ？ 17[ms]=プログラムが動き出す最初の時間
		// 17[ms]その後は17[ms]繰り返し

	}// Main_Game end

	// ---------------------------
	// ﾀｲﾏｰｸﾗｽ 1/60秒で1回動作
	// extends=継承
	// ---------------------------
	class timer_TSK extends TimerTask {

		public void run() {// 無限ループのプログラム

			// 計算やｱﾆﾒなどの処理
			Jk.move();
			// Jtm.move();
			Jtm2.move();
			Jtm3.move();
			Jtm4.move();
			Jtm5.move();
			Jtm6.move();
			Jtm7.move();
			Jtm8.move();
			Zk.move();
			Zk2.move();
			Zk3.move();
			Zk4.move();
			Zk5.move();
			Zk6.move();
			Zk7.move();
			Zk8.move();
			Zk9.move();
			Bom.UpDate();
			Hk.move();
			It_01.move();
			Go.move();
			eA.move();

			Graphics g2 = offimage.getDrawGraphics();// ｸﾞﾗﾌｨｯｸ初期化
			Graphics2D g = (Graphics2D) g2;

			if (offimage.contentsLost() == false) {// 利可能??
				g.translate(sz.left, sz.top);// ﾒﾆｭｰﾊﾞｰのｻｲｽﾞ補正
				g.clearRect(0, 0, X_Size, Y_Size);// 画面ｸﾘｱ(左上X、左上Y、右下x、右下y)
				g.setColor(Color.lightGray);// 色指定
				g.fillRect(0, 0, X_Size, Y_Size);// 塗りつぶし
				// g.drawImage(PNG,0,0,Wind);//仮背景の表示（試験の時はｺﾒﾝﾄｱｳﾄすること）

				g.drawImage(PNG, 0, 0, 960, 540, 0, 0, 800, 600, Wind);
				// --------------------------------------------------
				// 絵や文字を表示
				Hk.print(g, Wind);// 背景
				Jk.print(g, Wind);
				// Jtm.print(g, Wind);
				Jtm2.print(g, Wind);
				Jtm3.print(g, Wind);
				Jtm4.print(g, Wind);
				Jtm5.print(g, Wind);
				Jtm6.print(g, Wind);
				Jtm7.print(g, Wind);
				Jtm8.print(g, Wind);
				Zk.print(g, Wind);
				Zk2.print(g, Wind);
				Zk3.print(g, Wind);
				Zk4.print(g, Wind);
				Zk5.print(g, Wind);
				Zk6.print(g, Wind);
				Zk7.print(g, Wind);
				Zk8.print(g, Wind);
				Zk9.print(g, Wind);
				Bom.Disp(g, Wind);
				It_01.print(g, Wind);
				Go.print(g, Wind);
				eA.print(g, Wind);

				// 文字の表示
				g.setColor(Color.WHITE);// 色指定
				g.setFont(f);
				g.drawString("STG作成(A23万光洪)", 10, Y_Size - 20);

				// ---------------------------------------------------
				offimage.show();// ﾀﾞﾌﾞﾙﾊﾞｯﾌｧの切り替え
				g.dispose();// ｸﾞﾗﾌｨｯｸｲﾝｽﾀﾝｽの破棄

			} // if end ｸﾞﾗﾌｨｯｸOK??

		}// run end
	}// timer task class end

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

		// ゲームメインクラスのインスタンスを生成----------------
		Game_Main GM = new Game_Main();

	}

	// ----------------------------
	// Keyに関するメソッド
	// ----------------------------
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		if (e.getKeyCode() == KeyEvent.VK_UP)
			Jk.K_U = 1;
		// if(e.getKeyCode()==KeyEvent.VK_NUMPAD8)K_U=1;
		// if(e.getKeyCode()==KeyEvent.VK_W)K_U=1;
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			Jk.K_D = 1;
		// if(e.getKeyCode()==KeyEvent.VK_NUMPAD2)K_U=1;
		// if(e.getKeyCode()==KeyEvent.VK_S)K_U=1;
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			Jk.K_L = 1;
		// if(e.getKeyCode()==KeyEvent.VK_NUMPAD4)K_U=1;
		// if(e.getKeyCode()==KeyEvent.VK_A)K_U=1;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			Jk.K_R = 1;
		// if(e.getKeyCode()==KeyEvent.VK_NUMPAD6)K_U=1;
		// if(e.getKeyCode()==KeyEvent.VK_D)K_U=1;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

		if (e.getKeyCode() == KeyEvent.VK_UP)
			Jk.K_U = 0;
		// if(e.getKeyCode()==KeyEvent.VK_NUMPAD8)K_U=0;
		// if(e.getKeyCode()==KeyEvent.VK_W)K_U=0;
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			Jk.K_D = 0;
		// if(e.getKeyCode()==KeyEvent.VK_NUMPAD2)K_U=0;
		// if(e.getKeyCode()==KeyEvent.VK_S)K_U=0;
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			Jk.K_L = 0;
		// if(e.getKeyCode()==KeyEvent.VK_NUMPAD4)K_U=0;
		// if(e.getKeyCode()==KeyEvent.VK_A)K_U=0;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			Jk.K_R = 0;
		// if(e.getKeyCode()==KeyEvent.VK_NUMPAD6)K_U=0;
		// if(e.getKeyCode()==KeyEvent.VK_D)K_U=0;

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		Insets sz = Wind.getInsets();// Windの幅
		mx = e.getX() - sz.left;
		my = e.getY() - sz.top;

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
