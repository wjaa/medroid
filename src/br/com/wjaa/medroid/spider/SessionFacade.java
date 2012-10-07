package br.com.wjaa.medroid.spider;

import java.util.Date;
import java.util.List;

import br.com.wjaa.medroid.spider.exception.SessionException;
import br.com.wjaa.medroid.spider.vo.Categoria;
import br.com.wjaa.medroid.spider.vo.Conta;

/**
 * 
 * @author wagneraraujo-sao
 *
 */
public class SessionFacade {

	private static SessionFacade sf;
	private static Session s;
	private SessionFacade(){}
	
	static{
		sf = new SessionFacade();
	}
	
	
	
	
	public static SessionFacade getInstance(){
		return sf;
	}
	
	
	public void login(String user, String pass) throws SessionException{
		s = new Session(user, pass);
	}
	
	public boolean isLogged(){
		return s != null;
	}
	
	public void inserirTransacao(Date data, String descricao, String idCategoria, String idConta, Double valor) throws SessionException{
		s.inserirTransacao(data, descricao, idCategoria, idConta, valor);
	}
	
	public List<Categoria> getCategorias(){
		return s.getCategorias();
	}
	
	public List<Conta> getContas(){
		return s.getContas();
	}
	
}
