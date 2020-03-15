package com.zhoraDev;

import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.zhoraDev.getMaze.generateMaze;

class Game extends Canvas implements Runnable {

    static Color black=new Color(0, 0, 0);
    static Color white=new Color(255,255,255);
    static Color gray=new Color(130, 130, 130);
    static Color turquoise=new Color(54, 255, 239);

    protected static int a=50,b=60,nA=2*a+1,nB=2*b+1;
    protected static int[][] maze=new int [nA][nB];
    protected static int[][] mazeForDrawing=new int [nA+20][nB+20];
    protected static boolean[][] cellsHeroVisited=new boolean[nA][nB];

    private static int size=50,heroSize=11,mapSize=6,centralX,centralY,
            WIDTH=nB*size,HEIGHT=nA*size,mapX,mapY,fieldX,fieldY;

    private static boolean running=false;
    protected static movingObject hero;
    protected static movingObject enemy;

    BufferedImage torchSprite;

    private void start() {
        mapX=centralX/2-(nB*mapSize/2); mapY=centralY/2-(nA*mapSize/2);
        fieldX=centralX/2-heroSize/2; fieldY=centralY/2-heroSize/2;
        for (int i = 0; i < nA+20; i++)
            for (int j = 0; j < nB+20; j++)
                mazeForDrawing[i][j]=-1;
        for (int i = 0; i < nA; i++)
            for (int j = 0; j < nB; j++)
                mazeForDrawing[i+10][j+10]=maze[i][j];
        running = true;
        new Thread(this).start();
    }

    public void run() {
        addKeyListener(new movingObject.TAdapter());
        hero.x=size; hero.y=size;
        try { init(); } catch (IOException e) { e.printStackTrace(); }
        setIgnoreRepaint(true);
        while (running) {
            updateEnemy();
            updateHero();
            if (mapInProgress) renderMap();
            else render();
            try { Thread.sleep(10); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    private void init() throws IOException {
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
        g.setColor(black);
        g.clearRect(0,0,centralX,centralY);
        g.fillRect(0,0,centralX,centralY);

        for (int i = 0; i < nB; i++) {
            for (int j = 0; j < nA; j++) {
                if ((j-hero.y/size)*(j-hero.y/size)+(i-hero.x/size)*(i-hero.x/size)<=80){
                    switch (maze[j][i]) {
                        case(0): { g.setColor(white); break; }
                        case(-1): { g.setColor(black); break; }
                        case(-2): { g.setColor(gray); break; }
                    }
                    g.fillRect(fieldX+i*size-hero.x,fieldY+j*size-hero.y,size,size);
                }
            }
        }
        g.setColor(black);
        g.fillRect(centralX/2-350,0,700,centralY/2-350);
        g.fillRect(centralX/2-350,centralY/2+350,700,centralY/2-350);
        g.fillRect(0,0,centralX/2-350,centralY);
        g.fillRect(centralX/2+350,0,centralX/2-350,centralY);
        g.drawImage(torchSprite,centralX/2-350,centralY/2-350,700,700,this);
        g.setColor(turquoise);
        g.fillRect(fieldX,fieldY,heroSize,heroSize);
        g.dispose();
        bs.show();
    }

    public void renderMap() {
        BufferStrategy bs = getBufferStrategy();
        if (bs==null) {
            createBufferStrategy(2);
            requestFocus();
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.clearRect(0,0,centralX,centralY);
        g.setColor(black);
        g.fillRect(0,0,centralX,centralY);
        for (int i = 0; i < nB; i++) {
            for (int j = 0; j < nA; j++) {
                g.setColor(black);
                if (cellsHeroVisited[j][i])
                    switch (maze[j][i]) {
                        case(0): { g.setColor(white); break; }
                        case(-1): { g.setColor(black); break; }
                        case(-2): { g.setColor(gray); break; }
                    }
                g.fillRect(i*mapSize+mapX,j*mapSize+mapY,mapSize,mapSize);
            }
        }
        g.setColor(turquoise);
        g.fillRect(hero.x/size*mapSize+mapX,hero.y/size*mapSize+mapY,mapSize,mapSize);
        g.dispose();
        bs.show();
    }

    private void updateEnemy() {}

    private void updateHero() {
        if (!mapInProgress) {
            if (leftPressed) {
                if ((maze[hero.y/size][(hero.x-1)/size]==0)&&(maze[(hero.y+heroSize-1)/size][(hero.x-1)/size]==0))
                    hero.x+=hero.dx;
            }
            if (rightPressed) {
                if ((maze[hero.y/size][(hero.x+heroSize)/size]==0)&&(maze[(hero.y+heroSize-1)/size][(hero.x+heroSize)/size]==0))
                    hero.x+=hero.dx;
            }
            if (upPressed) {
                if ((maze[(hero.y-1)/size][hero.x/size]==0)&&(maze[(hero.y-1)/size][(hero.x+heroSize-1)/size]==0))
                    hero.y+=hero.dy;
            }
            if (downPressed) {
                if ((maze[(hero.y+heroSize)/size][hero.x/size]==0)&(maze[(hero.y+heroSize)/size][(hero.x+heroSize-1)/size]==0))
                    hero.y+=hero.dy;
            }
        }

        cellsHeroVisited[hero.y/size][hero.x/size]=cellsHeroVisited[hero.y/size+1][hero.x/size]=cellsHeroVisited[hero.y/size][hero.x/size+1]=
                cellsHeroVisited[hero.y/size-1][hero.x/size]=cellsHeroVisited[hero.y/size][hero.x/size-1]=cellsHeroVisited[hero.y/size-1][hero.x/size-1]=
                        cellsHeroVisited[hero.y/size-1][hero.x/size+1]=cellsHeroVisited[hero.y/size+1][hero.x/size-1]=cellsHeroVisited[hero.y/size+1][hero.x/size+1]=true;
    }

    private static boolean mapInProgress=false,leftPressed=false,rightPressed=false,upPressed=false,downPressed=false;
    public static void keyPressed(KeyEvent e) {
        int key=e.getKeyCode();
        if ((key==KeyEvent.VK_A)&&(!rightPressed)) { hero.dx = -1; leftPressed=true; }
        if ((key==KeyEvent.VK_D)&&(!leftPressed)) { hero.dx = 1; rightPressed=true; }
        if ((key==KeyEvent.VK_W)&&(!downPressed)) { hero.dy = -1; upPressed=true; }
        if ((key==KeyEvent.VK_S)&&(!upPressed)) { hero.dy = 1; downPressed=true; }
        if (key==KeyEvent.VK_M) { if (mapInProgress) { mapInProgress=false; } else { mapInProgress=true; }}
    }

    public static void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key==KeyEvent.VK_A) { leftPressed=false; if(!rightPressed) { hero.dx = 0; }}
        if (key==KeyEvent.VK_D) { rightPressed=false; if(!leftPressed) { hero.dx = 0; }}
        if (key==KeyEvent.VK_W) { upPressed=false; if(!downPressed) { hero.dy = 0; }}
        if (key== KeyEvent.VK_S) { downPressed=false; if(!upPressed) { hero.dy = 0; }}
    }

    public static void main(String[] args) {
        for (int i = 0; i < nA; i++) {
            for (int j = 0; j < nB; j++) {
                cellsHeroVisited[i][j]=false;
            }
        }
        Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
        generateMaze();
        Game game = new Game();
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(game);
        centralX=screen.width; centralY=screen.height;
        frame.setBounds(0,0,centralX,centralY);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setVisible(true);
        game.start();
    }
}