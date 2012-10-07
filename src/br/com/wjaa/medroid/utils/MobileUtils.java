package br.com.wjaa.medroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 
 * @author wagneraraujo-sao
 *
 */
public class MobileUtils {

	
	/**
	 * 
	 * @param contexto
	 * @return
	 */
	public static boolean temInternet(Context contexto){  
	      
		//Pego a conectividade do contexto o qual o metodo foi chamado
	    ConnectivityManager cm = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);  
	      
	  //Crio o objeto netInfo que recebe as informacoes da NEtwork
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();  
	      
	    //Se o objeto for nulo ou nao tem conectividade retorna false
	    if ( netInfo != null && netInfo.isConnected() && netInfo.isAvailable() ){  
	        return true;  
	    }
	    else{  
	        return false;
	    }
	}  
	
}
