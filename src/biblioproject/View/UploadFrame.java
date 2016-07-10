package biblioproject.View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;

import biblioproject.Controller.GestionePagina;
import biblioproject.Model.Biblioteca;
import biblioproject.Model.Opera;
import biblioproject.Model.User;

public class UploadFrame extends JFrame {

	
	private static final long serialVersionUID = -1623881826062829714L;
	private JPanel contentPane;
	private JTextField pathField;
	private JTextField operaField;
	private JTextField npaginaField;
	private BufferedImage image = null;
	private JTextField oidField;

	
	public UploadFrame(Biblioteca data, Opera opera, User user) throws Exception {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 0, 800, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPreview = new JLabel("Preview");
		lblPreview.setBounds(10, 24, 87, 14);
		lblPreview.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(lblPreview);
		
		pathField = new JTextField();
		pathField.setBounds(546, 67, 228, 20);
		contentPane.add(pathField);
		pathField.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 40, 526, 610);
		panel.setBorder(null);
		contentPane.add(panel);
		
		JLabel lblImage = new JLabel("");
		JScrollPane scroll = new JScrollPane(lblImage);
		panel.add(scroll);
		image = ImageIO.read(new URL("http://frank.altervista.org/nopreview.jpg"));
		ImageIcon imageIcon = new ImageIcon(image);
		lblImage.setIcon(imageIcon);
		
		JButton button = new JButton("Zoom +");
		button.setEnabled(false);
		button.setBounds(546, 571, 86, 23);
		button.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		
		JButton button_1 = new JButton("Zoom -");
		button_1.setEnabled(false);
		button_1.setBounds(546, 605, 86, 23);
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		Action act = new GestionePagina(this, data, pathField, lblImage, button, button_1); // View -> Controller
		button_1.addActionListener(act);
		contentPane.add(button_1);
		button.addActionListener(act);
		contentPane.add(button);
	
		JButton btnScegliFile = new JButton("Scegli File");
		btnScegliFile.setBounds(546, 92, 105, 23);
		btnScegliFile.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnScegliFile.addActionListener(act);
		contentPane.add(btnScegliFile);
		
		JLabel lblOpera = new JLabel("Opera");
		lblOpera.setBounds(546, 153, 46, 14);
		lblOpera.setFont(new Font("Tahoma", Font.PLAIN, 11));
		contentPane.add(lblOpera);
		
		operaField = new JTextField();
		operaField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		operaField.setBounds(546, 169, 228, 20);
		operaField.setEditable(false);
		operaField.setText(opera.getTitle());
		contentPane.add(operaField);
		operaField.setColumns(10);
		
		npaginaField = new JTextField();
		npaginaField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		npaginaField.setBounds(546, 217, 94, 20);
		contentPane.add(npaginaField);
		npaginaField.setColumns(10);
		
		JLabel lblNPagina = new JLabel("n\u00B0 Pagina");
		lblNPagina.setBounds(546, 200, 46, 14);
		lblNPagina.setFont(new Font("Tahoma", Font.PLAIN, 11));
		contentPane.add(lblNPagina);
		
		oidField = new JTextField();
		oidField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		oidField.setEditable(false);
		oidField.setBounds(663, 217, 94, 20);
		oidField.setText(Integer.toString(opera.getId()));
		contentPane.add(oidField);
		oidField.setColumns(10);
		
		Action act2 = new GestionePagina(this, data,  pathField , npaginaField, user, opera); // View -> Controller
		
		JButton btnUpload = new JButton("Upload");
		btnUpload.setBounds(582, 267, 154, 31);
		btnUpload.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnUpload.addActionListener(act2);
		contentPane.add(btnUpload);
		
		JLabel lblPath = new JLabel("Path");
		lblPath.setBounds(546, 51, 46, 14);
		lblPath.setFont(new Font("Tahoma", Font.PLAIN, 11));
		contentPane.add(lblPath);
		
		JLabel lblOperaId = new JLabel("Opera ID");
		lblOperaId.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOperaId.setBounds(663, 200, 46, 14);
		contentPane.add(lblOperaId);
		
	}
}
