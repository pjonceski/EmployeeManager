# Employee Manager Sample Application for Android #

This is sample application for managing employees providing all details about them. 

### About Employee Manager ###

Employee manager is an android application with purpose to show implementation of the viper clean architecture and android best practices.
To achieve clean application and responsive ui the application implements Dagger 2.1+, RxJava2 2.+, RxAndroid 2.+, ButterKnife 2.+, Retrofit 2.+, ConstraintLayout, RecyclerView, Firebase JobDispatcher, Picasso 2.7+.
The ui is designed following the material design guidelines.

### Features of Employee Manager ###

Employee Manager is an Android application that provides informations about Employees. 
At the first screen all employees are listed.Every item contains employee's avatar, name and company name. 
If you click on the employee item you navigate to details screen. The details screen provides detail information about every employee.
After you get the list of employees from server, the data is cached and you can explore the list of employees and their details offline.
Images are downloaded while scrolling through the list of employees. The images that are not shown on the screen continue to ne download in background.
If the app is not active the downloading of the missing images continues and will be retried later if internet connection fails at some point.

### How do I get set up? ###

1. Install Java 8 on the development pc.
2. Install the Android SDK, specifically the builtTools version 27.0.3.
3. Android Studio version 3.0 with the gradle build tools 3.0.1.
4. Import the project EmployeeManager into Android Studio.
5. Edit local.properties file at the root level of the project.Add the location of the installed android SDK.
Example: sdk.dir=C\:\\Users\\user\\AppData\\Local\\Android\\Sdk

### Supported devices ###

Min Android OS version 18(JELLY_BEAN_MR2).

### How do I install Employee Manager on my device? ###

Transfer the EmployeeManager.apk file in your android device and install it.

### Who do I talk to? ###

pjonceski@gmail.com
