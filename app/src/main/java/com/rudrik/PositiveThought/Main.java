package com.rudrik.PositiveThought;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.*;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Main extends Activity {
    private static String sID = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        // Create Database
        DBHelper _helper = new DBHelper(this);
        SQLiteDatabase _db = _helper.getWritableDatabase();
        _helper.InitiateDatabase(_db);
        _helper.close();

        //Open TipView page
        Button _btn = (Button) findViewById(R.id.btnStart);
        _btn.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(new Intent(Main.this, TipviewActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            try {
//                Airpush _ap = new Airpush(getApplicationContext());
//                _ap.startSmartWallAd();
            } catch (Exception e) {/* Suppress Airpush errors*/}
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub


        getMenuInflater().inflate(R.menu.mainviewmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        if (item.getItemId() == R.id.mainListFav) {
            startActivity(new Intent(Main.this, FavList.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
        return super.onOptionsItemSelected(item);
    }


}
