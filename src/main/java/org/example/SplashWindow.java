package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

public class SplashWindow extends JFrame {
    final private JProgressBar progressBar = new JProgressBar(0, 100);
    final private JLabel label = new JLabel("Loading....");
    private final JLabel iconLabel = new JLabel();
    private boolean stop = false;

    public SplashWindow() throws IOException {
        this.setSize(1000, 571);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Loading");
        this.setLocationRelativeTo(null);

        InputStream BG = SplashWindow.class.getClassLoader().getResourceAsStream("images/gradient.jpg");
        JPanel panel = new BackgroundPanel(BG);
        panel.setLayout(new GridBagLayout());

        InputStream imageStream = SplashWindow.class.getClassLoader().getResourceAsStream("images/splash.png");
        if (imageStream != null) {
            ImageIcon icon = new ImageIcon(ImageIO.read(imageStream));
            iconLabel.setIcon(icon);
        } else {
            iconLabel.setText("Image not found");
            iconLabel.setForeground(Color.WHITE);
        }

        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Color.WHITE);

        progressBar.setStringPainted(true);
        progressBar.setString("");
        progressBar.setPreferredSize(new Dimension(500, 5));
        progressBar.setForeground(Color.RED);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(iconLabel, gbc);

        gbc.gridy++;
        panel.add(label, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(progressBar, gbc);

        this.add(panel);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                stop = true;
                dispose();
            }
        });
    }

    private void terminateProgram() {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, "Unable to connect to the database. The program will now exit.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
            }
            System.exit(1);
        });
    }

    public void UpdareProgressBar() {
        new Thread(() -> {
            CountDownLatch dbLatch = new CountDownLatch(1);
            boolean[] dbSuccess = {false};

            for (int x = 0; x <= 100; x += 20) {
                if (stop) {
                    break;
                }

                int finalX = x;

                try {
                    SwingUtilities.invokeAndWait(() -> {
                        progressBar.setValue(finalX);
                        System.out.println("Progress at: " + finalX);

                        if (finalX == 20) {
                            label.setText("Loading Resources...");
                        } else if (finalX == 40) {
                            label.setText("Establishing Connection With Database...");

                            Thread dbThread = new Thread(() -> {
                                try {
                                    DatabaseConnect data = DatabaseConnect.getInstance();
                                    Connection connection = data.getConnection();

                                    if (connection != null && !connection.isClosed()) {
                                        SwingUtilities.invokeLater(() -> {
                                            label.setText("Connection Established Successfully!");
                                            System.out.println("[*] Database connected successfully!");
                                        });
                                        dbSuccess[0] = true;
                                    } else {
                                        SwingUtilities.invokeLater(() -> {
                                            label.setText("Couldn't Connect To The DB-Server, Check Your Connection.");
                                        });
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    dbLatch.countDown();
                                }
                            });

                            dbThread.start();

                            new Thread(() -> {
                                try {
                                    dbThread.join(10000);
                                    if (dbThread.isAlive()) {
                                        dbThread.interrupt();
                                        SwingUtilities.invokeLater(() -> {
                                            label.setText("Connection Timeout: Unable to connect to the database.");
                                        });
                                    }
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                    e.printStackTrace();
                                } finally {
                                    dbLatch.countDown();
                                }
                            }).start();
                        } else if (finalX == 80) {
                            label.setText("Setting Things For You...");
                        } else if (finalX > 80) {
                            label.setText("Almost There...");
                        }
                    });

                    if (finalX == 40) {
                        dbLatch.await();

                        if (!dbSuccess[0]) {
                            terminateProgram();
                            return;
                        }
                    }

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }

            SwingUtilities.invokeLater(() -> {
                if (!stop) {
                    dispose();
                    try {
                        new Login(this).Run();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }).start();
    }


    public void Run() {
        SwingUtilities.invokeLater(() -> {
            this.setVisible(true);
            this.UpdareProgressBar();
        });
    }

}
