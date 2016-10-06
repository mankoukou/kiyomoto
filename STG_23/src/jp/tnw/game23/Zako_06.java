package jp.tnw.game23;

public class Zako_06 extends Zako_05 {
	final int Max=20;//敵の個数
	double []Y2=new double [Max];//縦座標
	double []Ysp2=new double [Max];//縦スピード
	
	Jiki Jk;//自機クラス
	Bomb_Class Bom;
	Item_01 It_01;
    Jtama[] Jtm = new Jtama[8];

	
  //-------------------------
  		public void Req(){
  			Timer[0]=Timer[0]-FrameTime;

  			if(Timer[0]<0){
  				Timer[0]=0.36;//インタバールの再設定

  			for(int i=0;i<Max;i++ )
  			{
  				if(Flag[i]==0)//待機状態のみ
  				{
  			//出現位置初期化
  			XX[i]=450-24;
  			
  		    YY[i]=0;
  		    Y2[i]=0;


  		   
  		    //初速度初期化
  		    Xsp[i]=0;
  		    Ysp[i]=200;
  		    Ysp2[i]=0;
  		    
  		    //加速度初期化
  		    Xspg[i]=0;
  		    Yspg[i]=0;
  		    
  		    //角度方向
  		    kaku[i]=180;
  		    kaku_P[i]=-100;
  		    
  		    Ny[i]=2; //キャラクタ初期化
  		    Flag[i]=1;//出現OKフラグON!!
  		    break;//空き敵を使いきらないためfor文を途中で抜ける
  				}//if end
  			}//for end


  			}//Timer[] if end


  		}//Req1 end
  		
  		//全体が止まったら次の軌道に切り替え
  		public void Req2(){
  			int c;
  			c=0;
  			for(int i=0;i<Max;i++){//止まっているキャラの数を確認ｃがMaxになったら
  				if(Flag[i]==4){
  					c++;
  				}//for end
  				
  			if(c==Max){//次の軌道の初期化
  				W_Timer=W_Timer-FrameTime;
  				if(W_Timer<0){
  					W_Timer=1;//タイマー再セット
  				for( i=0;i<Max;i++){
  				Flag[i]=5;
  				}
  				
  				}
  				}
  			}
  			
  		}//Req2 end




  			//全体が止まったら次の軌道に切り替え
  			public void Req3(){
  				int c;
  				c=0;
  				for(int i=0;i<Max;i++){//止まっているキャラの数を確認ｃがMaxになったら
  					if(Flag[i]==5){
  						c++;
  					}//for end
  					
  				if(c==Max){//次の軌道の初期化
  					W_Timer=W_Timer-FrameTime;
  					if(W_Timer<0){
  						W_Timer=0;//タイマー再セット
  					for( i=0;i<Max;i++){
  						
  							Ysp[i]=200;
  							Xsp[i]=200;
  							Ysp2[i]=100;
  							XX[i]=50*Math.cos(kaku[i]*Math.PI/180.0)-50*Math.sin(kaku[i]*Math.PI/180.0)+500;
  							YY[i]=50*Math.sin(kaku[i]*Math.PI/180.0)+50*Math.cos(kaku[i]*Math.PI/180.0)+200+Y2[i];
  							
  					if(Y2[i]>540+48)	Flag[i]=6;
  					
  					}
  					}
  					}
  				}
  			}//Req2 end

  			public void Req4(){
  				int c;
  				c=0;
  				for(int i=0;i<Max;i++){//止まっているキャラの数を確認ｃがMaxになったら
  					if(Flag[i]==6){
  						c++;
  					}//for end
  					
  				if(c==Max){//次の軌道の初期化
  					W_Timer=W_Timer-FrameTime;
  					if(W_Timer<0){
  						W_Timer=1;//タイマー再セット
  					for( i=0;i<Max;i++){
  						
  					
  							Flag[i]=0;
  					
  					}
  					}
  					}
  				}
  			}//Req2 end


  		
  		
  		
  		//-------------------------
  		//絵を動かす
  		//-------------------------
  		public  void move() {

  			Req();
  			Req2();
  			Req3();
  			Req4();
  			




  			for(int i=0;i<Max;i++)
  			{if(Flag[i]!=0){
  		//加速度が初速に影響を与える------------------
  			Xsp[i]=Xsp[i]+FrameTime*Xspg[i];
  			Ysp[i]=Ysp[i]+FrameTime*Yspg[i];
  			Y2[i]=Y2[i]+FrameTime*Ysp2[i];
  	switch(Flag[i]){
  	case 1:

  		XX[i]=XX[i]+FrameTime*Xsp[i];
  		YY[i]=YY[i]+FrameTime*Ysp[i];
  		if(YY[i]>200)Flag[i]=2;
  		break;	
  	case 2:
  		
  		
  		XX[i]=50*Math.cos(kaku[i]*Math.PI/180.0)-50*Math.sin(kaku[i]*Math.PI/180.0)+500;
  		YY[i]=50*Math.sin(kaku[i]*Math.PI/180.0)+50*Math.cos(kaku[i]*Math.PI/180.0)+200;
  		if(kaku[0]<-360*2){Flag[i]=3;}
  		break;
  	case 3:
  		Xsp[i]=Ysp[i]=0;
  		Flag[i]=4;
  		break;
  	}			
  		


  			 //初速が座標に影響を与える------------------

  				
  			//円軌道--------------------------------
  				
  				kaku[i]=kaku[i]+FrameTime*kaku_P[i];
  				
  				
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
  						Bom.bomb_req(XX[i]+10,YY[i]+10,4);
  						
  						Flag[i]=0;	//あたったら消える
  						
  						int r = new java.util.Random().nextInt(10);

  						if(r==5){
  						It_01.Req(XX[i],YY[i],5);}
  						
  					}

  				//画面外処理--------------------------------
  				if(XX[i]<-48||XX[i]>960+48||YY[i]<-48||YY[i]>540+48){Flag[i]=0;}
  				//画面外のキャラクタを待機状態にさせる
  				
  				






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
