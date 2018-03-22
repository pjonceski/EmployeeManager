package mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import mk.pjonceski.empleyeemanager.R;
import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.ui.base_mvp.BaseMVPActivity;
import mk.pjonceski.empleyeemanager.ui.base_mvp.presenter.BasePresenter;

/**
 * In this activity list of all employees will be shown.
 * User can see employee details with click on one item of the list.
 * On item click navigates to {@link mk.pjonceski.empleyeemanager.ui.features.employee_details_feature.EmployeeDetailsActivity}.
 */
public class EmployeePreviewActivity extends BaseMVPActivity implements EmployeePreviewContract.View {
    @Inject
    EmployeePreviewContract.Presenter presenter;

    @BindView(R.id.employee_preview_employee_list)
    RecyclerView employeeListRecyclerView;

    @BindView(R.id.employee_preview_list_loading_indicator)
    ProgressBar listLoadingIndicator;

    @BindView(R.id.employee_preview_refresh_data_button)
    FloatingActionButton refreshDataButton;

    public BasePresenter getBasePresenter() {
        return presenter;
    }

    private EmployeeListRecyclerAdapter employeeListRecyclerAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.employee_preview_layout;
    }

    @Override
    public int getScreenTitle() {
        return R.string.employees_preview_title;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpScreen();
    }

    private void setUpScreen() {
        employeeListRecyclerAdapter = new EmployeeListRecyclerAdapter(
                employee -> {
                    presenter.onEmployeeChosenFromList(employee);
                }
        );
        employeeListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        employeeListRecyclerView.setAdapter(employeeListRecyclerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void showProgress() {
        listLoadingIndicator.setVisibility(View.VISIBLE);
        employeeListRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        listLoadingIndicator.setVisibility(View.GONE);
        employeeListRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void populateEmployeesList(List<Employee> employeeList) {
        employeeListRecyclerAdapter.setData(employeeList);
    }

    @OnClick(R.id.employee_preview_refresh_data_button)
    void refreshDataButtonClick() {
        presenter.onButtonRefreshDataClick();
    }
    
}
