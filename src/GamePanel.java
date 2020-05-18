import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JPanel;

import my.json.JSONArr;

public class GamePanel extends JPanel implements Runnable, MouseListener {
    public static final int MAX_LEVEL = 4;
    private static int life = 150;
    private int redeye_img = 0;
    private int knight_img = 0;
    private static int ZOOM = 1;
    private int amount = 0;
    private int wait = 0;
    private int inLevelInd;
    private Panel activePanel;
    private int redeye_ind = 0;
    private int knight_ind = 0;
    public static ArrayList<Animation> anim;
    private int frames;
    public static ArrayList<Tower> towers;
    public static ArrayList<Enemy> enemies;
    public static ArrayList<Shoot> shoots;
    private volatile Tower temptower;
    public static Block[][] blockarr;
    private static long bal = 600;
    public static BufferedImage img = new BufferedImage(Block.WIDTH * 30, Block.WIDTH * 20, BufferedImage.TYPE_INT_ARGB);
    private Graphics2D ctx;
    private Thread loop;
    private boolean renderEnd;
    private int imp_ind = 0;
    private int imp_img = 0;
    private int blob_img = 0;
    private int blob_ind = 0;
    private boolean inlevel;
    private int level = 0;
    private boolean levelEnd;
    private int ellie_ind;
    private int ellie_img;
    private final int startWidth;
    private final int startHeight;

    public GamePanel() {
        setPreferredSize(new Dimension(30 * Block.WIDTH, 20 * Block.WIDTH));
        setSize(30 * Block.WIDTH, 20 * Block.WIDTH);
        setLayout(null);
        setFocusable(true);
        setBackground(Color.BLACK);
        requestFocus();
        setVisible(true);
        addMouseListener(this);
        ctx = img.createGraphics();
        anim = new ArrayList<>();
        towers = new ArrayList<>();
        enemies = new ArrayList<>();
        shoots = new ArrayList<>();
        blockarr = new Block[30][20];
        for (int i = 0; i != 30; i++) {
            for (int j = 0; j != 20; j++) {
                blockarr[i][j] = new Block(i * Block.WIDTH, j * Block.WIDTH, Block.WIDTH, Block.WIDTH, Type.GRASS);
            }
        }
        for (int i = 0; i != 8; i++) blockarr[i][5].setType(Type.ROAD);
        for (int i = 5; i != 13; i++) blockarr[8][i].setType(Type.ROAD);
        for (int i = 8; i != 13; i++) blockarr[i][12].setType(Type.ROAD);
        for (int i = 12; i != 18; i++) blockarr[12][i].setType(Type.ROAD);
        for (int i = 12; i != 15; i++) blockarr[i][17].setType(Type.ROAD);
        for (int i = 17; i != 12; i--) blockarr[14][i].setType(Type.ROAD);
        for (int i = 14; i != 25; i++) blockarr[i][13].setType(Type.ROAD);
        for (int i = 13; i != 3; i--) blockarr[24][i].setType(Type.ROAD);
        for (int i = 25; i != 30; i++) blockarr[i][4].setType(Type.ROAD);
        startWidth = getWidth();
        startHeight = getHeight();
        loop = new Thread(this);
        loop.start();
    }

