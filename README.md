# CommCare Biometric Integration Sample

An Android app that demostrates how to use the [CommCare Support Library APIs](https://github.com/dimagi/commcare-android/blob/master/commcare-support-library/src/main/java/org/commcare/commcaresupportlibrary/identity/identity_integration.md) to integrate CommCare with a biometric provider app. 


# Working Setup

This sample can be used in combination with a CommCare app to see how a typical biometric integration would work. Follow these instructions to setup a working mock integration locally - 

1. Build and install this sample on an Android device
2. Install [CommCare](https://play.google.com/store/apps/details?id=org.commcare.dalvik&hl=en_IN&gl=US) from playstore on the same Android device
3. Open CommCare, click on "Enter Code" and enter the code "3zFlKqW" and click on "Start Install" to install the sample CommCare app
4. On the login page of CommCare, click on the menu (three dots) on top right of the title bar and click on "Enter Practice Mode"
5. Navigate as "Explore CommCare Practice Mode" -> "Case List" -> "Registration Form". Fill the details in the form and move to the "Enroll Biometric" question.
6. Click on "Get Data" to fire the registration intent to this Sample Android app. This should open up the sample app and clicking on "Return Result" button would return a mock result to the CommCare app. Go ahead and finish the registration form. This should register a beneficiary in CommCare app
7. You can now go to the "Case List" -> "Verification Form" to see the registered beneficiary. Click on the beneficiary to open the verification form. Click on "Get Data" now to fire up the verification intent and open up the sample app. Clicking on "Return Result" button would return a mock verification result to the CommCare app.
8. Navigate to "Case List" -> "Verification Form" and click on the "QR Code" symbol in the title bar. This should fire up the identification intent and open up the sample app. Clicking on "Return Result" button would return a mock identification result to the CommCare app. 



If you have any questions regarding this sample, please feel free to ping us on [CommCare developers forum](https://forum.dimagi.com/c/developers/5)
