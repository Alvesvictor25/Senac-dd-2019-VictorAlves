package model.entities;

import java.util.ArrayList;

public class Diretoria extends Lotacao {

	Diretor diretor;
	ArrayList<Gerente> gerentes;

	public Diretoria(int id, String nome, String sigla, Diretor diretor, ArrayList<Gerente> gerentes) {
		super(id, nome, sigla);
		this.diretor = diretor;
		this.gerentes = gerentes;
	}

	public Diretoria() {
		super();
	}

	public Diretor getDiretor() {
		return diretor;
	}

	public void setDiretor(Diretor diretor) {
		this.diretor = diretor;
	}

	public ArrayList<Gerente> getGerentes() {
		return gerentes;
	}

	public void setGerentes(ArrayList<Gerente> gerentes) {
		this.gerentes = gerentes;
	}

	@Override
	public String toString() {
		return "Diretoria [diretor=" + diretor + ", gerencias=" + gerentes + "]";
	}
}
