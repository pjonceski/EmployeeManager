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

    public EmployeeDataSourceImpl(EmployeeLocalDataSource employeeLocalDataSource) {
        this.employeeLocalDataSource = employeeLocalDataSource;
    }

    @Override
    public Flowable<List<Employee>> getAllEmployees() {
        return null;
    }

    @Override
    public Single<Optional<Employee>> getEmployeeById(int id) {
        return null;
    }
}
