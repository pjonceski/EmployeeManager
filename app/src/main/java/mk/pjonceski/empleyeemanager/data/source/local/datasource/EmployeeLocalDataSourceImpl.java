package mk.pjonceski.empleyeemanager.data.source.local.datasource;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.data.source.local.AppDBHelper;
import mk.pjonceski.empleyeemanager.data.source.local.DataMappers;
import mk.pjonceski.empleyeemanager.data.source.local.entities.EmployeeEntityContract;
import mk.pjonceski.empleyeemanager.utils.AppExecutors;
import mk.pjonceski.empleyeemanager.utils.helpers.Helpers;
import mk.pjonceski.empleyeemanager.data.models.Optional;
import mk.pjonceski.empleyeemanager.utils.static_utils.PublishersHelper;

/**
 * This class implements methods that publish data from local storage for the
 * contract {@link mk.pjonceski.empleyeemanager.data.repositories.EmployeeDataSource}.
 */

public class EmployeeLocalDataSourceImpl implements EmployeeLocalDataSource {
    private AppDBHelper appDBHelper;
    private AppExecutors appExecutors;
    private Helpers helpers;

    public EmployeeLocalDataSourceImpl(AppDBHelper appDBHelper, AppExecutors appExecutors, Helpers helpers) {
        this.appDBHelper = appDBHelper;
        this.appExecutors = appExecutors;
        this.helpers = helpers;

    }

    @Override
    public void insert(Employee employee) {
        appExecutors.getDiskIO().execute(() -> appDBHelper.getWritableDatabase().insert(EmployeeEntityContract.TABLE_EMPLOYEE,
                null,
                DataMappers.createFromEmployee(employee))
        );
    }

    @Override
    public void reinsertAll(@Nullable List<Employee> employeesList) {
        if (employeesList == null) {
            return;
        }
        appExecutors.getDiskIO().execute(() -> {
                    SQLiteDatabase db = appDBHelper.getWritableDatabase();
                    db.beginTransaction();
                    db.delete(EmployeeEntityContract.TABLE_EMPLOYEE, null, null);
                    for (int i = 0; i < employeesList.size(); i++) {
                        Employee employee = employeesList.get(i);
                        employee.setId(i + 1);
                        db.insert(EmployeeEntityContract.TABLE_EMPLOYEE,
                                null,
                                DataMappers.createFromEmployee(employee));
                    }
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    helpers.getServiceHelper().startDownloadImagesService();
                }
        );
    }

    @Override
    public Flowable<List<Employee>> getAllEmployees() {
        return Flowable.create(emitter -> {
            emitter.onNext(getAllEmployeesFromSqlLite());
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public Single<Optional<Employee>> getEmployeeById(int id) {
        return PublishersHelper.createSingle(getEmployeeByIdCallable(id));
    }

    @Override
    public List<Employee> getAllEmployeesFromSqlLite() {
        List<Employee> employeeList = new ArrayList<>();
        Cursor cursor = appDBHelper.getWritableDatabase().query(EmployeeEntityContract.TABLE_EMPLOYEE,
                null, null, null, null, null, EmployeeEntityContract.EmployeeColumns.NAME);
        if (cursor != null && cursor.getCount() >= 0) {
            Employee employeeToBeAdded;
            while (cursor.moveToNext()) {
                employeeToBeAdded = DataMappers.createFromCursor(cursor);
                if (employeeToBeAdded != null) {
                    employeeList.add(employeeToBeAdded);
                }
            }
            cursor.close();
        }
        return employeeList;
    }

    @Override
    public Callable<Optional<Employee>> getEmployeeByIdCallable(int id) {
        return () -> {
            Employee employee = null;
            Cursor cursor = appDBHelper.getWritableDatabase().query(EmployeeEntityContract.TABLE_EMPLOYEE,
                    null,
                    EmployeeEntityContract.EmployeeColumns._ID + " = ?",
                    new String[]{String.valueOf(id)},
                    null, null, null);
            if (cursor != null && cursor.getCount() >= 0) {
                if (cursor.moveToFirst()) {
                    employee = DataMappers.createFromCursor(cursor);
                }
                cursor.close();
            }
            return new Optional<>(employee);
        };
    }

    @Override
    public List<Employee> getAllUnscheduledEmployeesForDownloadingAvatarImage() {
        List<Employee> unscheduledEmployees = new ArrayList<>();
        Employee employee;
        Cursor cursor;
        SQLiteDatabase db = appDBHelper.getReadableDatabase();

        cursor = db.query(EmployeeEntityContract.TABLE_EMPLOYEE,
                null,
                EmployeeEntityContract.EmployeeColumns.AVATAR_STATUS + " = ?",
                new String[]{String.valueOf(EmployeeEntityContract.AvatarStatus.UNSCHEDULED)},
                null,
                null,
                EmployeeEntityContract.EmployeeColumns.NAME);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                employee = DataMappers.createFromCursor(cursor);
                unscheduledEmployees.add(employee);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return unscheduledEmployees;
    }

    @Override
    public void updateEmployeeAvatarStatus(int id, int avatarStatus) {
        appExecutors.getDiskIO().execute(() -> {
            SQLiteDatabase db = appDBHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(EmployeeEntityContract.EmployeeColumns.AVATAR_STATUS, avatarStatus);
            db.update(EmployeeEntityContract.TABLE_EMPLOYEE,
                    contentValues,
                    EmployeeEntityContract.EmployeeColumns._ID + "= ?",
                    new String[]{String.valueOf(id)}
            );
        });

    }

    @Override
    public void updateAllEmployeesAvatarStatus(int avatarStatus) {
        appExecutors.getDiskIO().execute(() -> {
            SQLiteDatabase db = appDBHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(EmployeeEntityContract.EmployeeColumns.AVATAR_STATUS, avatarStatus);
            db.update(EmployeeEntityContract.TABLE_EMPLOYEE,
                    contentValues, null, null);
        });

    }
}
