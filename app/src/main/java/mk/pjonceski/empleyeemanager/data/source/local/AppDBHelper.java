package mk.pjonceski.empleyeemanager.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import mk.pjonceski.empleyeemanager.data.source.local.entities.EmployeeEntityContract;

/**
 * The main {@link SQLiteOpenHelper} that creates and handles upgrades to database.
 */
@SuppressWarnings("unused")
public class AppDBHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "employee_manager.db";
    private final static int DATABASE_VERSION = 1;

    public AppDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(EmployeeEntityContract.CREATE_TABLE_EMPLOYEE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
