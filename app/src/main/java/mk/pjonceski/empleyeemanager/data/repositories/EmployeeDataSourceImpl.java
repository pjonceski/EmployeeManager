package mk.pjonceski.empleyeemanager.data.repositories;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.data.source.local.datasource.EmployeeLocalDataSource;
import mk.pjonceski.empleyeemanager.utils.Optional;

/**
 * Main Employees data provider(local or remote).
 */
public class EmployeeDataSourceImpl implements EmployeeDataSource {
    private EmployeeLocalDataSource employeeLocalDataSource;
    private EmployeeDataSource employeeRemoteDataSource;

    public EmployeeDataSourceImpl(EmployeeLocalDataSource employeeLocalDataSource
            , EmployeeDataSource employeeRemoteDataSource) {
        this.employeeLocalDataSource = employeeLocalDataSource;
        this.employeeRemoteDataSource = employeeRemoteDataSource;
    }

    @Override
    public Flowable<List<Employee>> getAllEmployees() {
        return employeeRemoteDataSource.getAllEmployees()
                .doOnNext(employees -> {
                    employeeLocalDataSource.reinsertAll(employees);
                });
    }

}
