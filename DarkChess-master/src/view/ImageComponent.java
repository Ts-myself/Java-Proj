package view;

import javax.swing.*;
import java.awt.*;

public class ImageComponent extends JComponent {
    public Image image;

    public ImageComponent(Image image) {
        this.setLayout(null);
        this.setFocusable(true);//Sets the focusable state of this Component to the specified value. This value overrides the Component's default focusability.
        this.image = image;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image,0, 0, image.getWidth(this), image.getHeight(this), this);
    }
}