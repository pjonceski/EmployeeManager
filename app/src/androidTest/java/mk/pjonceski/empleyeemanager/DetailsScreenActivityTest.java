package mk.pjonceski.empleyeemanager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.Html;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.navigation.Router;
import mk.pjonceski.empleyeemanager.ui.features.employee_details_feature.EmployeeDetailsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * This class test the {@link mk.pjonceski.empleyeemanager.navigation.Router}
 */

@RunWith(AndroidJUnit4.class)
public class DetailsScreenActivityTest {
    @Rule
    public IntentsTestRule<EmployeeDetailsActivity> employeePreviewActivityIntentsTestRule =
            new IntentsTestRule<>(EmployeeDetailsActivity.class, false, false);


    @Test
    public void testDetailActivityScreen_isStartedAndPopulated() {
        final Employee employee = new Employee("Petar",
                "<div>Biography</div>",
                "Company Name",
                "Programmer",
                "http://image.png");
        String htmlFormattedBiography = getHtmlFormattedBiography(employee.getBiography());

        Intent employeeIntent = new Intent();
        Bundle employeeData = new Bundle();

        employeeData.putParcelable(Router.BundleKeys.EMPLOYEE, employee);
        employeeIntent.putExtras(employeeData);

        employeePreviewActivityIntentsTestRule.launchActivity(employeeIntent);
        onView(withId(R.id.employee_avatar_name)).check(matches(withText(employee.getName())));
        onView(withId(R.id.employee_avatar_job_title)).check(matches(withText(employee.getJobTitle())));
        onView(withId(R.id.employee_details_biography)).check(matches(withText(htmlFormattedBiography)));
        onView(withId(R.id.employee_details_company)).check(matches(withText(employee.getCompanyName())));
    }

    private String getHtmlFormattedBiography(String biography) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(biography, Html.FROM_HTML_MODE_COMPACT).toString();
        } else {
            return Html.fromHtml(biography, Html.FROM_HTML_MODE_COMPACT).toString();
        }
    }

}
