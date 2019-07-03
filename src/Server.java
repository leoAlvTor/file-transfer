import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame {

    public JTextField txtAdd;
    private JButton btnSend;
    private JLabel lbl1;

    private File file;
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedInputStream bufferedInputStream;
    private FileInputStream fileInputStream;
    private OutputStream outputStream;

    private int inttry = 0;

    public final static int SOCKET_PORT = 2205;

    public JProgressBar progressBar;

    public Server(){
        init();
    }

    public void init(){
        this.setTitle("Server");
        this.setSize(399,399);
        this.setVisible(true);
        this.setDefaultCloseOperation(3);
        components();
    }

    public void components(){
        lbl1 = new JLabel("Please enter the file address");
        txtAdd = new JTextField(20);
        btnSend = new JButton("Send File");
        progressBar = new JProgressBar();

        JPanel pnl1 = new JPanel(new BorderLayout());
        JPanel pnl2 = new JPanel(new FlowLayout());

        pnl1.add(lbl1, BorderLayout.NORTH);
        pnl1.add(progressBar, BorderLayout.CENTER);
        pnl2.add(btnSend);
        pnl2.add(txtAdd);
        pnl1.add(pnl2, BorderLayout.SOUTH);

        add(pnl1);

        try {
            serverSocket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }


        btnSend.addActionListener(e -> {
            try {
                sendFile(txtAdd.getText());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }

	public void sendFile(String file2send) throws Exception{
        inttry += 1;

        JOptionPane.showMessageDialog(this, "Try number: "+ inttry);

        fileInputStream = null;
        bufferedInputStream = null;
        outputStream = null;
        //serverSocket = null;
        socket = null;

        try{
            while (true){
                System.out.println("Waiting...");
                try{
                    socket = serverSocket.accept();
                    file = new File(file2send);
                    byte [] bytes = new byte[(int)file.length()];
                    fileInputStream = new FileInputStream(file);
                    bufferedInputStream = new BufferedInputStream(fileInputStream);
                    bufferedInputStream.read(bytes, 0, bytes.length);
                    outputStream = socket.getOutputStream();
                    outputStream.write(bytes, 0, bytes.length);
                    outputStream.flush();
                    System.out.println("Done");

                }finally {
                    if(bufferedInputStream != null)
                        bufferedInputStream.close();
                    if(outputStream != null)
                        outputStream.close();
                    if(socket != null)
                        socket.close();
                    break;
                }
            }
        }finally {
            if(serverSocket != null)
                serverSocket.close();
        }
    }

    public static void main(String args[]){
        Server server = new Server();
    }
}

