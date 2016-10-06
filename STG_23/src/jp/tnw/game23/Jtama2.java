package jp.tnw.game23;

//------------------------------------
//継承の基本
//------------------------------------

public class Jtama2 extends Jtama{
	//差分
	Jiki Jk;//自機クラスを使用する目的でアドレス
	public void Req(){
		Timer[0]=Timer[0]-FrameTime;

		if(Timer[0]<0){
			Timer[0]=0.01;//インタバールの再設定

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
	    break;//空き敵を使いきらないためfor文を途中で抜ける
			}//if end
		}//for end


		}//Timer[] if end


	}//Req1 end

}
