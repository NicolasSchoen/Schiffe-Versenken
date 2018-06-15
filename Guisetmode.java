import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Guisetmode extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Guisetmode frame = new Guisetmode();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public Guisetmode() {
		/*setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);*/
		
		this.setTitle("Schiffe versenken-Modus auswaehlen");
		this.setSize(1000, 620);
		this.setResizable(false);
		this.setLocation(50, 50);
		getContentPane().setLayout(null);
		
		JButton btnPvp = new JButton("PvP");
		btnPvp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {					//pvp-button
				int wert = Integer.parseInt(textField.getText());
				
				if(wert >= 5 && wert <= 30 )
				{
					dispose();
					new Guiserver(wert, 1);
					//setship.setVisible(true);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Bitte Zahl zwischen 5 und 30 eingeben!");
				}
				
				
			}
		});
		btnPvp.setBounds(135, 138, 181, 45);
		getContentPane().add(btnPvp);
		
		JButton btnPvc = new JButton("PvC");
		btnPvc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {					//pvc-button
int wert = Integer.parseInt(textField.getText());
				
				if(wert >= 5 && wert <= 30)
				{
					dispose();
					//Guiplace setship = new Guiplace(wert);
					new Guiplace(wert, 2);
					//setship.setVisible(true);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Bitte Zahl zwischen 5 und 30 eingeben!");
				}
			}
		});
		btnPvc.setBounds(135, 209, 181, 43);
		getContentPane().add(btnPvc);
		
		JButton btnCvc = new JButton("CvC");
		btnCvc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			//Button cvc
				
				int wert = Integer.parseInt(textField.getText());
				
				if(wert >= 5 && wert <= 30 )
				{
					dispose();
					new Guiserver(wert, 3);
					//setship.setVisible(true);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Bitte Zahl zwischen 5 und 30 eingeben!");
				}
			}
		});
		btnCvc.setBounds(135, 273, 181, 43);
		getContentPane().add(btnCvc);
		
		JLabel lblFeldgroesse = new JLabel("Feldgroesse:");
		lblFeldgroesse.setBounds(57, 369, 85, 16);
		getContentPane().add(lblFeldgroesse);
		
		textField = new JTextField();
		textField.setText("10");
		textField.setBounds(135, 366, 116, 22);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("(5-30)");
		label.setBounds(260, 369, 56, 16);
		getContentPane().add(label);
		this.setVisible(true);
	}
}
