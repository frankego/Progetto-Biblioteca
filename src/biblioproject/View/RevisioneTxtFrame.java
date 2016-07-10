package biblioproject.View;


import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import biblioproject.Controller.GestioneOpera;
import biblioproject.Controller.GestionePagina;
import biblioproject.Model.Biblioteca;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class RevisioneTxtFrame {
	private static BufferedImage image = null;
	private static Biblioteca data;
	private static String opera;
	private static int page;
	private static String html;
	
	public RevisioneTxtFrame(Biblioteca data, String opera, int page, String html) throws Exception{
		this.data = data;
		this.opera = opera;
		this.page = page;
		this.html = html;
		main(new String[0]);
	}
	

    private static void initAndShowGUI() throws Exception {
       
        JFrame frame = new JFrame("Pagina "+page);
        frame.getContentPane().setLayout(null);
        final JFXPanel fxPanel = new JFXPanel();
        fxPanel.setBounds(610, 45, 555, 605);
        frame.getContentPane().add(fxPanel);
        frame.setSize(1191, 703);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
		panel.setBounds(10, 40, 526, 610);
		panel.setBorder(null);
		frame.getContentPane().add(panel);
		JLabel lblImage = new JLabel("");
		JScrollPane scroll = new JScrollPane(lblImage);
		panel.add(scroll);
		String operaurl = opera.replaceAll("\\s","%20");
		image = ImageIO.read(new URL("http://frank.altervista.org/"+operaurl+"/"+page+".jpg"));
		GestionePagina act = new GestionePagina(frame, data, null, lblImage, opera, page);
		image =  act.scaleImage(526, 603, image);
		act.setImage(image);
		ImageIcon imageIcon = new ImageIcon(image);
		lblImage.setIcon(imageIcon);
		
		JLabel lblTitoloOpera = new JLabel(opera);
		lblTitoloOpera.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 15));
		lblTitoloOpera.setBounds(10, 11, 141, 27);
		frame.getContentPane().add(lblTitoloOpera);
		JButton btnVai = new JButton("Rifiutalo");
		btnVai.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11));
		btnVai.setBounds(607, 11, 89, 23);
		btnVai.addActionListener(act);
		frame.getContentPane().add(btnVai);
		
		JButton button = new JButton("+");
		button.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11));
		button.setBounds(546, 602, 46, 23);
		button.addActionListener(act);
		frame.getContentPane().add(button);
		
		JButton button_1 = new JButton("-");
		button_1.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11));
		button_1.setBounds(546, 627, 46, 23);
		button_1.addActionListener(act);
		frame.getContentPane().add(button_1);
		
		JButton btnAccetta = new JButton("Approvalo");
		btnAccetta.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11));
		btnAccetta.setBounds(505, 11, 89, 23);
		frame.getContentPane().add(btnAccetta);
        btnAccetta.addActionListener(act);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
       });
    }

    private static void initFX(JFXPanel fxPanel) {
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }

    private static Scene createScene() {
        WebView web = new WebView();
        if(html.length() > 0) web.getEngine().loadContent(html);
        else web.getEngine().loadContent("<h3> Non ancora trascritta </h3>");
        Scene scene = new Scene(web);
        return (scene);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                	Platform.setImplicitExit(false);
					initAndShowGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        });
    }
}

