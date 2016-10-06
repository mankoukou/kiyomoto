package jp.tnw.game23;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public  class Item_01 {

	final double FrameTime=0.017;//
	final int Max=15;//敵の個数
	Font	f=new	Font("Default",Font.BOLD,16);//使用するフォントクラス宣言

	BufferedImage Item;//読み込むフィールド宣言
	double  x;
	double y;
	double []X=new double [Max];//横座標
	double []Y=new double [Max];//縦座標
	double []No=new double [Max];

	double []Xsp=new double [Max];//横スピード
	double []Ysp=new double [Max];//縦スピード
	double []Xspg=new double [Max];//横スピード(加速度)
	double []Yspg=new double [Max];//縦スピード(加速度)
	double []kaku=new double [Max];//角度
	double []kaku_P=new double [Max];//角度がマイナスかプラスする


	int[] Ny =new int[Max];//ｷｬﾗｸﾀの何列目


	int []Flag=new int [Max];//状態を保存している変数
	                         //0::待機
	                         //1::画面内
	                         //2::
	                         //3::
	
	double []Timer=new double [12];//敵のリクエストをｺﾝﾄﾛｰﾙします
	Jiki Jk;//自機クラス
	Zako_01 Zk;
	Zako_02 Zk2;
	Zako_03 Zk3;
	Zako_04 Zk4;
	Zako_05 Zk5;
	Zako_06 Zk6;
	Zako_07 Zk7;
	Zako_08 Zk8;
	Zako_09 Zk9;
	


	//------------------------------------
	//コンストラクタ1(特別な初期化メソッド)
	//------------------------------------
	public  Item_01 (){
	x=100;
	y=50;


	
	}
	
	
	//敵２番出現リクエスト
	//entry::どこから出現するか(x,y)とアイテムナンバー
	//exit::なし




	public  void  Req(double Ix,double Iy,int I_No)
		{//Timer[0]=Timer[0]-FrameTime;

		//if(Timer[0]<0){
			//Timer[0]=0.2;//インタバールの再設定

		for(int i=0;i<Max;i++ )
		{
			if(Flag[i]==0)//待機状態のみ
			{
		//出現位置初期化の最
		X[i]=Ix;	//青クラスｘ座標
	    Y[i]=Iy;   //青クラスｙ座標
	

	    Xsp[i]=60;//初速度初期化
	    Ysp[i]=60;
	    Xspg[i]=600;//加速度初期化
	    Yspg[i]=600;
	    //キャラクタ初期化
	    Flag[i]=1;//出現OKフラグON!!
	    kaku[i]=0-i*80;//
	    Ny[i]=I_No;
	    break;//空き敵を使いきらないためfor文を途中で抜ける
			}//if end
		}//for end


		//}//Timer[] if end


	}//Req1 end



	//--------------------------
	//衝突判定
	//entry::衝突判定の(x,y)
	//exit::0-＞当たりなし 1→当たりあり
	//--------------------------

















	//-------------------------
	//絵を動かす
	//-------------------------
	public  void move() {//引数でマウスの座標を渡す

		//Req(400,200,0);
	
		
	
		




		for(int i=0;i<Max;i++)
		{if(Flag[i]!=0){
 //加速度が初速に影響を与える------------------

			Xsp[i]=Xsp[i]+FrameTime*Xspg[i];
			Ysp[i]=Ysp[i]+FrameTime*Yspg[i];

		 //初速が座標に影響を与える------------------

			
		//軌道の切り替え
			X[i]=X[i]+FrameTime*Xsp[i]*Math.cos(kaku[i]*Math.PI/180);//ラジアンと角度の転換　Math.PI
	        Y[i]=Y[i]+FrameTime*Ysp[i]*Math.sin(kaku[i]*Math.PI/180);//ラジアンと角度の転換
			
	        
        //---------自機と当たり-------------------------------------------------
			double x_sa=X[i]+24-(Jk.x+50);//ｘ、ｙは中心じゃないです
			double y_sa=Y[i]+24-(Jk.y+50);//ちゃんとｘ+ｒ、ｙ+ｒで円心を計算する
			double D=Math.sqrt(x_sa*x_sa+y_sa*y_sa);
				if(D<=50+24){//青たま半径は50、緑たま半径は20;D<=r+R 接触
					Flag[i]=0;	//あたったら消える
					
					Jk.Item_Get[Ny[i]]++;
				}
		
				
			

			//画面外処理--------------------------------
			if(X[i]<-80||X[i]>960+40||Y[i]<-40||Y[i]>540+40)
			{
			Flag[i]=0;//画面外のキャラクタを待機状態にさせる



			}//if end



			//ループアニメション0--3までは長いから0.5～2.5にしてあげる
			//表示してる一枚一枚の図の時間を注意
			 		/*No=No+0.017*5;
		if(No>3)No=0;//ループアニメ*/


			if(No[i]<19.5){No[i]=No[i]+0.017*15;//時間の概念の共有
			if(No[i]>19.5)No[i]=0;

		}//if end

		}//FLag chk end
			}//for end
}





    //----------------------------------
	//絵を読み込む
	//----------------------------------
	public  void load() {//updata
		try {

			Item = ImageIO.read(getClass().getResource("stg_image/Item.png"));
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

g.drawImage(Item,
				(int)X[i],(int)Y[i],(int)X[i]+40,(int)Y[i]+40,//画面のどこに?x,y左上座標　右下座標
				0,40*Ny[i],40,40*(Ny[i]+1),//画像のどこか?x,y左上座標　右下座標
				Wind);//座標は小数ではダメ
			}//FLag chk end
		}//for end	



	

	}



}//Class end