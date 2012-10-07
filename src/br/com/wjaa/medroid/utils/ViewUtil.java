package br.com.wjaa.medroid.utils;

import java.util.List;

import br.com.wjaa.medroid.R;
import br.com.wjaa.medroid.spider.adapter.ItemAdapter;
import br.com.wjaa.medroid.spider.vo.Item;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class ViewUtil {

	public static void loadAutoComplete( Activity activity, String[] valores, int idComponent ) {
		AutoCompleteTextView textView = getAutoComplete( activity, idComponent );
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>( activity, R.layout.list_item, valores);
	    textView.setAdapter(adapter);
	}
	
	public static void loadSpinner( Activity activity, List<? extends Item> itens, int idComponent ) {
		final Spinner edit = (Spinner) activity.findViewById( idComponent );
		ItemAdapter itemAdapter = new ItemAdapter(activity.getApplicationContext(), itens);
		edit.setAdapter( itemAdapter );
	}
	
	public static Button getButton( Activity activity, int id ) {
		return (Button) activity.findViewById( id );
	}
	
	public static TextView getTextView( Activity activity, int id ) {
		return (TextView) activity.findViewById( id );
	}
	
	public static ImageView getImageView( Activity activity, int id ) {
		return (ImageView) activity.findViewById( id );
	}
	
	public static RadioButton getRadioButton( Activity activity, int id ) {
		return (RadioButton) activity.findViewById( id );
	}
	
	public static AutoCompleteTextView getAutoComplete( Activity activity, int id ) {
		return (AutoCompleteTextView) activity.findViewById( id );
	}
	
	
	public static Spinner getSpinner( Activity activity, int id ) {
		return (Spinner) activity.findViewById( id );
	}
	
	public static EditText getEditText( Activity activity, int id ) {
		return (EditText) activity.findViewById( id );
	}
	
	public static String getValue( EditText text ) {
		return text.getEditableText() == null ? "" : text.getEditableText().toString().trim();
	}
	
	public static String getEditTextValue( Activity activity, int id ) {
		return getValue( getEditText( activity, id ) );
	}
	
	public static Long getEditTextLongValue( Activity activity, int id ) {
		return getLongValue( getEditText( activity, id ) );
	}
	
	public static Long getLongValue( EditText text ) {
		return Long.valueOf(getValue( text ));
	}
	
	public static Integer getEditTextIntValue( Activity activity, int id ) {
		return getIntValue( getEditText( activity, id ) );
	}
	
	public static Integer getIntValue( EditText text ) {
		return Integer.valueOf(getValue( text ));
	}
	
	
	public static Double getEditTextDoubleValue( Activity activity, int id ) {
		return getDoubleValue( getEditText( activity, id ) );
	}
	
	public static Double getDoubleValue( EditText text ) {
		return Double.valueOf(getValue( text ));
	}
	
	public static void makeToast( Activity activity, String message ) {
		Toast.makeText( activity.getApplicationContext(), message, Toast.LENGTH_LONG).show();
	}
	
	public static void makeToast( Activity activity, int message ) {
		makeToast( activity, activity.getResources().getString( message ) );
	}
	
	public static void makeToast( Activity activity, Throwable e ) {
		makeToast( activity, e.getClass().getName() + " - " + e.getMessage() + " - " + e.getLocalizedMessage() );
	}
	
	public static ProgressDialog makeProgressDialog( Activity activity, String message ) {
		String strAguarde = activity.getResources().getString( R.string.msg_aguarde );
		return ProgressDialog.show( activity, strAguarde, message, true );
	}
	
	public static ProgressDialog makeProgressDialog( Activity activity, int message ) {
		return makeProgressDialog( activity, activity.getResources().getString( message ) );
	}
	
	public static void makeAlertDialog( Activity activity, String message ) {
		OnClickListener listener = new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int id) {
	        	dialog.dismiss();
	        }
	    };
	    makeAlertDialog( activity, message, listener );
	}	
	
	public static void makeAlertDialog( Activity activity, int message ) {
		OnClickListener listener = new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int id) {
	        	dialog.dismiss();
	        }
	    };
	    makeAlertDialog( activity, message, listener );
	}
	
	public static void makeAlertDialog( Activity activity, String message, OnClickListener listener ) {
		AlertDialog.Builder builder = new AlertDialog.Builder( activity );
		builder.setMessage( message );
		builder.setCancelable(false);
		builder.setTitle( R.string.msg_atencao );
		builder.setPositiveButton( android.R.string.ok, listener );
		final AlertDialog alert = builder.create();
		alert.show();
	}	
	
	public static void makeAlertDialog( Activity activity, int message, OnClickListener listener ) {
		makeAlertDialog( activity, activity.getResources().getString( message ), listener );
	}
	
	
	public static void makeYesNoDialog( final Activity activity, int message, OnClickListener yesListener, OnClickListener noListener ) {
		makeYesNoDialog(activity, activity.getResources().getString(message), yesListener, noListener);
	}
	
	public static void makeYesNoDialog( final Activity activity, String message, OnClickListener yesListener, OnClickListener noListener ) {
		AlertDialog.Builder builder = new AlertDialog.Builder( activity );
		builder.setMessage( message );
		builder.setCancelable(false);
		builder.setTitle( R.string.msg_atencao );
		builder.setPositiveButton(R.string.lbl_sim, yesListener );
		builder.setNegativeButton(R.string.lbl_nao, noListener );
		final AlertDialog alert = builder.create();
		alert.show();
	}
	
	public static void makeYesNoDialog( final Activity activity, int message, OnClickListener yesListener ) {
		OnClickListener noListener = new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int id) {
	        	dialog.dismiss();
	        }
	    };
		makeYesNoDialog( activity, message, yesListener, noListener );
	}
	
	public static void makeYesNoDialog( final Activity activity, String message, OnClickListener yesListener ) {
		OnClickListener noListener = new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int id) {
	        	dialog.dismiss();
	        }
	    };
		makeYesNoDialog( activity, message, yesListener, noListener );
	}
	
	public static boolean compareResource( Activity activity, int idResource, String valor ) {
		return activity.getResources().getString( idResource ).equals( valor );
	}
		
	public static void createViewList( ListActivity activity, OnItemClickListener listener, ListAdapter adapter ) {
		activity.setListAdapter( adapter );
		
		ListView listView = activity.getListView();
		listView.setTextFilterEnabled( true );
		listView.setOnItemClickListener( listener );
	}

}