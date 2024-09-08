import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

class snake_games extends JFrame{

    snake_games() {
        this.add(new GamePanel());
        this.setTitle("Snake Game");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
} 

class GamePanel extends JPanel implements ActionListener{
    
    final int SCREEN_HEIGHT = 600;
    final int SCREEN_WIDTH = 600;
    int DELAY = 85;
    final int UNIT_SIZE = 25;
    final int GAME_UNITS = (SCREEN_HEIGHT*SCREEN_WIDTH)/UNIT_SIZE;
    int x[] = new int[GAME_UNITS];
    int y[] = new int[GAME_UNITS];
    int bodyPart = 6;
    int apples;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if(running) {
            // for(int i = 0; i < SCREEN_HEIGHT; i++) {
            //     g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            //     g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            // }
            g.setColor(Color.green);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for(int i = 0; i < bodyPart; i++) {
                if(i == 0) {
                    g.setColor(Color.orange);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else {
                    //g.setColor(new Color(45, 180, 0));
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
        } 
        else {
            gameOver(g);
        }
    }

    public void move() {
        for(int i = bodyPart; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void newApple() {
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    public void checkCollision() {
        //check to see if the head hits the body
        for(int i = bodyPart; i > 0; i--) {
            if(x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }
        //check to see if the head hits the border
        if(x[0] < 0) {
            running = false;
        }
        if(x[0] > SCREEN_WIDTH) {
            running = false;
        }
        if(y[0] < 0) {
            running = false;
        }
        if(y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if(!running) {
            timer.stop();
        }
    }

    public void checkApple() {
        if(x[0] == appleX && y[0] == appleY) {
            bodyPart++;
            apples++;
            newApple();
        }
    }

    public void gameOver(Graphics g) {
        String gameOver = "Game Over";
        g.setColor(Color.GREEN);
        g.setFont(new Font("Cascadia code", Font.BOLD, 40));
        g.drawString(gameOver, (SCREEN_WIDTH-(gameOver.length()*22))/2, SCREEN_HEIGHT/2);

        String score = "Score: ";
        g.setColor(Color.GREEN);
        g.setFont(new Font("Cascadia code", Font.BOLD, 40));
        g.drawString(score+apples, (SCREEN_WIDTH-(score.length()*22))/2, (SCREEN_HEIGHT/2)+60);

        String playAgain = "Press 'ENTER' to play again";
        g.setColor(Color.BLUE);
        g.setFont(new Font("Cascadia code", Font.BOLD, 20));
        g.drawString(playAgain, (SCREEN_WIDTH-(playAgain.length()*12))/2, (SCREEN_HEIGHT/2)+120);
    }

    public void playAgain() {
        bodyPart = 6;
        apples = 0;
        direction = 'R';
        DELAY = 85;
        x = new int[GAME_UNITS];
        y = new int[GAME_UNITS];
        startGame();

    }

    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_S:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_A:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_D:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    playAgain();
                    break;
            }
        }
    }
}

class SnakeGame {
    public static void main(String[] args) {
        new snake_games();
    }
}