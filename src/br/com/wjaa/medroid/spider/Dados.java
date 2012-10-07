package br.com.wjaa.medroid.spider;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Wagner Araujo wagner.wjaa@gmail.com
 *
 */
public class Dados {

	
	private char tipoTransacaoId = 'D';
	private String dataTransacao; //:"11/09/2012",
	private String descricao; //:"fakkeeee",
	private String categoriaText; //":"",
	private String categoriaId = ""; //":"1346430",
	private String contaId = ""; //:"119095",
	private String contaDestinoId = ""; //":"",
	private String valor = ""; //:"10,00",
	private char consolidada = 'Y';
	private String qtdeDias = ""; //:"",
	private String tipoRepetir = ""; //:"",
	private String nota = ""; //:"",
	private String id = ""; //:"",
	private String timestamp = ""; //:"",
	private String futuras = ""; //:"",
	private String transferenciaId = ""; //:"",
	private String selectedId = ""; //:""
	
	
	public char getTipoTransacaoId() {
		return tipoTransacaoId;
	}
	public void setTipoTransacaoId(char tipoTransacaoId) {
		this.tipoTransacaoId = tipoTransacaoId;
	}
	public String getDataTransacao() {
		return dataTransacao;
	}
	public void setDataTransacao(Date dataTransacao) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		this.dataTransacao = sdf.format(dataTransacao);
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getCategoriaText() {
		return categoriaText;
	}
	public void setCategoriaText(String categoriaText) {
		this.categoriaText = categoriaText;
	}
	public String getCategoriaId() {
		return categoriaId;
	}
	public void setCategoriaId(String categoriaId) {
		this.categoriaId = categoriaId;
	}
	public String getContaId() {
		return contaId;
	}
	public void setContaId(String contaId) {
		this.contaId = contaId;
	}
	public String getContaDestinoId() {
		return contaDestinoId;
	}
	public void setContaDestinoId(String contaDestinoId) {
		this.contaDestinoId = contaDestinoId;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		DecimalFormat df = new DecimalFormat("#,###.00");  
		this.valor = df.format(valor);
	}
	public char getConsolidada() {
		return consolidada;
	}
	public void setConsolidada(char consolidada) {
		this.consolidada = consolidada;
	}
	public String getQtdeDias() {
		return qtdeDias;
	}
	public void setQtdeDias(String qtdeDias) {
		this.qtdeDias = qtdeDias;
	}
	public String getTipoRepetir() {
		return tipoRepetir;
	}
	public void setTipoRepetir(String tipoRepetir) {
		this.tipoRepetir = tipoRepetir;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getFuturas() {
		return futuras;
	}
	public void setFuturas(String futuras) {
		this.futuras = futuras;
	}
	public String getTransferenciaId() {
		return transferenciaId;
	}
	public void setTransferenciaId(String transferenciaId) {
		this.transferenciaId = transferenciaId;
	}
	public String getSelectedId() {
		return selectedId;
	}
	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}
	
}
