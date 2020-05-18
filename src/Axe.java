import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Axe extends Enemy {
	private int imageIndex;
	
	public Axe() {
		super(9, 40, 1.5, Block.WIDTH, Block.WIDTH,10);
	}
	public final static BufferedImage[] SRC;
	static{
		SRC = new BufferedImage[8];
		for(int i=0;i!=SRC.length;i++) {
			try {
				SRC[i] = ImageIO.read(new File("img/axe$"+(i+1)+".png"));
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
			if(imageIndex==8)imageIndex=0;
			setImageDilay(2);
		}
		else setImageDilay(getImageDilay()-1);
	}
}
