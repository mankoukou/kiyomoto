package jp.tnw.game23;

public class Zako_07 extends Zako_01{
	Jiki Jk;//自機クラス
	Bomb_Class Bom;
	Item_01 It_01;
	
	
	Jtama[] Jtm = new Jtama[8];
	
	
	
	//-------------------------
	//敵表示リクエスト
	//左上から右に放物線
	//-------------------------
	public void Req(){
		Timer[0]=Timer[0]-FrameTime;

		if(Timer[0]<0){
			Timer[0]=0.2;//インタバールの再設定

		for(int i=0;i<Max;i++ )
		{
			if(Flag[i]==0)//待機状態のみ
			{
		XX[i]=0;//出現位置初期化の最
	    YY[i]=160;


	    Xsp[i]=400;//初速度初期化
	    Ysp[i]=0;
	    Xspg[i]=0;//加速度初期化
	    Yspg[i]=0;
	    Ny[i]=5; //キャラクタ初期化
	    Flag[i]=1;//出現OKフラグON!!
	    break;//空き敵を使いきらないためfor文を途中で抜ける
			}//if end
		}//for end


		}//Timer[] if end
	}
		public  void move() {

			Req();
			//Req2();
			//Req3();
			//Req4();
			//Req5();
			//Req6();
			//Req7();
			//Req8();




			for(int i=0;i<Max;i++)
			{if(Flag[i]!=0){
	 //加速度が初速に影響を与える------------------

				Xsp[i]=Xsp[i]+FrameTime*Xspg[i];
				Ysp[i]=Ysp[i]+FrameTime*Yspg[i];

			 //初速が座標に影響を与える------------------

				XX[i]=XX[i]+FrameTime*Xsp[i];
				YY[i]=YY[i]+FrameTime*Ysp[i];
				
			//軌道の切り替え
				
				switch(Flag[i]){
				
				case 1:
					if(XX[i]>960-48){
						Xsp[i]=-400;
						Ysp[i]=200;
						Flag[i]=2;//次の軌道に切り替える
					}
					break;
					
				case 2:
					if(YY[i]>540-54){
						Xsp[i]=120;
						Ysp[i]=-270;
						Flag[i]=3;//次の軌道に切り替える
					}
					break;
				case 3:
					if(YY[i]<0){
						Xsp[i]=120;
						Ysp[i]=270;
						Flag[i]=4;//次の軌道に切り替える
					}
					break;
				case 4:
					if(YY[i]>540-54){
						Xsp[i]=-400;
						Ysp[i]=-200;
						Flag[i]=5;//次の軌道に切り替える
					}
					break;
			
				
				
				  
				
				
				}//switch end
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
						Bom.bomb_req(XX[i]+10,YY[i]+10,0);
						
						Flag[i]=0;	//あたったら消える
						
						int r = new java.util.Random().nextInt(10);

						if(r==5){
						It_01.Req(XX[i],YY[i],6);}
						
					}
				

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





}
