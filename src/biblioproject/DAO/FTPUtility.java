package biblioproject.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
 
import org.apache.commons.net.ftp.*;
 

public class FTPUtility {
 
    private String host = "ftp.frank.altervista.org";
    private int port = 21;
    private String username = "frank";
    private String password = "fregno";
 
    private FTPClient ftpClient = new FTPClient();
    private int replyCode;
 
    private OutputStream outputStream;
     
   
    public void connect() throws Exception {
        try {
            ftpClient.connect(host, port);
            replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                throw new Exception("FTP serve refused connection.");
            }
 
            boolean logged = ftpClient.login(username, password);
            if (!logged) {
                // failed to login
                ftpClient.disconnect();
                throw new Exception("Could not login to the server.");
            }
 
            ftpClient.enterLocalPassiveMode();
 
        } catch (IOException ex) {
            throw new Exception("I/O error: " + ex.getMessage());
        }
    }
 
    
    public void uploadFile(String path, String destDir, String fileName) throws Exception {
        try {
            boolean success = ftpClient.changeWorkingDirectory(destDir);
            if (!success) {
                throw new Exception("Could not change working directory to "
                        + destDir + ". The directory may not exist.");
            }
             
            success = ftpClient.setFileType(FTP.BINARY_FILE_TYPE);         
            if (!success) {
                throw new Exception("Could not set binary file type.");
            }
            InputStream is = new FileInputStream(path);
            ftpClient.storeFile(fileName , is);
             
        } catch (IOException ex) {
            throw new Exception("Error uploading file: " + ex.getMessage());
        }
    }
    
    public void deleteFile(String path){
    	try {
			ftpClient.deleteFile(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void createDir(String dir) throws IOException{
    	ftpClient.makeDirectory(dir);
    }
    
    public void removeDir(String dir) throws IOException{
    	ftpClient.removeDirectory(dir);
    }
 
   
    public void writeFileBytes(byte[] bytes, int offset, int length)
            throws IOException {
        outputStream.write(bytes, offset, length);
    }

    public void finish() throws IOException {
        outputStream.close();
        ftpClient.completePendingCommand();
    }
     

    public void disconnect() throws Exception {
        if (ftpClient.isConnected()) {
            try {
                if (!ftpClient.logout()) {
                    throw new Exception("Could not log out from the server");
                }
                ftpClient.disconnect();
            } catch (IOException ex) {
                throw new Exception("Error disconnect from the server: "
                        + ex.getMessage());
            }
        }
    }
}
