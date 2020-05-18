import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Tower{
	private int range;
	private int damage;
	private Panel panel;
	private BufferedImage img;
	private Block block;
	private int imageDilay;
	private int upgrade = 0;
	private int price;
	private int cooldown = 0;
	private int cooldownf;
	public final int max_upgrade;
	public int[] upgrade_price;
	public abstract void upgrade();
	public abstract void shoot(ArrayList<Enemy> e, ArrayList<Shoot> s);
	public int getUpgrade() {
		return upgrade;
	}
	public void addUpgrade() {
		GamePanel.addBal(-upgrade_price[upgrade]);
		price += upgrade_price[upgrade];
		upgrade++;
		
	}
	public abstract void render(Graphics2D ctx);
	public Tower(Block b, int p, Panel n, int m, int c, int d) {
		setBlock(b,true);
		imageDilay = 0;
		price = p;
		panel = n;
		max_upgrade = m;
		damage = d;
		setCooldownf(c);
	}
	public int getX() {
		return (int) block.getX();
	}
	public int getY() {
		return (int) block.getY();
	}
	public int getWidth() {
		return (int) block.getWidth();
	}
	public int getHeight() {
		return (int) block.getHeight();
	}
	public Block getBlock() {
		return block;
	}
	public void setBlock(Block block,boolean b) {
		this.block = block;
		if(b)block.setTower(this);
	}
	public int getImageDilay() {
		return imageDilay;
	}
	public void setImageDilay(int imageDilay) {
		this.imageDilay = imageDilay;
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public int getPrice() {
		return price;
	}
	public Panel getPanel() {
		return panel;
	}
	public void setPanel(Panel p) {
		panel = p;
	}
	public BufferedImage getImg() {
		return img;
	}
	public void setImg(BufferedImage img) {
		this.img = img;
	}
	public int getCenterX() {
		return (int) block.getCenterX();
	}
	public int getCenterY() {
		return (int) block.getCenterY();
	}
	public int getCooldown() {
		return cooldown;
	}
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	public int getCooldownf() {
		return cooldownf;
	}
	public void setCooldownf(int cooldownf) {
		this.cooldownf = cooldownf;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
}