package com.DoltishNeighbour;

import java.awt.event.MouseEvent;

import static com.DoltishNeighbour.Game.*;
import static com.DoltishNeighbour.drawing.*;

public class interaction {

    protected static boolean place=false,dig=false;
    protected static int clickedX,clickedY,distance;

    private static void getDistance(MouseEvent e){
        distance=((e.getX()-fieldX-heroSize/2)*(e.getX()-fieldX-heroSize/2)+(e.getY()-fieldY-heroSize/2)*(e.getY()-fieldY-heroSize/2));
        clickedX=(e.getX()-fieldX+circle.x)/size;
        clickedY=(e.getY()-fieldY+circle.y)/size;
    }

    protected static void tryDig(MouseEvent e) {
        if (dig) {
            getDistance(e);
            if (((Math.abs(hero.x+heroSize/2-size*clickedX)<size/4)&&(hero.y/size==clickedY))||((Math.abs(hero.y+heroSize/2-size*clickedY)<size/4)&&(hero.x/size==clickedX))
                    ||((Math.abs(hero.x+heroSize/2-size*(clickedX+1))<size/4)&&(hero.y/size==clickedY))||((Math.abs(hero.y+heroSize/2-size*(clickedY+1))<size/4)&&(hero.x/size==clickedX))) {
                if (maze[clickedY][clickedX]==-1) {
                    maze[clickedY][clickedX]=mazeForDrawing[clickedY+2][clickedX+2]=0;
                    dig=false;
                }
            }
        }
    }

    protected static void tryPlace(MouseEvent e) {
        if (place) {
            getDistance(e);
            if (!(((hero.x/size==clickedX)&&(hero.y/size==clickedY))||(((hero.x+heroSize-1)/size==clickedX)&&(hero.y/size==clickedY))
                    ||((hero.x/size==clickedX)&&((hero.y+heroSize-1)/size==clickedY))||(((hero.x+heroSize-1)/size==clickedX)&&((hero.y+heroSize-1)/size==clickedY)))) {
                if (((Math.abs(hero.x+heroSize/2-size*clickedX)<size/4)&&(hero.y/size==clickedY))||((Math.abs(hero.y+heroSize/2-size*clickedY)<size/4)&&(hero.x/size==clickedX))
                        ||((Math.abs(hero.x+heroSize/2-size*(clickedX+1))<size/4)&&(hero.y/size==clickedY))||((Math.abs(hero.y+heroSize/2-size*(clickedY+1))<size/4)&&(hero.x/size==clickedX))) {
                    if (maze[clickedY][clickedX]==0) {
                        maze[clickedY][clickedX]=mazeForDrawing[clickedY+2][clickedX+2]=-1;
                        place=false;
                    }
                }
            }
        }
    }

}
