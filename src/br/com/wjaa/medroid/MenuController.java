package br.com.wjaa.medroid;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import br.com.wjaa.medroid.utils.ViewUtil;

/**
 * 
 * @author wagneraraujo-sao
 *
 */
public class MenuController extends ListActivity implements OnItemClickListener {

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ViewUtil.createViewList( this, this, new ArrayAdapter<Object>( this, R.layout.list_menu, getResources().getStringArray( R.array.menuItems ) ) );
		
	}
	
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String menuItem = (String)parent.getAdapter().getItem( position );
		if( ViewUtil.compareResource( this,  R.string.mnu_inserir, menuItem ) ) {
			 Class<? extends Activity> classActivity = InputController.class;
			this.startActivity( new Intent( this, classActivity ) );
		} else if( ViewUtil.compareResource( this,  R.string.mnu_sair, menuItem ) ) {
			this.finish();
		}
	}
	
}
