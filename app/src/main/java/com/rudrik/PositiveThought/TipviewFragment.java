package com.rudrik.PositiveThought;


import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mqnvnfx.itwsdvr70223.AdView;

import java.lang.reflect.Field;
import java.util.Random;


public class TipviewFragment extends Fragment implements View.OnClickListener {

    private int CURRENT_Joke_NUMBER = 0;
    private int IS_FAV = 0;
    private int TOTAL_JOKE = 0;
    private DBHelper HELPER;
    private SQLiteDatabase DB;
    private Cursor Jokes;
    private ViewPager VP;
    private MyCursorPagerAdapter STATUS_PAGERADPT;
    private LayoutInflater INFLATER;
    private TextView TV_ITEMNO;
    private ImageView _ivFavNoFav;
    View rootView;
    private LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_statusmsg, container, false);

        Button _btnNext = (Button) rootView.findViewById(R.id.btnNext);
        _btnNext.setOnClickListener(this);
        Button _btnPrevious = (Button) rootView.findViewById(R.id.btnPrevious);
        _btnPrevious.setOnClickListener(this);
        _ivFavNoFav = (ImageView) rootView.findViewById(R.id.ivFavNoFav);
        _ivFavNoFav.setOnClickListener(this);
        Button _btnRandom = (Button) rootView.findViewById(R.id.btnShare);
        _btnRandom.setOnClickListener(this);
        TV_ITEMNO = (TextView) rootView.findViewById(R.id.tvItemNumber);
        this.inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        HELPER = new DBHelper(getActivity());
        DB = HELPER.getWritableDatabase();

        //set current tip no
        Cursor _cLastJokeviewd = DB.rawQuery("SELECT * FROM LastViewedJoke", null);
        _cLastJokeviewd.moveToFirst();
        CURRENT_Joke_NUMBER = _cLastJokeviewd.getInt(0);
        _cLastJokeviewd.close();

        //Set total Tip No
        Cursor _cTotalTips = DB.rawQuery("SELECT COUNT(*) FROM Jokes", null);
        _cTotalTips.moveToFirst();
        TOTAL_JOKE = _cTotalTips.getInt(0);
        _cTotalTips.close();

        //Get All the tips In cursor
        Jokes = DB.rawQuery("SELECT * FROM Jokes", null);

