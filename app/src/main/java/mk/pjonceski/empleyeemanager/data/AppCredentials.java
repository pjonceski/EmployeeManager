package mk.pjonceski.empleyeemanager.data;

/**
 * All the credentials and parameters are stored in this class.
 */
public class AppCredentials {
    private String baseUrl;
    private String baseAuthUsername;
    private String baseAuthPassword;

    public AppCredentials(String baseUrl, String baseAuthUsername, String baseAuthPassword) {
        this.baseUrl = baseUrl;
        this.baseAuthUsername = baseAuthUsername;
        this.baseAuthPassword = baseAuthPassword;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getBaseAuthUsername() {
        return baseAuthUsername;
    }

    public String getBaseAuthPassword() {
        return baseAuthPassword;
    }
}
