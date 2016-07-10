package biblioproject.Controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import com.sun.glass.events.MouseEvent;
import biblioproject.DAO.FTPUtility;
import biblioproject.DAO.OperaDAO;
import biblioproject.DAO.PageDAO;
import biblioproject.Model.Biblioteca;
import biblioproject.Model.Opera;
import biblioproject.Model.Page;
import biblioproject.Model.User;
import biblioproject.View.AggiungiOperaFrame;
import biblioproject.View.GestisciFrame;
import biblioproject.View.MainFrame;
import biblioproject.View.RevisioneFrame;
import biblioproject.View.TrascriviListFrame;
import biblioproject.View.UploadFrame;
import biblioproject.View.VisualizzaFrame;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Writer;
import javax.xml.transform.stream.StreamSource;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.s9api.QName;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.s9api.XdmNode;


public class GestioneOpera extends AbstractAction implements Gestione {


	private static final long serialVersionUID = -5796011512728364456L;
	private JFrame frame;
	private Biblioteca data;
	private DefaultListModel<String> list;
	private Component c;
	private Object o;
	private Object o2;
	
	public GestioneOpera( JFrame f, Biblioteca data, DefaultListModel<String> l, Component c, Object o, Object o2){
		this.frame= f;
		this.data = data;
		this.list = l;
		this.c    = c;
		this.o    = o;
		this.o2   = o2;
	}

	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		
		  case "Cerca" :   
			  if(((JTextField)c).getText().equals("")) refreshList();
		      else{
		      search(((ButtonGroup)o).getElements().nextElement().isSelected());	   
		      }
		      break;
		      
		  case "Acquisisci" : 
			  try {
				acquisisciImg();
			} catch (Exception e1) {
				e1.printStackTrace();
			} break;
			
		  case "Trascrivi"  : TrascriviListFrame ft = new TrascriviListFrame(data, ((User)o)); ft.setVisible(true);break;
			
		  case "Revisiona Acquisizioni" :
			  RevisioneFrame fimg = new RevisioneFrame(data,"img");
			  fimg.setVisible(true);
			  break;
			  
		  case "Revisiona Trascrizioni" :
			  RevisioneFrame ftext = new RevisioneFrame(data,"text");
			  ftext.setVisible(true);
			  break;
		  case "Visualizza"      : try {view(1,0);	} catch (Exception e1) {e1.printStackTrace();} break;
		  case "Vai"             : try {view(Integer.parseInt(((JTextField)c).getText()),1);	} catch (Exception e1) {e1.printStackTrace();} break;
		  case "Elimina Opera"   : remove(); break;
		  case "Aggiungi Opera"  : AggiungiOperaFrame f = new AggiungiOperaFrame(data, list); f.setVisible(true); break;
		  case "Aggiungi"        : add(); break;
		  case "Pubblica/Privata": edit(); break;
		  case "Gestisci Pagine" : if(((JList)c).getSelectedValue()==null){JOptionPane.showMessageDialog(new JFrame(), "Seleziona l'opera", "Attenzione", JOptionPane.WARNING_MESSAGE);}GestisciFrame fp = new GestisciFrame(data, "page",(String)((JList)c).getSelectedValue()); fp.setVisible(true); break;
		  case "Pannello Utenti" : GestisciFrame fu = new GestisciFrame(data, "user", null); fu.setVisible(true); break;
			  
