package mk.pjonceski.empleyeemanager.data.source.local.datasource;

import java.util.ArrayList;

import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.data.repositories.EmployeeDataSource;

/**
 * Contract for {@link EmployeeLocalDataSourceImpl}.
 * Here are defined methods for persisting and retrieving data from local storage.
 */
public interface EmployeeLocalDataSource extends EmployeeDataSource {
    void insert(Employee employee);

    /**
     * Deletes all persisted Employees and inserts the new ones.
     *
     * @param employeesList the list of employees
     */
    void reinsertAll(ArrayList<Employee> employeesList);

}
