package br.com.wjaa.medroid.spider;

import java.util.Date;

import br.com.wjaa.medroid.spider.vo.Categoria;
import br.com.wjaa.medroid.spider.vo.Conta;

/**
 * 
 * @author Wagner Araujo wagner.wjaa@gmail.com
 *
 */
public class MinhasEconomiasPutter {
	
	public static void main(String[] args) {
		
		try{
			Session s = new Session("wag182@gmail.com", "*753951*");

			
			
			Conta conta = s.getContas().get(2);
			Categoria categ = s.getCategorias().get(2);
			s.inserirTransacao(new Date(), "Agora", conta.getId(), categ.getId(), 20.0);
			
			/*Thread.sleep(1000 * 60 * 10);
			System.out.println("10 minutos depois");
			s.inserirTransacao(new Date(), "10 minutos depois", conta.getId(), categ.getId(), 30.0);
			
			
			Thread.sleep(1000 * 60 * 30);
			System.out.println("30 minutos depois");
			s.inserirTransacao(new Date(), "30 minutos depois", conta.getId(), categ.getId(), 30.0);
			
			Thread.sleep(1000 * 60 * 5);
			System.out.println("5 minutos depois");
			s.inserirTransacao(new Date(), "5 minutos depois", conta.getId(), categ.getId(), 30.0);*/
			/*Thread.sleep(3000);
			s.inserirTransacao(new Date(), "Teste3", conta.getId(), categ.getId(), 40.0);
			Thread.sleep(3000);
			s.inserirTransacao(new Date(), "Teste4", conta.getId(), categ.getId(), 50.0);
			Thread.sleep(3000);
			conta = s.getContas().get(3);
			categ = s.getCategorias().get(20);
			
			s.inserirTransacao(new Date(), "Teste5", conta.getId(), categ.getId(), 60.0);
			Thread.sleep(3000);
			
			conta = s.getContas().get(1);
			categ = s.getCategorias().get(40);
			
			s.inserirTransacao(new Date(), "Teste6", conta.getId(), categ.getId(), 70.0);
			Thread.sleep(3000);
			
			s.inserirTransacao(new Date(), "Teste7", conta.getId(), categ.getId(), 70.0);
			Thread.sleep(3000);
			
			s.inserirTransacao(new Date(), "Teste8", conta.getId(), categ.getId(), 70.0);
			Thread.sleep(3000);
			
			s.inserirTransacao(new Date(), "Teste9", conta.getId(), categ.getId(), 70.0);
			Thread.sleep(3000);
			
			s.inserirTransacao(new Date(), "Teste10", conta.getId(), categ.getId(), 70.0);
			Thread.sleep(3000);
			
			s.inserirTransacao(new Date(), "Teste11", conta.getId(), categ.getId(), 70.0);
			Thread.sleep(3000);
			
			s.inserirTransacao(new Date(), "Teste12", conta.getId(), categ.getId(), 70.0);
			Thread.sleep(3000);
			
			s.inserirTransacao(new Date(), "Teste6", conta.getId(), categ.getId(), 70.0);
			Thread.sleep(3000);
			
			s.inserirTransacao(new Date(), "Teste13", conta.getId(), categ.getId(), 70.0);
			Thread.sleep(3000);
			
			s.inserirTransacao(new Date(), "Teste14", conta.getId(), categ.getId(), 70.0);
			Thread.sleep(3000);
			
			s.inserirTransacao(new Date(), "Teste15", conta.getId(), categ.getId(), 70.0);
			*/
			System.out.println("Fim do processo.");
		}catch(Exception ex){
			System.err.println("Erro na aplicacao: ");
			ex.printStackTrace();
		}
		
		
		
	}
	

	
	

	

	
	
	
}
