import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import java.awt.Color;

public class Rideable extends Sprite{
    private static final Random random = new Random();
    private static final String rideableDir = "FroggerStyleProject/src/imgs/rideables";
    private static File directory = new File(rideableDir);
    private static File[] rideableFiles = directory.listFiles();
    private ArrayList<BufferedImage> rideableSprites = new ArrayList<>();
    private int centerY;

    public Rideable(int x, int centerY){
        super(x, centerY, 0, 0, 0, 0); //temp width/height
        this.centerY = centerY; 
        this.scaleWidth = 1.4;
        this.scaleHeight = 1.4;
        loadSprites();
        setType();
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        x+=vx;
		centerY+=vy;
        
        if(x > 700) x = -90;
        else if(x < -90) x = 700;

		init(x, centerY - this.height / 2);

        //draw image
		g2.drawRenderedImage(sprite, tx);

        //draw debugging box around sprite
		if(Frame.debugging){
			g.setColor(Color.green);
			g.drawRect(x, centerY - this.height / 2, width, height);
		}    
    }

    private void setType() {
        int randomInt = random.nextInt(3); 
        BufferedImage sprite = rideableSprites.get(randomInt);
        this.sprite = sprite; // From the Sprite base class
        this.scaleWidth = 1.4;
        this.scaleHeight = 1.4;
        if(randomInt == 2){
            this.scaleWidth *= 0.6;
            this.scaleHeight *= 0.6;
        }
        else if(randomInt == 3){
            this.scaleWidth *= 0.45;
            this.scaleHeight *= 0.45;
        }
        else{
            this.scaleWidth *= 0.85;
            this.scaleHeight *= 0.85;
        }
        this.width = (int) (sprite.getWidth() * scaleWidth); // Assuming scaling factor 1.4
        this.height = (int) (sprite.getHeight() * scaleHeight);
    }
    
    private void loadSprites() {            
        if (rideableFiles != null) {                
            for (File file : rideableFiles) {
                if (file.isFile()) {
                    String relativePath = "/imgs/rideables/" + file.getName();                        
                    BufferedImage img = loadImage(relativePath);
                    if (img != null) {
                        rideableSprites.add(img);
                    } else {
                        System.out.println("Failed to load image: " + relativePath);
                    }
                }
            }
        } else {
            System.out.println("Directory not found: " + rideableDir);
        }
    }
}
