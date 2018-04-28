import java.awt.Component;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.JButton;

public class SwingTest {

	private static void start()
	{
		JFrame frame = new JFrame("swing1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//inhalt
		frame.setContentPane(Box.createVerticalBox());
		frame.add(Box.createGlue());
		
		JLabel label1 = new JLabel("label1");
		label1.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.add(label1);
		
		frame.add(Box.createVerticalStrut(50));
		
		JButton button= new JButton("button");
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.addActionListener(
				(e) -> {
					System.out.println("Clicked button" + e.getActionCommand());});
		frame.add(button);
		
		frame.add(Box.createVerticalStrut(50));
		
		//images
		/*Box hBox = Box.createHorizontalBox();
		Icon green = new ImageIcon("green.png");
		hBox.createGlue();
		JLabel greenLabel = new JLabel(green);
		hBox.add(greenLabel);
		
		frame.add(hBox);*/
		
		//menu
		JMenuBar bar = new JMenuBar();
		JMenu file = new JMenu("File");
		
		JMenuItem open = new JMenuItem("Open");
		
		
		open.addActionListener(
				(e) -> {
					System.out.println("Clicked file->open" + e.getActionCommand());});
		
		
		file.add(open);
		bar.add(file);
		frame.setJMenuBar(bar);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(
			    () -> { start(); }
			);
	}
	
}
