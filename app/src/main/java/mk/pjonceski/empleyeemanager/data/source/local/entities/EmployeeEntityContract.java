package mk.pjonceski.empleyeemanager.data.source.local.entities;


import android.provider.BaseColumns;

/**
 * Database schema information for {@link mk.pjonceski.empleyeemanager.data.models.Employee}.
 * Also provides string to create the table for Employees.
 */

public final class EmployeeEntityContract {
    /**
     * Name of the table for storing employees.
     */
    public final static String TABLE_EMPLOYEE = "employee";

    private EmployeeEntityContract() {
    }

    public static final class EmployeeColumns implements BaseColumns {
        private EmployeeColumns() {
        }

        public static final String NAME = "name";
        public static final String BIOGRAPHY = "biography";
        public static final String COMPANY_NAME = "company_name";
        public static final String JOB_TITLE = "job_title";
        public static final String AVATAR = "avatar";
    }
/**Creates table employee*/
    public static final String CREATE_TABLE_EMPLOYEE = String.format("CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY , %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
            TABLE_EMPLOYEE,
            EmployeeColumns._ID,
            EmployeeColumns.NAME,
            EmployeeColumns.BIOGRAPHY,
            EmployeeColumns.COMPANY_NAME,
            EmployeeColumns.JOB_TITLE,
            EmployeeColumns.AVATAR);
}
