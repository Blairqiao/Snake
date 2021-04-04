package Game;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        int DifficultySpeed = 0;
        JFrame window = new JFrame("Snake");
        UIManager.put("MenuBar.foreground", Color.GREEN);
        UIManager.put("MenuItem.foreground", Color.GREEN);
        window.setPreferredSize(new Dimension(405,475));

        CanvasPanel canvas = new CanvasPanel();

        JMenuBar jMenuBar = new JMenuBar();
        window.setJMenuBar(jMenuBar);
        JMenu Difficulty = new JMenu("Difficulty");
        JMenuItem Easy = Difficulty.add("Easy");
        JMenuItem Medium = Difficulty.add("Medium");
        JMenuItem Hard = Difficulty.add("Hard");
        Difficulty.setBackground(Color.BLACK);
        Easy.setBackground(Color.BLACK);
        Medium.setBackground(Color.BLACK);
        Hard.setBackground(Color.BLACK);
        jMenuBar.add(Difficulty);
        Easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.difficulty = 0;
            }
        });
        Medium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.difficulty = 1;
            }
        });
        Hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.difficulty = 2;
            }
        });

        window.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if (e.getKeyChar() == 'w') {
                            if (canvas.direction == 2) {
                                canvas.direction = 2;
                            } else {
                                canvas.direction = 0;
                            }
                        } else if (e.getKeyChar() == 'd') {
                            if (canvas.direction == 3) {
                                canvas.direction = 3;
                            } else {
                                canvas.direction = 1;
                            }
                        } else if (e.getKeyChar() == 's') {
                            if (canvas.direction == 0) {
                                canvas.direction = 0;
                            } else {
                                canvas.direction = 2;
                            }
                        } else if (e.getKeyChar() == 'a') {
                            if (canvas.direction == 1) {
                                canvas.direction = 1;
                            } else {
                                canvas.direction = 3;
                            }
                        } else if (e.getKeyChar() == ' ') {
                            canvas.gamestate = 1;
                        } else if (e.getKeyChar() == 'r') {
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
