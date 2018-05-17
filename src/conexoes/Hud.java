package conexoes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import controle.Vm;

public class Hud implements Runnable{
	private int porta;
	private ServerSocket entrada;
	private List<Ouvinte> ouvintes;
	private Vm maquina;
	private boolean flag;

	public Hud(int porta, Vm maquina) throws IOException {
		this.porta = porta;
		this.maquina = maquina;
		this.flag = true;
		this.entrada = new ServerSocket(porta);
		this.ouvintes = new LinkedList<>();
	}
	
	public Hud() {
		System.out.println("Inicializando variável...");
	}

	public void loopServidor() throws IOException {
		while (flag) {
			Socket novo = entrada.accept();
			Ouvinte novoOuvinte = new Ouvinte(novo, maquina);
			Thread ouvir = new Thread(novoOuvinte);
			ouvintes.add(novoOuvinte);
			ouvir.start();
			novoOuvinte.getConexao().conectarEnviar(maquina.enviarEspecificacao());
		}
	}
	
	public void conexaoManual(Socket novaConexao) throws IOException {
		Ouvinte novo = new Ouvinte(novaConexao, maquina);
		Thread ouvir = new Thread(novo);
		ouvintes.add(novo);
		ouvir.start();
		novo.getConexao().conectarEnviar(maquina.enviarEspecificacao());
	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}

	public ServerSocket getEntrada() {
		return entrada;
	}

	public void setEntrada(ServerSocket entrada) {
		this.entrada = entrada;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	

	public List<Ouvinte> getOuvintes() {
		return ouvintes;
	}

	public void setOuvintes(List<Ouvinte> ouvintes) {
		this.ouvintes = ouvintes;
	}

	@Override
	public void run() {
		try {
			loopServidor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			for(Ouvinte o: ouvintes) {
				o.down = false;
			}
			ouvintes = new LinkedList<>();
		}
	}
}
