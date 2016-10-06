package jp.tnw.game23;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Zako_02 {

	final double FrameTime=0.017;//
	final int Max=20;//敵の個数
	Font	f=new	Font("Default",Font.BOLD,16);//使用するフォントクラス宣言

	BufferedImage Zako;//読み込むフィールド宣言
	double []XX=new double [Max];//横座標
	double []YY=new double [Max];//縦座標
	double []No=new double [Max];

	double []Xsp=new double [Max];//横スピード
	double []Ysp=new double [Max];//縦スピード
	double []Xspg=new double [Max];//横スピード(加速度)
	double []Yspg=new double [Max];//縦スピード(加速度)


	int[] Ny =new int[Max];//ｷｬﾗｸﾀの何列目


	int []Flag=new int [Max];//状態を保存している変数
	                         //0::待機
	                         //1::画面内
	                         //2::
	                         //3::

	double []Timer=new double [12];//敵のリクエストをｺﾝﾄﾛｰﾙします
	
	Jiki Jk;//自機クラス
	Bomb_Class Bom;
	Item_01 It_01;
	
	Jtama[] Jtm = new Jtama[8];

	//------------------------------------
	//コンストラクタ1(特別な初期化メソッド)
	//------------------------------------
	public  Zako_02 (){


	}



	//------------------------------------
	//コンストラクタ2(特別な初期化メソッド)
	//------------------------------------
	public Zako_02 (int x,int y){
		XX[0]=x;
		YY[0]=y;
	}

	//-------------------------
	//敵表示リクエスト
	//左上から右に放物線
	//-------------------------
	public void Req(){
		Timer[0]=Timer[0]-FrameTime;

		if(Timer[0]<0){
			Timer[0]=0.55;//インタバールの再設定

		for(int i=0;i<Max;i++ )
		{
			if(Flag[i]==0)//待機状態のみ
			{
		XX[i]=0;//出現位置初期化の最
	    YY[i]=540;


	    Xsp[i]=200;//初速度初期化
	    Ysp[i]=-450;
	    Xspg[i]=0;//加速度初期化
	    Yspg[i]=200;
	    Ny[i]=1; //キャラクタ初期化
	    Flag[i]=1;//出現OKフラグON!!
	    break;//空き敵を使いきらないためfor文を途中で抜ける
			}//if end
		}//for end


		}//Timer[] if end


	}//Req1 end

	

















	//-------------------------
	//絵を動かす
	//-------------------------
	public  void move() {

		Req();
		




		for(int i=0;i<Max;i++)
		{if(Flag[i]!=0){
 //加速度が初速に影響を与える------------------

			Xsp[i]=Xsp[i]+FrameTime*Xspg[i];
			Ysp[i]=Ysp[i]+FrameTime*Yspg[i];

		 //初速が座標に影響を与える------------------

			XX[i]=XX[i]+FrameTime*Xsp[i];
			YY[i]=YY[i]+FrameTime*Ysp[i];
			
			
			//衝突判定-------------------------------------------------
	        
			double x_sa=XX[i]+24-(Jk.x+50);//ｘ、ｙは中心じゃないです
			double y_sa=YY[i]+24-(Jk.y+50);//ちゃんとｘ+ｒ、ｙ+ｒで円心を計算する
			double D=Math.sqrt(x_sa*x_sa+y_sa*y_sa);
				if(D<=20+24){//青たま半径は50、緑たま半径は20;D<=r+R 接触
					Flag[i]=0;	//あたったら消える
					Bom.bomb_req(XX[i]+20,YY[i]+20,1);
					if(Jk.D_time<0)Jk.hp-=5;
					Jk.D_time=1;
					
				}
			
	          //赤弾の当たり
				int ok=Jtm[Jk.PW].Hit(XX[i],YY[i]);
				
				if(ok==1){//当たり
				//	Xsp[i]=0;
				//	Ysp[i]=0;
					Flag[i]=0;	//あたったら消える
					Bom.bomb_req(XX[i]+10,YY[i]+10,0);
					int r = new java.util.Random().nextInt(5);

					if(r==1){
					It_01.Req(XX[i],YY[i],1);}
					
				}	
			
		//軌道の切り替え
			
			switch(Flag[i]){
			
			case 1:
				if(YY[i]>540-48 && Ysp[i]>0 ){Xsp[i]=-75;
				Ysp[i]=-300; Yspg[i]=300;
				
				Flag[i]=2;
				}
				break;
			case 2:
				if(YY[i]>540-48 && Ysp[i]>0 ){Xsp[i]=-75;
				Ysp[i]=-200; Yspg[i]=200;
				
				Flag[i]=3;
				}
				break;
			case 3:
				if(YY[i]>540-48 && Ysp[i]>0 ){Xsp[i]=-75;
				Ysp[i]=-150; Yspg[i]=1500;
				
				Flag[i]=4;
				}
				break;
			case 4:
				if(YY[i]>540-48 && Ysp[i]>0 ){Xsp[i]=-75;
				Ysp[i]=-100; Yspg[i]=100;
				
				Flag[i]=5;
				}
				break;
			case 5:
				if(YY[i]>540-48 && Ysp[i]>0 ){Xsp[i]=-75;
				Ysp[i]=-75; Yspg[i]=75;
				
				Flag[i]=6;
				}
				break;
			case 6:
				if(YY[i]>540-48 && Ysp[i]>0 ){Xsp[i]=-75;
				Ysp[i]=-50; Yspg[i]=50;
				
				Flag[i]=7;
				}
				break;
			case 7:
				if(YY[i]>540-48 && Ysp[i]>0 ){Xsp[i]=-75;
				Ysp[i]=-25; Yspg[i]=25;
				
				Flag[i]=8;
				}
				break;
			case 8:
				if(YY[i]>540-48 && Ysp[i]>0 ){Xsp[i]=-75;
				Ysp[i]=0; Yspg[i]=0;
				
				Flag[i]=8;
				}
				break;
			
		
			
			
			  
			
			
			}//switch end
			

			//画面外処理--------------------------------
			if(XX[i]<-48||XX[i]>960+48||YY[i]<-48||YY[i]>540+48)
			{
			Flag[i]=0;//画面外のキャラクタを待機状態にさせる



			}//if end



			//ループアニメション0--3までは長いから0.5～2.5にしてあげる
			//表示してる一枚一枚の図の時間を注意
			 		/*No=No+0.017*5;
		if(No>3)No=0;//ループアニメ*/


			if(No[i]<9.5){No[i]=No[i]+0.017*15;//時間の概念の共有
			if(No[i]>9.5)No[i]=0;

		}//if end

		}//FLag chk end
			}//for end
}





    //----------------------------------
	//絵を読み込む
	//----------------------------------
	public  void load() {//updata
		try {
			Zako = ImageIO.read(getClass().getResource("stg_image/zako.png"));
		} catch (IOException e) {//読み込みエラーの場合
			e.printStackTrace();
		}
	}//


	//----------------------------------
	//絵の表示
	//----------------------------------
	public  void print(Graphics2D 	g ,JFrame	Wind) {//disp
		for(int i=0;i<Max;i++)
		{
			if(Flag[i]!=0){
		g.drawImage(Zako,
				(int)XX[i],(int)YY[i],48+(int)XX[i],54+(int)YY[i],//画面のどこに?x,y左上座標　右下座標
				48*(int)No[i],48*Ny[i],48*((int)No[i]+1),48*(Ny[i]+1),//画像のどこか?x,y左上座標　右下座標
				Wind);//座標は小数ではダメ
			}//FLag chk end
		}//for end

		//文字の表示(文字の原点は左下)(画像の原点は左上）
		/*g.setColor(Color.YELLOW);//色指定
		g.setFont(f);
		g.drawString("(X)座標:"+X,10,20);
		g.drawString("(Y)座標:"+Y,10,40);*/


	}
}