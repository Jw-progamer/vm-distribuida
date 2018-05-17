package controle;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import conexoes.Hud;
import conexoes.Ouvinte;

public class Principal {
	public static Scanner teclado = new Scanner(System.in);

	public static void statusMaquina(Vm maquina) {
		System.out.println("Status atual da maquina:" + maquina);
	}

	public static void println(String msg) {
		System.out.println(msg);
	}
	public static void main(String[] args) {
		System.out.print("Digite a quantidade de cpus inicial:");
		int cpus = teclado.nextInt();
		System.out.print("Digite a quantidade de memória inicial:");
		int memorias = teclado.nextInt();
		System.out.print("Digite ps procesos dispiníveis inicialmente:");
		int processos = teclado.nextInt();
		System.out.print("Digite a porta:");
		int porta = teclado.nextInt();

		Vm maquina = new Vm(processos, memorias, cpus);
		statusMaquina(maquina);
		Hud servidor = new Hud();
		try {
			servidor = new Hud(porta, maquina);
			Thread runServidor = new Thread(servidor);
			runServidor.start();
			println("O servidor foi aberto na porta 7077");
		} catch (IOException e) {
			println("Problemas na abertura do servidor");
		}

		do {
			println("Agora, digite a opção desejada:\n" + "	Conectar com alguém(1)\n"
					+ "	Visualizar o status da sua máquina(2)\n" + "	Visualizar as conexões que existem(3)\n"
					+ "Qual a sua escolha:");
			int decisao = teclado.nextInt();
			teclado.nextLine();
			switch (decisao) {
			case 1:
				println("Digite o endereço ip para se conectar: ");
				String ip = teclado.nextLine();
				println("Digite a porta para se conectar: ");
				int pteste = teclado.nextInt();
				teclado.nextLine();
				try {
					Socket nova = new Socket(ip, pteste);
					servidor.conexaoManual(nova);
				} catch (UnknownHostException e) {
					println("Erro de endereço desconhecido na conexão.");
				} catch (IOException e) {
					println("Erro de entrada e saida na aberturade conexão.");
				}
				break;

			case 2:
				println("Staus atual da máquina: " + maquina);
				break;

			case 3:
				for (Ouvinte o : servidor.getOuvintes()) {
					if(o.getConexao().isConnect()) {
						println(o.getConexao().toString());
					}
				}
				break;
			default:
				println("Por favor digite ua opção válida");
				break;
			}
		} while (true);

	}

}
