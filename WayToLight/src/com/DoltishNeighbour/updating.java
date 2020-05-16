package com.DoltishNeighbour;

import static com.DoltishNeighbour.Game.*;
import static com.DoltishNeighbour.actionListeners.*;
import static com.DoltishNeighbour.drawing.*;

public class updating {

    protected static boolean[][] cellsHeroVisited=new boolean[nA][nB];
    protected static int stamina=100;

    protected static void updateMazeVisits(){
        cellsHeroVisited[hero.y/size][hero.x/size]=cellsHeroVisited[hero.y/size+1][hero.x/size]=cellsHeroVisited[hero.y/size][hero.x/size+1]=
                cellsHeroVisited[hero.y/size-1][hero.x/size]=cellsHeroVisited[hero.y/size][hero.x/size-1]=cellsHeroVisited[hero.y/size-1][hero.x/size-1]=
                        cellsHeroVisited[hero.y/size-1][hero.x/size+1]=cellsHeroVisited[hero.y/size+1][hero.x/size-1]=cellsHeroVisited[hero.y/size+1][hero.x/size+1]=true;
    }

    protected static void updateHero() {
        if (!mapInProgress) {
            if (leftPressed) {
                hero.dx=-d;
                if ((maze[hero.y/size][(hero.x-d)/size]==0)&&(maze[(hero.y+heroSize-1)/size][(hero.x-d)/size]==0)) {
                    hero.x+=hero.dx;
                    if (getDistance()>=22500) { circle.x+=hero.dx; }
                }
                else if ((maze[hero.y/size][(hero.x-1)/size]==0)&&(maze[(hero.y+heroSize-1)/size][(hero.x-1)/size]==0)) {
                    hero.x-=1;
                    if (getDistance()>=22500) { circle.x-=1; }
                }
            }
            if (rightPressed) {
                hero.dx=d;
                if ((maze[hero.y/size][(hero.x+heroSize-1+d)/size]==0)&&(maze[(hero.y+heroSize-1)/size][(hero.x+heroSize-1+d)/size]==0)) {
                    hero.x+=hero.dx;
                    if (getDistance()>=22500) { circle.x+=hero.dx; }
                }
                else if ((maze[hero.y/size][(hero.x+heroSize)/size]==0)&&(maze[(hero.y+heroSize-1)/size][(hero.x+heroSize)/size]==0)) {
                    hero.x+=1;
                    if (getDistance()>=22500) { circle.x+=1; }
                }
            }
            if (upPressed) {
                hero.dy=-d;
                if ((maze[(hero.y-d)/size][hero.x/size]==0)&&(maze[(hero.y-d)/size][(hero.x+heroSize-1)/size]==0)) {
                    hero.y+=hero.dy;
                    if (getDistance()>=22500) { circle.y+=hero.dy; }
                }
                else if ((maze[(hero.y-1)/size][hero.x/size]==0)&&(maze[(hero.y-1)/size][(hero.x+heroSize-1)/size]==0)) {
                    hero.y-=1;
                    if (getDistance()>=22500) { circle.y-=1; }
                }
            }
            if (downPressed) {
                hero.dy=d;
                if ((maze[(hero.y+heroSize-1+d)/size][hero.x/size]==0)&(maze[(hero.y+heroSize-1+d)/size][(hero.x+heroSize-1)/size]==0)) {
                    hero.y+=hero.dy;
                    if (getDistance()>=22500) { circle.y+=hero.dy; }
                }
                else if ((maze[(hero.y+heroSize)/size][hero.x/size]==0)&(maze[(hero.y+heroSize)/size][(hero.x+heroSize-1)/size]==0)) {
                    hero.y+=1;
                    if (getDistance()>=22500) { circle.y+=1; }
                }
            }
            if (run) stamina-=30;
            if ((!run)&&(stamina<15000)) stamina+=10;
            if (stamina<=0) { run=false; d=5; }
        }
    }

    protected static int getDistance() {
        return (circle.x-(hero.x))*(circle.x-(hero.x))+(circle.y-(hero.y))*(circle.y-(hero.y));
    }

    protected static void updateEnemy() {
        mainTick%=10000;
        if (mainTick==0) {
            enemy.x=(int)(size*(1+(double)(size-enemySize)/size*Math.random()+2*((int)((double)b*Math.random()))));
            enemy.y=(int)(size*(1+(double)(size-enemySize)/size*Math.random()+2*((int)((double)a*Math.random()))));
            while ((enemy.x/size==hero.x/size)&&(hero.y/size==enemy.y/size)) {
                enemy.x=(int)(size*(1+(double)(size-enemySize)/size*Math.random()+2*((int)((double)b*Math.random()))));
                enemy.y=(int)(size*(1+(double)(size-enemySize)/size*Math.random()+2*((int)((double)a*Math.random()))));
            }
        }
    }

}
