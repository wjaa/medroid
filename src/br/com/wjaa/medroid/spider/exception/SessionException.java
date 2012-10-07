package br.com.wjaa.medroid.spider.exception;

/**
 * 
 * @author Wagner Araujo wagner.wjaa@gmail.com
 *
 */
public class SessionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1550578851629580752L;
	
	public SessionException(String msg){
		super(msg);
	}
	
	public SessionException(String msg, Exception e){
		super(msg, e);
	}

}
