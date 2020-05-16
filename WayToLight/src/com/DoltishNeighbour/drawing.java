package com.DoltishNeighbour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.DoltishNeighbour.Game.*;
import static com.DoltishNeighbour.updating.*;
import static com.DoltishNeighbour.interaction.*;
import static com.DoltishNeighbour.actionListeners.*;

public class drawing {

    static Color black=new Color(0, 0, 0);
    static Color white=new Color(255,255,255);
    static Color gray=new Color(130, 130, 130);
    static Color turquoise=new Color(54, 255, 239);
    static Color red=new Color(255, 5,0,255);

    protected static int size=470,heroSize=60,enemySize=120,alpha=750,betta=600,mapSize=6;
    protected static int[][] mazeForDrawing=new int [nA+4][nB+4];

    protected static JButton buttonStart=new JButton("Start");

    protected static void drawMenu(Graphics g) {
        buttonStart.setSize(100,100);
        buttonStart.setLocation(200,200);
        buttonStart.paint(g);
        buttonStart.addActionListener(e -> {
            System.out.println(1); running=true;
        });
    }

    protected static void drawMaze(Graphics g) {
        g.clearRect(-centralX,-centralY,3*centralX,3*centralY);
        for (int i = hero.x/size; i < hero.x/size+5; i++) {
            for (int j = hero.y/size; j < hero.y/size+5; j++) {
                switch (mazeForDrawing[j][i]) {
                    case(0): { g.setColor(white); break; }
                    case(-1): { g.setColor(gray); break; }
                    case(-2): { g.setColor(black); break; }
                    case(1): { g.setColor(red); break; }
                }
                g.fillRect(fieldX+(i-2)*size-circle.x,fieldY+(j-2)*size-circle.y,size,size);
            }
        }
    }

    protected static void drawMap(Graphics g) {
        g.clearRect(-centralX,-centralY,3*centralX,3*centralY);
        g.setColor(black);
        g.fillRect(0,0,centralX,centralY);
        for (int i = 0; i < nB; i++) {
            for (int j = 0; j < nA; j++) {
                g.setColor(black);
                if (cellsHeroVisited[j][i])
                    switch (maze[j][i]) {
                        case(0): { g.setColor(white); break; }
                        case(-1): { g.setColor(gray); break; }
                        case(-2): { g.setColor(black); break; }
                        case(1): { g.setColor(red); break; }
                    }
                g.fillRect(i*mapSize+mapX,j*mapSize+mapY,mapSize,mapSize);
            }
        }
        g.setColor(turquoise);
        g.fillRect(hero.x/size*mapSize+mapX,hero.y/size*mapSize+mapY,mapSize,mapSize);
    }

    protected static void drawHeroEnemy(Graphics g) {
        g.setColor(turquoise);
        g.fillRect(fieldX+(hero.x-circle.x),fieldY+(hero.y-circle.y),heroSize,heroSize);
        if ((Math.abs(circle.x/size-enemy.x)<=2)&&(Math.abs(circle.y/size-enemy.y)<=1)) {
            g.setColor(red);
            g.fillRect(fieldX+(enemy.x-circle.x),fieldY+(enemy.y-circle.y),enemySize,enemySize);
        }
    }

    private static int dX,dY;
    protected static void drawShell(Graphics g) {
        dX=hero.x-circle.x;
        dY=hero.y-circle.y;
        g.setColor(black);
        g.fillRect(centralX/2-alpha+dX,0,2*alpha,centralY/2-betta+dY);
        g.fillRect(centralX/2-alpha+dX,centralY/2+betta+dY,2*alpha,centralY/2-betta-dY);
        g.fillRect(0,0,centralX/2-alpha+dX,centralY);
        g.fillRect(centralX/2+alpha+dX,0,centralX/2-alpha-dX,centralY);
        g.drawImage(torchSprite,centralX/2-alpha+dX,centralY/2-betta+dY,alpha*2,betta*2,null);
    }

    protected static void createDebugConsole(Graphics g) {
        debugConsole.setText("");
        debugConsole.append("hero (X,Y): "+hero.x+" "+hero.y
                        +"\n enemy (X,Y): "+enemy.x+" "+enemy.y
                        +"\n Circle: "+circle.x+" "+circle.y
                        +"\n Left-up:"+hero.y/size+" "+(hero.x)/size
                        +"\n Left-down: "+(hero.y+heroSize-1)/size+" "+(hero.x)/size
                        +"\n Right-up: "+(hero.y)/size+" "+(hero.x+heroSize-1)/size
                        +"\n Right-down: "+(hero.y+heroSize-1)/size+" "+(hero.x+heroSize-1)/size
                        +"\n d: "+d
                        +"\n stamina: "+stamina
                        +"\n Distance: "+getDistance()
                        +"\n getDistance()>=22500?: "+(getDistance()>=22500)
                        +"\n clickedX: "+clickedX
                        +"\n clickedY: "+clickedY
                        +"\n clickDistance: "+distance
                        +"\n Right: "+Math.abs(hero.x+heroSize/2-size*clickedX)
                        +"\n Down: "+Math.abs(hero.y+heroSize/2-size*clickedY)
                        +"\n Left: "+Math.abs(hero.x+heroSize/2-size*(clickedX+1))
                        +"\n Up: "+Math.abs(hero.y+heroSize/2-size*(clickedY+1)));
        debugConsole.paint(g);
    }
}
