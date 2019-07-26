import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;




public class Client
  extends JFrame
{
  private JTextField txtName;
  private JTextField txtPath;
  private ActionListener taskPerformer;
  private Timer timer;
  private boolean flag;
  private int counter;
  private static final int SOCKET_PORT = 2205;
  private static final String SERVER = "10.10.10.10";
  private static String pathToSave = ""; private static final int FILE_SIZE = 9999999;
  
  public Client() {
    this.counter = 0;
    init();
  }
  
  public void init() {
    setTitle("Cliente");
    setDefaultCloseOperation(3);
    setSize(299, 100);
    setVisible(true);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());
    components();
    setSize(350, 100);
  }
  
  public void components() {
    JPanel pnl1 = new JPanel(new GridLayout(2, 2));
    JPanel pnl2 = new JPanel(new FlowLayout());
    
    JButton btnStart = new JButton("Iniciar Recepcion");
    btnStart.addActionListener(e -> {
          try {
            recibir();
          } catch (IOException e1) {
            
            e1.printStackTrace();
          } 
        });
    JButton btnClean = new JButton("Limpiar");
    btnClean.addActionListener(e -> clean());
    
    JLabel lblName = new JLabel("Nombre del archivo:");
    JLabel lblPath = new JLabel("Direccion de recepcion:");
    
    this.txtName = new JTextField(30);
    this.txtPath = new JTextField(30);
    
    pnl1.add(lblName, new GridLayout(1, 1));
    pnl1.add(this.txtName, new GridLayout(1, 2));
    pnl1.add(lblPath, new GridLayout(2, 1));
    pnl1.add(this.txtPath, new GridLayout(2, 2));
    
    pnl2.add(btnStart);
    pnl2.add(btnClean);
    
    add(pnl1, "Center");
    add(pnl2, "South");
  }
  
  public void clean() {
    this.txtName.setText("");
    this.txtPath.setText("");
    this.txtName.requestFocus();
  }
  
  public void recibir() throws IOException {
    sendName(this.txtName.getText());
    
    int current = 0;
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    Socket sock = null;
    try {
      try {
        sock = new Socket("10.10.10.10", 2205);
      } catch (Exception e) {
        System.out.println("ERROR ERROR ERROR ERROR");
      } 
      System.out.println("Connecting...");
      byte[] mybytearray = new byte[9999999];
      InputStream is = sock.getInputStream();
      fos = new FileOutputStream(pathToSave);
      bos = new BufferedOutputStream(fos);
      int bytesRead = is.read(mybytearray, 0, mybytearray.length);
      current = bytesRead;
      do {
        bytesRead = is.read(mybytearray, current, mybytearray.length - current);
        if (bytesRead < 0) continue;  current += bytesRead;
      } while (bytesRead > -1);
      bos.write(mybytearray, 0, current);
      bos.flush();
      System.out.println("File " + pathToSave + " downloaded (" + current + " bytes read)");
    } finally {
      
      if (fos != null) fos.close(); 
      if (bos != null) bos.close(); 
      if (sock != null) sock.close(); 
    } 
  }
  
  public void sendName(String file_name) {
    System.out.println(file_name);
    String leido = this.txtPath.getText();
    File file = new File(String.valueOf(leido) + file_name);
    try {
      file.createNewFile();
      pathToSave = String.valueOf(leido) + file_name;
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }

  
  public static void main(String[] args) { Client client = new Client(); }
}