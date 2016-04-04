package net.apryx.logger;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LogGui implements LogStream{

	private JTextArea log;
	
	public LogGui(){
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		log = new JTextArea();
		log.setEditable(false);
		log.setBackground(Color.BLACK);
		log.setForeground(Color.WHITE);
		log.setFont(new Font("Monospaced", Font.PLAIN, 13));
		
		frame.getContentPane().add(new JScrollPane(log));
		frame.setSize(800,400);
		frame.setVisible(true);
	}
	
	
	@Override
	public void println(String line) {
		log.append(line + "\n");
		log.setCaretPosition(log.getText().length());
	}
	
}
