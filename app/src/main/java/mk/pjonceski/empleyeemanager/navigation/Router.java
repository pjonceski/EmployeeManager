package mk.pjonceski.empleyeemanager.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * In this class are defined methods for the navigating inside the application.
 */
public interface Router {
    /**
     * This method enables navigation to {@link mk.pjonceski.empleyeemanager.ui.features.employee_details_feature.EmployeeDetailsActivity}
     *
     * @param data the extra data to be transferred.
     */
    void navigateToEmployeeDetailsActivity(@Nullable Bundle data);
}
