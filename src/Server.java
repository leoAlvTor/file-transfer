import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;
import java.net.UnknownHostException;


public class Server extends JFrame {
    public JTextField txtAdd;
    private JButton btnSend;
    private JLabel lbl1, lbl2;
    private File file;
    private ServerSocket serverSocket;

    public String obtenerIP (){//metodo que obtiene la ip del servidor
        String ip=null;
        try {
            ip=InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }
        return ip;
    }

    public Server() { init(); }
    private Socket socket; private BufferedInputStream bufferedInputStream; private FileInputStream fileInputStream; private OutputStream outputStream; public static final int SOCKET_PORT = 2205;

    public void init() {
        setTitle("Server");
        setSize(399, 399);
        setVisible(true);
        setDefaultCloseOperation(3);
        components();
        setSize(400, 400);
    }

    public void components() {
        this.lbl1 = new JLabel("Please enter the file address");
        this.lbl2= new JLabel("IP del Servidor: "+obtenerIP());
        this.txtAdd = new JTextField(20);
        this.btnSend = new JButton("Send File");

        JPanel pnl1 = new JPanel(new BorderLayout());
        JPanel pnl2 = new JPanel(new FlowLayout());

        pnl1.add(this.lbl1, "North");
        pnl1.add(this.lbl2, "Center");
        pnl2.add(this.btnSend);
        pnl2.add(this.txtAdd);
        pnl1.add(pnl2, "South");

        add(pnl1);




        try {
            this.serverSocket = new ServerSocket(2205);
        } catch (IOException e) {
            e.printStackTrace();
        }


        this.btnSend.addActionListener(e -> {
            try {
                sendFile(this.txtAdd.getText());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }


    public void sendFile(String file2send) throws Exception {
        this.fileInputStream = null;
        this.bufferedInputStream = null;
        this.outputStream = null;
        this.socket = null;

        try {
            System.out.println("Waiting...");
            try {
                this.socket = this.serverSocket.accept();
                this.file = new File(file2send);
                byte[] bytes = new byte[(int)this.file.length()];
                this.fileInputStream = new FileInputStream(this.file);
                this.bufferedInputStream = new BufferedInputStream(this.fileInputStream);
                this.bufferedInputStream.read(bytes, 0, bytes.length);
                this.outputStream = this.socket.getOutputStream();
                this.outputStream.write(bytes, 0, bytes.length);
                this.outputStream.flush();
                System.out.println("Done");
                JOptionPane.showMessageDialog(null,"¡Archivo enviado con exito!");
            } finally {}
            if (this.bufferedInputStream != null)
                this.bufferedInputStream.close();
            if (this.outputStream != null)
                this.outputStream.close();
            if (this.socket != null) {
                this.socket.close();
            }
        }
        finally {

            if (this.serverSocket != null) {
                this.serverSocket.close();
            }
        }
    }

    public static void main(String[] args) { Server server = new Server(); }
}