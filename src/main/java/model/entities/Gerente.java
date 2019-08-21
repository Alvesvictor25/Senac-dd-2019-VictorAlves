package model.entities;

public class Gerente extends Empregado {

	private double comissao;

	public Gerente() {
		super();
	}

	public Gerente(String nome, String cpf, String sexo, int idade, double salarioBruto, double comissao) {
		super(nome, cpf, sexo, idade, salarioBruto);
	}

	public double getComissao() {
		return comissao;
	}

	public void setComissao(double comissao) {
		this.comissao = comissao;
	}

	@Override
	public double calcularSalario() {
		return this.getSalarioBase() * 0.9 + this.comissao;
	}
	
	@Override
	public String toString() {
		return "Gerente [comissao=" + comissao + "]" + super.toString();
	}

}