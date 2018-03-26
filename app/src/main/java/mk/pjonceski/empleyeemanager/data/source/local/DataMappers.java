package mk.pjonceski.empleyeemanager.data.source.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.data.source.local.entities.EmployeeEntityContract;

/**
 * Contains methods that help map data from one type to another.
 * 1. From {@link android.database.Cursor} to model {@link mk.pjonceski.empleyeemanager.data.models} for fetching data.
 * 2. From {@link mk.pjonceski.empleyeemanager.data.models} to {@link android.content.ContentValues} for persisting data.
 */

public final class DataMappers {
    private DataMappers() {
    }

    /**
     * This method creates {@link ContentValues} that represents specific {@link Employee}.
     *
     * @param employee the employee to be converted
     * @return the content values to be persisted
     */
    public static ContentValues createFromEmployee(@NonNull Employee employee) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EmployeeEntityContract.EmployeeColumns._ID, employee.getId());
        contentValues.put(EmployeeEntityContract.EmployeeColumns.NAME, employee.getName());
        contentValues.put(EmployeeEntityContract.EmployeeColumns.BIOGRAPHY, employee.getBiography());
        contentValues.put(EmployeeEntityContract.EmployeeColumns.COMPANY_NAME, employee.getCompanyName());
        contentValues.put(EmployeeEntityContract.EmployeeColumns.JOB_TITLE, employee.getJobTitle());
        contentValues.put(EmployeeEntityContract.EmployeeColumns.AVATAR, employee.getAvatar());
        return contentValues;
    }

    /**
     * This method creates {@link Employee} that represents specific
     * row of table {@link EmployeeEntityContract#TABLE_EMPLOYEE} from database.
     * Cursor should have data.
     * Cursor should be closed outside of this method.
     *
     * @param cursor the cursor from which we get the data.
     * @return the populated object employee.
     */
    @Nullable
    public static Employee createFromCursor(@NonNull Cursor cursor) {
        Employee employee = null;
        if (!cursor.isClosed() && !cursor.isAfterLast()) {
            employee = new Employee();
            employee.setId(cursor.getInt(cursor.getColumnIndex(EmployeeEntityContract.EmployeeColumns._ID)));
            employee.setName(cursor.getString(cursor.getColumnIndex(EmployeeEntityContract.EmployeeColumns.NAME)));
            employee.setBiography(cursor.getString(cursor.getColumnIndex(EmployeeEntityContract.EmployeeColumns.BIOGRAPHY)));
            employee.setCompanyName(cursor.getString(cursor.getColumnIndex(EmployeeEntityContract.EmployeeColumns.COMPANY_NAME)));
            employee.setJobTitle(cursor.getString(cursor.getColumnIndex(EmployeeEntityContract.EmployeeColumns.JOB_TITLE)));
            employee.setAvatar(cursor.getString(cursor.getColumnIndex(EmployeeEntityContract.EmployeeColumns.AVATAR)));
            employee.setAvatarStatus(cursor.getInt(cursor.getColumnIndex(EmployeeEntityContract.EmployeeColumns.AVATAR_STATUS)));
        }
        return employee;
    }
}
