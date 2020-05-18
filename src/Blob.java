import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Blob extends Tower {
	public static final BufferedImage[] SRCA,SRCT;
	public BufferedImage[] SRC;
	private int[] minitime = new int[0];
	private MiniBlob[] miniblobs = new MiniBlob[0];
	private int imageIndex = 0;
	private double stickyness = 0.95;
	public Blob(Block b) {
		this(b,null);
	}
	public Blob(Block b,Panel p) {
		super(b,250,p,4,150,15);
		upgrade_price = new int[max_upgrade];
		upgrade_price[0] = 120;
		upgrade_price[1] = 200;
		upgrade_price[2] = 400;
		upgrade_price[3] = 600;
		setRange(200);
		setCooldownf(50);
	}
	@Override
	public void upgrade() {
		addUpgrade();
		if(getUpgrade()==1) {setCooldownf(30);setRange(240);}
		if(getUpgrade()==2) {stickyness=0.85;setRange(290);}
		if(getUpgrade()==3) {
			minitime = new int[1];
			miniblobs = new MiniBlob[1];
			minitime[0] = 65;
		}
		if(getUpgrade()==4) {
			minitime = new int[3];
			miniblobs = new MiniBlob[3];
			minitime[0] = 65;
			minitime[1] = 185;
			minitime[2] = 255;
		}
	}

	@Override
	public void shoot(ArrayList<Enemy> e, ArrayList<Shoot> s) {
		if(getCooldown()==0) {
			boolean shoot = false;
			for(int i=0;i!=e.size();i++) {
				if(Math.pow(e.get(i).getCenterX()-getCenterX(),2)+Math.pow(e.get(i).getCenterY()-getCenterY(),2)<Math.pow(getRange(), 2)) {
					shoot = true;
					double dx = (double)getCenterX()-e.get(i).getCenterX();
					double dy = (double)getCenterY()-e.get(i).getCenterY();
					s.add(new Slimeball(Math.PI+(e.get(i).getCenterY()<getCenterY()?Math.acos(dx/(Math.sqrt((dx*dx)+(dy*dy)))):-Math.acos(dx/(Math.sqrt((dx*dx)+(dy*dy))))),getX(),getY(),30,stickyness ));
					break;
				}
			}
			if(shoot)setCooldown(getCooldownf());
		}
		else setCooldown(getCooldown()-1);
		if(getUpgrade()>2) {
			for(int i=0;i!=miniblobs.length;i++) {
				if(miniblobs[i]==null) {
					miniblobs[i] = new MiniBlob(getRandomBlock(this));
					miniblobs[i].setPanel(new Panel(miniblobs[i],GamePanel.img,GameMain.gp));
				}
				miniblobs[i].shoot(GamePanel.enemies, GamePanel.shoots);
				if(minitime[i]==0) {
					miniblobs[i].getBlock().setTower(null);
					miniblobs[i].setBlock(getRandomBlock(this),true);
					minitime[i] = 100;
				}
				else minitime[i]--;
			}
		}
	}
	public static Block getRandomBlock(Blob b) {
		Block t = GamePanel.getBlock((int)(b.getCenterX()+(Math.random()*b.getRange())-(b.getRange()/2)),(int)(b.getCenterY()+(Math.random()*b.getRange())-(b.getRange()/2)));
		return (t.getType()==Type.GRASS&&t.getTower()==null)?t:getRandomBlock(b);
	}
	@Override
	public void render(Graphics2D ctx) {
		boolean b = false;
		for(int i=0;i!=GamePanel.enemies.size();i++) {
			if(Math.pow(GamePanel.enemies.get(i).getCenterX()-getCenterX(),2)+Math.pow(GamePanel.enemies.get(i).getCenterY()-getCenterY(),2)<Math.pow(getRange(), 2)) {
				b = true;
			}
		}
		SRC = b?SRCA:SRCT;
		ctx.drawImage(SRC[imageIndex>=SRC.length?0:imageIndex],getX(),getY(),getWidth(),getHeight(),null);
		if(getImageDilay()==0) {
			imageIndex++;
			if(imageIndex>=SRC.length)imageIndex=0;
			setImg(SRC[imageIndex]);
			setImageDilay(2);
		}
		else setImageDilay(getImageDilay()-1);
		for(MiniBlob mb:miniblobs) {
			if(mb!=null)mb.render(ctx);
		}
	}
	static{
		SRCT = new BufferedImage[6];
		SRCA = new BufferedImage[8];
		try {
			for(int i=0;i!=6;i++) {
				SRCT[i] = ImageIO.read(new File("img/blob$"+(i+1)+".png"));
			}
			for(int i=0;i!=8;i++) {
				SRCA[i] = ImageIO.read(new File("img/bloba$"+(i+1)+".png"));
			}
		}
		catch(IOException e) {}
	}
}
