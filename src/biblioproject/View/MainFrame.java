package biblioproject.View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import biblioproject.Controller.GestioneAction;
import biblioproject.Controller.GestioneOpera;
import biblioproject.Controller.GestioneMouse;
import biblioproject.Model.Biblioteca;
import biblioproject.Model.Opera;
import biblioproject.Model.User;

import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Iterator;

import javax.swing.border.BevelBorder;
import java.awt.Color;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 7469885287644889648L;
	private JPanel contentPane;
	private JTextField cercaField;
	private Biblioteca data;


	public MainFrame(Biblioteca data, User user) {
		
		int role = user.getRole();
		this.data = data;
		DefaultListModel<String> listaOpera=new DefaultListModel<String>();
		if(role == 0 || role == 1){
			Iterator<Opera> itr = data.getOperaList().iterator();
			while(itr.hasNext()){
				Opera next = itr.next();
				if(next.getStatus())listaOpera.addElement(next.getTitle());
			}
		}else{
		Iterator<Opera> itr = data.getOperaList().iterator();
		while(itr.hasNext()){
			listaOpera.addElement(itr.next().getTitle());
		}
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 470, 471);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList<String> list = new JList<String>(listaOpera);	
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBounds(10, 78, 233, 343);		
		scrollPane.setViewportView(list);
		contentPane.add(scrollPane);
		
		JLabel autore = new JLabel("-");
		autore.setFont(new Font("Tahoma", Font.BOLD, 11));
		autore.setBounds(310, 105, 134, 14);
		contentPane.add(autore);
		
		JLabel pagine = new JLabel("-");
		pagine.setFont(new Font("Tahoma", Font.BOLD, 11));
		pagine.setBounds(310, 130, 134, 14);
		contentPane.add(pagine);

		Action act5 = new GestioneOpera( this, data, listaOpera, list, user, null);
		JButton btnVisualizza = new JButton("Visualizza");
		btnVisualizza.setEnabled(false);
		btnVisualizza.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnVisualizza.setBounds(254, 165, 190, 33);
		btnVisualizza.addActionListener(act5);
		if(role != 0)contentPane.add(btnVisualizza);
		
		
		if(role > 1){
			JLabel lblStatus = new JLabel("Status:");
			lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblStatus.setBounds(254, 80, 46, 14);
			contentPane.add(lblStatus);
			
			JLabel status = new JLabel("-");
			status.setBounds(310, 80, 134, 14);
			status.setFont(new Font("Tahoma", Font.BOLD, 11));
			contentPane.add(status);
			GestioneMouse act = new GestioneMouse( this.data, "MainFrame", list, autore, pagine, btnVisualizza,status);
			list.addMouseListener(act);
			}
		else{
		    GestioneMouse act = new GestioneMouse( this.data, "MainFrame", list, autore, pagine, btnVisualizza,null);
		    list.addMouseListener(act);
			}
		
		cercaField = new JTextField();
		cercaField.setBounds(10, 26, 155, 20);
		contentPane.add(cercaField);
		cercaField.setColumns(10);
		
		
		JLabel lblAutore = new JLabel("Autore:");
		lblAutore.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAutore.setBounds(254, 105, 46, 14);
		contentPane.add(lblAutore);
		
		JRadioButton rdbtnTitolo = new JRadioButton("Titolo", true);
		rdbtnTitolo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnTitolo.setBounds(10, 48, 61, 23);
		contentPane.add(rdbtnTitolo);
		
		JRadioButton rdbtnAutore = new JRadioButton("Autore");
		rdbtnAutore.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnAutore.setBounds(73, 47, 61, 23);
		contentPane.add(rdbtnAutore);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnTitolo);
		group.add(rdbtnAutore);
		
		JLabel lblPagine = new JLabel("Pagine:");
		lblPagine.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPagine.setBounds(254, 130, 46, 14);
		contentPane.add(lblPagine);
		
		Action act2 = new GestioneOpera( this, data, listaOpera, cercaField, group, null);
		JButton btnCerca = new JButton("Cerca");
		btnCerca.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCerca.setBounds(175, 26, 68, 33);
		btnCerca.addActionListener(act2);
		contentPane.add(btnCerca);
		
		
		if(role==2 || role == 4){
		String actioncmd= "Acquisisci";
		if(role==4) actioncmd= "Trascrivi";
		Action act3 = new GestioneOpera( this, data, listaOpera, list, user, null);
		JButton btnAcquisisci = new JButton(actioncmd);
		btnAcquisisci.setBounds(253, 209, 191, 33);
		btnAcquisisci.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAcquisisci.addActionListener(act3);
		contentPane.add(btnAcquisisci);
		}
		if(role==3 || role==5){
			String actioncmd= "Revisiona Acquisizioni";
			if(role==5) actioncmd= "Revisiona Trascrizioni";
			JButton btnRevisioneImg = new JButton(actioncmd);
			btnRevisioneImg.setBounds(253, 209, 191, 33);
			btnRevisioneImg.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnRevisioneImg.addActionListener(act2);
			contentPane.add(btnRevisioneImg);
		}
		
		if(role==2 || role==4){
        Action act4 = new GestioneAction(data, user, null);
		JButton btnCronologiaOperazioni = new JButton("Cronologia Operazioni");
		btnCronologiaOperazioni.setBounds(253, 253, 191, 33);
		btnCronologiaOperazioni.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCronologiaOperazioni.addActionListener(act4);
		contentPane.add(btnCronologiaOperazioni);
		}
		
		if(role==6){
			Action act3 = new GestioneOpera( this, data, listaOpera, list, user, null);
						
			JButton btnPubblica = new JButton("Pubblica/Privata");
			btnPubblica.setBounds(253, 209, 191, 33);
			btnPubblica.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnPubblica.addActionListener(act3);
			contentPane.add(btnPubblica);
			
			JButton btnElimina = new JButton("Elimina Opera");
			btnElimina.setBounds(253, 253, 191, 33);
			btnElimina.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnElimina.addActionListener(act3);
			contentPane.add(btnElimina);
			
			JButton btnGestisciPag = new JButton("Gestisci Pagine");
			btnGestisciPag.setBounds(253, 297, 191, 33);
			btnGestisciPag.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnGestisciPag.addActionListener(act3);
			contentPane.add(btnGestisciPag);
			
			JButton btnUtenti = new JButton("Pannello Utenti");
			btnUtenti.setBounds(253, 388, 191, 33);
			btnUtenti.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnUtenti.addActionListener(act3);
			contentPane.add(btnUtenti);
			
			JButton btnPannelloUtenti = new JButton("Aggiungi Opera");
			btnPannelloUtenti.setBounds(253, 341, 191, 33);
			btnPannelloUtenti.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnPannelloUtenti.addActionListener(act3);
			contentPane.add(btnPannelloUtenti);
			
			JButton btnNewButton = new JButton("Gestione DB");
			btnNewButton.setBackground(Color.RED);
			btnNewButton.setBounds(254, 25, 190, 33);
			contentPane.add(btnNewButton);
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CambioDBFrame f = new CambioDBFrame();
					f.setVisible(true);
				}
			});
		}
	}
}
