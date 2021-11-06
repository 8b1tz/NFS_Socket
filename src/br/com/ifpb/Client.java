package br.com.ifpb;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws IOException {

		System.out.println("Cliente");
		Socket socket = new Socket("localhost", 3315);

		DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
		DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

		Scanner escolha = new Scanner(System.in);
		
		while (true) {
			System.out.print(socket.getInetAddress() + " -> ");
			String comando = escolha.next();
			dataOutputStream.writeUTF(comando);

			switch (comando) {
			case "readdir":
				System.out.println(dataInputStream.readUTF());
				break;

			case "rename":
				System.out.print("Arquivo que deseja alterar: ");
				dataOutputStream.writeUTF(escolha.next());
				System.out.print("Novo nome: ");
				dataOutputStream.writeUTF(escolha.next());
				break;

			case "create":
				System.out.print("Nome do arquivo que será criado: ");
				dataOutputStream.writeUTF(escolha.next());
				break;

			case "remove":
				System.out.print("Nome do arquivo que deseja remover: ");
				dataOutputStream.writeUTF(escolha.next());
				break;

			case "help":
				System.out.println("""
							Comandos:
							 readdir - leitura dos arquivos
							 remove  - remove arquivo
							 rename  - renomeia arquivo
							 create - cria arquivo
							 """);
				break;

			default:
				System.out.println("O comando ->"+ comando + "<- não existe");
			}
		}
	}
}
