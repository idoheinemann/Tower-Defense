import java.awt.Graphics2D;
import java.util.ArrayList;

public class Agent extends Tower {
	public Agent(Block b, Panel n) {
		super(b, 500, n, 4, 90, 25);
		setRange(10);
	}
	public Agent(Block b) {
		this(b,null);
	}
	@Override
	public void upgrade() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shoot(ArrayList<Enemy> e, ArrayList<Shoot> s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics2D ctx) {
		// TODO Auto-generated method stub

	}

}
