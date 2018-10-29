package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.GroupLayout;
import javax.swing.JFileChooser;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import text_processor.Text;

public class MainWindow extends JFrame 
{

	private static final long serialVersionUID = -583541423908400944L;
	private JPanel contentPane;
	private JTextField statusInput, statusOutput;
	private WordWrapWindow wordWrapWindow;
	private JTextArea txtrInputText, txtrOutputText;
	private Dimension outputPanelDimension;
	private JPanel outputPanel;
	private int initialWidth = 40;
	public static float constantResizing = (float) 10;

	public MainWindow() 
	{
		setTitle("Word Wrap - Dynamic Programming");
		setFont(new Font("Monospaced", Font.PLAIN, 12));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 835, 453);
		setLocationRelativeTo(null);
		
		wordWrapWindow = new WordWrapWindow(this, initialWidth);
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Monospaced", Font.BOLD, 12));
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("Monospaced", Font.BOLD, 12));
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JFileChooser fc = new JFileChooser();
                int result = fc.showOpenDialog(null);
                
                if (result == JFileChooser.APPROVE_OPTION){
                    File file = fc.getSelectedFile();
                    try {
						BufferedReader br = new BufferedReader(new FileReader(file));
						
						txtrInputText.setText("");
						
						while (br.ready()) {
							txtrInputText.setText(txtrInputText.getText() + br.readLine() + "\n");
						}
						
						br.close();
					} 
                    catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} 
                    catch (IOException e1) {
						e1.printStackTrace();
					}
                    
                }
                else {
                	JOptionPane.showMessageDialog(null, "File not found!", "Error Message", JOptionPane.ERROR_MESSAGE);
            	}
			}
		});
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_MASK));
		mntmOpen.setFont(new Font("Monospaced", Font.BOLD, 12));
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save as...");
		mntmSaveAs.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JFileChooser fc = new JFileChooser();
			    int result = fc.showSaveDialog(null);
			    
			    if (result == JFileChooser.APPROVE_OPTION) {
			    	File file = fc.getSelectedFile();
			    	try {
						BufferedWriter bw = new BufferedWriter(new FileWriter(file));
						String[] linesContent = txtrInputText.getText().split("\n");
						
						for (int i = 0; i < linesContent.length; ++i) {
							bw.write(linesContent[i] + "\n");
						}
						
						bw.close();
			    	} 
			    	catch (IOException e1) {
						e1.printStackTrace();
					}
			    }
			    else {
			    	JOptionPane.showMessageDialog(null, "File not found!", "Error Message", JOptionPane.ERROR_MESSAGE);
			    }
			}
		});
		mntmSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK));
		mntmSaveAs.setFont(new Font("Monospaced", Font.BOLD, 12));
		mnFile.add(mntmSaveAs);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmClear = new JMenuItem("Clear");
		mntmClear.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				txtrInputText.setText("");
			}
		});
		mntmClear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK));
		mntmClear.setFont(new Font("Monospaced", Font.BOLD, 12));
		mnFile.add(mntmClear);
		
		JMenu mnView = new JMenu("Compiler");
		mnView.setFont(new Font("Monospaced", Font.BOLD, 12));
		menuBar.add(mnView);
		
		JMenuItem mntmRun = new JMenuItem("Run");
		mntmRun.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		mntmRun.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String[] textLines = txtrInputText.getText().trim().replaceAll("\n", " ").split("\\\\");
				String newLine;
				txtrOutputText.setText("");
				
				for (int i = 0; i < textLines.length; ++i) {
					if (textLines[i].trim().isEmpty()) continue;
					if (wordWrapWindow.isGreedyAlgorithm()) newLine = Text.greedyWrap(textLines[i], wordWrapWindow.getWrapWidth()); 
					else newLine = Text.dpWrap(textLines[i], wordWrapWindow.getWrapWidth());
					
					if (i+1 < textLines.length) newLine += "\n\n";
					txtrOutputText.setText(txtrOutputText.getText() + newLine);
				}
			}
		});
		mntmRun.setFont(new Font("Monospaced", Font.BOLD, 12));
		mnView.add(mntmRun);
		
		JMenuItem mntmWordWrap = new JMenuItem("Word Wrap");
		mntmWordWrap.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				wordWrapWindow.open();
			}
		});
		mntmWordWrap.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		mntmWordWrap.setFont(new Font("Monospaced", Font.BOLD, 12));
		mnView.add(mntmWordWrap);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
		);
		
		JPanel panel_1 = new JPanel();
		
		JScrollPane scrollPaneInput = new JScrollPane();
		
		statusInput = new JTextField();
		statusInput.setEditable(false);
		statusInput.setBackground(new Color(238, 238, 238));
		statusInput.setFont(new Font("Monospaced", Font.PLAIN, 11));
		statusInput.setColumns(10);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(statusInput, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
				.addComponent(scrollPaneInput, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
					.addComponent(scrollPaneInput, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(statusInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		
		txtrInputText = new JTextArea();
		txtrInputText.setCaretColor(Color.GREEN);
		txtrInputText.setForeground(Color.GREEN);
		txtrInputText.setBackground(Color.BLACK);
		txtrInputText.setFont(new Font("Monospaced", Font.PLAIN, 12));
		txtrInputText.setToolTipText("Input text");
		scrollPaneInput.setViewportView(txtrInputText);
		
		//Extracted from: http://stackoverflow.com/questions/5139995/java-column-number-and-line-number-of-cursors-current-position
		// Add a caretListener to the editor. This is an anonymous class because it is inline and has no specific name.
		txtrInputText.addCaretListener(new CaretListener()
		{
			
			
			// Each time the caret is moved, it will trigger the listener and its method caretUpdate.
			// It will then pass the event to the update method including the source of the event (which is our textarea control)
			public void caretUpdate(CaretEvent e)
			{
				JTextArea editArea = (JTextArea) e.getSource();
				
				// Lets start with some default values for the line and column.
				int linenum = 1;
				int columnnum = 1;
				
				// We create a try catch to catch any exceptions. We will simply ignore such an error for our demonstration.
				try {
					// First we find the position of the caret. This is the number of where the caret is in relation to the start of the JTextArea
					// in the upper left corner. We use this position to find offset values (eg what line we are on for the given position as well as
					// what position that line starts on.
					int caretpos = editArea.getCaretPosition();
					linenum = editArea.getLineOfOffset(caretpos);
					
					// We subtract the offset of where our line starts from the overall caret position.
					// So lets say that we are on line 5 and that line starts at caret position 100, if our caret position is currently 106
					// we know that we must be on column 6 of line 5.
					columnnum = caretpos - editArea.getLineStartOffset(linenum);
					
					// We have to add one here because line numbers start at 0 for getLineOfOffset and we want it to start at 1 for display.
					linenum += 1;
				}
				catch (Exception ex) {
				}
				
				// Once we know the position of the line and the column, pass it to a helper function for updating the status bar.
				updateStatusInput(linenum, columnnum);
			}
		});
		panel_1.setLayout(gl_panel_1);
		contentPane.setLayout(gl_contentPane);
		outputPanel = new JPanel()
		{
			private static final long serialVersionUID = -6281264973604537644L;

			@Override
			public Dimension getPreferredSize() 
			{
//				return super.getPreferredSize();
				return outputPanelDimension;
			}
		};
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(outputPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
				.addComponent(outputPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		
		JScrollPane scrollPaneOutput = new JScrollPane();
		
		statusOutput = new JTextField();
		statusOutput.setFont(new Font("Monospaced", Font.PLAIN, 11));
		statusOutput.setEditable(false);
		statusOutput.setColumns(10);
		statusOutput.setBackground(new Color(238, 238, 238));
		GroupLayout gl_panel_2 = new GroupLayout(outputPanel);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPaneOutput, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
				.addComponent(statusOutput, GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(scrollPaneOutput, GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(statusOutput, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
		);
		
		txtrOutputText = new JTextArea();
		txtrOutputText.setEditable(false);
		txtrOutputText.setText("");
		txtrOutputText.setToolTipText("Output text");
		txtrOutputText.setForeground(Color.BLACK);
		txtrOutputText.setFont(new Font("Monospaced", Font.PLAIN, 12));
		scrollPaneOutput.setViewportView(txtrOutputText);
		
		//Extracted from: http://stackoverflow.com/questions/5139995/java-column-number-and-line-number-of-cursors-current-position
		// Add a caretListener to the editor. This is an anonymous class because it is inline and has no specific name.
		txtrOutputText.addCaretListener(new CaretListener()
		{
			
			
			// Each time the caret is moved, it will trigger the listener and its method caretUpdate.
			// It will then pass the event to the update method including the source of the event (which is our textarea control)
			public void caretUpdate(CaretEvent e)
			{
				JTextArea editArea = (JTextArea) e.getSource();
				
				// Lets start with some default values for the line and column.
				int linenum = 1;
				int columnnum = 1;
				
				// We create a try catch to catch any exceptions. We will simply ignore such an error for our demonstration.
				try {
					// First we find the position of the caret. This is the number of where the caret is in relation to the start of the JTextArea
					// in the upper left corner. We use this position to find offset values (eg what line we are on for the given position as well as
					// what position that line starts on.
					int caretpos = editArea.getCaretPosition();
					linenum = editArea.getLineOfOffset(caretpos);
					
					// We subtract the offset of where our line starts from the overall caret position.
					// So lets say that we are on line 5 and that line starts at caret position 100, if our caret position is currently 106
					// we know that we must be on column 6 of line 5.
					columnnum = caretpos - editArea.getLineStartOffset(linenum);
					
					// We have to add one here because line numbers start at 0 for getLineOfOffset and we want it to start at 1 for display.
					linenum += 1;
				}
				catch (Exception ex) {
				}
				
				// Once we know the position of the line and the column, pass it to a helper function for updating the status bar.
				updateStatusOutput(linenum, columnnum);
			}
		});

		//Comment this group of lines to use the WindowBuilder:
		TextLineNumber inputTLN = new TextLineNumber(txtrInputText);
		scrollPaneInput.setRowHeaderView(inputTLN);
		TextLineNumber outputTLN = new TextLineNumber(txtrOutputText);
		scrollPaneOutput.setRowHeaderView(outputTLN);
		statusInput.setText("Line: 1 Column: 0");
		statusOutput.setText("Line: 1 Column: 0");
		
		outputPanelDimension = new Dimension((int) (initialWidth*constantResizing), outputPanel.getHeight());
		outputPanel.setLayout(gl_panel_2);
		panel.setLayout(gl_panel);
	}
	
	//Extracted from: http://stackoverflow.com/questions/5139995/java-column-number-and-line-number-of-cursors-current-position
	// This helper function updates the status bar with the line number and column number.
	private void updateStatusInput(int linenumber, int columnnumber)
	{
		statusInput.setText("Line: " + linenumber + " Column: " + columnnumber);
	}
	
	//Extracted from: http://stackoverflow.com/questions/5139995/java-column-number-and-line-number-of-cursors-current-position
	// This helper function updates the status bar with the line number and column number.
	private void updateStatusOutput(int linenumber, int columnnumber)
	{
		statusOutput.setText("Line: " + linenumber + " Column: " + columnnumber);
	}
	
	public void resizeOutputPanel(int width)
	{
		outputPanelDimension.width = (int) (width*constantResizing);
		outputPanel.revalidate();
		outputPanel.repaint();
	}

}