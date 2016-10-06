package jp.tnw.game23;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;

public class Jtama3 extends Jtama{

	//-----------------------------------------
	//上方向に二発の弾
    //-----------------------------------------
	Jiki Jk;//自機クラスを使用する目的でアドレス
	public void Req(){
		Timer[0]=Timer[0]-FrameTime;

		if(Timer[0]<0){
			Timer[0]=0.01;//インタバールの再設定
			int cnt=2;//弾数

		for(int i=0;i<Max;i++ )
		{
			if(Flag[i]==0)//待機状態のみ
			{
		//出現位置初期化の最
		X[i]=Jk.x+30*cnt;	//青クラスｘ座標
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
	public  void print(Graphics2D 	g ,JFrame	Wind) {//disp				
		for(int i=0;i<Max;i++)
		{
			if(Flag[i]!=0){

g.drawImage(Jtama,
				(int)X[i],(int)Y[i],(int)X[i]+16,(int)Y[i]+16,//画面のどこに?x,y左上座標　右下座標
				16*(int)No[i],16*Ny[i],16*((int)No[i]+1),16*(Ny[i]+1),//画像のどこか?x,y左上座標　右下座標
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
