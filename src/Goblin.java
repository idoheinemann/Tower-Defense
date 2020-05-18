import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Goblin extends Enemy {
	private int imageIndex;

	public Goblin() {
		super(5, 28, 1, Block.WIDTH, Block.WIDTH,5);
	}
	public static final BufferedImage[] SRC;
	
	static{
		SRC = new BufferedImage[6];
		for(int i=0;i!=SRC.length;i++) {
			try {
				SRC[i] = ImageIO.read(new File("img/goblin$"+(i+1)+".png"));
			}
			catch(IOException e) {}
		}
	}
	@Override
	public void render(Graphics2D ctx) {
		setImg(SRC[imageIndex]);
		ctx.drawImage(SRC[imageIndex],(int)getX(),(int)getY(),getWidth(),getHeight(),null);
		if(getImageDilay()==0) {
			imageIndex++;
			if(imageIndex==6)imageIndex=0;
			setImageDilay(2);
		}
		else setImageDilay(getImageDilay()-1);
	}
	@Override
	public void die(int l) {
		if(getLife()<l) {
			GamePanel.anim.add(new GoblinDeath((int)getX(),(int)getY(),getWidth()));
		}
		super.die(l);
	}
}
