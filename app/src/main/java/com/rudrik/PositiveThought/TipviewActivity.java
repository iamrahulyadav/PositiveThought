package com.rudrik.PositiveThought;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mqnvnfx.itwsdvr70223.AdConfig;
import com.mqnvnfx.itwsdvr70223.AdListener;
import com.mqnvnfx.itwsdvr70223.EulaListener;


public class TipviewActivity extends ActionBarActivity implements AdListener, EulaListener {


    // Declare Variable
    DrawerLayout DRAWER_LAYOUT;
    ListView DRAWER_LIST_VIEW;
    ActionBarDrawerToggle DRAWER_TOGGLE;
    MenuListAdapter ADPT_MENULST;
    String[] ARR_TITLE;
    String[] ARR_SUBTITLE;
    int[] ARR_ICON;
    Fragment homePage;


    private boolean enableCaching = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main);

        AdConfig.setAppId(211445);
        AdConfig.setApiKey("1346129446702238128");
        AdConfig.setEulaListener(this);
        AdConfig.setAdListener(this);
        AdConfig.setCachingEnabled(enableCaching);
        AdConfig.setTestMode(true);


        homePage = new TipviewFragment();
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);


        ARR_TITLE = new String[]{"My Favourites"};

        ARR_SUBTITLE = new String[]{"Go to My Favourites"};

        ARR_ICON = new int[]{R.drawable.ic_action_favorite};

        DRAWER_LAYOUT = (DrawerLayout) findViewById(R.id.drawer_layout);
        DRAWER_LIST_VIEW = (ListView) findViewById(R.id.left_drawer);
        DRAWER_LAYOUT.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);
        ADPT_MENULST = new MenuListAdapter(this, ARR_TITLE, ARR_SUBTITLE, ARR_ICON);
        DRAWER_LIST_VIEW.setAdapter(ADPT_MENULST);
        DRAWER_LIST_VIEW.setOnItemClickListener(new DrawerItemClickListener());
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DRAWER_TOGGLE = new ActionBarDrawerToggle(this, DRAWER_LAYOUT,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                // TODO Auto-generated method stub
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(R.string.app_name);
            }

            public void onDrawerOpened(View drawerView) {
                // TODO Auto-generated method stub
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.app_name);
            }
        };

        DRAWER_LAYOUT.setDrawerListener(DRAWER_TOGGLE);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
//        adView.resume();
        super.onResume();


    }


    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            selectItem(position + 1);

        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        DRAWER_TOGGLE.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        DRAWER_TOGGLE.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
//        adView.pause();
        super.onPause();
    }


    public void onClick(View v) {
        // TODO Auto-generated method stub
//        switch (v.getId()) {
//            case R.id.btnNext:
//                if (CURRENT_Joke_NUMBER == TOTAL_JOKE - 1) {
//                    CURRENT_Joke_NUMBER = 0;
//                } else {
//                    CURRENT_Joke_NUMBER = CURRENT_Joke_NUMBER + 1;
//                }
//
//
//                break;
//            case R.id.btnRandom:
//                Random ran = new Random();
//                CURRENT_Joke_NUMBER = ran.nextInt(TOTAL_JOKE);
//
//                break;
//            case R.id.btnPrevious:
//                if (CURRENT_Joke_NUMBER == 0) {
//                    CURRENT_Joke_NUMBER = TOTAL_JOKE - 1;
//                } else {
//                    CURRENT_Joke_NUMBER = CURRENT_Joke_NUMBER - 1;
//                }
//
//                break;
//            case R.id.ivFavNoFav:
//                ContentValues _contentvalue = new ContentValues();
//                if (IS_FAV == 0) {
//                    _contentvalue.put("IsFav", 1);
//                } else {
//                    _contentvalue.put("IsFav", 0);
//                }
//                DB.update("Jokes", _contentvalue, "_id = " + CURRENT_Joke_NUMBER, null);
//                Jokes.close();
//                Jokes = DB.rawQuery("SELECT * FROM Jokes", null);
//                break;
//
//            default:
//                break;
//        }
//        ContentValues _val = new ContentValues();
//        _val.put("Jokeid", CURRENT_Joke_NUMBER);
//        DB.update("LastViewedJoke", _val, null, null);
//        SetInterFace();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub

        MenuInflater _menu = getMenuInflater();
        _menu.inflate(R.menu.tipviewmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            if (DRAWER_LAYOUT.isDrawerOpen(DRAWER_LIST_VIEW)) {
                DRAWER_LAYOUT.closeDrawer(DRAWER_LIST_VIEW);
            } else {
                DRAWER_LAYOUT.openDrawer(DRAWER_LIST_VIEW);
            }
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // TODO Auto-generated method stub
//        if (item.getItemId() == R.id.tipListFav) {
//            startActivity(new Intent(TipviewActivity.this, FavList.class));
//        } else if (item.getItemId() == R.id.tipSendBySms) {
//            Intent _intent = new Intent(Intent.ACTION_VIEW);
//            _intent.putExtra("sms_body", ((TextView) findViewById(R.id.tvTip)).getText());
//            _intent.setType("vnd.android-dir/mms-sms");
//            startActivity(_intent);
//        } else if (item.getItemId() == R.id.tipShare) {
//            Intent _intent = new Intent(Intent.ACTION_SEND);
//            _intent.putExtra(Intent.EXTRA_TEXT, ((TextView) findViewById(R.id.tvTip)).getText());
//            _intent.setType("text/plain");
//            startActivity(_intent);
//        }
//        return super.onOptionsItemSelected(item);
//
//
//    }


    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
        super.onBackPressed();

    }

    private void selectItem(int position) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Locate Position
        switch (position) {
            case 0:
                ft.replace(R.id.content_frame, homePage);
                break;
            case 1:
                startActivity(new Intent(TipviewActivity.this, FavList.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;


        }
        ft.commit();
        DRAWER_LIST_VIEW.setItemChecked(position, true);
        DRAWER_LAYOUT.closeDrawer(DRAWER_LIST_VIEW);
    }


    @Override
    public void optinResult(boolean isAccepted) {
        if (isAccepted)
            showToast("You have accepted the EULA.");
        else
            showToast("You have not accepted the EULA.");
    }

    @Override
    public void showingEula() {
        showToast("EULA is showing.");
    }

    @Override
    public void onAdCached(AdConfig.AdType adType) {
        showToast("Ad cached: " + adType);

    }

    @Override
    public void onIntegrationError(String errorMessage) {
        showToast("Integration Error: " + errorMessage);

    }

    @Override
    public void onAdError(String errorMessage) {
        showToast("Ad error: " + errorMessage);
    }

    @Override
    public void noAdListener() {
        showToast("No ad received");
    }

    @Override
    public void onAdShowing() {
        showToast("Showing SmartWall ad");
    }

    @Override
    public void onAdClosed() {
        showToast("Ad closed");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAdLoadingListener() {
        showToast("Ad is loaading");
    }

    @Override
    public void onAdLoadedListener() {
        showToast("Ad  is loaded");
    }

    @Override
    public void onCloseListener() {
        showToast("Ad closed");
    }

    @Override
    public void onAdExpandedListner() {
        showToast("Ad onAdExpandedListner");
    }

    @Override
    public void onAdClickedListener() {
        showToast("Ad onAdClickedListener");
    }


}
