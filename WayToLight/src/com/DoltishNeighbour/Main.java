package com.DoltishNeighbour;

import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.DoltishNeighbour.getMaze.generateMaze;
import static com.DoltishNeighbour.updating.*;
import static com.DoltishNeighbour.drawing.*;
import static com.DoltishNeighbour.actionListeners.*;

class Game extends Canvas implements Runnable {

    protected static int a=10,b=10,nA=2*a+1,nB=2*b+1,d=5,deltaT=5,centralX,centralY,mapX,mapY,fieldX,fieldY;
    protected static int[][] maze=new int[nA][nB];
    protected static int WIDTH=nB*size,HEIGHT=nA*size;
    protected static int mainTick=0;
    protected static movingObject hero,enemy,circle;
    protected static JTextArea debugConsole = new JTextArea(100,100);
    protected static BufferedImage torchSprite;

    void start() {
        preparing();
        running=true;
        //drawMenu(g);
        new Thread(this::run).start();
    }

    private static void preparing() {
        mainTick=0;
        generateMaze();
        mapX=centralX/2-(nB*mapSize/2); mapY=centralY/2-(nA*mapSize/2);
        fieldX=centralX/2-heroSize/2; fieldY=centralY/2-heroSize/2;
        hero=new movingObject(0,0,0,0);
        circle=new movingObject(0,0,0,0);
        enemy=new movingObject(1,1,0,0);
        hero.x=(int)(size*(1+(double)(size-heroSize)/size*Math.random()+2*((int)((double)b*Math.random()))));
        hero.y=(int)(size*(1+(double)(size-heroSize)/size*Math.random()+2*((int)((double)a*Math.random()))));
        circle.x=hero.x; circle.y=hero.y;
        enemy.x=(int)(size*(1+(double)(size-enemySize)/size*Math.random()+2*((int)((double)b*Math.random()))));
        enemy.y=(int)(size*(1+(double)(size-enemySize)/size*Math.random()+2*((int)((double)a*Math.random()))));
        while ((enemy.x/size==hero.x/size)&&(hero.y/size==enemy.y/size)) {
            enemy.x=(int)(size*(1+(double)(size-enemySize)/size*Math.random()+2*((int)((double)b*Math.random()))));
            enemy.y=(int)(size*(1+(double)(size-enemySize)/size*Math.random()+2*((int)((double)a*Math.random()))));
        }
        maze[hero.y/size][hero.x/size]=0;
        for (int i = 0; i < nB; i++) {
            for (int j = 0; j < nA; j++) {
                cellsHeroVisited[j][i]=false;
            }
        }
        for (int i = 0; i < nA+4; i++)
            for (int j = 0; j < nB+4; j++)
                mazeForDrawing[i][j]=-1;
        for (int i = 0; i < nA; i++)
            for (int j = 0; j < nB; j++)
                mazeForDrawing[i+2][j+2]=maze[i][j];
    }

    public void run() {
        addKeyListener(new actionListeners.TAdapter());
        addMouseListener(new actionListeners.customListener());

        try { init(); } catch (IOException e) { e.printStackTrace(); }
        setIgnoreRepaint(true);
        while (running) {
            mainTick+=deltaT;
            updateEnemy();
            updateHero();
            updateMazeVisits();
            if (mapInProgress) renderMap();
            else render();
            try { Thread.sleep(deltaT); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
        System.exit(0);
    }

    private static void init() throws IOException {
        torchSprite=ImageIO.read(new File("src/textures/gradientForTorch.png"));
    }

    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs==null) {
            createBufferStrategy(2);
            requestFocus();
            return;
        }
        Graphics g = bs.getDrawGraphics();
        drawMaze(g);
        drawHeroEnemy(g);
        if (!debugging){ drawShell(g); }
        else { createDebugConsole(g); }
        g.dispose();
        bs.show();
    }

    private void renderMap() {
        BufferStrategy bs = getBufferStrategy();
        if (bs==null) {
            createBufferStrategy(2);
            requestFocus();
            return;
        }
        Graphics g=bs.getDrawGraphics();
        drawMap(g);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
        Game game = new Game();
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        debugConsole.setBounds(10,10,200,800);
        debugConsole.setVisible(true);
        frame.add(game);
        centralX=screen.width; centralY=screen.height;
        frame.setBounds(0,0,centralX,centralY);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setVisible(true);
        game.start();
    }

    public static class movingObject {
        public int x,y,dx,dy;
        public movingObject(int x,int y,int dx,int dy) {
            this.x=x; this.y=y; this.dx=dx; this.dy=dy;
        }
    }
}