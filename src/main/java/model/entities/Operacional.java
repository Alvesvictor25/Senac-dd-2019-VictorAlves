package model.entities;

public class Operacional extends Empregado {

	public Operacional() {
		super();
	}

	public Operacional(String nome, String cpf, String sexo, int idade, double salarioBruto) {
		super(nome, cpf, sexo, idade, salarioBruto);
	}

	@Override
	public double calcularSalario() {
		return this.getSalarioBase() * 0.85;
	}

	@Override
	public String toString() {
		return "EmpregadoOperacional []" + super.toString();

	}

}