package mk.pjonceski.empleyeemanager.ui.features.employee_details_feature;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.view.MenuItem;

import com.squareup.picasso.Picasso;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import mk.pjonceski.empleyeemanager.R;
import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.ui.base_mvp.BaseMVPActivity;
import mk.pjonceski.empleyeemanager.ui.base_mvp.presenter.BasePresenter;
import mk.pjonceski.empleyeemanager.utils.helpers.Helpers;

/**
 * This activity shows all the details for employee.
 */
public class EmployeeDetailsActivity extends BaseMVPActivity implements EmployeeDetailsContract.View {
    @Inject
    EmployeeDetailsContract.Presenter presenter;

    @Inject
    Helpers helpers;

    @BindView(R.id.employee_avatar_image_avatar)
    AppCompatImageView avatarImageView;

    @BindView(R.id.employee_avatar_name)
    AppCompatTextView nameTextView;

    @BindView(R.id.employee_avatar_job_title)
    AppCompatTextView jobTitleTextView;

    @BindView(R.id.employee_details_company)
    AppCompatTextView companyTextView;

    @BindView(R.id.employee_details_biography)
    AppCompatTextView biographyTextView;

    public BasePresenter getBasePresenter() {
        return presenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.employee_details_layout;
    }

    @Override
    public int getScreenTitle() {
        return R.string.employees_details_title;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpScreen();
    }

    private void setUpScreen() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void populateScreenWithEmployee(Employee employee) {
        this.nameTextView.setText(employee.getName());
        this.jobTitleTextView.setText(employee.getJobTitle());
        this.companyTextView.setText(employee.getCompanyName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.biographyTextView.setText(Html.fromHtml(employee.getBiography(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            this.biographyTextView.setText(Html.fromHtml(employee.getBiography()));
        }
        File imageAvatarFile = helpers.getFileHelper().getImageFromAvatarsImageCache(String.valueOf(employee.getId()));
        if (imageAvatarFile != null) {
            Picasso.get().load(imageAvatarFile).into(avatarImageView);
        }
    }
}
