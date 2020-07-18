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
import javax.swing.JFileChooser;


public class GUI {

	private JFrame frame;
	private JTextField textFieldAddressXSL;
	private JTextField textFieldAddressXML;
	private JTextField textFieldFilenameXMLOutput;
	private JTextField textFieldParentAdressXMLOutput;
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
		
		JButton btnConvert = new JButton("Convert"); // Button Convert
		btnConvert.addActionListener(new ActionListener() { // Button Convert Functionality:
			
			public void actionPerformed(ActionEvent arg0) {
					boolean success = true;
					String addInputXML, addXSL, addOutputXMLFilename;
					
					try {
						addInputXML = textFieldAddressXML.getText(); 
						addXSL = textFieldAddressXSL.getText();
						addOutputXMLFilename = textFieldFilenameXMLOutput.getText();
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
		
		textFieldAddressXSL = new JTextField();
		textFieldAddressXSL.setBounds(144, 133, 251, 19);
		frame.getContentPane().add(textFieldAddressXSL);
		textFieldAddressXSL.setColumns(10);
		
		textFieldAddressXML = new JTextField();
	
		textFieldAddressXML.setColumns(10);
		textFieldAddressXML.setBounds(144, 75, 251, 19);
		frame.getContentPane().add(textFieldAddressXML);
		
		JLabel lblAddTheXMLAddress = new JLabel("Add the address of the XML file:");
		lblAddTheXMLAddress.setBounds(75, 48, 231, 15);
		frame.getContentPane().add(lblAddTheXMLAddress);
		
		JLabel lblAddTheXSLAddress = new JLabel("Add the address of the XSL file:");
		lblAddTheXSLAddress.setBounds(75, 106, 231, 15);
		frame.getContentPane().add(lblAddTheXSLAddress);
		
		JLabel lblAddTheXMLOutputFilename = new JLabel("Name of the output XML file:");
		lblAddTheXMLOutputFilename.setBounds(75, 160, 186, 15);
		frame.getContentPane().add(lblAddTheXMLOutputFilename);
		
		textFieldFilenameXMLOutput = new JTextField();
		textFieldFilenameXMLOutput.setColumns(10);
		textFieldFilenameXMLOutput.setBounds(263, 158, 132, 19);
		frame.getContentPane().add(textFieldFilenameXMLOutput);
		
		JButton btnClear = new JButton("Clear text fields");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldAddressXSL.setText("");
				textFieldAddressXML.setText("");
				textFieldFilenameXMLOutput.setText("");
				textFieldParentAdressXMLOutput.setText("");
			}
		});
		
		btnClear.setBounds(263, 235, 151, 25);
		frame.getContentPane().add(btnClear);
		
		JButton btnXMLFileChooser = new JButton("...");
		btnXMLFileChooser.addActionListener(new ActionListener() { // Button btnXMLFileChooser Functionality:
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				File workingDirectory = new File(System.getProperty("user.dir"));
	            fileChooser.setCurrentDirectory(workingDirectory);

				
				int returnVal = fileChooser.showOpenDialog(btnXMLFileChooser);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fileChooser.getSelectedFile();
		            		            try {
						Path fullPath = Paths.get(file.getAbsolutePath());
						textFieldAddressXML.setText(fullPath.toString());
						textFieldParentAdressXMLOutput.setText(fullPath.getParent().toString() + File.separator);
						if(textFieldFilenameXMLOutput.getText().isEmpty()) {
							textFieldFilenameXMLOutput.setText(file.getName().replaceFirst("[.][^.]+$", "") + "new.p4d");
						}
		            } catch (Exception ex) {
		            	String error = ex.getLocalizedMessage();
						JOptionPane.showMessageDialog(null, "Error: " + error);
		            }
		        }       
			}
		});
		btnXMLFileChooser.setBounds(407, 77, 29, 15);
		frame.getContentPane().add(btnXMLFileChooser);
		
		JButton btnXLSFileChooser = new JButton("...");
		btnXLSFileChooser.addActionListener(new ActionListener() { // Button btnXLSFileChooser Functionality: 
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				File workingDirectory = new File(System.getProperty("user.dir"));
	            fileChooser.setCurrentDirectory(workingDirectory);

				
				int returnVal = fileChooser.showOpenDialog(btnXLSFileChooser);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fileChooser.getSelectedFile();
		            try {
		            	Path fullPath = Paths.get(file.getAbsolutePath());
						textFieldAddressXSL.setText(fullPath.toString());
		            } catch (Exception ex) {
		            	String error = ex.getLocalizedMessage();
						JOptionPane.showMessageDialog(null, "Error: " + error);
		            }
		        }       
			}
		});
		btnXLSFileChooser.setBounds(407, 135, 29, 15);
		frame.getContentPane().add(btnXLSFileChooser);
		
		textFieldParentAdressXMLOutput = new JTextField();
		textFieldParentAdressXMLOutput.setEnabled(false);
		textFieldParentAdressXMLOutput.setColumns(10);
		textFieldParentAdressXMLOutput.setBounds(85, 187, 310, 19);
		frame.getContentPane().add(textFieldParentAdressXMLOutput);
	}
}
