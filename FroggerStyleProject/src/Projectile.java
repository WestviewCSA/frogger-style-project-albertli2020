import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.awt.Color;

public class Projectile extends Sprite{
    private static final int SPEED = 10;
    private boolean active = true;
    private BufferedImage sprite; 	
    private float rotationAngle;


    public Projectile(int x, int y, float angle){
        super(x, y, 0, 0, 0, 0);
        move(x - width / 2, y - width / 2);
        sprite = loadImage("/imgs/"+"dart.png");
        double radians = Math.toRadians(angle);
        this.rotationAngle = angle;
        this.vx = (int) (SPEED * Math.cos(radians));
        this.vy = (int) (SPEED * Math.sin(radians));
    }

    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform originalTransform = g2.getTransform();

        x += vx;
		y += vy;

        if (outOfBounds()) {
            active = false;
        }

        g2.translate(x + (width * scaleWidth) / 2, y + (height * scaleHeight) / 2); // Center the sprite
        g2.rotate(Math.toRadians(rotationAngle)); // Rotate
        g2.translate(-(width * scaleWidth) / 2, -(height * scaleHeight) / 2); // Translate back
        g2.drawImage(sprite, 0, 0, (int) (width * scaleWidth), (int) (height * scaleHeight), null);

        // restore original transform
        g2.setTransform(originalTransform);

        if (Frame.debugging) {
			g.setColor(Color.red);
			
			//get rotated hitbox
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

    private boolean outOfBounds(){
        return x + width < 0 || x > 610 || y + height < 0 || y > 630;
    }

    public boolean isActive() {
        return active;
    }

    	@Override
	public Rectangle hitbox() { //same as Monkey class
		//centers
		int centerX = x + (int)(width * scaleWidth / 2);
		int centerY = y + (int)(height * scaleHeight / 2);
		
		double angle = Math.toRadians(rotationAngle);
		
		//original corner points
		double[][] originalPoints = {
			{-width * scaleWidth / 2, -height * scaleHeight / 2},  // Top Left
			{width * scaleWidth / 2, -height * scaleHeight / 2},   // Top Right
			{width * scaleWidth / 2, height * scaleHeight / 2},    // Bottom Right
			{-width * scaleWidth / 2, height * scaleHeight / 2}    // Bottom Left
		};
		
		for (int i = 0; i < 4; i++) {
			originalPoints[i][1] *= 0.9;
		}	

		//new rotated points
		int[] xPoints = new int[4];
		int[] yPoints = new int[4];
		
		for (int i = 0; i < 4; i++) {
			//rotate points
			double rotatedX = originalPoints[i][0] * Math.cos(angle) - originalPoints[i][1] * Math.sin(angle);
			double rotatedY = originalPoints[i][0] * Math.sin(angle) + originalPoints[i][1] * Math.cos(angle);
			
			//translate back to coordinates
			xPoints[i] = centerX + (int)rotatedX;
			yPoints[i] = centerY + (int)rotatedY;
		}
		
		//create bounding rectangle of the rotated polygon
		int minX = Arrays.stream(xPoints).min().getAsInt();
		int maxX = Arrays.stream(xPoints).max().getAsInt();
		int minY = Arrays.stream(yPoints).min().getAsInt();
		int maxY = Arrays.stream(yPoints).max().getAsInt();
		
		return new Rectangle(minX, minY, maxX - minX, maxY - minY);
	}


}
