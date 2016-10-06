package jp.tnw.game23;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Zako_03 {

	final double FrameTime = 0.017;//
	final int Max = 20;// 敵の個数
	Font f = new Font("Default", Font.BOLD, 16);// 使用するフォントクラス宣言

	BufferedImage Zako;// 読み込むフィールド宣言
	double[] XX = new double[Max];// 横座標
	double[] YY = new double[Max];// 縦座標
	double[] No = new double[Max];

	double[] Xsp = new double[Max];// 横スピード
	double[] Ysp = new double[Max];// 縦スピード
	double[] Xspg = new double[Max];// 横スピード(加速度)
	double[] Yspg = new double[Max];// 縦スピード(加速度)
	double[] kaku = new double[Max];// 角度
	double[] kaku_P = new double[Max];// 角度がマイナスかプラスする

	double[] R = new double[Max];// 円の半径
	double[] Rsp = new double[Max];// 半径の変化スピード

	double W_Timer;// 全体を何秒とめる？

	int[] Ny = new int[Max];// ｷｬﾗｸﾀの何列目

	int[] Flag = new int[Max];// 状態を保存している変数
								// 0::待機
								// 1::画面内
								// 2::
								// 3::

	double[] Timer = new double[12];// 敵のリクエストをｺﾝﾄﾛｰﾙします

	Jiki Jk;// 自機クラス
	Bomb_Class Bom;
	Item_01 It_01;

	Jtama[] Jtm = new Jtama[8];

	// ------------------------------------
	// コンストラクタ1(特別な初期化メソッド)
	// ------------------------------------
	public Zako_03() {

	}

	// ------------------------------------
	// コンストラクタ2(特別な初期化メソッド)
	// ------------------------------------
	public Zako_03(int x, int y) {
		XX[0] = x;
		YY[0] = y;
	}

	// -------------------------
	// 敵表示リクエスト
	// 左上から右に放物線
	// -------------------------
	public void Req() {
		Timer[0] = Timer[0] - FrameTime;

		if (Timer[0] < 0) {
			Timer[0] = 0.55;// インタバールの再設定

			for (int i = 0; i < Max; i++) {
				if (Flag[i] == 0)// 待機状態のみ
				{
					XX[i] = 400;// 出現位置初期化の最
					YY[i] = 0;
					kaku[i] = 90;// 角度方向
					kaku_P[i] = 0;
					R[i] = 10;
					Rsp[i] = 0;

					Xsp[i] = 0;// 初速度初期化
					Ysp[i] = 600;
					Xspg[i] = 0;// 加速度初期化
					Yspg[i] = 0;
					Ny[i] = 3; // キャラクタ初期化
					Flag[i] = 1;// 出現OKフラグON!!
					break;// 空き敵を使いきらないためfor文を途中で抜ける
				} // if end
			} // for end

		} // Timer[] if end

	}// Req1 end

	public void Req1() {
		int c;
		c = 0;
		for (int i = 0; i < Max; i++) {// 止まっているキャラの数をカウントする；ｃがMaxになったら
			if (Flag[i] == 2) {
				c++;
			} // for end

			if (c == Max) {// 次の軌道の初期化
				W_Timer = W_Timer - FrameTime;
				if (W_Timer < 0) {
					W_Timer = 3;// タイマー再セット
					for (i = 0; i < Max; i++) {
						kaku[i] = i * 10;
						Ysp[i] = 100;
						Xsp[i] = 100;
						Flag[i] = 3;
					}
				}
			}
		}

	}// Req1 end

	// -------------------------
	// 絵を動かす
	// -------------------------
	public void move() {

		Req();
		Req1();

		for (int i = 0; i < Max; i++) {
			if (Flag[i] != 0) {
				// 加速度が初速に影響を与える------------------

				Xsp[i] = Xsp[i] + FrameTime * Xspg[i];
				Ysp[i] = Ysp[i] + FrameTime * Yspg[i];

				// 初速が座標に影響を与える------------------

				XX[i] = XX[i] + FrameTime * Xsp[i];
				YY[i] = YY[i] + FrameTime * Ysp[i];
				R[i] = R[i] + FrameTime * Rsp[i];
				// 初速が座標に影響を与える------------------

				if (Flag[i] == 2 && Xsp[i] == 0 && Ysp[i] == 0) {
					XX[i] = R[i] * Math.cos(kaku[i] * Math.PI / 180.0) - R[i] * Math.sin(kaku[i] * Math.PI / 180.0)
							+ 400;
					YY[i] = R[i] * Math.sin(kaku[i] * Math.PI / 180.0) + R[i] * Math.cos(kaku[i] * Math.PI / 180.0)
							+ 300;
				}
				// 円軌道--------------------------------

				kaku[i] = kaku[i] + FrameTime * kaku_P[i];

				switch (Flag[i]) {
				case 1:
					if (YY[i] > 300) {
						Xsp[i] = 0;
						Ysp[i] = 0;
						kaku_P[i] = -150;
						Rsp[i] = 20;
						Flag[i] = 2;

					}
					break;

				case 2:
					if (YY[i] > 540 - 48) {
						Xsp[i] = 500;
						Ysp[i] = 0;
					}
					break;

				}// switch end
					// 衝突判定-------------------------------------------------

				double x_sa = XX[i] + 24 - (Jk.x + 50);// ｘ、ｙは中心じゃないです
				double y_sa = YY[i] + 24 - (Jk.y + 50);// ちゃんとｘ+ｒ、ｙ+ｒで円心を計算する
				double D = Math.sqrt(x_sa * x_sa + y_sa * y_sa);
				if (D <= 20 + 24) {// 青たま半径は50、緑たま半径は20;D<=r+R 接触
					Flag[i] = 0; // あたったら消える
					Bom.bomb_req(XX[i] + 20, YY[i] + 20, 1);
					if(Jk.D_time<0)Jk.hp-=5;
					Jk.D_time=1;

				}

				// 赤弾の当たり
				int ok = Jtm[Jk.PW].Hit(XX[i], YY[i]);

				if (ok == 1) {// 当たり
					// Xsp[i]=0;
					// Ysp[i]=0;
					Flag[i] = 0; // あたったら消える
					Bom.bomb_req(XX[i] + 10, YY[i] + 10, 0);
					int r = new java.util.Random().nextInt(5);

					if (r == 1) {
						It_01.Req(XX[i], YY[i], 2);
					}

				}

				// 軌道の切り替え

				// 画面外処理--------------------------------
				if (XX[i] < -48 || XX[i] > 960 + 48 || YY[i] < -48 || YY[i] > 540 + 48) {
					Flag[i] = 0;// 画面外のキャラクタを待機状態にさせる

				} // if end

				// ループアニメション0--3までは長いから0.5～2.5にしてあげる
				// 表示してる一枚一枚の図の時間を注意
				/*
				 * No=No+0.017*5; if(No>3)No=0;//ループアニメ
				 */

				if (No[i] < 9.5) {
					No[i] = No[i] + 0.017 * 15;// 時間の概念の共有
					if (No[i] > 9.5)
						No[i] = 0;

				} // if end

			} // FLag chk end
		} // for end
	}

	// ----------------------------------
	// 絵を読み込む
	// ----------------------------------
	public void load() {// updata
		try {
			Zako = ImageIO.read(getClass().getResource("stg_image/zako.png"));
		} catch (IOException e) {// 読み込みエラーの場合
			e.printStackTrace();
		}
	}//

	// ----------------------------------
	// 絵の表示
	// ----------------------------------
	public void print(Graphics2D g, JFrame Wind) {// disp
		for (int i = 0; i < Max; i++) {
			if (Flag[i] != 0) {
				g.drawImage(Zako, (int) XX[i], (int) YY[i], 48 + (int) XX[i], 54 + (int) YY[i], // 画面のどこに?x,y左上座標
																								// 右下座標
						48 * (int) No[i], 48 * Ny[i], 48 * ((int) No[i] + 1), 48 * (Ny[i] + 1), // 画像のどこか?x,y左上座標
																								// 右下座標
						Wind);// 座標は小数ではダメ
			} // FLag chk end
		} // for end

		// 文字の表示(文字の原点は左下)(画像の原点は左上）
		/*
		 * g.setColor(Color.YELLOW);//色指定 g.setFont(f);
		 * g.drawString("(X)座標:"+X,10,20); g.drawString("(Y)座標:"+Y,10,40);
		 */

	}
}