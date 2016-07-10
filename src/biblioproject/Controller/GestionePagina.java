package biblioproject.Controller;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.swing.JList;

import biblioproject.DAO.*;
import biblioproject.Model.Action;
import biblioproject.Model.Biblioteca;
import biblioproject.Model.Page;
import biblioproject.Model.User;
import biblioproject.View.RevisioneFrame;
import biblioproject.View.RevisioneImgFrame;
import biblioproject.View.RevisioneTxtFrame;
import biblioproject.View.TrascriviFrame;
import biblioproject.Model.Opera;

public class GestionePagina extends AbstractAction implements Gestione  {
	
	private static final long serialVersionUID = 9048207555952726122L;
	
	private Biblioteca data;
	private JFrame frame;
	private BufferedImage image;
	private int zoom = 1;
	private Component c;
	private Object c2;
	private Object o;
	private Object o2;
	
	public GestionePagina(JFrame f, Biblioteca data, Component c, Object c2, Object o, Object o2){
		this.frame = f;
		this.data  = data;
		this.c     = c;
		this.c2    = c2;
		this.o     = o;
		this.o2    = o2;
	}
	
    public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()){
		case "Scegli File"              : loadImage(); break;
		case "Zoom +"                   : zoomIn();    break;
		case "Zoom -"                   : zoomOut();   break;
		case "+"                        : zoomIn();    break;
		case "-"                        : zoomOut();   break;
		case "Upload"                   : add();       break;
		case "Trascrivi"                : try {	writeText();} catch (Exception e2) {e2.printStackTrace();} break;
		case "Revisiona Acquisizione"   : try {	reviewImg();} catch (Exception e1) {e1.printStackTrace();} break;
		case "Revisiona Trascrizione"   : try {	reviewTxt();} catch (Exception e1) {e1.printStackTrace();} break;
		case "Approva"                  : try { changeStatusImg(true);	} catch (Exception e1) {e1.printStackTrace();} break;
		case "Rifiuta"                  : try { changeStatusImg(false);} catch (Exception e1) {e1.printStackTrace();} break;
		case "Approvalo"                : try { changeStatusTxt(true);	} catch (Exception e1) {e1.printStackTrace();} break;
		case "Rifiutalo"                : try { changeStatusTxt(false);} catch (Exception e1) {e1.printStackTrace();} break;
		case "Elimina Pagina"           : if(((JList)c).getSelectedValue()==null){JOptionPane.showMessageDialog(new JFrame(), "      Seleziona pagina","Attenzione", JOptionPane.WARNING_MESSAGE);break;}remove();    break;
		case "Elimina Testo"            : if(((JList)c).getSelectedValue()==null){JOptionPane.showMessageDialog(new JFrame(), "      Seleziona pagina","Attenzione", JOptionPane.WARNING_MESSAGE);break;};edit();      break;
		case "Upload XML file"          : try  {uploadText();}  catch (Exception e1) { e1.printStackTrace();}     break;
		default                         :              break;
		}
	}

	public void add() {
		
		boolean flag = true;
		
		if (((JTextField) c).getText().equals("") || ((JTextField) c2).getText().equals("")){
			flag = false;
			JOptionPane.showMessageDialog(new JFrame(), "Tutti i campi sono obbligatori","Attenzione", JOptionPane.WARNING_MESSAGE);
		}
		
		if(flag){
		Opera toAdd = ((Opera)o2);
		int pagenum = Integer.parseInt(((JTextField) c2).getText());
		if(pagenum > toAdd.getPages() ){
			JOptionPane.showMessageDialog(new JFrame(), "L'opera ha "+toAdd.getPages()+" pagine","Attenzione", JOptionPane.WARNING_MESSAGE);
			flag = false;
		}
		LinkedList<Page> pages = data.getPageList();
		Iterator<Page> itr = pages.iterator();
		while(itr.hasNext()){
			Page next = itr.next();
			if(next.getNumber() == pagenum && next.getOperaID() == toAdd.getId() ){
				JOptionPane.showMessageDialog(new JFrame(), "Pagina già esistente","Attenzione", JOptionPane.WARNING_MESSAGE);
				flag = false;
			}
		 }
		
		
		if(flag){
		FTPUtility ftp = new FTPUtility();
		try {
			ftp.connect();
			ftp.uploadFile(((JTextField) c).getText(), toAdd.getTitle(), pagenum+".jpg");
			ftp.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Page newpage = new Page(0, toAdd.getId(), pagenum, "" , toAdd.getTitle()+"/"+pagenum+".jpg", 0 );
		data.getPageList().add(newpage);
		PageDAO DAOpage = new PageDAO();
		DAOpage.add(newpage.toArray());
		Action newaction = new Action(0, ((User)o).getId(), ((Opera)o2).getId(), pagenum, 0, 0);
		data.getActionList().add(newaction);
		ActionDAO DAOaction = new ActionDAO();
		DAOaction.add(newaction.toArray());
		JOptionPane.showMessageDialog(new JFrame(), "      Upload Completato");
		frame.dispose();
		}
	 }
	}

	
	public void remove() {
		int selected = (int)((JList)c).getSelectedValue();
		Iterator<Page> itr = data.getPageList().iterator();
		while(itr.hasNext()){
			Page next = itr.next();
			if(next.getNumber() == selected){
				PageDAO DAO = new PageDAO();
				DAO.delete(next.getID());
				FTPUtility ftp = new FTPUtility();
				try {
					ftp.connect();
					ftp.deleteFile(next.getImage());
					ftp.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
				data.setPageList();
				break;
			}
		}
		DefaultListModel model = new DefaultListModel();
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for (Page p: data.getPageList()){
			arr.add(p.getNumber());
		}
		Collections.sort(arr);
		for (int i: arr){
			model.addElement(i);
		}
		((JList)c).setModel(model);
			
	}

	
	public void edit() {
		
			int selected = (int)((JList)c).getSelectedValue();
			Iterator<Page> itr = data.getPageList().iterator();
			while(itr.hasNext()){
				Page next = itr.next();
				if(next.getNumber() == selected){
					if(next.getStatus()>1){
					next.setText("");
					next.setStatus(1);
					PageDAO DAO = new PageDAO();
					DAO.edit(next.toArray());
					data.getPageList();
					}else JOptionPane.showMessageDialog(new JFrame(), "Pagina non ancora trascritta","Attenzione", JOptionPane.WARNING_MESSAGE);
						
					break;
			 }
			}
					
	}
	
	public void changeStatusImg(boolean status) throws Exception {
		
		String operaTitle = ((String)o);
		int pageNum       = ((int)o2);
		Opera opera = null;
		Page page   = null;
		LinkedList<Opera> operaList = data.getOperaList();
		Iterator<Opera> itrO = operaList.iterator();
		while(itrO.hasNext()){
			Opera next = itrO.next();
			if(next.getTitle().equals(operaTitle)) opera = next;
		}
		
		int count = 0;
		LinkedList<Page> pageList = data.getPageList();
		Iterator<Page> itrP = pageList.iterator();
		while(itrP.hasNext()){
			Page next = itrP.next();
			if(next.getNumber() == pageNum && next.getOperaID() == opera.getId()){
				page = next;
				break;
			}
			count++;
		}
		
		LinkedList<Action> actionList = data.getActionList();
		Iterator<Action> itr = actionList.iterator();
		while(itr.hasNext()){
			Action next = itr.next();
			if(next.getOperaID() == opera.getId() && next.getPage() == page.getNumber() ){
				if(status){
					page.setStatus(1);
					next.setStatus(1);
					PageDAO DAO = new PageDAO();
					DAO.edit(page.toArray());
					ActionDAO DAOact = new ActionDAO();
					DAOact.edit(next.toArray());
				} else {
					next.setStatus(2);
					PageDAO DAO = new PageDAO();
					DAO.delete(page.getID());
					data.getPageList().remove(count);
					ActionDAO DAOact = new ActionDAO();
					DAOact.edit(next.toArray());
					FTPUtility ftp = new FTPUtility();
					ftp.connect();
					ftp.deleteFile(page.getImage());
					ftp.disconnect();
				}
				break;
			}
		}
		RevisioneFrame f = new RevisioneFrame(data,"img");
		f.setVisible(true);
		frame.dispose();
	}
	
public void changeStatusTxt(boolean status) throws Exception {
		
		String operaTitle = ((String)o);
		int pageNum       = ((int)o2);
		Opera opera = null;
		Page page   = null;
		LinkedList<Opera> operaList = data.getOperaList();
		Iterator<Opera> itrO = operaList.iterator();
		while(itrO.hasNext()){
			Opera next = itrO.next();
			if(next.getTitle().equals(operaTitle)) opera = next;
		}
		
		LinkedList<Page> pageList = data.getPageList();
		Iterator<Page> itrP = pageList.iterator();
		while(itrP.hasNext()){
			Page next = itrP.next();
			if(next.getNumber() == pageNum && next.getOperaID() == opera.getId()){
				page = next;
				break;
			}
		}
		
		LinkedList<Action> actionList = data.getActionList();
		Iterator<Action> itr = actionList.iterator();
		while(itr.hasNext()){
			Action next = itr.next();
			if(next.getOperaID() == opera.getId() && next.getPage() == page.getNumber() ){
				if(status){
					page.setStatus(3);
					next.setStatus(1);
					String text = new String(Base64.encode(page.getText().getBytes()));
					page.setText(text);
					PageDAO DAO = new PageDAO();
					DAO.edit(page.toArray());
					ActionDAO DAOact = new ActionDAO();
					DAOact.edit(next.toArray());
				} else {
					next.setStatus(2);
					page.setStatus(1);
					page.setText("");
					PageDAO DAO = new PageDAO();
					DAO.edit(page.toArray());
					ActionDAO DAOact = new ActionDAO();
					DAOact.edit(next.toArray());
				}
				break;
			}
		}
		data.setPageList();
		frame.dispose();
	}
	
	public void writeText() throws Exception{
		JTable table = ((JTable)c);
		DefaultTableModel model = ((DefaultTableModel)o);
		int row = table.getSelectedRow();
		if(row != -1){
		TrascriviFrame f = new TrascriviFrame(data, ((User)o2), (String)model.getValueAt(row, 0), (int)model.getValueAt(row, 1));
		f.setVisible(true);
		}else{
			JOptionPane.showMessageDialog(new JFrame(), "Seleziona una pagina da trascrivere","Attenzione", JOptionPane.WARNING_MESSAGE);
		}
		frame.dispose();
	}
	
	public void reviewImg() throws Exception {
		JTable table = ((JTable)c);
		DefaultTableModel model = ((DefaultTableModel)o);
		int row = table.getSelectedRow();
		if(row != -1){
		RevisioneImgFrame f = new RevisioneImgFrame(data, (String)model.getValueAt(row, 0), (int)model.getValueAt(row, 2));
		f.setVisible(true);
		}else{
			JOptionPane.showMessageDialog(new JFrame(), "Seleziona un acquisizione","Attenzione", JOptionPane.WARNING_MESSAGE);
		}
		frame.dispose();
	}
	
	public void reviewTxt() throws Exception {
		JTable table = ((JTable)c);
		DefaultTableModel model = ((DefaultTableModel)o);
		int row = table.getSelectedRow();
		if(row != -1){
			
			String xml = "";
			String html = "";
			int operaid = 0;
			Iterator<Opera> itro = data.getOperaList().iterator();
			while(itro.hasNext()){
				Opera next = itro.next();
				if(next.getTitle().equals((String)model.getValueAt(row, 0))){
					operaid = next.getId();
					break;
				}
			}
			Iterator<Page> itrp = data.getPageList().iterator();
			while(itrp.hasNext()){
				Page next = itrp.next();
				if(next.getOperaID() == operaid && next.getNumber() == (int)model.getValueAt(row, 2)){
				    xml = next.getText();
					if(xml.length() > 0 ){
					TransformerFactory fact = new net.sf.saxon.TransformerFactoryImpl();
			        Source xslt = new StreamSource(new File("TEI/html/html.xsl"));
			        Transformer transformer = fact.newTransformer(xslt);
			        
			        StringWriter outWriter = new StringWriter();
			        StreamResult result = new StreamResult( outWriter );
			        Source text = new StreamSource(new StringReader(xml));
			        transformer.transform(text, result);
			        html = outWriter.getBuffer().toString();	
					}
				}
			}
			
		RevisioneTxtFrame f = new RevisioneTxtFrame(data, (String)model.getValueAt(row, 0), (int)model.getValueAt(row, 2), html);
		}else{
			JOptionPane.showMessageDialog(new JFrame(), "Seleziona un acquisizione","Attenzione", JOptionPane.WARNING_MESSAGE);
		}
		frame.dispose();
	}
	
	public void loadImage(){
		BufferedImage preview= null;
		JFileChooser fileChooser = new JFileChooser();
		int n = fileChooser.showOpenDialog(this.frame);
		if (n == JFileChooser.APPROVE_OPTION) {
			File path = fileChooser.getSelectedFile();
		    ((JTextField) c).setText(path.getAbsolutePath());
			
			try {
				image= ImageIO.read(path);
				preview = scaleImage(526,601,ImageIO.read(path));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			ImageIcon imageIcon = new ImageIcon(preview);
		    ((JLabel)c2).setIcon(imageIcon);
		    ((JButton)o).setEnabled(true);
		    ((JButton)o2).setEnabled(true);
		    this.frame.revalidate();
			this.frame.repaint();
		}
	}
	
	public void zoomIn(){
		BufferedImage preview = null;
		try {
			zoom+=1;
			preview = scaleImage(526*zoom,601*zoom,image);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		ImageIcon imageIcon = new ImageIcon(preview);
		((JLabel)c2).setIcon(imageIcon);
	}
	
	public void zoomOut(){
		BufferedImage preview = null;
		 if(zoom!=1){
			try {
				zoom-=1;
				preview = scaleImage(526*zoom,601*zoom,image);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			ImageIcon imageIcon = new ImageIcon(preview);
			((JLabel)c2).setIcon(imageIcon);
		 }
	}
	
	public void uploadText() throws IOException{
		JFileChooser fileChooser = new JFileChooser();
		int n = fileChooser.showOpenDialog(this.frame);
		if (n == JFileChooser.APPROVE_OPTION) {
			File path = fileChooser.getSelectedFile();
			BufferedReader br = new BufferedReader(new FileReader(new File(path.getAbsolutePath())));
			String line;
			StringBuilder sb = new StringBuilder();

			while((line=br.readLine())!= null){
			    sb.append(line.trim());
			}
			
			for(int i = 0; i<data.getPageList().size(); i++){
				Page next = data.getPageList().get(i);
				if(next.getNumber() == ((int)o2)){
					Opera nextopera = new Opera(next.getOperaID());
					if(nextopera.getTitle().equals(((String)o))){
						String str =Base64.encode(sb.toString().getBytes());
						next.setText(str);
						next.setStatus(2);
						Action azione = new Action(0, ((User)c2).getId(), next.getOperaID(), next.getNumber(), 1, 0 );
						ActionDAO DAOact = new ActionDAO();
						DAOact.add(azione.toArray());
						PageDAO DAO = new PageDAO();
						DAO.edit(next.toArray());
						JOptionPane.showMessageDialog(new JFrame(), "      Upload testo completato");
						data.setPageList();
						data.setActionList();
						this.frame.dispose();
					}
				}
			}
			PageDAO DAO = new PageDAO();
			
		}			
	}
	
	public void setImage(BufferedImage i){
		this.image = i;
	}
	
	public BufferedImage scaleImage(int w, int h, BufferedImage img) throws Exception {
	    BufferedImage bi;
	    bi = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
	    Graphics2D g2d = (Graphics2D) bi.createGraphics();
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
	    g2d.drawImage(img, 0, 0, w, h, null);
	    g2d.dispose();
	    return bi;
	}

}
