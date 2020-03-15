package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

class Beat extends JFrame {

    public double Scale=100, N, Omega1=16, Omega2=15, A=150, DeltaPhi=0;
    public int x,y,x1,y1;

    public Beat(){
        super("Beat");
        setSize(getToolkit().getScreenSize());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setVisible(true);
        N=getWidth()/Scale;
    }

    @Override
    public void paint(Graphics g) {
        setIgnoreRepaint(true);
        createBufferStrategy(2);
        BufferStrategy bufferStrategy=getBufferStrategy();
         Thread thread = new Thread(() -> {
             while (1==1){
                 Graphics gc=bufferStrategy.getDrawGraphics();
                 Graphics2D g1 =(Graphics2D) gc;
                 gc.clearRect(-3000,-3000,6000,6000);
                 gc.setColor(Color.GRAY); g1.setStroke(new BasicStroke(1));
                 for (int i=getWidth()/2; i<getWidth(); i+=100) gc.drawLine(i,0,i,getHeight());
                 for (int i=getWidth()/2; i>0; i-=100) gc.drawLine(i,0,i,getHeight());
                 for (int i=getHeight()/2; i<getHeight(); i+=100) gc.drawLine(0,i,getWidth(),i);
                 for (int i=getHeight()/2; i>0; i-=100) gc.drawLine(0,i,getWidth(),i);
                 gc.setColor(Color.BLACK); g1.setStroke(new BasicStroke(2));
                 x=0; y=(int) (A*Math.sin(DeltaPhi))+getHeight()/2;
                 for (double i = 0.001; i < N; i+=0.001) {
                     x1=(int)(i*Scale);
                     y1= (int) (A*(Math.sin(Omega1*i)+Math.sin(Omega2*i+DeltaPhi)))+getHeight()/2;
                     gc.drawLine(x,y,x1,y1);
                     x=x1; y=y1;
                 }
                 DeltaPhi+=0.01;
                 gc.dispose();
                 bufferStrategy.show();
                 try { Thread.sleep(50); }
                 catch (InterruptedException e){ e.printStackTrace(); }
             }
         });
         thread.start();
    }

    public static void main(String[] args) { new Beat(); }
}
