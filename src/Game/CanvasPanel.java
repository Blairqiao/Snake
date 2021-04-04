package Game;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CanvasPanel extends JPanel {
    int[][] grid = new int[20][20];

    BufferedImage img=null;
    BufferedImage melon=null;
    BufferedImage east=null;
    BufferedImage north=null;
    BufferedImage south=null;
    BufferedImage west=null;

    int gamestate = 0;
    // 0) not started
    // 1) started
    // 2) gameover
    int direction = 0;
    int Score = 0;
    int highscore = 0;
    long speed = 100;
    int difficulty = 1;

    java.util.List<int[]> snake = new ArrayList<>();

    public CanvasPanel() {
        try {
            img=ImageIO.read(CanvasPanel.class.getClassLoader().getResource("apple.png").openStream());
            melon=ImageIO.read(CanvasPanel.class.getClassLoader().getResource("watermelon.png").openStream());
            east=ImageIO.read(CanvasPanel.class.getClassLoader().getResource("east.png").openStream());
            north=ImageIO.read(CanvasPanel.class.getClassLoader().getResource("north.png").openStream());
            south=ImageIO.read(CanvasPanel.class.getClassLoader().getResource("south.png").openStream());
            west=ImageIO.read(CanvasPanel.class.getClassLoader().getResource("west.png").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


        snake.add(new int[]{10,8});
        snake.add(new int[]{10,9});
        snake.add(new int[]{10,10});
        snake.add(new int[]{10,11});
        grid[snake.get(0)[0]][snake.get(0)[1]] = 1;
        grid[snake.get(1)[0]][snake.get(1)[1]] = 1;
        grid[snake.get(2)[0]][snake.get(2)[1]] = 1;
        grid[snake.get(3)[0]][snake.get(3)[1]] = 1;

        RandomMelon(1);
        RandomApple(5);
//        grid[3][3] = 2;
    }

    public void restart() {
        snake = new ArrayList<>();
        grid = new int[20][20];
        snake.add(new int[]{10,8});
        snake.add(new int[]{10,9});
        snake.add(new int[]{10,10});
        snake.add(new int[]{10,11});
        grid[snake.get(0)[0]][snake.get(0)[1]] = 1;
        grid[snake.get(1)[0]][snake.get(1)[1]] = 1;
        grid[snake.get(2)[0]][snake.get(2)[1]] = 1;
        grid[snake.get(3)[0]][snake.get(3)[1]] = 1;
        RandomApple(5);
        RandomMelon(1);
        Score = 0;
        direction = 0;
        if (difficulty == 0){
            speed = 135;
        } else if (difficulty == 1){
            speed = 100;
        } else if (difficulty == 2){
            speed = 60;
        }

    }

    public void playsound() {
        try {
            InputStream audio = this.getClass().getClassLoader().getResourceAsStream("apple.wav");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(audio);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void RandomApple(int num) {
        for (int i=0; i<num;i++) {
            int appleX = (int)(Math.random() * 20);
            int appleY = (int)(Math.random() * 20);
            if (grid[appleX][appleY]==1 || grid[appleX][appleY]==2){
                grid[(int)(Math.random() * 20)][(int)(Math.random() * 20)]=2;
            } else {
                grid[appleX][appleY] = 2;
            }
        }
    }

    public void RandomMelon(int numMelon) {
        for (int m = 0; m < numMelon; m++) {
            int melX = (int)(Math.random() * 20);
            int melY = (int)(Math.random() * 20);
            if (grid[melX][melY]==1 || grid[melX][melY]==3||  grid[melX][melY] == 2 || grid[melX][melY] == 4){
                grid[(int)(Math.random() * 20)][(int)(Math.random() * 20)]=3;
            } else {
                grid[melX][melY] = 3;
            }
        }

    }

    public void movenext() {
        if (gamestate == 0 || gamestate == 2){
            return;
        }

        int[] newhead = new int[2];
        newhead[0] = snake.get(0)[0];
        newhead[1] = snake.get(0)[1];
//generate newhead
        int[] tail = snake.remove(snake.size()-1);
        if (direction == 0){
            newhead[1] = (newhead[1] - 1);
        }else if (direction == 1){
            newhead[0] = (newhead[0] + 1);
        }else if (direction == 2){
            newhead[1] = (newhead[1] + 1);
        }else if (direction == 3){
            newhead[0] = (newhead[0] - 1);
        }
        // gameover check

        if (newhead[1] == -1||newhead[1]==20){
            gamestate = 2;
            return;
        }
        if (newhead[0] == -1||newhead[0]==20){
            gamestate = 2;
            return;
        }
        if (grid[newhead[0]][newhead[1]]==1){
            gamestate = 2;
            return;
        }

        //apple
        if (grid[newhead[0]][newhead[1]]==2){
            if (speed == 20){
            } else{
                speed = speed - 1;
            }
            if (highscore <= Score){
                highscore += 1;
            }
            Score += 1;
            RandomApple(1);
            this.playsound();
            snake.add(tail);
        } else {
            grid[tail[0]][tail[1]] = 0;
        }

        //watermelon
        if (grid[newhead[0]][newhead[1]]==3){
            if (speed == 20){
            } else{
                speed = speed - 3;
            }
            if (highscore <= Score){
                highscore += 3;
            }
            Score += 3;
            RandomMelon(1);
            this.playsound();
            snake.add(tail);
        } else {
            grid[tail[0]][tail[1]] = 0;
        }
        snake.add(0,newhead);
        grid[newhead[0]][newhead[1]]=1;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font old = g.getFont();

        g.setColor(Color.black);


        for (int i = 0; i<20; i++ ){
            for (int j=0;j<20;j++){
                if (grid[i][j]==1){//snake body
                    if (i==snake.get(0)[0]&&j==snake.get(0)[1]){//if it is head
                        if (direction==0){
                            g.drawImage( north,i*20,j*20,20,20,null);
                        }else if (direction==1){
                            g.drawImage(east,i*20,j*20,20,20,null);
                        } else if (direction==2){
                            g.drawImage(south,i*20,j*20,20,20,null);
                        }else if (direction==3){
                            g.drawImage(west,i*20,j*20,20,20,null);
                        }
                    } else {//body without head.
                        g.setColor(Color.green);
                        g.fillRect(i*20,j*20,20,20);
                        g.setColor(Color.black);
                    }
                } else {//other cell
                    g.drawRect(i*20,j*20,20,20);

                }
                if (grid[i][j]==2){//apple
                    g.drawImage(img, i * 20, j * 20, 20, 20, null);
                } else if (grid[i][j]==3){
                    g.drawImage(melon, i * 20, j * 20, 20, 20, null);
                }
            }
        }
        if (gamestate == 0){
            g.setFont(new Font("TimesRoman", Font.BOLD, 20));
            g.drawString("Press Space to Start",135,200);
            g.setFont(old);
        } else if (gamestate == 2){
            g.setFont(new Font("TimesRoman", Font.BOLD, 20));
            g.drawString("Game Over",175,200);
            g.drawString("Your Score is: "+Score,175,220);
            g.setFont(old);
            g.drawString("Press r to Restart",175,240);
        }
        g.drawString("Your Score: " + Score,280,420);
        g.drawString("Speed: " + (136-speed),40,420);
        g.drawString("Highscore: " + highscore, 150,420);

    }
}
