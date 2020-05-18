import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class Shoot {
	private double speed;
	private double x;
	private double y;
	private int height;
	private int damage;
	private int width;
	private double angle;
	public abstract boolean attack(Enemy e);
	public void attack(Enemy[] e) {
		for(Enemy n:e) {
			attack(n);
		}
	}
	public boolean move() {
		x += Math.cos(angle)*speed;
		y += Math.sin(angle)*speed;
		if(x+width < 0|| x >GamePanel.img.getWidth()||y+height < 0|| y>GamePanel.img.getHeight()) {
			GamePanel.shoots.remove(this);
			return true;
		}
		return false;
	}
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	public abstract void render(Graphics2D ctx);
	public Shoot(double ang, double s, int nx, int ny, int w, int h, int d) {
		angle = ang;
		speed = s;
		x = nx;
		y = ny;
		width = w;
		height = h;
		damage = d;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public void setWidth(int t) {
		width = t;
	}
	public void setHeight(int t) {
		height = t;
	}
	public boolean intersects(Enemy e) {
		return e!=null?new Rectangle((int)x,(int)y,width,height).intersects(new Rectangle((int)e.getX(),(int)e.getY(),e.getWidth(),e.getHeight())):false;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public double getCenterX() {
		return width/2+x;
	}
	public double getCenterY() {
		return height/2+y;
	}
}
