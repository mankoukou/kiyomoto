package jp.tnw.game23;

import java.awt.Color;
//
//マウス操作のデモです。
//
//FrameやCanvasを使うのに必要なimport文
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Jiki {

	final double SP = 150;// 移動速度

	final double FrameTime = 0.017;//
	final int Max = 1;// 敵の個数
	final int HP_Max = 500;// 最大HP
	Font f = new Font("Default", Font.BOLD, 16);// 使用するフォントクラス宣言

	BufferedImage Jiki;// 読み込むフィールド宣言
	BufferedImage dmg;
	double x;
	double y;
	double[] X = new double[Max];// 横座標
	double[] Y = new double[Max];// 縦座標
	double[] No = new double[Max];

	double[] Xsp = new double[Max];// 横スピード
	double[] Ysp = new double[Max];// 縦スピード
	double[] Xspg = new double[Max];// 横スピード(加速度)
	double[] Yspg = new double[Max];// 縦スピード(加速度)

	double[] Ny = new double[Max];// ｷｬﾗｸﾀの何列目

	double count;// ダメージ点滅カウンター

	double D_time;// 点滅時間

	int D_flag;// ダメージ点滅0:ノーマル;1:影

	int hp;// 現在hp

	int[] Flag = new int[Max];// 状態を保存している変数
								// 0::待機
								// 1::画面内
								// 2::
								// 3::

	double[] Timer = new double[12];// 敵のリクエストをｺﾝﾄﾛｰﾙします

	int PW;// パワーアップ アイテムの個数

	int[] Item_Get = new int[12];

	int K_U, K_D, K_L, K_R;// キーボートから入力

	// クラス変数の宣言
	Jtama[] Jtm = new Jtama[8];
	Haikei Hk;

	// ------------------------------------
	// コンストラクタ1(特別な初期化メソッド)
	// ------------------------------------
	public Jiki() {
		D_time = 2;
		D_flag = 0;
		x = 960 / 2 - 100 / 2;
		y = 400;
		hp = HP_Max;
		//hp = 250;//試し

	}

	// -------------------------
	// 敵表示リクエスト
	// 左上から右に放物線
	// -------------------------
	public void Req() {
		Timer[0] = Timer[0] - FrameTime;

		if (Timer[0] < 0) {
			Timer[0] = 0.10;// インタバールの再設定

			for (int i = 0; i < Max; i++) {
				if (Flag[i] == 0)// 待機状態のみ
				{
					// 出現位置初期化の最
					X[i] = x;
					Y[i] = y;
					PW = 0;

					Xsp[i] = 0;// 初速度初期化
					Ysp[i] = -600;
					Xspg[i] = 0;// 加速度初期化
					Yspg[i] = 0;
					// キャラクタ初期化
					Flag[i] = 1;// 出現OKフラグON!!
					break;// 空き敵を使いきらないためfor文を途中で抜ける
				} // if end
			} // for end

		} // Timer[] if end

	}// Req1 end

	// -------------------------
	// 絵を動かす
	// -------------------------
	public void move() {// 引数でマウスの座標を渡すint mx,int my

		Req();
		// x=mx-50;
		// y=my-50;

		// キーで移動
		int Key_st = K_U + K_D * 10 + K_L * 100 + K_R * 1000;// 0001,0011,0111,1111,0010,1010,1110,0100,1100,1001,1011,1101
		int Kaku, lmt_fl;
		lmt_fl = 0;

		Kaku = 400;// 初期化

		switch (Key_st) {
		case 0:
			Kaku = 400;
			break;// キー入力なしは動かさないので400set
		case 1:
			Kaku = 270;
			if (y < 50)
				lmt_fl = 1;
			break;// ↑
		case 10:
			Kaku = 90;
			if (y > 470)
				lmt_fl = 1;
			break;// ↓
		case 100:
			Kaku = 180;
			if (x < 50)
				lmt_fl = 1;
			break;// ←
		case 1000:
			Kaku = 0;
			if (x > 960 - 100 - 50)
				lmt_fl = 1;
			break;// →

		// case 11:
		// case 1100:

		/*
		 * case 1101: Kaku = 270; break;// ↑ case 1110: Kaku = 90; break;// ↓
		 * case 111: Kaku = 180; break;// ← case 1011: Kaku = 0; break;// →
		 */

		case 101:
			Kaku = 180 + 45;
			if (y < 50)
				Kaku = 180;
			if (x < 50)
				Kaku = 270;
			if (y < 50 && x < 50)
				lmt_fl = 1;
			break;// 左斜め上
		case 1001:
			Kaku = 270 + 45;
			if (y < 50)
				Kaku = 0;
			if (x > 960 - 100 - 50)
				Kaku = 270;
			if (y < 50 && x > 960 - 100 - 50)
				lmt_fl = 1;
			break;// 右斜め上
		case 110:
			Kaku = 90 + 45;
			if (y > 470)
				Kaku = 180;
			if (x < 50)
				Kaku = 90;
			if (y > 470 && x < 50)
				lmt_fl = 1;
			break;// 左斜め下
		case 1010:
			Kaku = 45;
			if (y > 470)
				Kaku = 0;
			if (x > 960 - 100 - 50)
				Kaku = 90;
			if (y > 470 && x > 960 - 100 - 50)
				lmt_fl = 1;
			break;// 右斜め下
		}
		if (Kaku < 400 && lmt_fl == 0) {// 正確なキーが入っていれば移動

			y += FrameTime * SP * Math.sin(Kaku * Math.PI / 180);

			x += FrameTime * SP * Math.cos(Kaku * Math.PI / 180);

		}
		
		
		//Mapとのあたり？？
		if (Hk.Hit(x+50,y+60)==1 && D_time<=0){
			D_time=2;
			hp-=50;
		}
		// -------点滅処理--------
		 D_time -= FrameTime;

		if (D_time >0) {

			count += FrameTime * 20;
			D_flag = (int) count % 2;// 偶数奇数判定
		}else{
			D_flag=0;
		}
		/*
		 * if(Key_st==1) y+=FrameTime*-SP; if(Key_st==10) y+=FrameTime*SP;
		 * if(Key_st==100) x+=FrameTime*-SP; if(Key_st==1000) x+=FrameTime*SP;
		 * 
		 * if(Key_st==111) x+=FrameTime*-SP; if(Key_st==1011) x+=FrameTime*SP;
		 * if(Key_st==1101) y+=FrameTime*-SP; if(Key_st==1110) y+=FrameTime*SP;
		 * 
		 * if(Key_st==101) {y+=FrameTime*-SP*Math.sin(Math.PI/4);
		 * x+=FrameTime*-SP*Math.cos(Math.PI/4);} if(Key_st==1001)
		 * {y+=FrameTime*-SP*Math.sin(Math.PI/4);
		 * x+=FrameTime*SP*Math.cos(Math.PI/4);} if(Key_st==110)
		 * {y+=FrameTime*SP*Math.sin(Math.PI/4);
		 * x+=FrameTime*-SP*Math.cos(Math.PI/4);} if(Key_st==1010)
		 * {y+=FrameTime*SP*Math.sin(Math.PI/4);
		 * x+=FrameTime*SP*Math.cos(Math.PI/4);}
		 */
		// アイテムが弾に影響を与える

		Jtm[PW].Req();

		if (Item_Get[0] == 1)
			PW = 1;
		if (Item_Get[0] == 2 && PW == 1)
			PW = 2;
		if (Item_Get[0] == 3 && PW == 2)
			PW = 3;
		if (Item_Get[0] == 4 && PW == 3)
			PW = 4;
		if (Item_Get[0] == 5 && PW == 4)
			PW = 5;
		if (Item_Get[0] == 6 && PW == 5)
			PW = 6;

		for (int i = 0; i < Max; i++) {
			if (Flag[i] != 0) {
				// 加速度が初速に影響を与える------------------

				Xsp[i] = Xsp[i] + FrameTime * Xspg[i];
				Ysp[i] = Ysp[i] + FrameTime * Yspg[i];

				// 初速が座標に影響を与える------------------

				X[i] = X[i] + FrameTime * Xsp[i];
				Y[i] = Y[i] + FrameTime * Ysp[i];

				// 軌道の切り替え

				// 画面外処理--------------------------------
				if (X[i] < -80 || X[i] > 960 + 40 || Y[i] < -40 || Y[i] > 540 + 40) {
					Flag[i] = 0;// 画面外のキャラクタを待機状態にさせる

				} // if end

				// ループアニメション0--3までは長いから0.5～2.5にしてあげる
				// 表示してる一枚一枚の図の時間を注意
				/*
				 * No=No+0.017*5; if(Ny>3)No=0;//ループアニメ
				 */

				if (Ny[i] < 6.5) {
					Ny[i] = Ny[i] + 0.017 * 10;// 時間の概念の共有
					if (Ny[i] > 6.5)
						Ny[i] = 0;

				} // if end

			} // FLag chk end
		} // for end
	}

	// ----------------------------------
	// 絵を読み込む
	// ----------------------------------
	public void load() {// updata
		try {
			Jiki = ImageIO.read(getClass().getResource("stg_image/jiki1.png"));
			dmg = ImageIO.read(getClass().getResource("stg_image/dmg.png"));

		} catch (IOException e) {// 読み込みエラーの場合
			e.printStackTrace();
		}
	}//

	// ----------------------------------
	// 絵の表示
	// ----------------------------------
	public void print(Graphics2D g, JFrame Wind) {// disp
		for (int i = 0; i < Max; i++) {
			g.drawImage(Jiki, (int) x, (int) y, (int) x + 100, (int) y + 100, // 画面のどこに?x,y左上座標
																				// 右下座標
					100 * D_flag, 100 * (int) Ny[i], 100 * D_flag + 100, 100 * ((int) Ny[i] + 1), // 画像のどこか?x,y左上座標
					// 右下座標
					Wind);// 座標は小数ではダメ

			// ダメージバー影----------------------------------------
			g.drawImage(dmg, 0 + 50, 20, 144 + 50, 8 + 20, // 画面のどこに?x,y左上座標
					// 右下座標
					0, 8 * 0, 144, 8 * 1, // 画像のどこか?x,y左上座標
											// 右下座標
					Wind);// 座標は小数ではダメ
			// ダメージバー-----------------------------------------
			
			double H=144*((double)hp/(double)HP_Max);
			
			g.drawImage(dmg, 0 + 50, 20, (int)H + 50, 8 + 20, // 画面のどこに?x,y左上座標
					// 右下座標
					0, 8 * 1,(int)H, 8 * 2, // 画像のどこか?x,y左上座標
					// 右下座標
					Wind);// 座標は小数ではダメ

		}

		// 文字の表示(文字の原点は左下)(画像の原点は左上）
		// g.setColor(Color.YELLOW);//色指定
		// g.setFont(f);
		// g.drawString("(X)座標:"+x,10,20);
		// g.drawString("(Y)座標:"+y,10,40);
		// 文字の表示(文字の原点は左下)(画像の原点は左上）
		g.setColor(Color.MAGENTA);// 色指定
		g.setFont(f);
		// g.drawString("Item0:" + Item_Get[0], 10, 20);
		// g.drawString("Item1:" + Item_Get[1], 10, 40);
		// g.drawString("Item2:" + Item_Get[2], 10, 60);
		// g.drawString("Item3:" + Item_Get[3], 10, 80);
		// g.drawString("Item4:" + Item_Get[4], 10, 100);
		// g.drawString("Item5:" + Item_Get[5], 10, 120);
		// g.drawString("Item6:" + Item_Get[6], 10, 140);
		// g.drawString("Item7:" + Item_Get[7], 10, 160);

		// g.drawString("PW:" + PW, 10, 180);
		// g.drawString("(Y)座標:"+y,10,40);

	}

}// Class end