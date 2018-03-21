package mk.pjonceski.empleyeemanager.data.source.remote.retrofit;

import java.util.List;

import mk.pjonceski.empleyeemanager.data.models.Employee;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * All rest api end points are declared here.
 */
public interface RetrofitApi {
    @GET("list")
    Call<List<Employee>> getAllEmployees();
}
