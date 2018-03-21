package mk.pjonceski.empleyeemanager.data.source.local.datasource;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Single;
import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.data.source.local.AppDBHelper;
import mk.pjonceski.empleyeemanager.data.source.local.entities.EmployeeEntityContract;
import mk.pjonceski.empleyeemanager.utils.AppExecutors;
import mk.pjonceski.empleyeemanager.utils.DataMappers;
import mk.pjonceski.empleyeemanager.utils.Optional;
import mk.pjonceski.empleyeemanager.utils.PublishersHelper;

/**
 * This class implements methods that publish data from local storage for the
 * contract {@link mk.pjonceski.empleyeemanager.data.repositories.EmployeeDataSource}.
 */

public class EmployeeLocalDataSourceImpl implements EmployeeLocalDataSource {
    private AppDBHelper appDBHelper;
    private AppExecutors appExecutors;

    public EmployeeLocalDataSourceImpl(AppDBHelper appDBHelper, AppExecutors appExecutors) {
        this.appDBHelper = appDBHelper;
        this.appExecutors = appExecutors;
    }

    @Override
    public void insert(Employee employee) {
        appExecutors.getDiskIO().execute(() -> {
                    appDBHelper.getWritableDatabase().insert(EmployeeEntityContract.TABLE_EMPLOYEE,
                            null,
                            DataMappers.createFromEmployee(employee));
                }
        );
    }

    @Override
    public void reinsertAll(ArrayList<Employee> employeesList) {
        appExecutors.getDiskIO().execute(() -> {
                    SQLiteDatabase db = appDBHelper.getWritableDatabase();
                    db.beginTransaction();
                    db.delete(EmployeeEntityContract.TABLE_EMPLOYEE, null, null);
                    for (Employee employee : employeesList) {
                        db.insert(EmployeeEntityContract.TABLE_EMPLOYEE,
                                null,
                                DataMappers.createFromEmployee(employee));
                    }
                    db.setTransactionSuccessful();
                    db.endTransaction();
                }
        );
    }

    @Override
    public Flowable<List<Employee>> getAllEmployees() {
        return PublishersHelper.createFlowable(getAllEmployeesCallable(), null);
    }

    @Override
    public Single<Optional<Employee>> getEmployeeById(int id) {
        return PublishersHelper.createSingle(getEmployeeWithIdCallable(id), null);
    }

    /**
     * Returns all employees from local database.
     *
     * @return callable that return all employes
     */
    private Callable<List<Employee>> getAllEmployeesCallable() {
        return () -> {
            List<Employee> employeeList = new ArrayList<>();
            Cursor cursor = appDBHelper.getWritableDatabase().query(EmployeeEntityContract.TABLE_EMPLOYEE,
                    null, null, null, null, null, null);
            if (cursor != null && cursor.getCount() >= 0) {
                while (cursor.moveToNext()) {
                    employeeList.add(DataMappers.createFromCursor(cursor));
                }
                cursor.close();
            }
            return employeeList;
        };
    }

    /**
     * Returns one employee for provided id from local database.
     *
     * @return callable that return employee or null if none
     */
    private Callable<Optional<Employee>> getEmployeeWithIdCallable(int id) {
        return () -> {
            Employee employee = null;
            Cursor cursor = appDBHelper.getWritableDatabase().query(EmployeeEntityContract.TABLE_EMPLOYEE,
                    null,
                    EmployeeEntityContract.EmployeeColumns._ID + " = ?",
                    new String[]{String.valueOf(id)},
                    null, null, null);
            if (cursor != null && cursor.getCount() >= 0) {
                while (cursor.moveToFirst()) {
                    employee = DataMappers.createFromCursor(cursor);
                }
                cursor.close();
            }
            return new Optional<>(employee);
        };
    }

}