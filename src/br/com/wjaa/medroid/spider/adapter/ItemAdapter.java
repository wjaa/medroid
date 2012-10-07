package br.com.wjaa.medroid.spider.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.wjaa.medroid.R;
import br.com.wjaa.medroid.spider.vo.Item;

public class ItemAdapter extends BaseAdapter {

	private List<? extends Item> itens;
	private Context context;
	
	public ItemAdapter(Context context, List<? extends Item> itens){
		this.itens = itens;
		this.context = context;
	}
	
	
	@Override
	public int getCount() {
		return itens.size();
	}

	@Override
	public Object getItem(int position) {
		return itens.get(position);
	}

	@Override
	public long getItemId(int position) {
		return Long.valueOf(itens.get(position).getId());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Item i = itens.get(position);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		TextView view = (TextView)inflater.inflate(R.layout.list_item, null);
		view.setText(i.getNome());
		return view;
	}

	

}