//        Cursor _test = DB.rawQuery("Select * FROM Jokes", null);
//        _test.getCount();
//        Toast.makeText(getActivity().getApplicationContext(), String.valueOf(_test.getCount()), Toast.LENGTH_LONG).show();


        VP = (ViewPager) rootView.findViewById(R.id.vpStatusMessage);
        STATUS_PAGERADPT = new MyCursorPagerAdapter();
        VP.setAdapter(STATUS_PAGERADPT);
        VP.setCurrentItem(CURRENT_Joke_NUMBER);
        VP.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
                TV_ITEMNO.setText((VP.getCurrentItem() + 1) + " / " + Jokes.getCount());
                ContentValues _val = new ContentValues();
                _val.put("Jokeid", VP.getCurrentItem());
                DB.update("LastViewedJoke", _val, null, null);
                SetInterFace();
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        setHasOptionsMenu(true);


        AdView adView = (AdView) rootView.findViewById(R.id.myAdView);
        if (adView != null)
            adView.loadAd();

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuInflater mInflater = new MenuInflater(getActivity().getApplicationContext());
        mInflater.inflate(R.menu.tipviewmenu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_refresh) {
//            Random _random = new Random();
//            VP.setCurrentItem(_random.nextInt(ARR_STATUS.size()));
//        } else
        if (item.getItemId() == R.id.tipShare) {
            ShareStatus();
        } else if (item.getItemId() == R.id.tipSendBySms) {

            Intent _intent = new Intent(Intent.ACTION_VIEW);
            View _view = VP.findViewWithTag(getActivity().getResources().getString(R.string.statusview) + VP.getCurrentItem());
            String _status = ((TextView) _view.findViewById(R.id.page_textview)).getText().toString();
            _intent.putExtra("sms_body", _status);
            _intent.setType("vnd.android-dir/mms-sms");
            startActivity(_intent);
        } else if (item.getItemId() == R.id.tipListFav) {
            startActivity(new Intent(getActivity().getApplicationContext(), FavList.class));
            getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else if (item.getItemId() == R.id.tipCopyText) {
            View _view = VP.findViewWithTag(getActivity().getResources().getString(R.string.statusview) + VP.getCurrentItem());
            String _status = ((TextView) _view.findViewById(R.id.page_textview)).getText().toString();
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(_status);
            } else {
                ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = android.content.ClipData.newPlainText("Positive Thought", _status);
                clipboard.setPrimaryClip(clip);
            }
            Toast.makeText(getActivity().getApplicationContext(), "Text Copied To ClipBoard..!!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void ShareStatus() {
        Intent _intent = new Intent(Intent.ACTION_SEND);
        View _view = VP.findViewWithTag(getActivity().getResources().getString(R.string.statusview) + VP.getCurrentItem());
        String _status = ((TextView) _view.findViewById(R.id.page_textview)).getText().toString();
        _intent.putExtra(Intent.EXTRA_TEXT, _status);
        _intent.setType("text/plain");
        startActivity(_intent);
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
            case R.id.btnNext:
                VP.setCurrentItem(VP.getCurrentItem() + 1);
                break;
            case R.id.btnShare:
                Random _random = new Random();
                VP.setCurrentItem(_random.nextInt(Jokes.getCount()));
                break;
            case R.id.btnPrevious:
                VP.setCurrentItem(VP.getCurrentItem() - 1);
                break;
            case R.id.ivFavNoFav:
                ContentValues _contentvalue = new ContentValues();
                if (IS_FAV == 0) {
                    _contentvalue.put("IsFav", 1);
                } else {
                    _contentvalue.put("IsFav", 0);
                }
                DB.update("Jokes", _contentvalue, "_id = " + VP.getCurrentItem(), null);
                Jokes.close();
                Jokes = DB.rawQuery("SELECT * FROM Jokes", null);
                break;

            default:
                break;
        }
        ContentValues _val = new ContentValues();
        _val.put("Jokeid", VP.getCurrentItem());
        DB.update("LastViewedJoke", _val, null, null);
        Jokes.moveToPosition(VP.getCurrentItem());
        SetInterFace();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void SetInterFace() {
        // TODO Auto-generated method stub
        Jokes.moveToPosition(VP.getCurrentItem());

//        TextView _tv = (TextView) findViewById(R.id.tvTip);
//        String _temp = Jokes.getString(1);
//        _tv.setText(_temp);
//
//        setTitle("Thought :" + String.valueOf(CURRENT_Joke_NUMBER + 1) + " / " + TOTAL_JOKE);
        if (Jokes.getInt(2) == 1) {
            _ivFavNoFav.setImageResource(R.drawable.fav);
            IS_FAV = 1;
        } else {
            _ivFavNoFav.setImageResource(R.drawable.nofav);
            IS_FAV = 0;
        }
    }

    // Detach FragmentTabHost
    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // Remove FragmentTabHost
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public class MyCursorPagerAdapter extends PagerAdapter {

        public MyCursorPagerAdapter() {

        }

        @Override
        public int getCount() {
            if (Jokes == null) {
                return 0;
            } else {
                return Jokes.getCount();
            }
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (View) arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View page = inflater.inflate(R.layout.laytextview, null);
            position = position % Jokes.getCount();
            Jokes.moveToPosition(position);

            ((TextView) page.findViewById(R.id.page_textview)).setText(String.valueOf(Jokes.getString(Jokes.getColumnIndex("Joke"))).trim());

            page.setTag(getActivity().getResources().getString(R.string.statusview) + position);
            Log.d("::::", Jokes.getString(Jokes.getColumnIndex("Joke")));
//            messageTextView.setText(cursor.getString(cursor.getColumnIndex("Joke")));
            ((ViewPager) view).addView(page, 0);
            return page;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
            object = null;
        }

        public void swapCursor(Cursor cursor) {
            //if(cursor != null) {

            Jokes = cursor;
            //notifyDataSetChanged();
            //}
        }
    }
}
