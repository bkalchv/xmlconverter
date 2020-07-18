import java.awt.EventQueue;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import java.awt.Button;
import javax.swing.JFileChooser;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import javax.swing.DropMode;
import javax.swing.JSlider;

public class GUI {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	/**
	 * @wbp.nonvisual location=210,377
	 */
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
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
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 321);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnConvert = new JButton("Convert");
		btnConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					boolean success = true;
					String addInputXML, addXSL, addOutputXMLFilename;
					
					try {
						addInputXML = textField_1.getText();
						addXSL = textField.getText();
						addOutputXMLFilename = textField_2.getText();
						Path outputPath = Paths.get(addInputXML).getParent();
						
						
						DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();            
				        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				        Document doc = docBuilder.parse (new File(addInputXML));
				        Source xslt = new StreamSource(new File(addXSL)); 
				        
				        TransformerFactory prettyPrint = TransformerFactory.newInstance();
				        Transformer transformer = prettyPrint.newTransformer(xslt);
	
				        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
				        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
				        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
				        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");                        
	
				        DOMSource source = new DOMSource(doc);
				        StreamResult result = new StreamResult(new File(outputPath.toString() + File.separator + addOutputXMLFilename));        
				        transformer.transform(source, result);
				        
					} catch (Exception e) {
						String error = e.getLocalizedMessage();
						JOptionPane.showMessageDialog(null, "Error: " + error);
						success = false;
					}
						if (success == true) {
						JOptionPane.showMessageDialog(null, "Success!");
						
						}
				
			}
		});
		
		btnConvert.setBounds(99, 235, 117, 25);
		frame.getContentPane().add(btnConvert);
		
		textField = new JTextField();
		textField.setBounds(144, 133, 251, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
	
		textField_1.setColumns(10);
		textField_1.setBounds(144, 75, 251, 19);
		frame.getContentPane().add(textField_1);
		
		JLabel lblAddTheAdress = new JLabel("Add the adress of the XML file:");
		lblAddTheAdress.setBounds(75, 48, 231, 15);
		frame.getContentPane().add(lblAddTheAdress);
		
		JLabel lblAddTheAdress_2 = new JLabel("Add the adress of the XSL file:");
		lblAddTheAdress_2.setBounds(75, 106, 231, 15);
		frame.getContentPane().add(lblAddTheAdress_2);
		
		JLabel lblAddTheAdress_2_1 = new JLabel("Name  of output XML file:");
		lblAddTheAdress_2_1.setBounds(75, 160, 186, 15);
		frame.getContentPane().add(lblAddTheAdress_2_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(263, 158, 132, 19);
		frame.getContentPane().add(textField_2);
		
		JButton btnNewButton = new JButton("Clear text fields");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				textField_3.setText("");
			}
		});
		
		btnNewButton.setBounds(263, 235, 151, 25);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("...");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				File workingDirectory = new File(System.getProperty("user.dir"));
	            fileChooser.setCurrentDirectory(workingDirectory);

				
				int returnVal = fileChooser.showOpenDialog(btnNewButton_1);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fileChooser.getSelectedFile();
		            		            try {
						Path fullPath = Paths.get(file.getAbsolutePath());
						textField_1.setText(fullPath.toString());
						textField_3.setText(fullPath.getParent().toString() + File.separator);
						if(textField_2.getText().isEmpty()) {
							textField_2.setText(file.getName().replaceFirst("[.][^.]+$", "") + "new.p4d");
						}
		            } catch (Exception ex) {
		            	String error = ex.getLocalizedMessage();
						JOptionPane.showMessageDialog(null, "Error: " + error);
		            }
		        }       
			}
		});
		btnNewButton_1.setBounds(407, 77, 29, 15);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("...");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				File workingDirectory = new File(System.getProperty("user.dir"));
	            fileChooser.setCurrentDirectory(workingDirectory);

				
				int returnVal = fileChooser.showOpenDialog(btnNewButton_1);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fileChooser.getSelectedFile();
		            try {
		            	Path fullPath = Paths.get(file.getAbsolutePath());
						textField.setText(fullPath.toString());
		            } catch (Exception ex) {
		            	String error = ex.getLocalizedMessage();
						JOptionPane.showMessageDialog(null, "Error: " + error);
		            }
		        }       
			}
		});
		btnNewButton_1_1.setBounds(407, 135, 29, 15);
		frame.getContentPane().add(btnNewButton_1_1);
		
		textField_3 = new JTextField();
		textField_3.setEnabled(false);
		textField_3.setColumns(10);
		textField_3.setBounds(85, 187, 310, 19);
		frame.getContentPane().add(textField_3);
	}
}
