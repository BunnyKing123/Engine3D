package io.github.bunnyking123.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame {
	
	JFrame frame;
	JPanel panel;
	Timer time;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Frame frame = new Frame();
		frame.setup();
	}
	
	public void setup() {
		frame = new JFrame("3d Engine");
		panel = new Panel3D();
		time = new Timer(10, new TimerListener());
		time.start();
		frame.setSize(800, 800);
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	class TimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			panel.repaint();
		}
		
	}

}
