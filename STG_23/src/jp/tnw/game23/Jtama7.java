package jp.tnw.game23;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Jtama7 extends Jtama{

	//-----------------------------------------
	//上方向に二発の弾
    //-----------------------------------------
	Jiki Jk;//自機クラスを使用する目的でアドレス

	public void Req(){
		Timer[0]=Timer[0]-FrameTime;

		if(Timer[0]<0){
			Timer[0]=0.01;//インタバールの再設定
			int cnt=1;//弾数

		for(int i=0;i<Max;i++ )
		{
			if(Flag[i]==0)//待機状態のみ
			{
		//出現位置初期化の最
		X[i]=Jk.x+45;	//青クラスｘ座標
	    Y[i]=Jk.y;   //青クラスｙ座標
	

	    Xsp[i]=600;//初速度初期化
	    Ysp[i]=-600;
	    Xspg[i]=0;//加速度初期化
	    Yspg[i]=0;
	    //キャラクタ初期化
	    Flag[i]=1;//出現OKフラグON!!
	    kaku[i]=90;//
	    Ny[i]=0;
	    
	    cnt--;
	    if(cnt==0)break;//空き敵を使いきらないためfor文を途中で抜ける
			}//if end
		}//for end


		}//Timer[] if end


	}//Req1 end
	//--------------------------
	//衝突判定
	//entry::衝突判定の(x,y)
	//exit::0-＞当たりなし 1→当たりあり
	//--------------------------
    public int Hit(double x,double y){
    	int ok;//結果を緑クラスに教える変数
    	ok=0;
		for(int i=0;i<Max;i++)
		{if(Flag[i]!=0){//リクエストされたものは移動
			
		}
			double x_sa=(X[i]+8)-(x+20);//ｘ、ｙは中心じゃないです
			double y_sa=(Y[i]+8)-(y+20);//ちゃんとｘ+ｒ、ｙ+ｒで円心を計算する
			double D=Math.sqrt(x_sa*x_sa+y_sa*y_sa);
				if(D<=8+20){//青たま半径は50、緑たま半径は20;D<=r+R 接触
					//Xsp[i]=0;
					//Ysp[i]=0;
					X[i]=2000;
					Flag[i]=0;
					
					ok =1;//当たり！！
		}//if end
		}//for end
		return ok;
	}//Hit end
	//-------------------------
		//絵を動かす
		//-------------------------
		public  void move() {//引数でマウスの座標を渡す

			//Req();
			
		
			




			for(int i=0;i<Max;i++)
			{if(Flag[i]!=0){
	 //加速度が初速に影響を与える------------------

				Xsp[i]=Xsp[i]+FrameTime*Xspg[i];
				Ysp[i]=Ysp[i]+FrameTime*Yspg[i];

			 //初速が座標に影響を与える------------------

				
			//軌道の切り替え
				X[i]=X[i]+FrameTime*Xsp[i]*Math.cos(kaku[i]*Math.PI/180);//ラジアンと角度の転換　Math.PI
		        Y[i]=Y[i]+FrameTime*Ysp[i]*Math.sin(kaku[i]*Math.PI/180);//ラジアンと角度の転換
				//衝突判定-------------------------------------------------
		        
			
					
				

				//画面外処理--------------------------------
				if(X[i]<-80||X[i]>960+40||Y[i]<-40||Y[i]>540+40)
				{X[i]=2000;
				Flag[i]=0;//画面外のキャラクタを待機状態にさせる



				}//if end



				//ループアニメション0--3までは長いから0.5～2.5にしてあげる
				//表示してる一枚一枚の図の時間を注意
				 		/*No=No+0.017*5;
			if(No>3)No=0;//ループアニメ*/


				if(No[i]<3.5){No[i]=No[i]+0.017*15;//時間の概念の共有
				if(No[i]>3.5)No[i]=0;

			}//if end

			}//FLag chk end
				}//for end
	}


	    //----------------------------------
		//絵を読み込む
		//----------------------------------
		public  void load() {//updata
			try {

				Jtama = ImageIO.read(getClass().getResource("stg_image/tama.png"));
			} catch (IOException e) {//読み込みエラーの場合
				e.printStackTrace();
			}
		}//

		public  void print(Graphics2D 	g ,JFrame	Wind) {//disp				
			for(int i=0;i<Max;i++)
			{
				if(Flag[i]!=0){

	g.drawImage(Jtama,
					(int)X[i],(int)Y[i],(int)X[i]+16+15,(int)Y[i]+16+15,//画面のどこに?x,y左上座標　右下座標
					32*(int)No[i],81,32*((int)No[i]+1),110,//画像のどこか?x,y左上座標　右下座標
					Wind);//座標は小数ではダメ


				}//FLag chk end
			}//for end	



			//文字の表示(文字の原点は左下)(画像の原点は左上）
			g.setColor(Color.YELLOW);//色指定
			g.setFont(f);
			//g.drawString("(X)座標:"+x,10,20);
			//g.drawString("(Y)座標:"+y,10,40);


		}
}