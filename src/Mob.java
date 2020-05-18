import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Mob extends Enemy {
	private int imageIndex;
	public Mob() {
		super(15, 65, 1.8, Block.WIDTH, Block.WIDTH,15);
	}
	public static final BufferedImage[] SRC = new BufferedImage[9];
	@Override
	public void render(Graphics2D ctx) {
		setImg(SRC[imageIndex]);
		ctx.drawImage(SRC[imageIndex],(int)getX(),(int)getY(),getWidth(),getHeight(),null);
		if(getImageDilay()==0) {
			imageIndex++;
			if(imageIndex==8)imageIndex=0;
			setImageDilay(2);
		}
		else setImageDilay(getImageDilay()-1);
	}
	static{
		for(int i=0;i!=SRC.length;i++) {
			try {
				SRC[i] = ImageIO.read(new File("img/mob$"+(i+1)+".png"));
			}
			catch(IOException e) {}
		}
	}
}
