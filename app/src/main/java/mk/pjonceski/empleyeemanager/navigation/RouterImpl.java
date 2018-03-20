package mk.pjonceski.empleyeemanager.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import mk.pjonceski.empleyeemanager.App;
import mk.pjonceski.empleyeemanager.ui.features.employee_details_feature.EmployeeDetailsActivity;

/**
 * Provides methods that handle navigation inside the application.
 */
public class RouterImpl implements Router {
    private App app;

    public RouterImpl(App app) {
        this.app = app;
    }

    @Override
    public void navigateToEmployeeDetailsActivity(Bundle data) {
        if (app.getCurrentActivity() != null) {
            app.getCurrentActivity().startActivity(
                    createIntentFromCurrentActivity(data));
        }
    }

    /**
     * This method creates {@link Intent} that will be used to start activity.
     * If parameter data is null then the intent will contain no extra data.
     *
     * @param data the data to be transferred to the other activity if not null.
     * @return the created intent
     */
    private Intent createIntentFromCurrentActivity(@Nullable Bundle data) {
        Intent intent = new Intent(app.getCurrentActivity(), EmployeeDetailsActivity.class);
        if (data != null) {
            intent.putExtras(data);
        }
        return intent;
    }
}
