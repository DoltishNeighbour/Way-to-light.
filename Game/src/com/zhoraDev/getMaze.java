package com.zhoraDev;

import java.util.ArrayList;
import java.util.Stack;

import static com.zhoraDev.Game.nA;
import static com.zhoraDev.Game.nB;
import static com.zhoraDev.Game.a;
import static com.zhoraDev.Game.b;
import static com.zhoraDev.Game.maze;

public class getMaze {

    static ArrayList<Integer> directions=new ArrayList<>();

    public static void generateMaze() {
        int x=(int)(Math.random()*b),y=(int)(Math.random()*a),numDirections,direction,notVisited=a*b-1;
        boolean[][] mazeVisited=new boolean[a][b];
        Stack<cell> Stack= new Stack<>();
        cell C=new cell(0,0);

        generateBasicMaze();
        for (int i = 0; i < a; i++)
            for (int j = 0; j < b; j++)
                mazeVisited[i][j]=false;

        mazeVisited[y][x]=true;
        while (notVisited!=0) {
            getDirections(mazeVisited,x,y);
            numDirections=directions.size();
            if (numDirections>0) {
                C.x=x; C.y=y;
                Stack.push(C);
                direction=(int)(Math.random()*(numDirections));
                direction=directions.get(direction);
                switch (direction) {
                    case (0): {
                        mazeVisited[y][x-1]=true;
                        maze[2*y+1][2*x]=0;
                        notVisited--; x--;
                        break;
                    }
                    case (1): {
                        mazeVisited[y-1][x]=true;
                        maze[2*y][2*x+1]=0;
                        notVisited--; y--;
                        break;
                    }
                    case (2): {
                        mazeVisited[y][x+1]=true;
                        maze[2*y+1][2*x+2]=0;
                        notVisited--; x++;
                        break;
                    }
                    case (3): {
                        mazeVisited[y+1][x]=true;
                        maze[2*y+2][2*x+1]=0;
                        notVisited--; y++;
                        break;
                    }
                }
            } else {
                if (!Stack.isEmpty()) {
                    C=Stack.pop();
                    x=C.x; y=C.y;
                } else {
                    while (numDirections==0) {
                        x=(int)(Math.random()*b);
                        y=(int)(Math.random()*a);
                        if (mazeVisited[y][x]) {
                            getDirections(mazeVisited,x,y);
                            numDirections=directions.size();
                        }
                    }
                }
            }
        }
        checkIsolated();
        addBreakingWalls();
    }

    private static void generateBasicMaze(){
        for (int i = 0; i < nA; i+=2)
            for (int j = 0; j < nB; j++)
                maze[i][j]=-1;
        for (int i = 0; i < nB; i+=2)
            for (int j = 0; j < nA; j++)
                maze[j][i]=-1;
    }

    private static void checkIsolated() {
        int dir;
        for (int i = 1; i < nA-1; i+=2) {
            for (int j = 1; j < nB-1; j+=2) {
                if ((maze[i][j+1]==-1)&&(maze[i+1][j]==-1)&&(maze[i][j-1]==-1)&&(maze[i-1][j]==-1)) {
                    dir=(int)(Math.random()*4);
                    switch (dir) {
                        case (0): { maze[i-1][j]=0; break; }
                        case (1): { maze[i][j-1]=0; break; }
                        case (2): { maze[i+1][j]=0; break; }
                        case (3): { maze[i][j+1]=0; break; }
                    }
                }
            }
        }
    }

    private static void getDirections(boolean[][] A,int i,int j) {
        directions.clear();
        if (i>0) {if (!A[j][i-1]) {directions.add(0);}}
        if (j>0) {if (!A[j-1][i]) {directions.add(1);}}
        if (i<b-1) {if (!A[j][i+1]) {directions.add(2);}}
        if (j<a-1) {if (!A[j+1][i]) {directions.add(3);}}
    }

    private static void addBreakingWalls() {
        int N=nA*nB/100;
        int x,y;
        for (int o = 0; o < N; o++) {
            x=(int)(Math.random()*(nA-2)+2);
            y=(int)(Math.random()*(nB-2)+2);
            maze[x][y]=-2;
        }

    }
}

class cell {
    public static int x, y;
    cell(int i,int j) {
        this.x=i; this.y=j;
    }
}