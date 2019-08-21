package model.entities;

public abstract class Empregado {

	private int id;
	private String nome;
	private String cpf;
	private String sexo;
	private int idade;
	private double salarioBruto;
	private double descontoImpostoDeRenda;
	private double contribuicaoPrevidencia;
	private double salarioBase;
	private double salarioLiquido;

	public Empregado(String nome, String cpf, String sexo, int idade, double salarioBruto) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.sexo = sexo;
		this.idade = idade;
		this.salarioBruto = salarioBruto;
		calcularSalarioBase();
	}

	public Empregado() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public double getSalarioBruto() {
		return salarioBruto;
	}

	public void setSalarioBruto(double salarioBruto) {
		this.salarioBruto = salarioBruto;
	}

	public double getDescontoImpostoDeRenda() {
		return descontoImpostoDeRenda;
	}

	public void setContribuicaoPrevidencia(double contribuicaoPrevidencia) {
		this.contribuicaoPrevidencia = contribuicaoPrevidencia;
	}

	public double getContribuicaoPrevidencia() {
		return contribuicaoPrevidencia;
	}

	public double getSalarioBase() {
		return salarioBase;
	}

	public double getSalarioLiquido() {
		return salarioLiquido;
	}

	public void setSalarioLiquido(double salarioLiquido) {
		this.salarioLiquido = salarioLiquido;
	}

	public void calcularIR() {
		double valorIR = 0;

		if (this.salarioBruto < 1800) {
			valorIR = 0;
		} else if (this.salarioBruto > 1800 && this.salarioBruto < 3000) {
			valorIR = this.salarioBruto * 0.10;
		} else {
			valorIR = this.salarioBruto * 0.15;
		}
		this.descontoImpostoDeRenda = valorIR;
	}

	public void calcularContribuicaoPrevidencia() {

		if (this.idade < 45) {
			setContribuicaoPrevidencia(this.getSalarioBruto() * 0.12);
		} else {
			setContribuicaoPrevidencia(this.getSalarioBruto() * 0.08);
			;
		}

	}

	private void calcularSalarioBase() {
		calcularIR();
		calcularContribuicaoPrevidencia();
		salarioBase = this.salarioBruto - (this.descontoImpostoDeRenda + this.contribuicaoPrevidencia);
	}

	public abstract double calcularSalario();

	@Override
	public String toString() {
		return "Empregado [nome=" + nome + ", cpf=" + cpf + ", sexo=" + sexo + ", idade=" + idade + ", salarioBruto="
				+ salarioBruto + ", descontoImpostoRenda=" + descontoImpostoDeRenda + ", descontoPrevidencia="
				+ contribuicaoPrevidencia + ", salarioBase=" + salarioBase + "]";
	}

}
