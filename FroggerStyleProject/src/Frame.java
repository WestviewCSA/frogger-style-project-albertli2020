import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	private static final Random random = new Random();
	public static boolean debugging = true;
	public static boolean simpleMovement = true;
	//Timer related variables
	BufferedImage background;
	long ellapseTime = 0;
	Font timeFont = new Font("Courier", Font.BOLD, 70);
	Font myFont = new Font("Courier", Font.BOLD, 40);
	
	Monkey dart = new Monkey(275, 570);
	Bloons[] bloons1 = new Bloons[10];
	Bloons[] bloons2 = new Bloons[10];
	Bloons[] bloons3 = new Bloons[10];
	Bloons[] bloons4 = new Bloons[10];
	Bloons[] bloons5 = new Bloons[10];

	Rideable[] rideables1 = new Rideable[4];
	Rideable[] rideables2 = new Rideable[4];
	Rideable[] rideables3 = new Rideable[4];
	Rideable[] rideables4 = new Rideable[4];

	int lifeCounter = 5;
	ArrayList<Lives> lives = new ArrayList<>();
	int bloonSpeed = 2;
	
	int width = 610;
	int height = 630;	


	public void paint(Graphics g) {
		super.paintComponent(g);

		if (background != null) {
            g.drawImage(background, 0, 0, 610, 630, null);
        }

		for(int i = 0; i < 10; i++){
			bloons1[i].paint(g);
			bloons2[i].paint(g);
			bloons3[i].paint(g);
			bloons4[i].paint(g);
			bloons5[i].paint(g);
		}

		
		for(int i = 0; i < 4; i++){
			rideables1[i].paint(g);
			rideables2[i].paint(g);
			rideables3[i].paint(g);
			rideables4[i].paint(g);
		}
	

		for(int i = 0; i < lifeCounter; i++){
			lives.get(i).paint(g);
		}

		dart.rotate(getAngle(dart.x, dart.y));
		dart.paint(g);
		//System.out.println(MouseInfo.getPointerInfo().getLocation());
		//System.out.println(dart.x + "," + dart.y);

		if(debugging){
			g.setColor(Color.RED);
			int centerX = dart.x + (int)(dart.width * dart.scaleWidth) / 2;
			int centerY = dart.y + (int)(dart.height * dart.scaleHeight) / 2;
			double angle = Math.toRadians(getAngle(dart.x, dart.y) + 90);
			int lineLength = 50;
			int lineX = centerX + (int)(Math.cos(angle) * lineLength);
			int lineY = centerY + (int)(Math.sin(angle) * lineLength);
			g.drawLine(centerX, centerY, lineX, lineY);
		}
	}
	
	public static void main(String[] arg) {
		Frame f = new Frame();
	}
	
	public Frame() {
		try{
            URL imageURL = getClass().getResource("/imgs/bgstart.png");
            background = ImageIO.read(imageURL);
        } catch (IOException e) {
            e.printStackTrace();
        }

		for(int i = 1; i <= lifeCounter; i++) lives.add(new Lives(i)); //create life counter
		
		for(int i = 0; i < 10; i++){ //create bloons
			bloons1[i] = new Bloons(10 + i * 125, 510, 1);
			bloons1[i].updateX(-bloonSpeed);
			bloons2[i] = new Bloons(2 + i * 125, 455, 3);
			bloons2[i].updateX(bloonSpeed);
			bloons3[i] = new Bloons(28 + i * 125, 280, 8);
			bloons3[i].updateX(bloonSpeed);
			bloons4[i] = new Bloons(15 + i * 125, 225, 2);
			bloons4[i].updateX(-bloonSpeed);
			bloons5[i] = new Bloons(20 + i * 125, 165, 5);
			bloons5[i].updateX(bloonSpeed);
		}

		for(int i = 0; i < 4; i++){
			rideables1[i] = new Rideable(random.nextInt(80) + i * 165, 408);
			rideables1[i].updateX(2);
			rideables2[i] = new Rideable(random.nextInt(80) + i * 165, 355);
			rideables2[i].updateX(-2);
			rideables3[i] = new Rideable(random.nextInt(80) + i * 165, 117);
			rideables3[i].updateX(-2);
			rideables4[i] = new Rideable(random.nextInt(80) + i * 165, 67);
			rideables4[i].updateX(2);
		}

		JFrame f = new JFrame("Blooner");
		f.setSize(new Dimension(width, height));
		f.setBackground(Color.white);
		f.add(this);
		f.setResizable(false);
 		f.addMouseListener(this);
		f.addKeyListener(this);
			
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("FroggerStyleProject/dart.png").getImage().getScaledInstance(10, 22, Image.SCALE_DEFAULT),
				new Point(0,0),"custom cursor"));	
		
		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public float getAngle(int x, int y) {
		Point screenMouse = MouseInfo.getPointerInfo().getLocation();
		Point frameLocation = this.getLocationOnScreen();
	
		// Calculate relative mouse position within the JFrame
		int mouseX = screenMouse.x - frameLocation.x;
		int mouseY = screenMouse.y - frameLocation.y;
	
		float angle = (float) Math.toDegrees(Math.atan2(mouseY - y, mouseX - x));
	
		if (angle < 0) {
			angle += 360;
		}
		return angle - 90;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		
	
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// 38 = up, 40 = down, 37 = left, 39 = right
		int keyCode = arg0.getKeyCode();
		if(simpleMovement){
			if(keyCode == 38) dart.updateY(-3);
			else if(keyCode == 40) dart.updateY(3);
			if(keyCode == 37) dart.updateX(-3);
			else if(keyCode == 39) dart.updateX(3);
		}
		else{
			double rotationRadians = Math.toRadians(dart.rotationAngle + 90); // +90 to align with the rotation method
			int speed = 3;

			if(keyCode == 38) { // Up
				dart.updateX((int)(speed * Math.cos(rotationRadians)));
				dart.updateY((int)(speed * Math.sin(rotationRadians)));
			}
			else if(keyCode == 40) { // Down
				dart.updateX((int)(-speed * Math.cos(rotationRadians)));
				dart.updateY((int)(-speed * Math.sin(rotationRadians)));
			}
			if(keyCode == 37) { // Left
				dart.updateX(-(int)(speed * Math.cos(rotationRadians + Math.PI/2)));
				dart.updateY(-(int)(speed * Math.sin(rotationRadians + Math.PI/2)));
			}
			else if(keyCode == 39) { // Right
				dart.updateX(-(int)(speed * Math.cos(rotationRadians - Math.PI/2)));
				dart.updateY(-(int)(speed * Math.sin(rotationRadians - Math.PI/2)));
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int keyCode = arg0.getKeyCode();
		if(simpleMovement){
			if(keyCode == 37) dart.updateX(0);
			if(keyCode == 39) dart.updateX(0);
			if(keyCode == 38) dart.updateY(0);
			if(keyCode == 40) dart.updateY(0);
		}
		else{
			if(keyCode == 38 || keyCode == 40) {
				dart.updateY(0);
				dart.updateX(0);
			}
			else if(keyCode == 37 || keyCode == 39) {
				dart.updateX(0);
				dart.updateY(0);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
