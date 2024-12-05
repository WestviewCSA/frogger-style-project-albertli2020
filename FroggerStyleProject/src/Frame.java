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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	public static boolean debugging = true;
	public static boolean simpleMovement = true;
	//Timer related variables
	BufferedImage background;
	long ellapseTime = 0;
	Font timeFont = new Font("Courier", Font.BOLD, 70);
	Font myFont = new Font("Courier", Font.BOLD, 40);
	
	Monkey dart = new Monkey(275, 570);
	Rideable ride1 = new Rideable(400, 400);
	int width = 610;
	int height = 630;	
	

	public void paint(Graphics g) {
		super.paintComponent(g);

		if (background != null) {
            g.drawImage(background, 0, 0, 610, 630, null);
        }
		ride1.paint(g);

		dart.rotate(getAngle(dart.x, dart.y));
		dart.paint(g);
		System.out.println(MouseInfo.getPointerInfo().getLocation());
		System.out.println(dart.x + "," + dart.y);

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
		if(simpleMovement){
			int keyCode = arg0.getKeyCode();
			if(keyCode == 38) dart.updateY(-5);
			else if(keyCode == 40) dart.updateY(5);
			else if(keyCode == 37) dart.updateX(-5);
			else if(keyCode == 39) dart.updateX(5);
		}
		else{
			int keyCode = arg0.getKeyCode();
			double rotationRadians = Math.toRadians(dart.rotationAngle + 90); // +90 to align with the rotation method
			int speed = 5;

			if(keyCode == 38) { // Up
				dart.updateX((int)(speed * Math.cos(rotationRadians)));
				dart.updateY((int)(speed * Math.sin(rotationRadians)));
			}
			else if(keyCode == 40) { // Down
				dart.updateX((int)(-speed * Math.cos(rotationRadians)));
				dart.updateY((int)(-speed * Math.sin(rotationRadians)));
			}
			else if(keyCode == 37) { // Left
				dart.updateX((int)(speed * Math.cos(rotationRadians + Math.PI/2)));
				dart.updateY((int)(speed * Math.sin(rotationRadians + Math.PI/2)));
			}
			else if(keyCode == 39) { // Right
				dart.updateX((int)(speed * Math.cos(rotationRadians - Math.PI/2)));
				dart.updateY((int)(speed * Math.sin(rotationRadians - Math.PI/2)));
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		int keyCode = arg0.getKeyCode();
		if(keyCode == 38 || keyCode == 40) {
			dart.updateY(0);
			dart.updateX(0);
		}
		else if(keyCode == 37 || keyCode == 39) {
			dart.updateX(0);
			dart.updateY(0);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
