import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet extends Shoot {
	public static BufferedImage SRC;
	public Bullet(double ang, int nx, int ny, int s, int d) {
		super(ang, 10, nx, ny, s, (int)(0.35*(double)s), d);
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
			SRC = ImageIO.read(new File("img/bullet.png"));
		}
		catch(IOException e) {}
	}
}
