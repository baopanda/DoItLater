package dunght.example.com.doitlater.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import dunght.example.com.doitlater.model.Remind;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "doit";
    public static final String TABLE_REMIND = "remind";

    private static final String REMIND_ID = "id";
    private static final String REMIND_IMG = "img";
    private static final String REMIND_DATE = "date";
    private static final String REMIND_TIME = "time";
    private static final String REMIND_STATE = "state";
    private static final String REMIND_TITLE = "title";
    private static final String REMIND_CONTENT = "content";
    private static final String REMIND_PHONE = "phone";


    public static final int DATABSE_VERSION = 1;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlRemindQuery = "CREATE TABLE " + TABLE_REMIND + " ("
                + REMIND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + REMIND_IMG + " INT, "
                + REMIND_DATE + " TEXT, "
                + REMIND_TIME + " TEXT, "
                + REMIND_STATE + " TEXT, "
                + REMIND_TITLE + " TEXT, "
                + REMIND_CONTENT + " TEXT,"
                + REMIND_PHONE + " TEXT )";

        sqLiteDatabase.execSQL(sqlRemindQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REMIND);
        onCreate(sqLiteDatabase);
    }

    public void addRemind(Remind remind) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REMIND_IMG, remind.getImg());
        contentValues.put(REMIND_DATE, remind.getDate());
        contentValues.put(REMIND_TIME, remind.getTime());
        contentValues.put(REMIND_STATE, remind.getState());
        contentValues.put(REMIND_TITLE, remind.getTitle());
        contentValues.put(REMIND_CONTENT, remind.getContent());
        contentValues.put(REMIND_PHONE, remind.getPhone());
        db.insert(TABLE_REMIND, null, contentValues);
        db.close();
    }

    public boolean isExist(Remind remind) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {REMIND_ID};
        String selection = REMIND_IMG + " = ?" + " AND " + REMIND_DATE + " = ?" + " AND " + REMIND_TIME + " = ?"
                + " AND " + REMIND_STATE + " = ?" + " AND " + REMIND_TITLE + " = ?" + " AND " + REMIND_CONTENT + " = ?";
        String[] selectionArgs = {String.valueOf(remind.getImg()), remind.getDate(), remind.getTime(), remind.getState(), remind.getTitle(), remind.getContent()};
        Cursor cursor = db.query(TABLE_REMIND, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public ArrayList<Remind> getAllRemind() {
        ArrayList<Remind> listRemind = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_REMIND;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Remind remind = new Remind();
                remind.setId(cursor.getInt(0));
                remind.setImg(cursor.getInt(1));
                remind.setDate(cursor.getString(2));
                remind.setTime(cursor.getString(3));
                remind.setState(cursor.getString(4));
                remind.setTitle(cursor.getString(5));
                remind.setContent(cursor.getString(6));
                remind.setPhone(cursor.getString(7));
                listRemind.add(remind);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listRemind;
    }

    public void updateRemind(Remind remind) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(REMIND_DATE, remind.getDate());
        values.put(REMIND_TIME, remind.getTime());
        values.put(REMIND_STATE, remind.getState());
        values.put(REMIND_TITLE, remind.getTitle());
        values.put(REMIND_CONTENT, remind.getContent());
        values.put(REMIND_PHONE, remind.getPhone());
        db.update(TABLE_REMIND, values, REMIND_ID + " = ?", new String[]{String.valueOf(remind.getId())});
    }

    public void deleteRemind(Remind remind) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMIND, REMIND_ID + " = ?", new String[]{String.valueOf(remind.getId())});
        db.close();
    }
}
