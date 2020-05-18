import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;

import my.json.*;

public class GameMain extends JFrame {
	public static GamePanel gp;
	GameMain(String title){
        super(title);
        setContentPane(gp = new GamePanel());
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setPreferredSize(new Dimension(30*Block.WIDTH,20*Block.WIDTH));
        setSize(30*Block.WIDTH,20*Block.WIDTH);
        setLocationRelativeTo(null);
        setLayout(null);
    }
    public static void main(String[] args){
        GameMain gm = new GameMain("Tower Defence");
        gm.setVisible(true);
    }
}
