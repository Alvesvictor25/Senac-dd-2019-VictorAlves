package model.entities;

import java.util.ArrayList;

public class Gerencia {

	Gerente gerente;
	ArrayList<Operacional> operacionais;

	public Gerencia(Gerente gerente, ArrayList<Operacional> operacionais) {
		super();
		this.gerente = gerente;
		this.operacionais = operacionais;
	}

	public Gerencia() {
		super();
	}

	public Gerente getGerente() {
		return gerente;
	}

	public void setGerente(Gerente gerente) {
		this.gerente = gerente;
	}

	public ArrayList<Operacional> getOperacionais() {
		return operacionais;
	}

	public void setOperacionais(ArrayList<Operacional> operacionais) {
		this.operacionais = operacionais;
	}

}
