import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bomb extends Shoot {
	public static BufferedImage SRC;
	private int range;
	private int countdown;
	public Bomb(double ang, int nx, int ny, int s, int d, int c) {
		super(ang, 1, nx, ny, s, s, d);
		range = 230;
		countdown = c;
	}

	@Override
	public boolean attack(Enemy e) {
		e.die(getDamage());
		Enemy[] en = new Enemy[0];
		for(int i=0;i!=GamePanel.enemies.size();i++) {
			if(Math.pow(GamePanel.enemies.get(i).getCenterX()-getCenterX(),2)+Math.pow(GamePanel.enemies.get(i).getCenterY()-getCenterY(),2)<range*range) {
				Enemy[] temp = en;
				en = new Enemy[temp.length+1];
				for(int k=0;k!=temp.length;k++) {
					en[k] = temp[k];
				}
				en[en.length-1] = GamePanel.enemies.get(i);
			}
		}
		for(Enemy n:en) {
			n.die(getDamage());
		}
		GamePanel.anim.add(new Explotion((int)getCenterX()-(range/2),(int)getCenterY()-(range/2),range));
		return true;
	}

	@Override
	public void render(Graphics2D ctx) {
		ctx.drawImage(SRC,(int)getX(),(int)getY(),getWidth(),getHeight(),null);
	}
	@Override
	public boolean move() {
		super.move();
		if(GamePanel.getBlock((int)getCenterX(),(int)getCenterY()).getType()==Type.ROAD&&((int)GamePanel.getBlock((int)getCenterX(),(int)getCenterY()).getCenterX()==(int)getCenterX()||(int)GamePanel.getBlock((int)getCenterX(),(int)getCenterY()).getCenterY()==(int)getCenterY())) {
			setX(getX()-(Math.cos(getAngle())*getSpeed()));
			setY(getY()-(Math.sin(getAngle())*getSpeed()));
		}
		if(countdown == 0) {
			Enemy[] e = new Enemy[0];
			for(int i=0;i!=GamePanel.enemies.size();i++) {
				if(Math.pow(GamePanel.enemies.get(i).getCenterX()-getCenterX(),2)+Math.pow(GamePanel.enemies.get(i).getCenterY()-getCenterY(),2)<range*range) {
					Enemy[] temp = e;
					e = new Enemy[temp.length+1];
					for(int k=0;k!=temp.length;k++) {
						e[k] = temp[k];
					}
					e[e.length-1] = GamePanel.enemies.get(i);
				}
			}
			attack(e);
			GamePanel.shoots.remove(this);
			GamePanel.anim.add(new Explotion((int)getCenterX()-(range/2),(int)getCenterY()-(range/2),range));
			return true;
		}
		countdown--;
		return false;
	}
	static{
		try{
			SRC = ImageIO.read(new File("img/bomb.png"));
		}
		catch(IOException e) {}
	}
}
