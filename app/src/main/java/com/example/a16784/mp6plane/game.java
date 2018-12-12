package com.example.a16784.mp6plane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import java.util.Vector;


class Var{
    public static int kills=0;
    public static int w,h;
    public static float fraction;//
    public static Vector<plane> list=new Vector<plane>();
    public static Vector<plane> Elist=new Vector<plane>();
    public static Bitmap Varplane,Eplane,Background,bullet;
    public static Varplane Var;
    public static Background b;
    public static int myHp = 10;
}

public class game extends View {
    private Paint p=new Paint();
    private float x,y;
    private float VarX,VarY;

    public game(Context context) {
        super(context);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent e) {
                if(e.getAction()==MotionEvent.ACTION_DOWN){
                    x=e.getX();
                    y=e.getY();
                    VarX=Var.Var.r.left;
                    VarY=Var.Var.r.top;
                }
                float x0=VarX+e.getX()-x;
                float y0=VarY+e.getY()-y;
                x0=x0<Var.w-Var.Var.w/2?x0:Var.w-Var.Var.w/2;
                x0=x0>-Var.Var.w/2?x0:-Var.Var.w/2;
                y0=y0<Var.h-Var.Var.h/2?y0:Var.h-Var.Var.h/2;
                y0=y0>-Var.Var.h/2?y0:-Var.Var.h/2;
                Var.Var.setX(x0);
                Var.Var.setY(y0);
                return true;
            }
        });

        setBackgroundColor(Color.BLACK);

        Var.Varplane= BitmapFactory.decodeResource(getResources(),R.mipmap.plane);//加载图片
        Var.Eplane=BitmapFactory.decodeResource(getResources(),R.mipmap.enevar);
        Var.bullet=BitmapFactory.decodeResource(getResources(),R.mipmap.zd);
        Var.Background=BitmapFactory.decodeResource(getResources(), R.mipmap.background);

        new Thread(new re()).start();
        new Thread(new loadEnemy()).start();
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        Var.w=w;
        Var.h=h;
        Var.fraction= (float) (Math.sqrt(Var.w * Var.h)/ Math.sqrt(1920 * 1080));
        p.setTextSize(50*Var.fraction);
        p.setColor(Color.WHITE);
        Var.b=new Background();
        Var.Var=new Varplane();
    }
    @Override
    protected void onDraw(Canvas g) {
        super.onDraw(g);
        g.drawBitmap(Var.b.img,null,Var.b.r,p);

        for(int i=0;i<Var.list.size();i++){
            plane h=Var.list.get(i);
            g.drawBitmap(h.img,null,h.r,p);
        }
        g.drawText("Kills："+Var.kills,0,Var.h-100,p);
        g.drawText("HP："+Var.myHp,0,Var.h-150,p);
    }
    private class re implements Runnable {
        @Override
        public void run() {
            while(true){
                try { Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
                postInvalidate();
            }
        }
    }
    private class loadEnemy implements Runnable{
        @Override
        public void run() {
            while(true){
                if (Var.kills < 30) {
                    try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
                    //enemy emerges every 1.3s
                } else {
                    try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
                }
                try {
                    new Eplane();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
class plane{
    public RectF r=new RectF();
    public int hp;
    public float w,h;
    public Bitmap img;
    public void setX(float x){
        r.left=x;
        r.right=x+w;
    }
    public void setY(float y){
        r.top=y;
        r.bottom=y+h;
    }

    public boolean hit(plane oBackground,float px) {
        px*=Var.fraction;
        if (r.left+px - oBackground.r.left <= oBackground.w && oBackground.r.left - this.r.left+px <= this.w-px-px)
            if (r.top+px - oBackground.r.top <= oBackground.h && oBackground.r.top - r.top+px <= h-px-px) {
                return true;
            }

        return false;

    }
}
class Background extends plane implements  Runnable{
    public Background(){
        w=Var.w;
        h=Var.h*2;
        img=Var.Background;
        setX(0);
        setY(-Var.h);
        new Thread(this).start();
    }
    @Override
    public void run() {
        while(true){
            try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
            if(r.top+2<=0){
                setY(r.top+2);
            }else{
                setY(-Var.h);
            }
        }
    }
}

class Eplane extends plane implements Runnable{
    private long speed=(long) (Math.random()*10)+10;
    public Eplane(){
//        w=Var.w/5.4f;
//        h=Var.h/9.6f;
        w=h=300*Var.fraction;
        setX((float)( Math.random()*(Var.w-w)));
        setY(-h + 150);
        img=Var.Eplane;
        hp = 6;
        Var.list.add(this);
        Var.Elist.add(this);
        new Thread(this).start();
    }

    @Override
    public void run() {
        while(hp>0){
            try {Thread.sleep(speed);} catch (InterruptedException e) {e.printStackTrace();}
            if (Var.kills > 50 && Var.kills < 200) {
                setY(r.top+Var.kills*Var.fraction / 10);
            } else if (Var.kills < 50) {
                setY(r.top+5*Var.fraction);
            } else {
                setY(r.top+15*Var.fraction);
            }
            if(r.top>=Var.h)break;
        }
        Var.list.remove(this);
        Var.Elist.remove(this);
    }
}

class Varplane extends plane implements Runnable{
    public Varplane(){
        w=h=400*Var.fraction;
        setX(Var.w/2-w/2);
        setY(Var.h*0.7f-h/2);
        img=Var.Varplane;
        Var.list.add(this);
        new Thread(this).start();
    }

    @Override
    public void run() {
        while(true){
            if (Var.kills < 30) {
                try {Thread.sleep(200);} catch (InterruptedException e) {e.printStackTrace();}
            } else if (Var.kills < 50) {
                try {Thread.sleep(150);} catch (InterruptedException e) {e.printStackTrace();}
            } else {
                try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
            }
            new bullet(this);
            try {
                for(int i=0;i<Var.Elist.size();i++){
                    plane h=Var.Elist.get(i);
                    if(hit(h,30)){
                        Var.myHp-=1;
                        h.hp=0;
                        break;
                    }
                    if (Var.myHp <= 0) {
                        Main2Activity.score = Var.kills;
                        MainActivity.dead();
//                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
class bullet extends plane implements Runnable{
    private int damage;
    private float speed;

    public bullet(plane plane){
        w=h=190*Var.fraction;
        img=Var.bullet;
        speed=6*Var.fraction;
        damage=6;
        
        setX(plane.r.left+plane.w/2-w/2);
        setY(plane.r.top-h/2);
        Var.list.add(this);
        new Thread(this).start();
    }

    @Override
    public void run() {
        boolean flag=false;
        while(true){
            try {Thread.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
            setY(r.top-speed);
            try {
                for(int i=0;i<Var.Elist.size();i++){
                    plane h=Var.Elist.get(i);
                    if(hit(h,30)){
                        h.hp-=damage;
                        flag=true;
                        Var.kills++;
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
            if(flag || r.top+h<=0)break;
        }
        Var.list.remove(this);
    }
}
