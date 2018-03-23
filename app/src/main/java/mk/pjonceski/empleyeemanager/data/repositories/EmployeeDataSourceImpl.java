package mk.pjonceski.empleyeemanager.data.repositories;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
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
            return employeeRemoteDataSource.getAllEmployees()
                    .doOnNext(employees -> {
                        employeeLocalDataSource.reinsertAll(employees);
                    });
        } else {
            return employeeLocalDataSource.getAllEmployees();
        }
    }

}
