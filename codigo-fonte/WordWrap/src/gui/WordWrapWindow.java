package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;

public class WordWrapWindow extends JDialog 
{

	private static final long serialVersionUID = 436795320750049716L;
	private int width;
	private int algorithmIndex;    //0 - Greedy, 1 - Dynamic Programming.
	private JSpinner spinner;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public WordWrapWindow(MainWindow mainWindow, int initialWidth) 
	{
		width = initialWidth;
		algorithmIndex = 0;
		setTitle("Word Wrap");
		setResizable(false);
		setModal(true);
		setFont(new Font("Monospaced", Font.PLAIN, 12));
		setBounds(100, 100, 216, 140);
		
		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JLabel label = new JLabel("Width");
		label.setFont(new Font("Monospaced", Font.BOLD, 12));
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(width, 1, 81, 1));
		spinner.setFont(new Font("Monospaced", Font.PLAIN, 12));
		comboBox = new JComboBox();
		
		JButton button = new JButton("Ok");
		button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{	
				try {
					width = (int) spinner.getValue();
					algorithmIndex = comboBox.getSelectedIndex();
					mainWindow.resizeOutputPanel(width);
					setVisible(false);
				}
				catch (NumberFormatException exc) {
				}
			}
		});
		button.setFont(new Font("Monospaced", Font.BOLD, 12));
		
		JButton button_1 = new JButton("Cancel");
		button_1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				setVisible(false);
			}
		});
		button_1.setFont(new Font("Monospaced", Font.BOLD, 12));
		
		JLabel lblAlgorithm = new JLabel("Algorithm");
		lblAlgorithm.setFont(new Font("Monospaced", Font.BOLD, 12));
		
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Greedy", "D. Programming"}));
		comboBox.setFont(new Font("Monospaced", Font.PLAIN, 12));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(39)
					.addComponent(label)
					.addGap(4)
					.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(button)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button_1))
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addComponent(lblAlgorithm)
							.addGap(4)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(label))
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblAlgorithm))
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(button_1)
						.addComponent(button)))
		);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);
	}
	
	public int getWrapWidth()
	{
		return width;
	}
	
	public boolean isGreedyAlgorithm()
	{
		if (comboBox.getSelectedIndex() == 0) return true;
		return false;
	}

	public void open()
	{
		spinner.setValue(width);
		comboBox.setSelectedIndex(algorithmIndex);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
//	public static void main(String[] args) 
//	{
//		try {
//			WordWrapWindow dialog = new WordWrapWindow(null, 81);
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
}