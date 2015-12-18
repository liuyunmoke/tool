package com.pipipark.j.windows;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.pipipark.j.system.core.PPPConstant;
import com.pipipark.j.system.core.PPPLogger;

import java.awt.Toolkit;

public class PPPFrame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void show() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PPPFrame window = new PPPFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PPPFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		PPPLogger.debug(PPPFrame.class,"启动面板");
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(PPPConstant.Systems.DEFAULT_RESOURCE_PATH+"windows/icon.jpg")));
		frame.setTitle("应用程序");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
