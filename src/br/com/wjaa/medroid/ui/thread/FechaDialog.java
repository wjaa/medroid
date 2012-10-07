package br.com.wjaa.medroid.ui.thread;

import br.com.wjaa.medroid.utils.ViewUtil;
import android.app.Activity;
import android.app.ProgressDialog;

public class FechaDialog implements Runnable {
	
	private Activity activity;
	private ProgressDialog dialog;
	private String message;
	
	public FechaDialog( Activity activity, ProgressDialog dialog, String message ) {
		this.activity = activity;
		this.dialog = dialog;
		this.message = message;
	}
	
	public FechaDialog( Activity activity, ProgressDialog dialog, int message ) {
		this.activity = activity;
		this.dialog = dialog;
		this.message = activity.getResources().getString( message );
	}
	
	public FechaDialog( Activity activity, ProgressDialog dialog, Throwable exception ) {
		this.activity = activity;
		this.dialog = dialog;
		this.message = exception.getMessage();
	}

	@Override
	public void run() {
		dialog.dismiss();
		//ViewUtil.makeToast( activity, message );
		ViewUtil.makeAlertDialog(activity, message);
	}
}

