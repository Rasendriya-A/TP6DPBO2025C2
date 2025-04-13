import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Start extends JFrame{
    public Start() {
        setTitle("Welcome");
        setSize(300, 200);
        setLocationRelativeTo(null); // tengah layar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Selamat Datang di Flappy Bird", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));

        JButton startButton = new JButton("Mulai Bermain");
        startButton.setFont(new Font("Arial", Font.PLAIN, 16));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tutup form ini
                dispose();

                // buat sebuah frame
                JFrame frame = new JFrame("Flappy Bird");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(360, 640);
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                //frame.setVisible(true);

                // buat objek JPanel
                FlappyBird flappyBird = new FlappyBird();
                frame.add(flappyBird);
                frame.pack();
                flappyBird.requestFocus();
                frame.setVisible(true);
            }
        });

        add(label, BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);
    }
}
