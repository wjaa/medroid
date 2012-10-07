package br.com.wjaa.medroid.spider;


/**
 * 
 * @author Wagner Araujo wagner.wjaa@gmail.com
 *
 */
public class Values {

	private String timestamp;
	private String id;
	private String valorInicial;
	private Boolean ativo;
	private String tipoContaId;
	private String nome;
	private String saldo;
	
	private String nestedLevel;
	private String text;
	private String parentId;
	
	
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValorInicial() {
		return valorInicial;
	}
	public void setValorInicial(String valorInicial) {
		this.valorInicial = valorInicial;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public String getTipoContaId() {
		return tipoContaId;
	}
	public void setTipoContaId(String tipoContaId) {
		this.tipoContaId = tipoContaId;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSaldo() {
		return saldo;
	}
	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}
	public String getNestedLevel() {
		return nestedLevel;
	}
	public void setNestedLevel(String nestedLevel) {
		this.nestedLevel = nestedLevel;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	
	public boolean isCategoria(){
		return this.nestedLevel != null && !"".equals(this.nestedLevel) && 
				this.text != null  && !"".equals(this.text);
	}
	
	public boolean isConta(){
		return this.nome != null && !"".equals(this.nome);
	}
	
}
