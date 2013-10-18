import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JScrollPane;

import java.awt.Graphics;
import java.awt.Color;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main {

		public static int MAIN_FRAME_X = 512;
		public static int MAIN_FRAME_Y = 512;

		int MAIN_WORLD_X = 0;
		int MAIN_WORLD_Y = 0;
	static JFrame frame = new JFrame();

	public static void main(String[] args) {

	 //   final JFrame frame = new JFrame();
		final Core core = new Core();
		final Background bg = new Background();

		frame.setSize(MAIN_FRAME_X,MAIN_FRAME_Y);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(bg);
		frame.setGlassPane(core);
		bg.setVisible(true);
		core.setVisible(true);
		frame.setVisible(true);
		core.setOpaque(false);

		int delay = 1000/60; //milliseconds
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				core.update();
				frame.repaint();
			}
		 };
		new Timer(delay, taskPerformer).start();

		/*frame.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					System.out.println("update");
				}
			}
			public void keyReleased(KeyEvent e) {
			}
			public void keyTyped(KeyEvent e) {
			}
		});*/
	}
}
//http://www.youtube.com/watch?v=sUjSluboyY4
//http://www.macs.hw.ac.uk/guidebook/?name=JButton&page=2