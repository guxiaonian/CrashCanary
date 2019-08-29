package fairy.easy.crashcanary.crash.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fairy.easy.crashcanary.crash.db.dao.CrashData;


public class CrashOpenHelper extends SQLiteOpenHelper {
    public CrashOpenHelper(Context context) {
        super(context, CrashData.DATABASE_NAME, null, CrashData.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + CrashData.TABLE_PERSON.TABLE_NAME +
                "(" +
                CrashData.TABLE_PERSON._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CrashData.TABLE_PERSON._START_TIME + " TEXT," +
                CrashData.TABLE_PERSON._DETAILED_ERROR + " TEXT," +
                CrashData.TABLE_PERSON._NORMAL_ERROR + " TEXT" +
                ")";

        db.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
