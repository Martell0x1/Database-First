package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class BackgroundPanel extends JPanel {
    private final Image backgroundImage;

    public BackgroundPanel(InputStream imageStream) throws IOException {
        try {
            backgroundImage = ImageIO.read(imageStream);
        } catch (IOException e) {
            throw new IOException("Failed to load background image!", e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