		    default      : break;
		}
	}
	
	public void add() {
		boolean error = false;
		if(((JTextField)c).getText().equals("") || ((JTextField)o).getText().equals("") || ((JTextField)o2).getText().equals("") ){
			JOptionPane.showMessageDialog(new JFrame(), "Tutti i campi sono obbligatori", "Inane warning", JOptionPane.WARNING_MESSAGE);
			error = true;
		}
		String titolo = ((JTextField)c).getText();
		String autore = ((JTextField)o).getText();
		int   npagine = Integer.parseInt(((JTextField)o2).getText());
		
		LinkedList<Opera> operaList = data.getOperaList();
		Iterator<Opera> itr = operaList.iterator();
		while(itr.hasNext()){
			if(itr.next().getTitle().equals(titolo)){
				JOptionPane.showMessageDialog(new JFrame(), "Opera già esistente", "Inane warning", JOptionPane.WARNING_MESSAGE);
				error = true;
			}
		}
		
		if(!error){
			Opera o = new Opera(0, titolo, autore, npagine, false);
			OperaDAO DAO = new OperaDAO();
			DAO.add(o.toArray());
			data.setOperaList();
			FTPUtility ftp = new FTPUtility();
			try {
				ftp.connect();
				ftp.createDir(titolo);
				ftp.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
			refreshList();
			frame.dispose();
		}
		
	}

	public void remove() {
		boolean error = false;
		if((((JList)c).getSelectedValue()) == null) {
        	error = true;
        	JOptionPane.showMessageDialog(new JFrame(), "Selezionare l'opera da eliminare", "Inane warning", JOptionPane.WARNING_MESSAGE);
        }
		
		if(!error){
			int id = 0;
			int dialogButton = JOptionPane.YES_NO_OPTION;
			int result = JOptionPane.showConfirmDialog (null, "Sei sicuro di voler eliminare l'opera selezionata?","Attenzione",dialogButton);
			if(result == 0){
				String selected = (String)((JList)c).getSelectedValue();
				LinkedList<Opera> operaList = data.getOperaList();
				Iterator<Opera> itr = operaList.iterator();
				while(itr.hasNext()){
					Opera next = itr.next();
					if(next.getTitle().equals(selected)){
						id = next.getId();
						OperaDAO DAO = new OperaDAO();
						DAO.delete(id);
						break;
					}
				}
				LinkedList<Page> pageList = data.getPageList();
				
				Iterator<Page> itr2 = pageList.iterator();
				while(itr2.hasNext()){
					Page next = itr2.next();
					if(next.getOperaID() == id){
						PageDAO DAOP = new PageDAO();
						DAOP.delete(next.getID());
					}
				}
					data.setOperaList();
					data.setPageList();
					refreshList();
					FTPUtility ftp = new FTPUtility();
					try {
						ftp.connect();
						ftp.removeDir(selected);
						ftp.disconnect();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	

	public void edit() {
		boolean error = false;
		LinkedList<Opera> operaList = data.getOperaList();
		Iterator<Opera> itr = operaList.iterator();	
        if((((JList)c).getSelectedValue()) == null) {
        	error = true;
        	JOptionPane.showMessageDialog(new JFrame(), "Selezionare l'opera", "Inane warning", JOptionPane.WARNING_MESSAGE);
        }
        if(!error){
			while(itr.hasNext()){
			Opera next = itr.next();
			if(next.getTitle().equals(((JList)c).getSelectedValue())){
				if(next.getStatus() == false) {
					next.setStatus(true);
					JOptionPane.showMessageDialog(frame, "L'Opera è ora Pubblica");
					OperaDAO DAO = new OperaDAO();
					DAO.edit(next.toArray());
				}
				else {
					next.setStatus(false);
					OperaDAO DAO = new OperaDAO();
					DAO.edit(next.toArray());
					JOptionPane.showMessageDialog(frame, "L'Opera è ora Privata");
				}
				break;
			}
		}	
	 }
	}
	
	public void view(int p, int close) throws Exception {
		boolean error = false;
		LinkedList<Opera> operaList = data.getOperaList();
		Iterator<Opera> itr = operaList.iterator();
		Opera selected = null;
		int oid = 0;
		String titolo;
		
		if( close == 0){
        if((((JList)c).getSelectedValue()) == null) {
        	error = true;
        	JOptionPane.showMessageDialog(new JFrame(), "Selezionare l'opera", "Inane warning", JOptionPane.WARNING_MESSAGE);
        }
		}
		if(!error){
				
		if(close == 0) {
			while(itr.hasNext()){
			Opera next = itr.next();
			if(next.getTitle().equals(((JList)c).getSelectedValue())) selected= next;
			}
			oid = selected.getId();
			titolo = selected.getTitle();
		}
		else {
			titolo = (String)o;
			oid= (int)o2;
		}
		boolean found = false;
		String xml = "";
		String html = "";
		Iterator<Page> itrp = data.getPageList().iterator();
		while(itrp.hasNext()){
			Page next = itrp.next();
			if(next.getOperaID() == oid && next.getNumber() == p){
				if(next.getStatus() > 0) found = true;
				if(next.getStatus() == 3){
			    xml = next.getText();
				if(xml.length() > 0 ){
				TransformerFactory fact = new net.sf.saxon.TransformerFactoryImpl();
		        Source xslt = new StreamSource(new File("TEI\\html\\html.xsl"));
		        Transformer transformer = fact.newTransformer(xslt);
		        
		        StringWriter outWriter = new StringWriter();
		        StreamResult result = new StreamResult( outWriter );
		        Source text = new StreamSource(new StringReader(xml));
		        transformer.transform(text, result);
		        html = outWriter.getBuffer().toString();
				}
				}
			}
		}
		if(found){
		VisualizzaFrame f = new VisualizzaFrame( data, titolo, p, html, oid);
		if(close == 1) this.frame.dispose();
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Pagina non esistente", "Attenzione", JOptionPane.WARNING_MESSAGE);
		}
		
		}
	}
	
	public void acquisisciImg() throws Exception{
         
		boolean error = false;
		LinkedList<Opera> operaList = data.getOperaList();
		Iterator<Opera> itr = operaList.iterator();
		Opera selected = null;	
        if((((JList)c).getSelectedValue()) == null) {
        	error = true;
        	JOptionPane.showMessageDialog(new JFrame(), "Selezionare l'opera", "Inane warning", JOptionPane.WARNING_MESSAGE);
        }
		
		if(!error){
			while(itr.hasNext()){
			Opera next = itr.next();
			if(next.getTitle().equals(((JList)c).getSelectedValue())) selected= next;
		}	
		
		UploadFrame f = new UploadFrame( data, selected, ((User)o));
		f.setVisible(true);
		}
	}
	
	public void refreshList(){
		list.removeAllElements();
		LinkedList<Opera> operaList = data.getOperaList();
		Iterator<Opera> itr = operaList.iterator();
		while(itr.hasNext()){
			list.addElement(itr.next().getTitle());
		}	
	}
	
	public void search(boolean filter){
		String search = ((JTextField)c).getText();
		list.removeAllElements();
		LinkedList<Opera> operaList = data.getOperaList();
		Iterator<Opera> itr = operaList.iterator();
		
		if(filter){
		while(itr.hasNext()){
			String operaTitle= itr.next().getTitle();
			for(int i=0; i<search.length(); i++){
				if(Character.toLowerCase(search.charAt(i)) == Character.toLowerCase(operaTitle.charAt(i))){
					if( i == search.length()-1 ) list.addElement(operaTitle);
					continue;
				} else {
					break;
				}
			}
		 }
		} else {
			while(itr.hasNext()){
				Opera o = itr.next();
				String operaAuthor= o.getAuthor();
				for(int i=0; i<search.length(); i++){
					if(Character.toLowerCase(search.charAt(i)) == Character.toLowerCase(operaAuthor.charAt(i))){
						if( i == search.length()-1 ) list.addElement(o.getTitle());
						continue;
					} else {
						break;
					}
				}
			 }
		}
	}
}
