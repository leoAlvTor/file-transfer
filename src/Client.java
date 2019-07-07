import java.io.*;
import java.net.*;
import java.io.File;
import java.io.IOException;

public class Client {
	//COMPROBANO NUEVA RAMA PEDRO 
    private final static int SOCKET_PORT = 2205;      // you may change this
    private final static String SERVER = "localhost";  // localhost
    private static String pathToSave = "/home/Leo/Documents/Recive/File";
    private final static int FILE_SIZE = 6022386;
    /*
     * 
     * ella no te ama
     * 
     * 
     */
    public static void main(String args[])throws IOException{
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        try {
            sock = new Socket(SERVER, SOCKET_PORT);
            System.out.println("Connecting...");
            byte [] mybytearray  = new byte [FILE_SIZE];
            InputStream is = sock.getInputStream();
            fos = new FileOutputStream(pathToSave);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray,0,mybytearray.length);

            current = bytesRead;
            do {
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length-current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);

            bos.write(mybytearray, 0 , current);
            bos.flush();
            System.out.println("File " + pathToSave
                    + " downloaded (" + current + " bytes read)");
        }
        finally {
            if (fos != null) fos.close();
            if (bos != null) bos.close();
            if (sock != null) sock.close();
        }
    }

    public void createFile(String path){
        try{
            File filesito = new File(path);
            filesito.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        pathToSave = path;
    }
}
