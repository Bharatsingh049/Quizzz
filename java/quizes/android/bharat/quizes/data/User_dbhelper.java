package quizes.android.bharat.quizes.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import quizes.android.bharat.quizes.data.User_contract.Quiz_entry;
import quizes.android.bharat.quizes.data.User_contract.User_entry;

public class User_dbhelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="User_data.db";
    private static final int DATABASE_VERSION=1;
    public User_dbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    String SQL_CREATE_QUIZ_TABLE="CREATE TABLE "+Quiz_entry.Table_Name+"("
            +Quiz_entry.Id+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +Quiz_entry.Column_Quiz_Question_Category+" TEXT NOT NULL, "
            +Quiz_entry.Column_Quiz_Question+" TEXT NOT NULL, "
            +Quiz_entry.Column_Quiz_Correct_Answer+" TEXT NOT NULL, "
            +Quiz_entry.Column_Quiz_Incorrect_First+" TEXT NOT NULL, "
            +Quiz_entry.Column_Quiz_Incorrect_Second+" TEXT NOT NULL, "
            +Quiz_entry.Column_Quiz_Incorrect_Third+" TEXT NOT NULL);";

    String SQL_CREATE_USER_TABLE=" CREATE TABLE "+User_entry.Table_Name+"("
            +User_entry.Column_User_Name+" TEXT NOT NULL, "
            +User_entry.Column_User_Number+" TEXT NOT NULL);";

    db.execSQL(SQL_CREATE_USER_TABLE);
    db.execSQL(SQL_CREATE_QUIZ_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
