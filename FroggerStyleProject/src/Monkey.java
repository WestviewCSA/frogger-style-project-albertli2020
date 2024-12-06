import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.awt.Rectangle;


public class Monkey extends Sprite{
	private BufferedImage sprite; 	
	float rotationAngle;
	private boolean riding = false;

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
			g.setColor(Color.red);
			
			// Get rotated hitbox
			Rectangle boundingBox = hitbox();
			
			int[] xPoints = {
				boundingBox.x, 
				boundingBox.x + boundingBox.width, 
				boundingBox.x + boundingBox.width, 
				boundingBox.x
			};
			int[] yPoints = {
				boundingBox.y, 
				boundingBox.y, 
				boundingBox.y + boundingBox.height, 
				boundingBox.y + boundingBox.height
			};
			
			g.drawPolygon(xPoints, yPoints, 4);
		}
	
    }

	public void rotate(float angle) {
        this.rotationAngle = angle;
    }

	public boolean getRiding(){
		return riding;
	}

	public void setRiding(boolean riding){
		this.riding = riding;
	}

	public void lockTo(Rideable r){
		x += r.vx;
		if(!r.collision(this.hitbox())){
			riding = false;
		}
	}

	@Override
	public Rectangle hitbox() {
		// Calculate the center of the monkey sprite
		int centerX = x + (int)(width * scaleWidth / 2);
		int centerY = y + (int)(height * scaleHeight / 2);
		
		// Calculate rotated corners
		double angle = Math.toRadians(rotationAngle);
		
		// Original corner points relative to center
		double[][] originalPoints = {
			{-width * scaleWidth / 2, -height * scaleHeight / 2},  // Top Left
			{width * scaleWidth / 2, -height * scaleHeight / 2},   // Top Right
			{width * scaleWidth / 2, height * scaleHeight / 2},    // Bottom Right
			{-width * scaleWidth / 2, height * scaleHeight / 2}    // Bottom Left
		};
		
		for (int i = 0; i < 4; i++) {
			originalPoints[i][1] *= 0.9;
		}	

		// Rotated points
		int[] xPoints = new int[4];
		int[] yPoints = new int[4];
		
		for (int i = 0; i < 4; i++) {
			// Rotate point
			double rotatedX = originalPoints[i][0] * Math.cos(angle) - originalPoints[i][1] * Math.sin(angle);
			double rotatedY = originalPoints[i][0] * Math.sin(angle) + originalPoints[i][1] * Math.cos(angle);
			
			// Translate back to screen coordinates
			xPoints[i] = centerX + (int)rotatedX;
			yPoints[i] = centerY + (int)rotatedY;
		}
		
		// Find bounding rectangle of the rotated polygon
		int minX = Arrays.stream(xPoints).min().getAsInt();
		int maxX = Arrays.stream(xPoints).max().getAsInt();
		int minY = Arrays.stream(yPoints).min().getAsInt();
		int maxY = Arrays.stream(yPoints).max().getAsInt();
		
		return new Rectangle(minX, minY, maxX - minX, maxY - minY);
	}
}
