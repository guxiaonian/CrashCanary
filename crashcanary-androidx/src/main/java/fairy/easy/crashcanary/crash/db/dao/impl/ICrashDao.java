package fairy.easy.crashcanary.crash.db.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fairy.easy.crashcanary.crash.db.CrashBean;
import fairy.easy.crashcanary.crash.db.CrashOpenHelper;
import fairy.easy.crashcanary.crash.db.dao.CrashDao;
import fairy.easy.crashcanary.crash.db.dao.CrashData;


public class ICrashDao implements CrashDao {


    private SQLiteDatabase db;

    private CrashOpenHelper helper;

    public ICrashDao(Context context) {
        if (helper != null) {
            return;
        }
        helper = new CrashOpenHelper(context);
    }


    @Override
    public void addCrashBean(CrashBean crashBean) {
        try {
            db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CrashData.TABLE_PERSON._START_TIME, crashBean.getStartTime());
            values.put(CrashData.TABLE_PERSON._DETAILED_ERROR, crashBean.getDetailedError());
            values.put(CrashData.TABLE_PERSON._NORMAL_ERROR, crashBean.getNormalError());
            db.insert(CrashData.TABLE_PERSON.TABLE_NAME, null, values);
        } catch (Exception e) {
            //ignore
        }

    }


    @Override
    public void deleteALL() {
        try {
            db = helper.getWritableDatabase();
            db.delete(CrashData.TABLE_PERSON.TABLE_NAME, null, null);
            db.execSQL("UPDATE sqlite_sequence SET seq = 0 WHERE name='" + CrashData.TABLE_PERSON.TABLE_NAME + "'");
        } catch (Exception e) {
            //ignore
        }

    }

    @Override
    public void closeSQL() {
        try {
            db = helper.getReadableDatabase();
            db.close();
        } catch (Exception e) {
            //ignore
        }
    }

    @Override
    public CrashBean queryCrashBeanById(int id) {
        Cursor cursor = null;
        CrashBean crashBean = null;
        try {
            db = helper.getReadableDatabase();
            String selection = CrashData.TABLE_PERSON._ID + "=?";
            String[] selectionArgs = {id + ""};
            cursor = db.query(CrashData.TABLE_PERSON.TABLE_NAME, null, selection, selectionArgs, null, null, null);
            if (cursor.moveToNext()) {
                int pid = cursor.getInt(cursor.getColumnIndex(CrashData.TABLE_PERSON._ID));
                String pTime = cursor.getString(cursor.getColumnIndex(CrashData.TABLE_PERSON._START_TIME));
                String dError = cursor.getString(cursor.getColumnIndex(CrashData.TABLE_PERSON._DETAILED_ERROR));
                String nError = cursor.getString(cursor.getColumnIndex(CrashData.TABLE_PERSON._NORMAL_ERROR));
                crashBean = new CrashBean(pTime, nError, dError, pid);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return crashBean;
    }

    @Override
    public List<CrashBean> queryAll() {
        List<CrashBean> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            db = helper.getReadableDatabase();
            cursor = db.query(CrashData.TABLE_PERSON.TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                int pid = cursor.getInt(cursor.getColumnIndex(CrashData.TABLE_PERSON._ID));
                String pTime = cursor.getString(cursor.getColumnIndex(CrashData.TABLE_PERSON._START_TIME));
                String dError = cursor.getString(cursor.getColumnIndex(CrashData.TABLE_PERSON._DETAILED_ERROR));
                String nError = cursor.getString(cursor.getColumnIndex(CrashData.TABLE_PERSON._NORMAL_ERROR));
                list.add(new CrashBean(pTime, nError, dError, pid));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    @Override
    public void delCrashBeanById(int id) {
        try {
            db = helper.getReadableDatabase();
            db.delete(CrashData.TABLE_PERSON.TABLE_NAME, CrashData.TABLE_PERSON._ID + "=?", new String[]{id + ""});
        }catch (Exception e){
            //ignore

        }
    }

}
