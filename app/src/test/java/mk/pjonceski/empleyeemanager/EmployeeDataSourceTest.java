package mk.pjonceski.empleyeemanager;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.data.repositories.EmployeeDataSource;
import mk.pjonceski.empleyeemanager.data.source.local.datasource.EmployeeLocalDataSource;
import mk.pjonceski.empleyeemanager.data.source.remote.datasource.EmployeeRemoteDataSource;
import mk.pjonceski.empleyeemanager.data.source.remote.datasource.EmployeeRemoteDataSourceImpl;
import mk.pjonceski.empleyeemanager.data.source.remote.retrofit.RetrofitApi;
import mk.pjonceski.empleyeemanager.utils.helpers.SystemStateHelper;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This test is for {@link mk.pjonceski.empleyeemanager.data.repositories.EmployeeDataSource}
 */
public class EmployeeDataSourceTest {
    private List<Employee> fakeSqlLiteEmployeeData;
    private List<Employee> fakeRemoteEmployeeData;

    @Before
    public void initFakeData() {
        fakeSqlLiteEmployeeData = new ArrayList<>();
        fakeSqlLiteEmployeeData.add(new Employee("Petar", "Petar Biography", "Info Biro", "Android Developer", ""));

        fakeRemoteEmployeeData = new ArrayList<>();
        fakeRemoteEmployeeData.add(new Employee("Trajko", "Trajko Biography", "Info Biro", "Web Developer", ""));
    }

    @Test
    public void dataOfflineTest() {
        SystemStateHelper systemStateHelper = mock(SystemStateHelper.class);
        RetrofitApi retrofitApi = mock(RetrofitApi.class);
        EmployeeLocalDataSource employeeLocalDataSource = mock(EmployeeLocalDataSource.class);
        EmployeeRemoteDataSource employeeRemoteDataSource = mock(EmployeeRemoteDataSource.class);

        EmployeeDataSource employeeDataSource = new EmployeeRemoteDataSourceImpl(retrofitApi);

        when(systemStateHelper.isInternetAvailable()).thenReturn(false);
        when(employeeLocalDataSource.getAllEmployeesFromSqlLite()).thenReturn(fakeSqlLiteEmployeeData);
        try {
            when(employeeRemoteDataSource.getAllEmployeesFromRestApi()).thenReturn(fakeRemoteEmployeeData);
        } catch (IOException ioException) {
        }

        employeeDataSource.getAllEmployees()
                .subscribe(employees -> {
                    assertEquals(employees.get(0).getName(), fakeSqlLiteEmployeeData.get(0).getName());
                }, throwable -> {
                });
    }

    @Test
    public void dataOnlineTest() {
        SystemStateHelper systemStateHelper = mock(SystemStateHelper.class);
        RetrofitApi retrofitApi = mock(RetrofitApi.class);
        EmployeeLocalDataSource employeeLocalDataSource = mock(EmployeeLocalDataSource.class);
        EmployeeRemoteDataSource employeeRemoteDataSource = mock(EmployeeRemoteDataSource.class);

        EmployeeDataSource employeeDataSource = new EmployeeRemoteDataSourceImpl(retrofitApi);

        when(systemStateHelper.isInternetAvailable()).thenReturn(true);
        when(employeeLocalDataSource.getAllEmployeesFromSqlLite()).thenReturn(fakeSqlLiteEmployeeData);
        try {
            when(employeeRemoteDataSource.getAllEmployeesFromRestApi()).thenReturn(fakeRemoteEmployeeData);
        } catch (IOException ioException) {
        }

        employeeDataSource.getAllEmployees()
                .subscribe(employees -> {
                    assertEquals(employees.get(0).getName(), fakeRemoteEmployeeData.get(0).getName());
                }, throwable -> {
                });
    }


}
