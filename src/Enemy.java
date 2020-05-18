import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public abstract class Enemy {
	private BufferedImage img;
	private Block lastblock;
	private Block currentblock;
	private double x = 0;
	private double y;
	private int height;
	private int width;
	private double speed;
	private int imageDilay;
	private int life;
	private int money;
	private int damage;
	private final int startlife;
	public abstract void render(Graphics2D ctx);
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	public void drawLife(Graphics2D ctx) {
		ctx.setColor(Color.WHITE);
		ctx.fillRect((int)x+1,(int)y-7, width-1, 5);
		ctx.setColor(Color.GREEN);
		ctx.fillRect((int)x+2,(int)y-6,(width-2)*life/startlife,4);
		ctx.setColor(Color.RED);
		ctx.fillRect((width-2)*life/startlife+(int)x+2,(int)y-6, width-2-((width-2)*life/startlife), 4);
	}
	public Enemy(int d,int l,double s, int w, int h, int m) {
		setDamage(d);
		life = startlife = l;
		speed = s;
		width = w;
		height = h;
		money = m;
		x = (int) GamePanel.blockarr[0][5].getX()+1;
		y = (int) GamePanel.blockarr[0][5].getY();
		lastblock = GamePanel.blockarr[0][5];
		currentblock = GamePanel.blockarr[1][5];
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double d) {
		this.speed = d;
	}
	public int getCenterX() {
		return (int)x+(width/2);
	}
	public int getCenterY() {
		return (int)y+(height/2);
	}
	public boolean move() {
		try{
			for(double d=speed;d>0;d--) {
				int[] dir = lastblock.getDir(currentblock);
				x += dir[0]*(d>1?1:d);
				y += dir[1]*(d>1?1:d);
				if(currentblock==null)currentblock = GamePanel.getBlock((int)x+Block.WIDTH,(int)y);
				if(GamePanel.getBlock(getCenterX()+(dir[0]*Block.WIDTH/2),getCenterY()+(dir[1]*Block.WIDTH/2)).getType()!=Type.ROAD) {
					Block temp = null;
					if(GamePanel.getBlock((int)currentblock.getCenterX()-Block.WIDTH,(int)currentblock.getCenterY()).getType()==Type.ROAD&&GamePanel.getBlock((int)currentblock.getCenterX()-Block.WIDTH,(int)currentblock.getCenterY())!=lastblock) {
						temp = GamePanel.getBlock((int)currentblock.getCenterX()-Block.WIDTH,(int)currentblock.getCenterY());
					}
					if(GamePanel.getBlock((int)currentblock.getCenterX()+Block.WIDTH,(int)currentblock.getCenterY()).getType()==Type.ROAD&&GamePanel.getBlock((int)currentblock.getCenterX()+Block.WIDTH,(int)currentblock.getCenterY())!=lastblock) {
						if(dir[1]!=-1)temp = GamePanel.getBlock((int)currentblock.getCenterX()+Block.WIDTH,(int)currentblock.getCenterY());
					}
					if(GamePanel.getBlock((int)currentblock.getCenterX(),(int)currentblock.getCenterY()-Block.WIDTH).getType()==Type.ROAD&&GamePanel.getBlock((int)currentblock.getCenterX(),(int)currentblock.getCenterY()-Block.WIDTH)!=lastblock) {
						temp = GamePanel.getBlock((int)currentblock.getCenterX(),(int)currentblock.getCenterY()-Block.WIDTH);
					}
					if(GamePanel.getBlock((int)currentblock.getCenterX(),(int)currentblock.getCenterY()+Block.WIDTH).getType()==Type.ROAD&&GamePanel.getBlock((int)currentblock.getCenterX(),(int)currentblock.getCenterY()+Block.WIDTH)!=lastblock) {
						temp = GamePanel.getBlock((int)currentblock.getCenterX(),(int)currentblock.getCenterY()+Block.WIDTH);
					}
					lastblock = currentblock;
					currentblock = temp;
				}
			}
				return false;
		}
		catch(NullPointerException e) {
			return true;
		}
	}
	public int getImageDilay() {
		return imageDilay;
	}
	public void setImageDilay(int imageDilay) {
		this.imageDilay = imageDilay;
	}
	public void setX(double nx) {
		x = nx;
	}
	public void setY(double ny) {
		y = ny;
	}
	public void die(int l) {
		life -= l;
		if(life<=0) {
			GamePanel.addBal(money);
			GamePanel.enemies.remove(this);
		}
	}
	public BufferedImage getImg() {
		return img;
	}
	public void setImg(BufferedImage img) {
		this.img = img;
	}
}
