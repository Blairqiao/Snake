package Game;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Snake");

        window.setPreferredSize(new Dimension(405,460));

        CanvasPanel canvas = new CanvasPanel();
        window.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar()=='w'){
                    if (canvas.direction==2){
                        canvas.direction=2;
                    } else{
                        canvas.direction=0;
                    }
                } else if (e.getKeyChar()=='d'){
                    if (canvas.direction==3){
                        canvas.direction=3;
                    } else{
                        canvas.direction=1;
                    }
                }else if (e.getKeyChar()=='s'){
                    if (canvas.direction==0){
                        canvas.direction=0;
                    } else{
                        canvas.direction=2;
                    }
                }else if (e.getKeyChar()=='a'){
                    if (canvas.direction==1){
                        canvas.direction=1;
                    } else{
                        canvas.direction=3;
                    }
                }else if (e.getKeyChar()==' '){
                    canvas.gamestate = 1;
                }else if (e.getKeyChar()=='r'){
                    canvas.restart();
                    canvas.gamestate = 0;
                }

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        Thread t = new Thread(()->{
            while (true){
                try {
                    Thread.sleep(canvas.speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                canvas.movenext();
                canvas.repaint();
            }
        });
        t.start();

        window.getContentPane().add(canvas, BorderLayout.CENTER);
        window.pack();
        window.setVisible(true);
    }
}
