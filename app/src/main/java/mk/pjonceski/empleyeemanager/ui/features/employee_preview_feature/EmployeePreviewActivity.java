package mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import mk.pjonceski.empleyeemanager.R;
import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.ui.base_mvp.BaseMVPActivity;
import mk.pjonceski.empleyeemanager.ui.base_mvp.presenter.BasePresenter;
import mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature.adapter.EmployeeListRecyclerAdapter;
import mk.pjonceski.empleyeemanager.utils.helpers.Helpers;

/**
 * In this activity list of all employees will be shown.
 * User can see employee details with click on one item of the list.
 * On item click navigates to {@link mk.pjonceski.empleyeemanager.ui.features.employee_details_feature.EmployeeDetailsActivity}.
 */
public class EmployeePreviewActivity extends BaseMVPActivity implements EmployeePreviewContract.View {
    @Inject
    EmployeePreviewContract.Presenter presenter;
    @Inject
    Helpers helpers;

    private BroadcastReceiver networkStateChangeReceiver;

    @BindView(R.id.employee_preview_employee_list)
    RecyclerView employeeListRecyclerView;

    @BindView(R.id.employee_preview_list_loading_indicator)
    ProgressBar listLoadingIndicator;

    @BindView(R.id.employee_preview_refresh_data_button)
    FloatingActionButton refreshDataButton;

    @BindView(R.id.has_internet_layout)
    RelativeLayout hasInternetLayout;

    @BindView(R.id.no_internet_layout)
    RelativeLayout noInternetLayout;

    @BindView(R.id.no_internet_close)
    AppCompatImageView noInternetCloseImageView;

    @BindView(R.id.has_internet_close)
    AppCompatImageView hasInternetCloseImageView;

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
        registerNetworkStateChangeReceiver();
    }

    private void setUpScreen() {
        employeeListRecyclerAdapter = new EmployeeListRecyclerAdapter(
                employee -> presenter.onEmployeeChosenFromList(employee),
                helpers);
        employeeListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        employeeListRecyclerView.setAdapter(employeeListRecyclerAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterNetworkStateChangeReceiver();
    }

    @Override
    public void showDataLoadingIndicator() {
        listLoadingIndicator.setVisibility(View.VISIBLE);
        employeeListRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideDataLoadingIndicator() {
        listLoadingIndicator.setVisibility(View.GONE);
        employeeListRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void populateEmployeesList(List<Employee> employeeList) {
        employeeListRecyclerAdapter.setData(employeeList);
    }

    @Override
    public void setConnectivityIndicatorsOffline() {
        setTitle(R.string.employees_preview_title_offline);
        setNetworkLayoutsOffline();
    }

    @Override
    public void clearConnectivityLayoutIndicators() {
        setTitle(getScreenTitle());
        hideNetworkStateLayouts();
    }

    @Override
    public void setInternetAvailableAgainIndicator() {
        setNetworkLayoutsOnline();
    }

    @OnClick(R.id.employee_preview_refresh_data_button)
    void refreshDataButtonClick() {
        presenter.onButtonRefreshDataClick();
    }

    @OnClick(R.id.no_internet_close)
    void noInternetLayoutCloseClick() {
        hideNetworkStateLayouts();
    }

    @OnClick(R.id.has_internet_close)
    void hasInternetLayoutCloseClick() {
        hideNetworkStateLayouts();
    }

    private void registerNetworkStateChangeReceiver() {
        networkStateChangeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                presenter.connectivityChange(helpers.getSystemStateHelper().isInternetAvailable());
            }
        };
        IntentFilter networkStateChangeIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(networkStateChangeReceiver, networkStateChangeIntentFilter);
    }

    private void unRegisterNetworkStateChangeReceiver() {
        if (networkStateChangeReceiver != null) {
            unregisterReceiver(networkStateChangeReceiver);
        }
    }

    private void setNetworkLayoutsOffline() {
        hasInternetLayout.setVisibility(View.GONE);
        noInternetLayout.setVisibility(View.VISIBLE);
    }

    private void setNetworkLayoutsOnline() {
        hasInternetLayout.setVisibility(View.VISIBLE);
        noInternetLayout.setVisibility(View.GONE);
    }

    private void hideNetworkStateLayouts() {
        hasInternetLayout.setVisibility(View.GONE);
        noInternetLayout.setVisibility(View.GONE);
    }
}
