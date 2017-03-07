package com.rudrik.PositiveThought;


import com.rudrik.PositiveThought.R;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class FavList extends ListActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favouritelist);

		DBHelper _helper=new DBHelper(this);
		SQLiteDatabase _db=_helper.getWritableDatabase();
		Cursor _cursor=_db.rawQuery("SELECT * FROM Jokes WHERE IsFav=1", null);
		if (_cursor.getCount()>0) {
			_cursor.moveToFirst();
			String[] _s=new String[_cursor.getCount()];
			for (int i = 0; i < _cursor.getCount(); i++) {
				_s[i]=_cursor.getString(_cursor.getColumnIndex("Joke"));
				_cursor.moveToNext();
			}
			_cursor.close();
			setListAdapter(new  FavListAdapter(this, _s));
		} 
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent _intent = new Intent(this, FavTipView.class);
		_intent.putExtra("Joke", ((TextView) v.findViewById(R.id.tvTip)).getText());
		startActivity(_intent);
	}

}
