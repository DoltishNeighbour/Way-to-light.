package com.zhoraDev;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class movingObject implements ActionListener {

    public static int x=0,y=0,dx,dy;

    @Override
    public void actionPerformed(ActionEvent e) { }
    public static class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) { Game.keyReleased(e); }
        @Override
        public void keyPressed(KeyEvent e) { Game.keyPressed(e); }
    }
}