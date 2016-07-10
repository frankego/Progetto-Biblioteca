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

public class RevisioneImgFrame extends JFrame {

	

	private static final long serialVersionUID = -1623881826062829714L;
	private JPanel contentPane;
	private BufferedImage image = null;

	
	public RevisioneImgFrame(Biblioteca data, String opera, int page) throws Exception {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 0, 800, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JLabel lblPreview = new JLabel("Acquisizione");
		lblPreview.setBounds(10, 24, 182, 14);
		lblPreview.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(lblPreview);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 40, 526, 610);
		panel.setBorder(null);
		contentPane.add(panel);
		
		JLabel lblImage = new JLabel("");
		JScrollPane scroll = new JScrollPane(lblImage);
		panel.add(scroll);
		String operaurl = opera.replaceAll("\\s","%20");
		image = ImageIO.read(new URL("http://frank.altervista.org/"+operaurl+"/"+page+".jpg"));
		GestionePagina act = new GestionePagina(this, data, null, lblImage, opera, page);
		image =  act.scaleImage(526, 603, image);
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
		
		JButton btnApprova = new JButton("Approva");
		btnApprova.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnApprova.setBounds(557, 160, 89, 23);
		btnApprova.addActionListener(act);
		contentPane.add(btnApprova);
		
		JButton btnRifiuta = new JButton("Rifiuta");
		btnRifiuta.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRifiuta.setBounds(656, 160, 89, 23);
		btnRifiuta.addActionListener(act);
		contentPane.add(btnRifiuta);
		
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
		
	}
}

