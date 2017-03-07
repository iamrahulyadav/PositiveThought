package com.rudrik.PositiveThought;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {


    private static String DB_NAME = "RUD_DATABASE";
    private static int DB_VERSION = 1;
    private static Context CONTEXT;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        // TODO Auto-generated constructor stub
        DBHelper.CONTEXT = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String _createJokeTable = "CREATE TABLE IF NOT EXISTS Jokes(_id INTEGER PRIMARY KEY, Joke VARCHAR, IsFav INTEGER)";
        db.execSQL(_createJokeTable);
        String _createLastViewedJokeTable = "CREATE TABLE IF NOT EXISTS LastViewedJoke(JokeId INTEGER)";
        db.execSQL(_createLastViewedJokeTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }

    public void InitiateDatabase(SQLiteDatabase db) {
        try {
            //Insert Joke In database
            String[] _jokes = CONTEXT.getResources().getStringArray(R.array.Joke);
            int _totalJokesInArray = _jokes.length;

            db.beginTransaction();
            Cursor _cJoke = db.rawQuery("SELECT COUNT(*) FROM Jokes", null);
            _cJoke.moveToFirst();

            int _TotalJokeInDB = _cJoke.getInt(0);
            if (_totalJokesInArray > _TotalJokeInDB) {
                for (int i = _TotalJokeInDB; i < _totalJokesInArray; i++) {
                    ContentValues _vals = new ContentValues();
                    _vals.put("_id", i);
                    _vals.put("Joke", _jokes[i]);
                    _vals.put("IsFav", 0);
                    db.insert("Jokes", null, _vals);
                }
            }

            //Insert Last viewd Joke in Database
            Cursor _cLastViewedJoke = db.rawQuery("SELECT * FROM LastViewedJoke", null);
            if (_cLastViewedJoke.getCount() == 0) {
                ContentValues _vals = new ContentValues();
                _vals.put("JokeId", 0);
                db.insert("LastViewedJoke", null, _vals);
            }
            _cLastViewedJoke.close();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

    }
}
