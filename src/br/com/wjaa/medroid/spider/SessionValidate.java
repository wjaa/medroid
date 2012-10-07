package br.com.wjaa.medroid.spider;

import java.util.Date;

/**
 * 
 * @author wagneraraujo-sao
 *
 */
class SessionValidate {

	
	private Date dateSession;
	private int min;
	
	/**
	 * 
	 * @param date
	 * @param limit
	 */
	public SessionValidate(Date date, int min){
		this.dateSession = date;
		this.min = min;
	}
	
	
	public boolean isSessionValid(){
		long dif =  new Date().getTime() - this.dateSession.getTime(); 
		dif = dif / 1000l / 60l;
		System.out.println(dif);
		return dif < this.min;
		
	}
	
	public void updateTimeSession(Date date){
		this.dateSession = date;
	}
	
}
