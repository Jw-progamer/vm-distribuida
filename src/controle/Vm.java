package controle;

public class Vm {
	private int processos;
	private int memoria;
	private int cpu;

	public Vm(int cpus, int memorias, int processos) {
		super();
		this.processos = processos;
		this.memoria = memorias;
		this.cpu = cpus;
	}

	public String enviarEspecificacao() {
		return "<"+cpu + ";" + memoria + ";" + processos + ">";
	}

	public void receberEspecificações(String espeficacao) {
		espeficacao = espeficacao.replaceAll("<", "");
		espeficacao = espeficacao.replaceAll(">", "");
		espeficacao = espeficacao.replaceAll(" ","");
		String[] plus = espeficacao.split(";");
		for (int i = 0; i < plus.length; i++) {
			System.out.print(plus[i]);
		}
		cpu += Integer.parseInt(plus[0].trim());
		memoria += Integer.parseInt(plus[1].trim());
		processos += Integer.parseInt(plus[2].trim());
		
		
	}

	public int getProcessos() {
		return processos;
	}

	public void setProcessos(int processos) {
		this.processos = processos;
	}

	public int getMemoria() {
		return memoria;
	}

	public void setMemoria(int memoria) {
		this.memoria = memoria;
	}

	public int getCpu() {
		return cpu;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	@Override
	public String toString() {
		return "Cpus: " + cpu + " , Memória: " + memoria + " , Processos disponíveis: " + processos;
	}

}
