import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Rideable extends Sprite{
    private static final Random random = new Random();
    private static final String rideableDir = "FroggerStyleProject/src/imgs/rideables";
    private static File directory = new File(rideableDir);
    private static File[] rideableFiles = directory.listFiles();
    private ArrayList<BufferedImage> rideableSprites = new ArrayList<>();

    public Rideable(int x, int y){
        super(x, y, 0, 0, 0, 0); //temp width/height
        this.scaleWidth = 1.4;
        this.scaleHeight = 1.4;
        loadSprites();
        setType();
    }

    private void setType() {
        int randomInt = random.nextInt(3); 
        BufferedImage sprite = rideableSprites.get(randomInt);
        this.sprite = sprite; // From the Sprite base class
        this.width = (int) (sprite.getWidth() * 1.4); // Assuming scaling factor 1.4
        this.height = (int) (sprite.getHeight() * 1.4);
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        super.paint(g);
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
