package conexoes;

import java.io.IOException;
import java.net.Socket;

import controle.Vm;

public class Ouvinte implements Runnable {
	private Conexao conexao;
	@SuppressWarnings("unused")
	private Vm maquina;
	boolean down;

	public Ouvinte(Socket ouvinte, Vm maquina) {
		this.conexao = new Conexao(ouvinte, maquina);
		this.maquina = maquina;
		this.down = true;
	}

	public void loopConexao() throws IOException {
		@SuppressWarnings("unused")
		String msg = null;
		while(down) {
			if((msg = conexao.conectarReceber()) == null) {
				//feed.novaMensagem(msg);
				msg = null;
			}else {
				continue;
			}
		}
	}
	
	

	public Conexao getConexao() {
		return conexao;
	}

	public void setConexao(Conexao conexao) {
		this.conexao = conexao;
	}

	@Override
	public void run() {
		try {
			loopConexao();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
