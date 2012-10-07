package br.com.wjaa.medroid.spider.vo;

/**
 * 
 * @author Wagner Araujo wagner.wjaa@gmail.com
 *
 */
public class Conta implements Item {

	private String id;
	private String nome;
	
	
	public Conta(String id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString(){
		return this.nome;
	}
	
}
