package Pacote;

import java.util.Objects;

public class Perfil {
	private String nome, genero, corCabelo, local, ft;
	private int idade;
	
	
	public Perfil(String nome, String genero, String corCabelo, String local, String ft, int idade) {
		super();
		this.nome = nome;
		this.genero = genero;
		this.corCabelo = corCabelo; 
		this.local = local;
		this.ft = ft;
		this.idade = idade;
	}
	
	public Perfil () {
		
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the genero
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * @param genero the genero to set
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}

	/**
	 * @return the corCabelo
	 */
	public String getCorCabelo() {
		return corCabelo;
	}

	/**
	 * @param corCabelo the corCabelo to set
	 */
	public void setCorCabelo(String corCabelo) {
		this.corCabelo = corCabelo;
	}

	/**
	 * @return the local
	 */
	public String getLocal() {
		return local;
	}

	/**
	 * @param local the local to set
	 */
	public void setLocal(String local) {
		this.local = local;
	}

	/**
	 * @return the ft
	 */
	public String getFt() {
		return ft;
	}

	/**
	 * @param ft the ft to set
	 */
	public void setFt(String ft) {
		this.ft = ft;
	}

	/**
	 * @return the idade
	 */
	public int getIdade() {
		return idade;
	}

	/**
	 * @param idade the idade to set
	 */
	public void setIdade(int idade) {
		this.idade = idade;
	}

	@Override
	public String toString() {
		return "Peril [nome=" + nome + ", genero=" + genero + ", corCabelo=" + corCabelo + ", local=" + local + ", ft="
				+ ft + ", idade=" + idade + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(corCabelo, ft, genero, idade, local, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Perfil other = (Perfil) obj;
		return Objects.equals(corCabelo, other.corCabelo) && Objects.equals(ft, other.ft)
				&& Objects.equals(genero, other.genero) && idade == other.idade && Objects.equals(local, other.local)
				&& Objects.equals(nome, other.nome);
	}

	
	
	
	
	
	
}