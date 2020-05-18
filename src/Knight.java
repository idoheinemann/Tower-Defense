import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Knight extends Tower {
	private int imageIndex = 0;
	private int bombcooldown = 0;
	private int cutcooldown = 0;
	public static final BufferedImage[] SRC;
	public Knight(Block b) {
		this(b,null);
	}
	public Knight(Block b,Panel p) {
		super(b,100,p,3,25,15);
		upgrade_price = new int[max_upgrade];
		upgrade_price[0] = 40;
		upgrade_price[1] = 100;
		upgrade_price[2] = 250;
		setRange(200);
	}
	@Override
	public void upgrade() {
		addUpgrade();
		if(getUpgrade()==1)setRange(getRange()+30);
		if(getUpgrade()==2)setDamage(19);
	}
	@Override
	public void render(Graphics2D ctx) {
		setImg(SRC[imageIndex]);
		ctx.drawImage(SRC[imageIndex],getX(),getY(),getWidth(),getHeight(),null);
		if(getImageDilay()==0) {
			imageIndex++;
			if(imageIndex==9)imageIndex=0;
			setImageDilay(2);
		}
		else setImageDilay(getImageDilay()-1);
	}
	static{
		SRC = new BufferedImage[9];
		for(int i=0;i!=SRC.length;i++) {
			try {
				SRC[i] = ImageIO.read(new File("img/knight$"+(2+i)+".png"));
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void shoot(ArrayList<Enemy> e,ArrayList<Shoot> s) {
		try {
			if(getCooldown()<=0) {
				boolean shoot = false;
				for(int i=0;i!=e.size();i++) {
					if(Math.pow(e.get(i).getCenterX()-getCenterX(),2)+Math.pow(e.get(i).getCenterY()-getCenterY(),2)<Math.pow(getRange(), 2)) {
						shoot = true;
						double dx = (double)getCenterX()-e.get(i).getCenterX();
						double dy = (double)getCenterY()-e.get(i).getCenterY();
						s.add(new Bullet(Math.PI+(e.get(i).getCenterY()<getCenterY()?Math.acos(dx/(Math.sqrt((dx*dx)+(dy*dy)))):-Math.acos(dx/(Math.sqrt((dx*dx)+(dy*dy))))),getX(),getY(),30,getDamage()));
						break;
					}
				}
				if(shoot)setCooldown(getCooldownf());
			}
			else setCooldown(getCooldown()-1);
			if(cutcooldown==0) {
				boolean shoot = false;
				for(int i=0;i!=e.size();i++) {
					if(Math.pow(e.get(i).getCenterX()-getCenterX(),2)+Math.pow(e.get(i).getCenterY()-getCenterY(),2)<10000) {
						boolean g = false;
						if(e.get(i).getLife()<=getDamage())g=true;
						e.get(i).die(1);
						shoot = true;
						if(g)i--;
					}
				}
				if(shoot)cutcooldown=5;
			}
			if(getUpgrade()==3&&bombcooldown<=0) {
				double[] arr = new double[0];
				for(int i=getCenterX()-getRange();i<=getCenterX()+getRange();i+=Block.WIDTH) {
					for(int j=getCenterY()-getRange();j<=getCenterY()+getRange();j+=Block.WIDTH) {
						if(GamePanel.getBlock(i, j)==null)continue;
						if(GamePanel.getBlock(i, j).getType()==Type.ROAD) {
							double[] temp = arr;
							arr = new double[temp.length+1];
							for (int k = 0; k < temp.length; k++) {
								arr[k] = temp[k];
							}
							Block b = GamePanel.getBlock(i, j);
							int dx = (int) (getCenterX()-b.getCenterX());
							int dy = (int) (getCenterY()-b.getCenterY());
							arr[arr.length-1] = Math.PI+(b.getCenterY()<getCenterY()?Math.acos(dx/(Math.sqrt((dx*dx)+(dy*dy)))):-Math.acos(dx/(Math.sqrt((dx*dx)+(dy*dy)))));
						}
					}
				}
				s.add(new Bomb(arr[(int) (Math.random()*arr.length)], getCenterX(), getCenterY(), 30, 10, 300));
				bombcooldown = 600;
			}
			else if(getUpgrade()==3)bombcooldown--;
		}catch(NullPointerException ex) {}
	}
}
