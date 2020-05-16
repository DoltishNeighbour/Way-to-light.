package com.DoltishNeighbour;

import java.awt.event.*;

import static com.DoltishNeighbour.Game.*;
import static com.DoltishNeighbour.interaction.*;
import static com.DoltishNeighbour.updating.*;

public class actionListeners implements ActionListener {

    protected static boolean running=false,mapInProgress=false,leftPressed=false,rightPressed=false,upPressed=false,downPressed=false,run=false,debugging=false;

    @Override
    public void actionPerformed(ActionEvent e) { }
    public static class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case (KeyEvent.VK_A): { leftPressed=false; if(!rightPressed) { hero.dx=0; } break; }
                case (KeyEvent.VK_D): { rightPressed=false; if(!leftPressed) { hero.dx=0; }break; }
                case (KeyEvent.VK_W): { upPressed=false; if(!downPressed) { hero.dy=0; } break; }
                case (KeyEvent.VK_S): { downPressed=false; if(!upPressed) { hero.dy=0; } break; }
                case (KeyEvent.VK_SHIFT): { run=false; d=5; }
            }
        }
        @Override
        public void keyPressed(KeyEvent e) {
            int key=e.getKeyCode();
            switch (key) {
                case (KeyEvent.VK_A): { if (!rightPressed) { leftPressed=true; } break; }
                case (KeyEvent.VK_D): { if (!leftPressed) { rightPressed=true; } break; }
                case (KeyEvent.VK_W): { if (!downPressed) { upPressed=true; } break; }
                case (KeyEvent.VK_S): { if (!upPressed) { downPressed=true; } break; }
                case (KeyEvent.VK_M): { if (mapInProgress) { mapInProgress=false; } else { mapInProgress=true; } break; }
                case (KeyEvent.VK_G): { if (dig) { dig=false; } else { dig=true; place=false; } break; }
                case (KeyEvent.VK_R): { if (place) { place=false; } else { place=true; dig=false; } break; }
                case (KeyEvent.VK_SHIFT): { if (stamina>=5000) { run=true; d=8; } break; }
                case (KeyEvent.VK_ENTER): { if (debugging) { debugging=false; } else { debugging=true; } break; }
                case (KeyEvent.VK_ESCAPE): {  running=false; break; }
            }
        }
    }

    public static class customListener implements MouseListener {
        public void mouseClicked(MouseEvent e) { }
        public void mouseEntered(MouseEvent e) { }
        public void mouseExited(MouseEvent e) { }
        public void mouseReleased(MouseEvent e) { }
        public void mousePressed(MouseEvent e) {
            tryDig(e); tryPlace(e);
        }
    }

}