    public void render() {
        renderEnd = false;
        ctx.clearRect(0, 0, img.getWidth(), img.getHeight());
        for (int i = 0; i != 30; i++) {
            for (int j = 0; j != 20; j++) {
                blockarr[i][j].render(ctx);
            }
        }
        for (int i = 0; i != enemies.size(); i++) {
            enemies.get(i).render(ctx);
            enemies.get(i).drawLife(ctx);
        }
        for (int i = 0; i != towers.size(); i++) {
            towers.get(i).render(ctx);
        }
        for (int i = 0; i != shoots.size(); i++) {
            shoots.get(i).render(ctx);
        }
        for (int i = 0; i != anim.size(); i++) {
            if (anim.get(i).render(ctx)) {
                anim.remove(i);
                i--;
            }
        }
        ctx.setColor(new Color(0.9f, 0.9f, 0.9f, 0.7f));
        ctx.fillRect(360, 60, 100, 100);
        ctx.drawImage(RedEye.SRC[redeye_ind], 360, 60, 100, 100, null);
        if (redeye_img == 0) {
            redeye_ind++;
            if (redeye_ind == RedEye.SRC.length) redeye_ind = 0;
            redeye_img = 2;
        } else redeye_img--;
        ctx.fillRect(520, 60, 100, 100);
        ctx.drawImage(Knight.SRC[knight_ind], 520, 60, 100, 100, null);
        if (knight_img == 0) {
            knight_ind++;
            if (knight_ind == Knight.SRC.length) knight_ind = 0;
            knight_img = 2;
        } else knight_img--;
        ctx.fillRect(680, 60, 100, 100);
        ctx.drawImage(Imp.SRC[imp_ind], 680, 60, 100, 100, null);
        if (imp_img == 0) {
            imp_ind++;
            if (imp_ind == Imp.SRC.length) imp_ind = 0;
            imp_img = 2;
        } else imp_img--;
        ctx.fillRect(840, 60, 100, 100);
        ctx.drawImage(Blob.SRCT[blob_ind], 840, 60, 100, 100, null);
        if (blob_img == 0) {
            blob_ind++;
            if (blob_ind == Blob.SRCT.length) blob_ind = 0;
            blob_img = 2;
        } else blob_img--;
        ctx.fillRect(1000, 60, 100, 100);
        ctx.drawImage(Ellie.SRCT[ellie_ind], 1000, 60, 100, 100, null);
        if (ellie_img == 0) {
            ellie_ind++;
            if (ellie_ind == Ellie.SRCT.length) ellie_ind = 0;
            ellie_img = 2;
        } else ellie_img--;
        if (activePanel != null) {
            try {
                activePanel.render(ctx);
                ctx.setColor(new Color(1f, 1f, 1f, 0.3f));
                ctx.fillOval(activePanel.getTower().getCenterX() - activePanel.getTower().getRange(), activePanel.getTower().getCenterY() - activePanel.getTower().getRange(), activePanel.getTower().getRange() * 2, activePanel.getTower().getRange() * 2);
            } catch (NullPointerException e) {
                activePanel = null;
            }
        }
        ctx.setColor(Color.WHITE);
        ctx.setFont(new Font("baloonist", 1, 40));
        ctx.drawString(inlevel ? "❚❚" : "►", 40, img.getHeight() - 80);
        ctx.setColor(Color.black);
        ctx.drawString("50$", 370, 50);
        ctx.drawString("100$", 530, 50);
        ctx.drawString("100$", 530, 50);
        ctx.drawString("200$", 690, 50);
        ctx.drawString("250$", 850, 50);
        ctx.drawString("3000$", 1010, 50);
        if (temptower != null) {
            try {
                ctx.setColor(new Color(isBlockLegal() && temptower.getPrice() <= bal ? 0.0f : 1.0f, isBlockLegal() && temptower.getPrice() <= bal ? 1.0f : 0.0f, 0.0f, 0.3f));
                ctx.fillOval((int) temptower.getBlock().getCenterX() - temptower.getRange(), (int) temptower.getBlock().getCenterY() - temptower.getRange(), 2 * temptower.getRange(), 2 * temptower.getRange());
                temptower.render(ctx);
            } catch (NullPointerException e) {
            }
        }
        ctx.setColor(new Color(1f, 1f, 1f, 0.8f));
        ctx.setFont(new Font("baloonist", 1, 35));
        ctx.drawString("Life: " + life, 30, 45);
        ctx.drawString("Balance: " + bal + "$", 30, 85);
        renderEnd = true;
        Graphics g = getGraphics();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
    }

