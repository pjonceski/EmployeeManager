package mk.pjonceski.empleyeemanager.data.repositories;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.data.source.local.datasource.EmployeeLocalDataSource;
import mk.pjonceski.empleyeemanager.data.source.remote.datasource.EmployeeRemoteDataSource;
import mk.pjonceski.empleyeemanager.utils.helpers.Helpers;

/**
 * Main Employees data provider(local or remote).
 */
public class EmployeeDataSourceImpl implements EmployeeDataSource {
    private EmployeeLocalDataSource employeeLocalDataSource;
    private EmployeeRemoteDataSource employeeRemoteDataSource;
    private Helpers helpers;

    public EmployeeDataSourceImpl(EmployeeLocalDataSource employeeLocalDataSource,
                                  EmployeeRemoteDataSource employeeRemoteDataSource,
                                  Helpers helpers) {
        this.employeeLocalDataSource = employeeLocalDataSource;
        this.employeeRemoteDataSource = employeeRemoteDataSource;
        this.helpers = helpers;
    }

    @Override
    public Flowable<List<Employee>> getAllEmployees() {
        if (helpers.getSystemStateHelper().isInternetAvailable()) {
            return Flowable.create(emitter -> {
                emitter.onNext(this.getEmployeesFromLocalOrRemoteStorage());
                emitter.onComplete();
            }, BackpressureStrategy.BUFFER);
        } else {
            helpers.getSharedPrefHelper().setDataSourceIsFromLocalStorage();
            return employeeLocalDataSource.getAllEmployees();
        }
    }

    /**
     * This method returns list of all employees from network if request to endpoint is success.
     * If request fails than it retrieves data from local storage.
     *
     * @return the list of employees
     */
    @NonNull
    private List<Employee> getEmployeesFromLocalOrRemoteStorage() {
        List<Employee> employeesList = null;
        try {
            employeesList = employeeRemoteDataSource.getAllEmployeesFromRestApi();
        } catch (IOException ex) {
            /**Problem finding endpoint or other critical network problem.*/
        }
        if (employeesList == null) {
            /**Network failure.*/
            helpers.getSharedPrefHelper().setDataSourceIsFromLocalStorage();
            employeesList = employeeLocalDataSource.getAllEmployeesFromSqlLite();
        } else {
            /**Data is from cloud. It is cached to local storage.*/
            helpers.getSharedPrefHelper().setDataSourceIsFromRemoteStorage();
            employeeLocalDataSource.reinsertAll(employeesList);
        }
        return employeesList;
    }

}
