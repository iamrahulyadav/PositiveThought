package com.rudrik.PositiveThought;


import com.rudrik.PositiveThought.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FavListAdapter extends BaseAdapter {

	private Activity CONTEXT;
	private String[] Jokes;
	
	public FavListAdapter(Activity c, String[] values)
	{
		CONTEXT = c;
		Jokes = values;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return Jokes.length;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return Jokes[position];
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View _row = convertView;
		if(_row == null){
			LayoutInflater _inflater = CONTEXT.getLayoutInflater();
			_row = _inflater.inflate(R.layout.favlistrow,null,false);
		}
		
		TextView _tvId = (TextView) _row.findViewById(R.id.tvId);
		TextView _tvTip = (TextView) _row.findViewById(R.id.tvTip);

		_tvId.setText(String.valueOf(position));
		_tvTip.setText(Jokes[position]);

		return _row;
		
	
	}

}
