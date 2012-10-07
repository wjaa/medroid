package br.com.wjaa.medroid;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import br.com.wjaa.medroid.spider.SessionFacade;
import br.com.wjaa.medroid.spider.exception.SessionException;
import br.com.wjaa.medroid.spider.vo.Categoria;
import br.com.wjaa.medroid.spider.vo.Conta;
import br.com.wjaa.medroid.ui.thread.FechaDialog;
import br.com.wjaa.medroid.utils.ViewUtil;

/**
 * 
 * @author wagneraraujo-sao
 *
 */
public class InputController extends Activity implements OnClickListener, Runnable {
	
	private ProgressDialog pd;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.input);
		SessionFacade sessionFacade = SessionFacade.getInstance();
		List<Categoria> categorias = sessionFacade.getCategorias();
		List<Conta> contas = sessionFacade.getContas();
		ViewUtil.loadSpinner(this, categorias, R.id.in_categoria);
		ViewUtil.loadSpinner(this,contas, R.id.in_conta);
		
		ViewUtil.getButton(this, R.id.botaoInserir).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		pd = ViewUtil.makeProgressDialog(this, R.string.msg_aguarde_insert);
		new Thread( this ).start(); 
	}

	private void limpaCampos() {
		ViewUtil.getTextView(this, R.id.in_descricao).setText("");
		ViewUtil.getSpinner(this, R.id.in_categoria).setSelection(0);
		ViewUtil.getSpinner(this, R.id.in_conta).setSelection(0);
		ViewUtil.getTextView(this, R.id.in_valor).setText("");
		
	}

	@Override
	public void run() {
		SessionFacade sf = SessionFacade.getInstance();
		
		DatePicker dpData = (DatePicker)this.findViewById(R.id.in_data);
		TextView tvDescricao = ViewUtil.getTextView(this, R.id.in_descricao);
		Spinner spCategoria = ViewUtil.getSpinner(this, R.id.in_categoria);
		Spinner spConta = ViewUtil.getSpinner(this, R.id.in_conta);
		TextView tvValor = ViewUtil.getTextView(this, R.id.in_valor);
		Calendar c = GregorianCalendar.getInstance();
		c.set(dpData.getYear(), dpData.getMonth(),dpData.getDayOfMonth());
		
		
		try {
			sf.inserirTransacao(c.getTime(), tvDescricao.getText().toString(), String.valueOf(spCategoria.getSelectedItemId()), 
					String.valueOf(spConta.getSelectedItemId()), Double.valueOf(tvValor.getText().toString()));
			
			this.runOnUiThread(new FechaDialog(this, pd, "Transação enviada com sucesso!"));
			this.runOnUiThread(new Clean());
			
		} catch (NumberFormatException e) {
			this.runOnUiThread(new FechaDialog(this, pd, "Valor da transação inválida"));
		} catch (SessionException e) {
			this.runOnUiThread(new FechaDialog(this, pd, e.getMessage()));
			
		}
		
	}

	class Clean implements Runnable{

		@Override
		public void run() {
			InputController.this.limpaCampos();
		}
		
	}
}
