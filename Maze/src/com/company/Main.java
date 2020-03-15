package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class Main{

    public static int[][] Maze = new int[601][601];


    public static void main(String[] args) throws IOException {
        File MazeIn = new File("Maze.txt");
        Scanner scanner = new Scanner(MazeIn);
        int x=1,y=1, xend=599, yend=599, k;
        for (int i = 0; i < 601; i++) for (int j = 0; j < 601; j++) Maze[i][j]=scanner.nextInt();
        Maze[1][0]=Maze[599][600]=1;
        BufferedImage MazeInput = ImageIO.read(new File("MazeInput.png"));
        BufferedImage MazeSolved = ImageIO.read(new File("MazeSolved.png"));
        BufferedImage MazeWave = ImageIO.read(new File("MazeWave.png"));

        GenerateImage(MazeInput);
        ImageIO.write(MazeInput, "png", new File("MazeInput.png"));

        for (int i = 1; i < 600; i++)
            for (int j = 1; j < 600; j++)
                check(i,j);

        GenerateImage(MazeSolved);
        ImageIO.write(MazeSolved, "png", new File("MazeSolved.png"));

        Wave(1,1,3);
        System.out.println(Maze[599][599]-1);
        Way(MazeWave,599,599);
        MazeWave.setRGB(1, 0, Green.getRGB());
        MazeWave.setRGB(599, 600, Green.getRGB());
        ImageIO.write(MazeWave, "png", new File("MazeWave.png"));
    }



    private static void check(int x, int y) {
        int n=0;
        if (Maze[x][y]==1) {
            if (Maze[x][y-1]!=1) n++;
            if (Maze[x-1][y]!=1) n++;
            if (Maze[x][y+1]!=1) n++;
            if (Maze[x+1][y]!=1) n++;
            if (n==4) Maze[x][y]=2;
            if (n==3) {
                Maze[x][y]=2;
                if (Maze[x][y+1]==1) check(x,y+1);
                if (Maze[x+1][y]==1) check(x+1,y);
                if (Maze[x][y-1]==1) check(x,y-1);
                if (Maze[x-1][y]==1) check(x-1,y);
            }
        }
    }

    public static void GenerateImage(BufferedImage png) throws IOException {
        Color Black = new Color(0, 0, 0);
        Color White = new Color(255,255,255);
        Color Red = new Color(255, 0, 0);
        for (int i = 0; i < 601; i++) {
            for (int j = 0; j < 601; j++){
                if (Maze[i][j]==0) png.setRGB(i, j, Black.getRGB());
                if (Maze[i][j]==1) png.setRGB(i, j, White.getRGB());
                if (Maze[i][j]==2) png.setRGB(i, j, Red.getRGB());
            }
        }
    }

    public static void Wave(int x, int y, int k) {
        k++;
            if((x>=1)&(y>=1)&(x<=599)&(y<=599)) {
            if (Maze[x][y-1]==1) { Maze[x][y-1]=k; Wave(x,y-1,k); }
            if (Maze[x-1][y]==1) { Maze[x-1][y]=k; Wave(x-1,y,k); }
            if (Maze[x][y+1]==1) { Maze[x][y+1]=k; Wave(x,y+1,k); }
            if (Maze[x+1][y]==1) { Maze[x+1][y]=k; Wave(x+1,y,k); }
        }
    }

    public static Color Green = new Color(0, 255, 0);

    public static void Way(BufferedImage png, int x, int y) throws IOException {
            png.setRGB(x, y, Green.getRGB());
            if((x>=1)&(y>=1)&(x<=599)&(y<=599)) {
            if (Maze[x][y-1]==(Maze[x][y]-1)) { Way(png, x,y-1); }
            if (Maze[x-1][y]==(Maze[x][y]-1)) { Way(png,x-1,y); }
            if (Maze[x][y+1]==(Maze[x][y]-1)) { Way(png, x,y+1); }
            if (Maze[x+1][y]==(Maze[x][y]-1)) { Way(png,x+1,y); }
            }
    }

}