    @Override
    public void run() {
        while (life > 0 && level < MAX_LEVEL) {
            while (!inlevel) {
                try {
                    render();
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            JSONArr levels = null;
            inLevelInd = 0;
            amount = 0;
            wait = 0;
            try {
                levels = new JSONArr(new Scanner(new File("levels/level" + (level + 1) + ".json")).useDelimiter("\\A").next());
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            System.out.println(levels);
            while (inlevel) {
                if (amount == 0 && wait == 0) {
                    inLevelInd++;
                    if (levels.size() != inLevelInd) {
                        if (levels.getObj(inLevelInd).get("type") == null) {
                            wait = levels.getObj(inLevelInd).getInt("wait");
                        } else {
                            wait = levels.getObj(inLevelInd).getInt("wait");
                            amount = levels.getObj(inLevelInd).getInt("amount");
                        }
                    }
                }
                if (wait == 0 && inLevelInd != levels.size()) {
                    if (levels.getObj(inLevelInd).get("type").toString().equals(levels.getObj(inLevelInd).get("type"))) {
                        if (levels.getObj(inLevelInd).getString("type").equals("goblin")) enemies.add(new Goblin());
                        if (levels.getObj(inLevelInd).getString("type").equals("axe")) enemies.add(new Axe());
                        if (levels.getObj(inLevelInd).getString("type").equals("mob")) enemies.add(new Mob());
                        if (levels.getObj(inLevelInd).getString("type").equals("rouge")) enemies.add(new Rouge());
                        if (levels.getObj(inLevelInd).getString("type").equals("wizard")) enemies.add(new Wizard());
                    } else if (levels.getObj(inLevelInd).get("type") != null) {
                        for (int i = 0; i != levels.getObj(inLevelInd).getArr("type").size(); i++) {
                            if (levels.getObj(inLevelInd).getArr("type").getString(i).equals("goblin"))
                                enemies.add(new Goblin());
                            if (levels.getObj(inLevelInd).getArr("type").getString(i).equals("axe"))
                                enemies.add(new Axe());
                            if (levels.getObj(inLevelInd).getArr("type").getString(i).equals("mob"))
                                enemies.add(new Mob());
                            if (levels.getObj(inLevelInd).getArr("type").getString(i).equals("rouge"))
                                enemies.add(new Rouge());
                            if (levels.getObj(inLevelInd).getArr("type").getString(i).equals("wizard"))
                                enemies.add(new Wizard());
                        }
                    }
                    amount--;
                    wait = levels.getObj(inLevelInd).getInt("wait");
                }
                wait--;
                try {
                    for (int i = 0; i < enemies.size(); i++) {
                        if (enemies.get(i).move()) {
                            die(enemies.get(i).getDamage());
                            enemies.remove(i);
                            i--;
                        }
                    }
                } catch (NullPointerException e) {
                }
                for (int i = 0; i != towers.size(); i++) {
                    towers.get(i).shoot(enemies, shoots);
                }
                for (int i = 0; i < shoots.size(); i++) {
                    if (shoots.get(i).move()) {
                        i--;
                        continue;
                    }
                    for (int j = 0; j < enemies.size(); j++) {
                        if (shoots.get(i).intersects(
                                enemies.get(j))) {
                            if (shoots.get(i).attack(enemies.get(j))) {
                                shoots.remove(i);
                                i--;
                                break;
                            }
                        }
                    }
                }
                if (inLevelInd == levels.size() && enemies.isEmpty() || life <= 0) {
                    shoots.clear();
                    inlevel = false;
                }
                render();
                frames++;
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            bal += levels.getInt(0);
            level++;
        }
        Graphics g = getGraphics();
        g.setFont(new Font("baloonist", 1, 120));
        g.setColor(Color.white);
        g.drawString("You " + (level == 3 && enemies.isEmpty() ? "Won" : "Lost"), 200, 200);
        g.dispose();
    }

    public static Block getBlock(int x, int y) {
        for (Block[] ba : blockarr) {
            for (Block b : ba) {
                if (new Rectangle(x, y, 1, 1).intersects(b)) return b;
            }
        }
        return null;
    }

    public static int getZOOM() {
        return ZOOM;
    }

    public static void setZOOM(int zOOM) {
        ZOOM = zOOM;
    }

    public boolean isBlockLegal() {
        try {
            return temptower != null && (temptower.getBlock().getType() == Type.GRASS && temptower.getBlock().getTower() == null);
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static long getBal() {
        return bal;
    }

    public static void addBal(long b) {
        bal += b;
    }

    private MouseMotionListener mll = new MouseMotionListener() {
        @Override
        public void mouseDragged(MouseEvent e) {
            try {
                if (temptower != null)
                    temptower.setBlock(getBlock((int) ((double) e.getX() / ((double) getWidth() / (double) startWidth)), (int) ((double) e.getY() / ((double) getHeight() / (double) startHeight))), false);
            } catch (NullPointerException | IndexOutOfBoundsException ex) {
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }
    };

    @Override
    public void mouseClicked(MouseEvent e) {
        Rectangle r = new Rectangle(e.getX() * startWidth / getWidth(), e.getY() * startHeight / getHeight(), 3, 3);
        if (activePanel != null) {
            System.out.println("X:" + r.getX());
            System.out.println("Y:" + r.getY());
            if (r.intersects(new Rectangle((startWidth - 20), 0, 20, 20)))
                activePanel = null;
            if (r.intersects(new Rectangle((startWidth - 220), 215, 160, 60)) && activePanel.getTower().getUpgrade() != activePanel.getTower().max_upgrade && bal >= activePanel.getTower().upgrade_price[activePanel.getTower().getUpgrade()])
                activePanel.getTower().upgrade();
            if (r.intersects(new Rectangle((startWidth - 220), (startHeight - 180), 160, 60))) {
                GamePanel.addBal(activePanel.getTower().getPrice() / 10 * 9);
                activePanel.getTower().getBlock().setTower(null);
                towers.remove(activePanel.getTower());
                activePanel = null;
            }
        }
        if (r.intersects(new Rectangle(10, (startHeight - 110), 80, 80)) && !inlevel)
            inlevel = true;
        System.out.println("x:" + e.getX() + " ,y:" + e.getY());
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Rectangle r = new Rectangle(e.getX() * startWidth / getWidth(), e.getY() * startHeight / getHeight(), 1, 1);
        if (r.intersects(new Rectangle(360, 60, 100, 100))) {
            temptower = new RedEye(blockarr[8][2]);
            addMouseMotionListener(mll);
        } else if (r.intersects(new Rectangle(520, 60, 100, 100))) {
            temptower = new Knight(blockarr[11][2]);
            addMouseMotionListener(mll);
        } else if (r.intersects(new Rectangle(680, 60, 100, 100))) {
            temptower = new Imp(blockarr[14][2]);
            addMouseMotionListener(mll);
        } else if (r.intersects(new Rectangle(840, 60, 100, 100))) {
            temptower = new Blob(blockarr[17][2]);
            addMouseMotionListener(mll);
        } else if (r.intersects(new Rectangle(1000, 60, 100, 100))) {
            temptower = new Ellie(blockarr[20][2]);
            addMouseMotionListener(mll);
        } else {
            boolean sel = false;
            for (Block[] ba : blockarr) {
                for (Block b : ba) {

                    if (b.intersects(r)) {
                        System.out.println(b.x + ", " + b.y + ", " + b.width + ", " + b.height);
                        System.out.println(r);
                        System.out.println(b.getTower());
                        System.out.println(towers.get(0).getBlock().toString());
                        if (b.getTower() != null) {
                            activePanel = b.getTower().getPanel();
                            sel = true;
                        }
                    }
                }
            }
            if (r.intersects(new Rectangle((startWidth - 260), 0, 260, getHeight())))
                sel = true;
            if (!sel) {
                activePanel = null;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isBlockLegal() && bal >= temptower.getPrice()) {
            addBal(-temptower.getPrice());
            temptower.setBlock(temptower.getBlock(), true);
            temptower.setPanel(new Panel(temptower, img, this));
            towers.add(temptower);
        }
        while (!renderEnd) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {
            }
        }
        temptower = null;
    }

    public static int getLife() {
        return life;
    }

    public static void die(int l) {
        life -= l;
    }
}
