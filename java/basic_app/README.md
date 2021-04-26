# FEED.ME fruit store sample app
<img src="https://user-images.githubusercontent.com/61788924/97542099-d906f000-19ce-11eb-8fe3-911d616ea953.jpg" height="400">

## What is it for?
This app demonstrates the basic functionality of AppsFlyer's OneLink deep-linking solution.
The app will be able to register it in AppsFlyer's dashboard, create real OneLink link and 'test-drive' them.

> You can read more in our Developer Hub for Android [here](https://dev.appsflyer.com/hc/docs/android).

## Compatability
- **Android version >= 29**

- **AppsFlyer SDK versions >= 5.4.1**

## How to use this app?
1. Clone the repository
2. Change the package to your own package name. This is the package name that will be used AppsFlyer's dashboard.
3. Get your AppsFlyer Dev Key using [these instructions][get_af_devkey].
4. Create the file `java/simple_deep_link_pass_data/app/src/main/java/com/appsflyer/onelink/appsflyeronelinkbasicapp/AppsFlyerConstants.java` with the following content:
```java
package YOUR_PACKAGE_NAME;

public class AppsFlyerConstants {
    static final String afDevKey = "XXXXXXXXXXXXX";
}
```
5. Ask your marketer to create some OneLink links and start running fruit campaigns 🍎


[get_af_devkey]: https://support.appsflyer.com/hc/en-us/articles/207032066-iOS-SDK-integration-for-developers#integration-31-retrieving-your-dev-key
