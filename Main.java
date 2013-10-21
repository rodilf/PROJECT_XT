import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Main {

	public static int MAIN_FRAME_X = 512;
	public static int MAIN_FRAME_Y = 512;

	static JFrame frame = new JFrame();
    static Core core = new Core();
    static Background bg = new Background();

	public static void main(String[] args) {

	    frame.addComponentListener(new ComponentAdapter() {
	        public void componentResized(ComponentEvent e) {
	            bg.setSize(frame.getSize());
	            core.setSize(frame.getSize());
	            core.update();
	        }
	    });

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final JLayeredPane layeredPane = new JLayeredPane();

				frame.setSize(MAIN_FRAME_X,MAIN_FRAME_Y);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setContentPane(layeredPane);

				layeredPane.add(bg);
				layeredPane.add(core, new Integer(1));

				bg.setSize(frame.getSize());
				core.setSize(frame.getSize());
				bg.setVisible(true);
				core.setVisible(true);
				frame.setVisible(true);
				core.setOpaque(false);

				int delay = 1000/60;

				ActionListener taskPerformer = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						core.update();
						layeredPane.repaint();
					}
		 		};
		 		new Timer(delay, taskPerformer).start();
			}
		});
	}
}