package jp.tnw.game23;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
//--------------------
//爆発関連
//--------------------

public class Bomb_Class {

	final int WX=960;//画面サイズの定義222222
	final int WY=540;
	

	BufferedImage tk0PNG;//背景の読み込むメモリ宣言
	final	double	FrameTime=0.0166667;//1/60秒
	final	int	max=30;//最大数

	int		flag[]=new	int[max];//0:出現していない
	double	a_wait[]=new	double[max];//ｱﾆﾒwait time
	double	a_wait_cp[]=new	double[max];//ｱﾆﾒwait time copy

	int		no[]=new	int[max];//ｱﾆﾒﾅﾝﾊﾞｰ
	int		nox1[]=new	int[max];//ﾘｸｴｽﾄﾅﾝﾊﾞｰ
	int		nox2[]=new	int[max];//ｱﾆﾒｻｲｽﾞx
	int		noy[]=new	int[max];//ｱﾆﾒｻｲｽﾞy
	int		nom[]=new	int[max];//ｱﾆﾒｻｲｽﾞmax

	double	zx[]=new	double[max];//表示座標　x
	double	zy[]=new	double[max];//表示座標　y
	double	timer;//ﾘｸｴｽﾄﾀｲﾏｰ


//----------------
//	ｺﾝｽﾄﾗｸﾀ
//----------------
	public Bomb_Class(){

//	Load();
	for(int cnt=0;cnt<max;cnt++){
		flag[cnt]=0;
	}

}
//---------------------------
//	ﾘｸｴｽﾄ関数
//	entry;座標x,y 爆発ﾅﾝﾊﾞｰ,
//---------------------------
public void	bomb_req(double	xx,double	yy,int	b_no){

	double	ani_wait[]={0.02,0.02,0.02,0.02,0.02,0.01,0.01,0.01,0.01};//wait
	int		ani_size[]={48,48,32,32,32,32,16,16,16};//size
	int		  ani_pt[]={10,10,10,10,5,5,5,5,4};//ﾊﾟﾀｰﾝ数
	int		  ani_xx[]={ 0,16*3,16*6,16*8,16*10,16*12,16*14,16*15,16*16};//先頭

	for(int cnt=0;cnt<max;cnt++){
	if(flag[cnt]==0){

		flag[cnt]=1;//
		no[cnt]=0;

		a_wait[cnt]=ani_wait[b_no];//timr
		a_wait_cp[cnt]=ani_wait[b_no];//time copy
		nox1[cnt]=ani_xx[b_no];//ヨコ先頭
		nox2[cnt]=ani_size[b_no];//幅
		noy[cnt]=ani_size[b_no];//幅
		nom[cnt]=ani_pt[b_no];//max

		zx[cnt]=xx-ani_size[b_no]/2;//表示座標　x
		zy[cnt]=yy-ani_size[b_no]/2;//表示座標　y

		break;
	}//if end
	}//for end

}
//---------------------------
//	処理
//---------------------------
public void	UpDate(){

	for(int cnt=0;cnt<max;cnt++){
		if(flag[cnt]!=0){

		a_wait[cnt]-=FrameTime;
		if(a_wait[cnt]<0){
			a_wait[cnt]=a_wait_cp[cnt];//timer reset

			no[cnt]++;
			if(no[cnt]>nom[cnt]) flag[cnt]=0;//爆発end
		}

		}//flag end
	}//for end

}
//---------------
//	○敵読み込み
//----------------
public void	Load(){
	try {
		tk0PNG= ImageIO.read(getClass().getResource("stg_image/bomb.png"));
	} catch (IOException e) {
		e.printStackTrace();
	}

}//Load end

//-----------------------
//	表示関数
//-----------------------
public void	Disp(Graphics		g,JFrame	w){

	for(int cnt=0;cnt<max;cnt++){

		if(flag[cnt]!=0)
		g.drawImage(tk0PNG,(int)zx[cnt],(int)zy[cnt],(int)zx[cnt]+nox2[cnt],(int)zy[cnt]+noy[cnt],

				nox1[cnt],
				noy[cnt]*no[cnt],

				nox1[cnt]+nox2[cnt],
				noy[cnt]*no[cnt]+noy[cnt],

				w);


	}


}

}
