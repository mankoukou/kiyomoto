package jp.tnw.game23;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class enemyAttcak {

	final double FrameTime = 0.017;//
	final int Max = 1000;// 敵の個数
	Font f = new Font("Default", Font.BOLD, 16);// 使用するフォントクラス宣言

	BufferedImage enemyAttcak;// 読み込むフィールド宣言
	double[] X = new double[Max];// 横座標
	double[] Y = new double[Max];// 縦座標
	double[] No = new double[Max];

	double[] Xsp = new double[Max];// 横スピード
	double[] Ysp = new double[Max];// 縦スピード
	double[] Xspg = new double[Max];// 横スピード(加速度)
	double[] Yspg = new double[Max];// 縦スピード(加速度)
	double[] kaku = new double[Max];// 角度
	double[] kaku_P = new double[Max];// 角度がマイナスかプラスする
	double[] R = new double[Max];// 円の半径
	double[] Rsp = new double[Max];// 半径の変化スピード
	double Sp = 75;
	int S_kaku;
	int[] Ny = new int[Max];// ｷｬﾗｸﾀの何列目

	int[] Flag = new int[Max];// 状態を保存している変数
								// 0::待機
								// 1::画面内
								// 2::
								// 3::

	double[] Timer = new double[12];// 敵のリクエストをｺﾝﾄﾛｰﾙします

	double timer;// 敵弾のリクエストをｺﾝﾄﾛｰﾙします

	Jiki Jk;// 自機クラス
	Bomb_Class Bom;
	Item_01 It_01;

	Jtama[] Jtm = new Jtama[8];

	// ------------------------------------
	// コンストラクタ1(特別な初期化メソッド)
	// ------------------------------------
	public enemyAttcak() {

	}

	// -------------------------
	// ﾘｸｴｽﾄ(上下弾一発)
	// -------------------------
	public void Req1(double x, double y, int n) {

		int[][] AngleData = { { 90, 999 }, // ノーマル0
				{ 90 + 30, 90 - 30, 999 }, // ノーマル1
				{ 90 + 30, 90, 90 - 30, 999 }, // ノーマル2
				{ 90 + 65, 90 + 35, 90, 90 - 35, 25, 999 }, // ノーマル3
				{ 90 + 65, 90 + 35, 90, 90 - 35, 25, 999 }, // ノーマル4
				{ 0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 999 }, // ノーマル5
				{ 270, 90, 180, 0, 999 },

		};

		int count = 0;// getする角度データの場所

		for (int i = 0; i < Max; i++) {
			if (Flag[i] == 0) {

				Flag[i] = 1;// ﾘｸｴｽﾄ！！！
				X[i] = x;
				Y[i] = y;
				Xsp[i] = Sp;
				Ysp[i] = Sp;
				kaku[i] = AngleData[n][count];
				count++;

				if (AngleData[n][count] == 999)
					break;// 空き敵を使いきらないためfor文を途中で抜ける

			} // for end

		} //

	}// Req1 end
	// -------------------------
		// 自機狙い弾
		// -------------------------
		public void Req2(double x, double y, int n) {

			//狙う角度計算------------------------------
		
			double AcquireAngle;//狙う角度
			
			double x_sa=Jk.x+50-x;//差が0になるときbug
			double y_sa=Jk.y+50-y;
			if(x_sa==0)x_sa=0.000001;
			if(x_sa>0){
			AcquireAngle =Math.atan(y_sa/x_sa) * 180/Math.PI;//atan=1/tan
			}else{
			AcquireAngle =Math.atan(y_sa/x_sa) * 180/Math.PI+180;//atan=1/tan
			}
			
			
			
			

			for (int i = 0; i < Max; i++) {
				if (Flag[i] == 0) {

					Flag[i] = 1;// ﾘｸｴｽﾄ！！！
					X[i] = x;
					Y[i] = y;
					Xsp[i] = Sp*5;
					Ysp[i] = Sp*5;
					kaku[i] = AcquireAngle;
			
					//count++;

					//if (AngleData[n][count] == 999)
						break;// 空き敵を使いきらないためfor文を途中で抜ける

				} // for end

			} //

		}// Req1 end
	

	// -------------------------
	// 絵を動かす
	// -------------------------
	public void move() {// 青クラスから変数をもらう

		int ok;

		timer -= FrameTime;
		if (timer < 0) {
			timer = 1;
			//Req1(960 / 2, 540 / 2, 5);// ﾘｸｴｽﾄ
			Req2(960 / 2, 540 / 2, 5);// ﾘｸｴｽﾄ

		}

		for (int i = 0; i < Max; i++) {
			if (Flag[i] != 0) {
				// 加速度が初速に影響を与える------------------

				Xsp[i] = Xsp[i] + FrameTime * Xspg[i];
				Ysp[i] = Ysp[i] + FrameTime * Yspg[i];

				// 初速が座標に影響を与える------------------

				X[i] = X[i] + FrameTime * Xsp[i] * Math.cos(kaku[i] * Math.PI / 180);// ラジアンと角度の転換
																						// Math.PI
				Y[i] = Y[i] + FrameTime * Ysp[i] * Math.sin(kaku[i] * Math.PI / 180);// ラジアンと角度の転換

				//
				//
				// switch(Flag[i]) {
				// case 1:
				// if(Y[i]>27*i){
				// Xsp[i]=0;
				// Ysp[i]=0;
				// Flag[i]=2;
				// }
				// break;
				// }

				// 衝突判定-------------------------------------------------
				//
				// double x_sa=XX[i]+24-(Jk.x+50);//ｘ、ｙは中心じゃないです
				// double y_sa=YY[i]+24-(Jk.y+50);//ちゃんとｘ+ｒ、ｙ+ｒで円心を計算する
				// double D=Math.sqrt(x_sa*x_sa+y_sa*y_sa);
				// if(D<=20+24){//青たま半径は50、緑たま半径は20;D<=r+R 接触
				//
				// Flag[i]=0; //あたったら消える
				// Bom.bomb_req(XX[i]+20,YY[i]+20,1);
				// if(Jk.D_time<0)Jk.hp-=5;
				// Jk.D_time=1;
				//
				// }
				//
				// //赤弾の当たり
				// int ok=Jtm[Jk.PW].Hit(XX[i],YY[i]);
				//
				// if(ok==1){//当たり
				// // Xsp[i]=0;
				// // Ysp[i]=0;
				//
				// Flag[i]=0; //あたったら消える
				// Bom.bomb_req(XX[i]+10,YY[i]+10,0);
				// int r = new java.util.Random().nextInt(10);
				//
				// if(r==5){
				// It_01.Req(XX[i],YY[i],3);}
				//
				// }

				// 円軌道--------------------------------

				// 画面外処理--------------------------------
				if (X[i] < -48 || X[i] > 960 + 48 || Y[i] < -48 || Y[i] > 540 + 48) {
					Flag[i] = 0;
				}
				// 画面外のキャラクタを待機状態にさせる

				// ループアニメション0--3までは長いから0.5～2.5にしてあげる
				// 表示してる一枚一枚の図の時間を注意
				/*
				 * No=No+0.017*5; if(No>3)No=0;//ループアニメ
				 */

				if (No[i] < 9.5) {
					No[i] = No[i] + 0.017 * 10;// 時間の概念の共有
					if (No[i] > 9.5)
						No[i] = 0;

				} // if end

			} // FLag chk end
		} // for end

	}// move end

	// ----------------------------------
	// 絵を読み込む
	// ----------------------------------
	public void load() {// updata
		try {
			enemyAttcak = ImageIO.read(getClass().getResource("stg_image/tama.png"));
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
				g.drawImage(enemyAttcak, (int) X[i], (int) Y[i], (int) X[i] + 16, (int) Y[i] + 16, // 画面のどこに?x,y左上座標
																									// 右下座標
						16 * (int) No[i], 16, 16 * ((int) No[i] + 1), 16 + 16, // 画像のどこか?x,y左上座標
																				// 右下座標
						Wind);// 座標は小数ではダメ

				// 文字の表示(文字の原点は左下)(画像の原点は左上）
				/*
				 * g.setColor(Color.YELLOW);//色指定 g.setFont(f);
				 * g.drawString("(X)座標:"+X,10,20);
				 * g.drawString("(Y)座標:"+Y,10,40);
				 */

			}

		} // Class end
	}
}
