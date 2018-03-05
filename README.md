**Tweather Application**

The project was developed in Android Studio 3.0.1. The screenshots were all taken on a Galaxy Note 8.

The application consists of two primary fragments to fetch current and future weather from the server. The fetch process happens in an AsyncTask.

When the weather is cloudy a custom CloudView is shown. When the weather is sunny, a custom SunView is shown. Note that these two views are animated and will keep spinning slowly.

There are a few instrumentation tests in the package. Since this is not a full fledged application, I stuck to using the actual remote endpoints to retrieve the information. In an actual application, a mock web server should be used to provide this information. With enough time, I would add more tests to expand code coverage.

**Important Information to run tests**

In order to run the Espresso based tests in MainActivityTest.java, you will have to modify the CloudView/SunView in order to get the tests to pass or otherwise Espresso will time out after 60 seconds as it will not get the application to be idle (because of the animations in CloudView/SunView).
