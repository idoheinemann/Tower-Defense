import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Slimeball extends Shoot {
	public static BufferedImage SRC;
	private double stick;
	public Slimeball(double ang, int nx, int ny, int s,double st) {
		super(ang, 4.2, nx, ny, s, s, 10);
		stick = st;
	}

	@Override
	public boolean attack(Enemy e) {
		e.die(getDamage());
		e.setSpeed(e.getSpeed()*stick);
		return true;
	}

	@Override
	public void render(Graphics2D ctx) {
		ctx.translate(getX()+getWidth()/2, getY()+getHeight()/2);
		ctx.rotate(Math.PI+getAngle());
		ctx.drawImage(SRC,-getWidth()/2,-getHeight()/2,getWidth(),getHeight(),null);
		ctx.rotate(-Math.PI-getAngle());
		ctx.translate(-(getX()+getWidth()/2), -(getY()+getHeight()/2));
	}
	static{
		try {
			SRC = ImageIO.read(new File("img/slimeball.png"));
		}
		catch(IOException e) {}
	}
}
