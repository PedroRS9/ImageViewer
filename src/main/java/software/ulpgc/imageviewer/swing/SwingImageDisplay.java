package software.ulpgc.imageviewer.swing;

import software.ulpgc.imageviewer.Image;
import software.ulpgc.imageviewer.ImageDisplay;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SwingImageDisplay extends JPanel implements ImageDisplay {
    private Shift shift = Shift.Null;
    private Released released = Released.Null;
    private int initShift;
    private final List<Paint> paints = new ArrayList<>();

    public SwingImageDisplay() {
        this.addMouseListener(mouseListener());
        this.addMouseMotionListener(mouseMotionListener());
        this.setDoubleBuffered(true);
    }

    private MouseListener mouseListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                initShift = e.getX();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                released.offset(e.getX() - initShift);
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) { }
        };
    }

    private MouseMotionListener mouseMotionListener() {
        return new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                shift.offset(e.getX() - initShift);
            }

            @Override
            public void mouseMoved(MouseEvent e) {}
        };
    }

    @Override
    public void paint(Image image, int offset) {
        paints.add(new Paint(load(image.name()), offset));
        repaint();
    }

    @Override
    public void clear() {
        paints.clear();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!paints.isEmpty()) {
            for (Paint paint : paints) {
                Dimension scaledDimension = getScaledDimension(
                        new Dimension(paint.bufferedImage.getWidth(), paint.bufferedImage.getHeight()),
                        new Dimension(this.getWidth(), this.getHeight())
                );
                int x = ((this.getWidth() - scaledDimension.width) / 2) + paint.offset;
                int y = (this.getHeight() - scaledDimension.height) / 2;
                g.drawImage(paint.bufferedImage, x, y, scaledDimension.width, scaledDimension.height, null);
            }
        }
    }

    public static Dimension getScaledDimension(Dimension imageSize, Dimension boundary) {
        double widthRatio = boundary.getWidth() / imageSize.getWidth();
        double heightRatio = boundary.getHeight() / imageSize.getHeight();
        double ratio = Math.min(widthRatio, heightRatio);

        int scaledWidth = (int) (imageSize.getWidth() * ratio);
        int scaledHeight = (int) (imageSize.getHeight() * ratio);

        return new Dimension(scaledWidth, scaledHeight);
    }

    @Override
    public void on(Shift shift) {
        this.shift = shift != null ? shift : Shift.Null;
    }

    @Override
    public void on(Released released) {
        this.released = released != null ? released : Released.Null;
    }

    private BufferedImage load(String name) {
        try {
            return ImageIO.read(new File(name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private record Paint(BufferedImage bufferedImage, int offset) {
    }
}
