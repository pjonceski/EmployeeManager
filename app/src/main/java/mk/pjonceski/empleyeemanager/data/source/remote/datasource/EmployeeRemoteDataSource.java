package mk.pjonceski.empleyeemanager.data.source.remote.datasource;

import java.util.List;
import java.util.concurrent.Callable;

import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.data.repositories.EmployeeDataSource;
import mk.pjonceski.empleyeemanager.data.source.local.datasource.EmployeeLocalDataSourceImpl;

/**
 * Contract for {@link EmployeeLocalDataSourceImpl}.
 * Here are defined methods for persisting and retrieving data from local storage.
 */
public interface EmployeeRemoteDataSource extends EmployeeDataSource {
    Callable<List<Employee>> getAllEmployeesCallable();

}
