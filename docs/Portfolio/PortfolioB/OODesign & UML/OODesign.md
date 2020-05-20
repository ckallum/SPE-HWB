## High Level Architecture

<img src="/includes/high-level.png">

The high level diagram shows us the two main parts of the system. Firebase is an external service which we will use to handle our user authentication as well as our database and storage system. The Android application we will create has two versions depending on the user logging in and as such, will display a different UI to a regular user and a different UI to an admin.

We will be using three parts of Firebase in our application. Firebase authentication will be used for logging in and creating accounts. Firebase automatically encrypts passwords for us as well as setting up email templates to send to users for when they forget their passwords. Cloud Firestore is also used as our database for the application. It is a No-SQL database which we will use for storing event information and linking users to their interested events. Finally we will also use Firebase Storage which will be used to store images users upload to display in their profile.

The Android application will be used by the users to display events added to the database by the admins. Furthermore, the application will serve as an information hub for the Sports, Exercise & Health part of the university displaying information about differnet venues at the university along with links to social media, news and membership information. The admin version of the application will also feature the same user validation however the admin UI will only allow the admins to manage current event information and create new events. This will then be sent to Firestore and updated accordingly in the database, also updating the app for regular users.

## Dynamic UML Diagram

<img src="/includes/sequence.png">

The sequence diagram above displays the data flow when a user is attempting to log into the application. We use the MVC design pattern as well as the observer design pattern. Initally the user enters their information into the view and the controller receives this information. The controller observes the form being changed and waits for the FormState to send a response on whether the input is valid to enable the button press. If the data is valid, the button becomes pressable. If the data is invalid, a reason for why the data is invalid is printed until the controller eventually observes valid data from the FormState. Once the button is pressed, the data is sent to the model which verifies the user information in Firebase. If the login information is correct, it will then return what type of account has just logged in. This allows the controller to tell the view which UI to display to the user.

## Static UML Diagram

<img src="/includes/View3.png">

The static UML diagram shows the layout of how the activities are linked with the ViewModelController, showcasing our implementation of the MVC design pattern. Activities allow the app to show the user a UI and are a core element in Android development. They are the View part of the design pattern. The application starts at the MainActivity, which executes the LoginActivity or NavigationActivity, depending on if the user is already logged in. We use the ViewModelController class as the Controller in our application, which executes logic that responds to a user's request. Furthermore, it uses the FormState and FirebaseAuthResult as MutableLiveData which lets the controller know if their data value has been changed. The GlobalUser is used as the Model part of the application. We use the GlobalUser to communicate with Firebase. This design choice was made as it allowed us to have a centralised call to Firebase instead of each class calling to Firebase independently.
