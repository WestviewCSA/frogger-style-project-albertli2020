import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Monkey extends Sprite{
	private BufferedImage sprite; 	
	float rotationAngle;
	public Monkey(int x, int y) {
		super(x, y, 0, 0, 0, 0);
		sprite = loadImage("/imgs/"+"monkey.png"); //load the monkey image
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
		this.scaleWidth = 1.5;
		this.scaleHeight = 1.5;
	}

	public Monkey(){
		super(0, 0, 0, 0, 0, 0);
		sprite = loadImage("/imgs/"+"monkey.png");
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
	}

	@Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform originalTransform = g2.getTransform();

        // Apply translation and rotation
		x += vx;
		y += vy;

		if(x < 0) x = 0;
		else if(x + width > 610) x = 610 - width;
		if(y < 0) y = 0;
		else if(y + height > 590) y = 590 - height;

        g2.translate(x + (width * scaleWidth) / 2, y + (height * scaleHeight) / 2); // Center the sprite
        g2.rotate(Math.toRadians(rotationAngle)); // Rotate
        g2.translate(-(width * scaleWidth) / 2, -(height * scaleHeight) / 2); // Translate back
        g2.drawImage(sprite, 0, 0, (int) (width * scaleWidth), (int) (height * scaleHeight), null);

        // Restore original transform
        g2.setTransform(originalTransform);

        // Draw debugging hitbox
        if (Frame.debugging) {
            g.setColor(Color.GREEN);
            g.drawRect(x, y, (int) (width * scaleWidth), (int) (height * scaleHeight));
        }
    }

	public void rotate(float angle) {
        this.rotationAngle = angle;
    }

}
