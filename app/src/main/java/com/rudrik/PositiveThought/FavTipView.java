package com.rudrik.PositiveThought;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class FavTipView extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favtipview);

        String _value = getIntent().getExtras().getString("Joke");
        TextView _tv = (TextView) findViewById(R.id.tvTip);
        _tv.setText(_value);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.favtipmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == R.id.favtipmenuSendbySMS) {
            Intent _intent = new Intent(Intent.ACTION_VIEW);
            _intent.putExtra("sms_body", ((TextView) findViewById(R.id.tvTip)).getText());
            _intent.setType("vnd.android-dir/mms-sms");
            startActivity(_intent);
        } else if (item.getItemId() == R.id.favtipmenuShare) {
            Intent _intent = new Intent(Intent.ACTION_SEND);
            _intent.putExtra(Intent.EXTRA_TEXT, ((TextView) findViewById(R.id.tvTip)).getText());
            _intent.setType("text/plain");
            startActivity(_intent);
        } else if (item.getItemId() == R.id.favtipCopyText) {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(((TextView) findViewById(R.id.tvTip)).getText());
            } else {
                ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = android.content.ClipData.newPlainText("Positive Thought", ((TextView) findViewById(R.id.tvTip)).getText());
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(getApplicationContext(), "Text Copied To ClipBoard..!!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
