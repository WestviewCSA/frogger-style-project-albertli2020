import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class Bloons extends Sprite{
	private static final Random random = new Random();
	private static final String bloonsDir = "src/imgs/bloon sprites";
    private static File directory = new File(bloonsDir);
    private static File[] bloonsFiles = directory.listFiles();
    private ArrayList<BufferedImage> bloonSprites = new ArrayList<>();

    private int rbe = 1;

	public Bloons(int x, int y) {
        super(x, y, 0, 0, 0, 0); // temp width/height
        rbe = random.nextInt(11) + 1;
        System.out.println(directory);
        System.out.println(System.getProperty("user.dir"));
        loadSprites();
        setSpriteByRBE();
    }

	public Bloons(){
		super(0, 0, 0, 0, 0, 0);
	}

	//load sprites
	private void loadSprites() {            
        if (bloonsFiles != null) {        
			Arrays.sort(bloonsFiles);        
            for (File file : bloonsFiles) {
                if (file.isFile()) {
                    String relativePath = "/imgs/bloon sprites/" + file.getName();  
					System.out.println(relativePath);                      
                    BufferedImage img = loadImage(relativePath);
                    if (img != null) {
                        bloonSprites.add(img);
                    } else {
                        System.out.println("Failed to load image: " + relativePath);
                    }
                }
            }
        } else {
            System.out.println("Directory not found: " + bloonsDir);
        }
    }

	//set bloon type
	private void setSpriteByRBE() {
        if (rbe > 0 && rbe <= bloonSprites.size()) {
            BufferedImage sprite = bloonSprites.get(rbe - 1);
            this.sprite = sprite; // From the Sprite base class
            this.width = sprite.getWidth() * 2; // Assuming scaling factor 2
            this.height = sprite.getHeight() * 2;
        }
    }

	@Override
    public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
        setSpriteByRBE(); //update sprite based on rbe
		if(x > 1250){
			x = -30;
			rbe = random.nextInt(11) + 1;
		}
		else if(x + width < 0){
			x = 1250;
			rbe = random.nextInt(11) + 1;
		}
		super.paint(g);
	}

    public void updateRBE(int newRBE){
		this.rbe = newRBE;
		setSpriteByRBE();
	}

}