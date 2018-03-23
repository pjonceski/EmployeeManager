package mk.pjonceski.empleyeemanager.data.repositories;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import mk.pjonceski.empleyeemanager.data.models.Employee;

/**
 * In this class are defined methods that publish different types of data for employees.
 */

public interface EmployeeDataSource {

    Flowable<List<Employee>> getAllEmployees();

}
