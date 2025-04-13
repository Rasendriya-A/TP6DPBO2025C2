import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int frameWidth = 360;
    int frameHeight = 640;

    Image backgroundImage;
    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;

    //player
    int playerStartPosX = frameWidth / 8;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;
    Player player;

    // pipes attributes
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    ArrayList<Pipe> pipes;

    // game logic
    Timer gameLoop;
    Timer pipesCooldown;
    int gravity = 1;
    boolean gameOver = false;
    double score = 0;
    JLabel resultScore;
    boolean gameOverWindowShown = false;

    // constructor
    public FlappyBird() {
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setFocusable(true);
        addKeyListener(this);
        initUI();

        // setPreferredSize(new Dimension(360, 640));
        // setBackground(Color.blue);

        // load images
        backgroundImage = new ImageIcon(getClass().getResource("./assets/background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("./assets/bird.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("./assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("./assets/upperPipe.png")).getImage();

        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes = new ArrayList<Pipe>();

        pipesCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("pipa");
                placePipes();
            }
        });
        pipesCooldown.start();

        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, frameWidth, frameHeight, null);

        g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void initUI() {
        setLayout(null);
        resultScore = new JLabel();
        resultScore.setFont(new Font("Arial", Font.PLAIN, 32));
        resultScore.setForeground(Color.WHITE);
        resultScore.setBounds(10, 10, 300, 40);
        add(resultScore);
    }

    public void move() {
        if (gameOver) {
            updateScoreLabel();
            return;
        }

        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());

            if (!pipe.passed && player.getPosX() > pipe.getPosX() + pipe.getWidth()) {
                pipe.passed = true;
                score += 0.5; // Diset 0.5, karena terdapat dua pipa (atas dan bawah). Sehingga ketika melewati kedua pipa tersebut, skor akan menjadi 1
            }

            if (collision(player, pipe)) {
                gameOver = true;
            }
        }

        if (player.getPosY() > frameHeight) {
            gameOver = true;
        }

        updateScoreLabel();
    }

    public void updateScoreLabel() {
        String scoreText = String.valueOf((int) score);
        resultScore.setText(scoreText);
    }

    public boolean collision(Player a, Pipe b) {
        return  a.getPosX() < b.getPosX() + b.getWidth() &&     // Pojok kiri atas player tidak dapat menyentuh pojok kanan atas pipa
                a.getPosX() + a.getWidth() > b.getPosX() &&     // Pojok kanan atas player tidak dapat melewati pojok kiri atas pipa
                a.getPosY() < b.getPosY() + b.getHeight() &&    // Pojok kiri atas player tidak dapat menyentuh pojok kiri bawah pipa
                a.getPosY() + a.getHeight() > b.getPosY();      // Pojok kiri bawah player tidak dapat melewati pojok kiri atas pipa
    }

    public void placePipes() {
        int randomPosY = (int) (pipeStartPosY - (pipeHeight / 4) - Math.random() * (pipeHeight / 2));
        int openingSpace = frameHeight/4;

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, (randomPosY + openingSpace + pipeHeight), pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    public void showGameOverWindow() {
        JFrame gameOverFrame = new JFrame("Game Over");
        gameOverFrame.setSize(300, 100);
        gameOverFrame.setLayout(new BorderLayout());
        gameOverFrame.setLocationRelativeTo(null); // center screen

        JLabel message = new JLabel("Game Over!", SwingConstants.CENTER);
        message.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel finalScore = new JLabel("Skor akhir: " + (int) score, SwingConstants.CENTER);
        finalScore.setFont(new Font("Arial", Font.PLAIN, 18));

        gameOverFrame.add(message, BorderLayout.NORTH);
        gameOverFrame.add(finalScore, BorderLayout.CENTER);

        gameOverFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            pipesCooldown.stop();
            gameLoop.stop();

            if (!gameOverWindowShown) {
                showGameOverWindow();
                gameOverWindowShown = true;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.setVelocityY(-10);
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            if (gameOver) {
                //spacebar to restart
                player.setPosY(playerStartPosY);
                player.setVelocityY(0);
                pipes.clear();
                score = 0;
                gameOver = false;
                gameOverWindowShown = false;
                gameLoop.start();
                pipesCooldown.start();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
