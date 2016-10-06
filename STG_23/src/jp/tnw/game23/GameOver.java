package jp.tnw.game23;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GameOver {
	final double FrameTime = 0.166666667;
	BufferedImage bgPNG;// 読み込むフィールド宣言
	boolean gameOver;

	float alpha;
	Jiki Jk;
	Bomb_Class Bom;

	public GameOver() {
		gameOver = false;
		alpha = 0.0f;

	}

	public void move() {
		if (Jk.hp <= 0) {	//或いはif(Jk.hp>=0)return;自機ｈｐチェック
			gameOver = true;
			alpha += FrameTime * 0.01;
			if (alpha >= 1)
				alpha = 1.0f;
			//自機の周りに爆発をランダムに重ねる---------------
			int r = new java.util.Random().nextInt(9);
			int rx=new java.util.Random().nextInt(100);
			int ry=new java.util.Random().nextInt(100);
			Bom.bomb_req(Jk.x + rx, Jk.y + ry, r);

		}
		
	
		
		
		

	}

	public void load() {// updata
		try {
			bgPNG = ImageIO.read(getClass().getResource("stg_image/g_over.png"));

		} catch (IOException e) {// 読み込みエラーの場合
			e.printStackTrace();
		}
	}//

	public void print(Graphics2D g, JFrame Wind) {//

		// 半透明処理-------------------------

		if (gameOver) {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

			g.drawImage(bgPNG, 0, 0, 960, 540, 0, 0, 800, 600, Wind);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0F));

		}
	}

}
