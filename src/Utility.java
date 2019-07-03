import java.io.File;
import java.io.IOException;
import java.util.Scanner;
public class Utility {
	public Utility() {}
	
	public static void main(String args[]) {
		sendName("/home/Leo/Downloads/File.txt");
	}
	
	public static void sendName(String name) {
		// /home/Leo/Downloads/File
		String [] caracteres = name.split("/");
		System.out.println(caracteres[caracteres.length-1]);
		System.out.println("Ingrese la direccion en donde guardar el archivo:");
		Scanner sc = new Scanner(System.in);
		String leido = sc.next();
		
		File file = new File(leido + caracteres[caracteres.length-1]);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
