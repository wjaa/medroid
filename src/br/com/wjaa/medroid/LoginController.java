/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.wjaa.medroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import br.com.wjaa.medroid.spider.SessionFacade;
import br.com.wjaa.medroid.spider.exception.SessionException;
import br.com.wjaa.medroid.ui.thread.FechaDialog;
import br.com.wjaa.medroid.utils.MobileUtils;
import br.com.wjaa.medroid.utils.ViewUtil;


/**
 * 
 * @author wagneraraujo-sao
 *
 */
public final class LoginController extends Activity implements OnClickListener, Runnable{
  
	private static final String TAG = "LoginController";
	private ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState){
    	StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites()
    			.detectNetwork().penaltyLog().build()); 
        Log.v(TAG, "Activity State: onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ViewUtil.getButton(this, R.id.botaoLogin).setOnClickListener(this);
        ViewUtil.getButton(this, R.id.botaoSair).setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		try{
			switch (v.getId()) {
				case R.id.botaoLogin:
					if (MobileUtils.temInternet(this.getApplicationContext())){
						pd = ViewUtil.makeProgressDialog(this, R.string.msg_aguarde_login);
						new Thread(this).start(); 
					}else{
						ViewUtil.makeAlertDialog(this, "Você está sem conexão com a internet.");
					}
					break;
				case R.id.botaoSair: this.finish(); break;
			}
			
		}catch(Exception ex){
			if (pd != null){
				pd.dismiss();
			}
			ViewUtil.makeAlertDialog(this, "Não foi possivel conectar ao serviço. \n Verifique sua conexão de internet.");
		}
		
		
	}

	@Override
	public void run() {
		String user = ViewUtil.getEditTextValue(this, R.id.in_usuario);
		String pass = ViewUtil.getEditTextValue(this, R.id.in_senha);
		
		SessionFacade sm = SessionFacade.getInstance();
		try {
			sm.login(user, pass);
			Intent intent = new Intent( this.getApplicationContext(), MenuController.class  );
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			this.startActivity( intent );
		} catch (SessionException e) {
			Log.e(TAG, "Falha ao autenticar o usuário.\n Usuário ou senha inválido ou falta de conexão com a Internet.", e);
			this.runOnUiThread(new FechaDialog(this, pd, "Usuário ou senha inválida ou falta de conexão com a Internet."));
		}
		if (pd != null){
			pd.dismiss();
		}
		
	}
    
}
