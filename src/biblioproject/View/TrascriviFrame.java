package biblioproject.View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import biblioproject.Controller.GestionePagina;
import biblioproject.Model.Biblioteca;
import biblioproject.Model.User;

public class TrascriviFrame extends JFrame {

	private JPanel contentPane;
	private BufferedImage image = null;


	public TrascriviFrame(Biblioteca data, User user, String opera, int page) throws Exception {
	
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 0, 800, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		String filePath = new File("").getAbsolutePath();
		filePath += "\\JEdit\\jedit.exe";

		
		Runtime.getRuntime().exec(filePath);
		
		JLabel lblPreview = new JLabel("Acquisizione");
		lblPreview.setBounds(10, 24, 182, 14);
		lblPreview.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(lblPreview);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 40, 526, 603);
		panel.setBorder(null);
		contentPane.add(panel);
		
		JLabel lblImage = new JLabel("");
		JScrollPane scroll = new JScrollPane(lblImage);
		panel.add(scroll);
		String operaurl = opera.replaceAll("\\s","%20");
		image = ImageIO.read(new URL("http://frank.altervista.org/"+operaurl+"/"+page+".jpg"));
		GestionePagina act = new GestionePagina(this, data, null, lblImage, opera, page);
		image =  act.scaleImage(526, 610, image);
		act.setImage(image);
		ImageIcon imageIcon = new ImageIcon(image);
		lblImage.setIcon(imageIcon);
		
		JButton btnZoom = new JButton("Zoom +");
		btnZoom.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnZoom.setBounds(546, 567, 89, 23);
		btnZoom.addActionListener(act);
		contentPane.add(btnZoom);
		
		JButton btnZoom_1 = new JButton("Zoom -");
		btnZoom_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnZoom_1.setBounds(546, 595, 89, 23);
		btnZoom_1.addActionListener(act);
		contentPane.add(btnZoom_1);
		
		JLabel lblOpera = new JLabel("Opera:");
		lblOpera.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOpera.setBounds(557, 85, 46, 14);
		contentPane.add(lblOpera);
		
		JLabel lblPagina = new JLabel("Pagina:");
		lblPagina.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPagina.setBounds(557, 110, 46, 14);
		contentPane.add(lblPagina);
		
		JLabel labelTitolo = new JLabel("");
		labelTitolo.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelTitolo.setBounds(623, 85, 124, 14);
		contentPane.add(labelTitolo);
		
		JLabel labeln = new JLabel("");
		labeln.setFont(new Font("Tahoma", Font.BOLD, 11));
		labeln.setBounds(622, 110, 124, 14);
		contentPane.add(labeln);
		
		labelTitolo.setText(opera);
		labeln.setText(Integer.toString(page));
		
		Action act2 = new GestionePagina(this, data, null, user, opera, page);
		JButton btnUploadTextFile = new JButton("Upload XML file");
		btnUploadTextFile.setBounds(556, 142, 218, 35);
		btnUploadTextFile.addActionListener(act2);
		contentPane.add(btnUploadTextFile);
	}

}
