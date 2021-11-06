package br.com.ifpb;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Server {
	public static void main(String[] args) throws  IOException {

		System.out.println("Servidor");
		ServerSocket serverSocket = new ServerSocket(3315);
		Socket socket = serverSocket.accept();

		String caminho = System.getProperty("user.dir") + "\\src\\br\\com\\ifpb\\arquivos";
		File dir = new File(caminho);

		DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
		DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

		while (true) {
			String comando = dataInputStream.readUTF();
			switch (comando) {

			case "readdir":
				System.out.println(socket.getInetAddress() + " readdir");

				File[] arquivos = dir.listFiles();
				List<String> listaFiles = new ArrayList<>();
				if( arquivos != null){
				for (File f : arquivos) {
					if (f.isFile()) {
						listaFiles.add(f.getName());
					}
				}
					dataOutputStream.writeUTF(listaFiles.toString());
					break;
				}

			case "rename":
				File f = new File(caminho + "\\" + dataInputStream.readUTF() + ".txt");
				File rf = new File(caminho + "\\" + dataInputStream.readUTF() + ".txt");
				
				if (f.renameTo(rf)) {
					System.out.println(socket.getInetAddress() + " rename");
				} else {
					 throw new java.io.IOException("Esse nome já existe!");
				}
	
				break;

			case "create":
			
				File fc = new File(caminho + "\\" + dataInputStream.readUTF() + ".txt");
				if (fc.createNewFile()) {
					System.out.println(socket.getInetAddress() + " create");
				} else {
					throw new java.io.IOException("Arquivo já existe!");
				}
				break;
				
			case "remove":
				File fr = new File(caminho + "\\" + dataInputStream.readUTF() + ".txt");
				if(fr.delete()) {
					System.out.println(socket.getInetAddress() + " delete");
				}
				else {
					throw new java.io.IOException("Arquivo não existe!");
				}
				break;

			case "help":
				System.out.println(socket.getInetAddress() + " help");
			}
		}
	}

}