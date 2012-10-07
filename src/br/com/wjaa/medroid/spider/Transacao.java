package br.com.wjaa.medroid.spider;

import java.util.ArrayList;
import java.util.List;

/*
 * 
 * 
 */
public class Transacao {

	private Integer tid;
	private List<Dados> data;
	private String action = "queue";
	private String method = "transacaoSalvar";
	private Integer clientid = 1;
	
	
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	public List<Dados> getData() {
		return data;
	}
	public void setData(List<Dados> data) {
		this.data = data;
	}
	public void addData(Dados data){
		if (this.data == null){
			this.data = new ArrayList<Dados>();
		}
		this.data.add(data);  
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Integer getClientid() {
		return clientid;
	}
	public void setClientid(Integer clientid) {
		this.clientid = clientid;
	}

	
}
