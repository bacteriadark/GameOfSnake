package gameOfSnake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Snake  extends JPanel implements ActionListener {
	
	    private final int WIDTH = 300;
	    private final int HEIGHT = 300;
	    private final int UNIT_SIZE = 10;
	    private final int GAME_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
	    private final int DELAY = 75;
	    
	    // 視窗設定區

	    private final int[] x = new int[GAME_UNITS];
	    private final int[] y = new int[GAME_UNITS];

	    private int bodyParts = 6;
	    private int applesEaten;
	    private int appleX;
	    private int appleY;

	    private boolean up = false;
	    private boolean down = false;
	    private boolean left = false;
	    private boolean right = true;
	    private boolean inGame = true;

	    private final Timer timer;

	    public Snake() {
	        setPreferredSize(new Dimension(WIDTH, HEIGHT));
	        setBackground(Color.BLACK);
	        setFocusable(true);
	        addKeyListener(new MyKeyAdapter());

	        spawnApple();

	        timer = new Timer(DELAY, this);
	        timer.start();
	    }

	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        draw(g);
	    }

	    public void draw(Graphics g) {
	        if (inGame) {
	            // 設定蘋果
	            g.setColor(Color.RED);
	            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

	            // 設定蛇
	            for (int i = 0; i < bodyParts; i++) {
	                if (i == 0) {
	                    g.setColor(Color.GREEN);
	                } else {
	                    g.setColor(new Color(45, 180, 0));
	                }
	                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
	            }

	            // 顯示得分
	            g.setColor(Color.WHITE);
	            g.setFont(new Font("SansSerif", Font.BOLD, 20));
	            FontMetrics metrics = getFontMetrics(g.getFont());
	            String scoreText = "Score: " + applesEaten;
	            g.drawString(scoreText, (WIDTH - metrics.stringWidth(scoreText)) / 2, g.getFont().getSize());
	        } else {
	            gameOver(g);
	        }
	    }

	    public void spawnApple() {
	    	// 生成蘋果位置
	        Random random = new Random();
	        appleX = random.nextInt((int) (WIDTH / UNIT_SIZE)) * UNIT_SIZE;
	        appleY = random.nextInt((int) (HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
	    }

	    public void checkApple() {
	    	// 檢測有沒有吃到
	        if (x[0] == appleX && y[0] == appleY) {
	            bodyParts++;
	            applesEaten++;
	            spawnApple();
	        }
	    }

	    public void checkCollision() {
	        // 撞牆判定
	        if (x[0] >= WIDTH || x[0] < 0 || y[0] >= HEIGHT || y[0] < 0) {
	            inGame = false;
	        }

	        // 狀身體判定
	        for (int i = 1; i < bodyParts; i++) {
	            if (x[i] == x[0] && y[i] == y[0]) {
	                inGame = false;
	            }
	        }

	        if (!inGame) {
	            timer.stop();
	        }
	    }

	    public void gameOver(Graphics g) {
	        g.setColor(Color.RED);
	        g.setFont(new Font("SansSerif", Font.BOLD, 40));
	        FontMetrics metrics = getFontMetrics(g.getFont());
	        String gameOverText = "Game Over";
	        g.drawString(gameOverText, (WIDTH - metrics.stringWidth(gameOverText)) / 2, HEIGHT / 2);

	        g.setColor(Color.WHITE);
	        g.setFont(new Font("SansSerif", Font.BOLD, 20));
	        metrics = getFontMetrics(g.getFont());
	        String scoreText = "Score: " + applesEaten;
	        g.drawString(scoreText, (WIDTH - metrics.stringWidth(scoreText)) / 2, (HEIGHT / 2) + 30);
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {
	        if (inGame) {
	            move();
	            checkApple();
	            checkCollision();
	        }
	        repaint();
	    }

	    public void move() {
	    	
	    	// 移動
	        for (int i = bodyParts; i > 0; i--) {
	            x[i] = x[i - 1];
	            y[i] = y[i - 1];
	        }

	        if (up) {
	            y[0] -= UNIT_SIZE;
	        }
	        if (down) {
	            y[0] += UNIT_SIZE;
	        }
	        if (left) {
	            x[0] -= UNIT_SIZE;
	        }
	        if (right) {
	            x[0] += UNIT_SIZE;
	        }
	    }

	    private class MyKeyAdapter extends KeyAdapter {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            switch (e.getKeyCode()) {
	                case KeyEvent.VK_LEFT:
	                    if (!right) {
	                        left = true;
	                        up = false;
	                        down = false;
	                    }
	                    break;
	                case KeyEvent.VK_RIGHT:
	                    if (!left) {
	                        right = true;
	                        up = false;
	                        down = false;
	                    }
	                    break;
	                case KeyEvent.VK_UP:
	                    if (!down) {
	                        up = true;
	                        right = false;
	                        left = false;
	                    }
	                    break;
	                case KeyEvent.VK_DOWN:
	                    if (!up) {
	                        down = true;
	                        right = false;
	                        left = false;
	                    }
	                    break;
	            }
	        }
	    }

	    public static void main(String[] args) {
	    	
	    	// 啟動遊戲式窗
	        JFrame frame = new JFrame("Snake Game");
	        Snake snakeGame = new Snake();
	        frame.add(snakeGame);
	        frame.pack();
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	    }
	}



