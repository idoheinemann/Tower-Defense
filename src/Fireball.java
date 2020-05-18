import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Fireball extends Shoot {
	public static BufferedImage SRC;
	public Fireball(double ang, double s, int x, int y, int size, int d) {
		super(ang, s, x, y, size, size, d);
	}

	@Override
	public boolean attack(Enemy e) {
		e.die(getDamage());
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
			SRC = ImageIO.read(new File("img/fireball.png"));
		}
		catch(IOException e) {}
	}
}
