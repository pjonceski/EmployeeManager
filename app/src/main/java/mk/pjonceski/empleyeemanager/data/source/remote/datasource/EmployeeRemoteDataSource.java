package mk.pjonceski.empleyeemanager.data.source.remote.datasource;

import java.io.IOException;
import java.util.List;

import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.data.repositories.EmployeeDataSource;
import mk.pjonceski.empleyeemanager.data.source.local.datasource.EmployeeLocalDataSourceImpl;

/**
 * Contract for {@link EmployeeLocalDataSourceImpl}.
 * Here are defined methods for persisting and retrieving data from local storage.
 */
public interface EmployeeRemoteDataSource extends EmployeeDataSource {
    /**
     * Returns all employees from rest api.
     * Throws IO Exception for bad endpoint.
     * * @return list of employees or null for network failure.
     */
    List<Employee> getAllEmployeesFromRestApi() throws IOException;


}
