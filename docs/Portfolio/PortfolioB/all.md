## Overview

**Client:** University of Bristol Sports, Exercise & Health department

Our project is aimed at helping the University of Bristol's Health and Wellbeing Department with goal of streamlining the users experience and ease of accessibility regarding events organised by the department via the creation of a mobile application. The app should act as an information point and a way to view, register and interact with such events.

The client/department is in charge of three main sports facilities as well as providing these facilities to societies at the university and the wider community. They are also responsible for running different events around the university, primarily with their Be:Active program being used to encourage more students to take part in sports

The problem the client has specified is that a lot of their organised events run in scenarios where there is no easy way for them to keep track of attendance and interest. Furthermore, there currently isn't a user-friendly/accessible way to find information regarding such events especially for mobile-users.

Our solution aims is to solve the clients problems thourgh creating an app that will allow students to access these features through their mobile phones. Hopefully, this increases the students engagements with these events by creating a user-friendly central point of information in contrast to students having to navigate Facebook for specific events.

Other potential features may include aspects of user-to-user interaction, commonly seen in conventional social media platforms, such as a comment section on events or an in-app messenger to find people to go to events with. The app may also feature a learning platform that will allow students to expand their knowledge about general, physical and mental wellbeing.

We have decided to base the application on Android since it is open-source and free to use. Android is also easy to setup and compatible on a wide range of devices which would make the app accessible to a larger student population. The programming language we decided to use was Java, as it is the standard for Android development.

We aim to eventually release the app to the public and allow students and the wider-population to have access to the app.




## Stakeholders


The first step in establishing the requirements of a project is to identify the stakeholders involved and how they impact/are impacted by our product.
The key stakeholders in our project are:
- **Students/General University Population** - They will be the primary target for our application

- **Staff from the Health and Wellbeing Department** - They are the client of the product whom ultimately manage and provide the service to the University population.

- **Google Cloud Services** - They are the main providers of the API and cloud services, they are responsible for hosting, storing and providing the majority of functionalities within our application and are responsible for any technical issues that may arise in terms of the cloud side of the application.

### Use Case Diagram
<img src="/includes/use_case.png" />

## User Stories and Flows

The next steps are to identify some “user stories” for each stakeholder and their flow steps. For our project we have identified the following ones:

**1. For students and external users:**

“As a user, I want to be able to view all available events in one place, so that it’s easier to find what I’m interested in.”

“As a user, I want to be able to easily access information about the University Health and Wellbeing Department in one place, so that it’s easier to find what I’m interested in.”

“As a user, I want to be able to view the details of the events, so that I can make an idea of what is going to happen in the event and decide whether I want to participate or not.”

“As a user, I want to be able to book an event, so that I can let the staff know I want to attend it.”

**Basic flow**:

1. Download the app
2. User registers an account and logs in
3. Browse through the events
4. Select an event that you may be interested in
5. Read the details of that event
6. Book the event
7. Manage user details
8. Navigate to University links( social media's, website etc. )
9. Find information about the venues the University provides.
10. Log out

**Alternative Flow**:

1. Download the app
2. User forgets/resets password then logs in
3. Filter the events
4. Browse through them
5. Select an event
6. Read the description
7. The user logs-out and the event is not booked.

**Exceptional Flow**:
- The user decides to close the app part-way through logging in
- The user loses internet connection while logging in.
- User tries to login with incorrect permissions/account details

**2. Admins:**

“As the health and wellbeing department staff, we want to be able to register attendees for events, so that we can keep track of them easier.”

1. User attends event
2. They mark the event as attended on the app
3. The staff is able to see who has attended the event

“As the health and wellbeing department staff, we want to be able to manage events inside the application, so that we can add and delete events depending on user privileges (administrator/user).”

1. User books event
2. Event gets cancelled
3. Gym staff deletes the event from the app
4. The user receives a notification about the cancellation

“As the health and wellbeing department staff, we want to be able to receive feedback for our events, so that we know what to improve.”

1. Student attends an event
2. After the event is finished, they write a feedback for that event on the app

The story we have chosen to focus on is: “As a user, I want to be able to view all available events and information about the Health and Wellbeing Department in one place, so that it’s easier to find what I’m interested in.”

## Requirements
The next step is to specify the atomic requirements, which are then divided into two categories: functional and non-functional requirements.

For the main story, the atomic requirements are:

“The user must be able to download the app.”

“The user must be able to browse through all the available events.”

“The user should be able to filter the available events.”

“The user shall be able to read the description of the available events.”

“The user must be able to book an event.”

“The user shall be able to cancel the booking for any event.”

“The user must be notified in case an event gets cancelled.”

“The staff should be able to receive feedback after each event.”

### Funtional Requirements

BOOKING-1.0 : The user shall be able to download an application in order to allow them to use all the facilities provided by the system.

BOOKING-2.0 : The user must be able to browse through all available events from the timetable, in order to allow them to decide which events they want to attend.

BOOKING-2.1 : The user should be able to filter the events by their interest, in order to be easier for them to find something that they would like.

BOOKING-2.2 : The user shall be able to read a description of any of the available events in order to help them decide if they want to attend the event or not.

BOOKING-3.0 : The user must be able to book any available event in order to let the staff know they want to attend it.

BOOKING-3.1 : The user shall be able to cancel a booking for an event in order to let the staff know they are no longer able to attend it.

CANCELLATION-1.0 : The staff must be able to cancel any event, in case the event won’t take place anymore.

CANCELLATION-1.1 : The user must be notified in case the staff cancels an event that the user was going to attend, in order to let them know they are not able to host it anymore.

FEEDBACK-1.0 : The staff should be able to receive feedback from the attendees of their events, in order to allow them to improve their services.

### Non-functional Requirements

EFFICIENCY-1.0 : The application shall meet the agreed response time and capacity requirements.

USABILITY-1.0 : The application should be capable enough to support 100 000 users without affecting its performance.

USABILITY-1.1 : The application shall be able to accommodate increased users and volumes.

USABILITY-1.2 : The application must work with any Android phone running version > 20.0

USABILITY-1.3: An admin (with no programming experience) must be able to add a new event in under 5 minutes.

USABILITY-1.4: An user must be able to easily book, manage, update their profile and have access to all Department links.

SECURITY-1.0 : The application must provide access only to legitimate users.


## High Level Architecture

<img src="/includes/high-level2.png">

The high level diagram shows us the two main parts of the system. Firebase is an external service which we will use to handle our user authentication, database and storage system. The application has two interfaces depending on the user privileges( user and admin ).

We will use Firebase Authentication, Firestore and Storage as our main cloud services for the application. Firebase authentication will be used for logging in and creating accounts. Firebase automatically encrypts passwords for us as well as setting up email templates to send to users for when they forget their passwords. Cloud Firestore is also used as our database for the application. It is a No-SQL database which we will use for storing event information and linking users to their interested events. Finally we will also use Firebase Storage which will be used to store images users upload to display in their profile.

Users will be displayed events added to the database by the admins. Furthermore, the application will serve as an information hub for the Sports, Exercise & Health part of the university displaying information about different venues at the university along with links to social media, news and membership information. The admin version of the application will also feature the same user validation however the admin UI will only allow the admins to manage current event information and create new events. This will then be sent to Firestore and updated accordingly in the database, also updating the app for regular users.

## Dynamic UML Diagram

<img src="/includes/sequence.png">

The sequence diagram above displays the data flow when a user is attempting to login into the application, it is the same for any other user interaction regarding creating events, updating profiles etc. We use the Factory, MVC as well as the observer/spectator design patterns. Initally a controller is created via the ControllerFactory. This controller observes the form being changed by the user. It then sends the input to the FormState model and waits for a response on whether the input is valid. If it is valid, the button is enable to pressed. If the data is invalid, a reason for why the data is invalid is displayed in the corresponding text box. Once the button is pressed, the data is sent to the model that interacts with the Firebase server requests/responses which verifies the user information via Firebase Authentication. If the login information is correct, it will then by query the relevant collection via Firebase Firestore to retrieve the user information( priviliges, email etc.). The relevant UI is then displayed.

## Static UML Diagram

<img src="/includes/static.png">

The static UML diagram shows the layout of how the activities are linked with the ViewModelController, showcasing our implementation of the MVC design pattern. Activities control the lifecycle of the application and are a key component of Android development. We also use Fragment to provide modularity in regards to the UI. The application starts at the MainActivity, which executes the LoginActivity or NavigationActivity, depending on if the user is already logged in. We use the ViewModelController class as the Controller in our application, which executes logic that responds to a user's input/request. Furthermore, it uses the FormState as MutableLiveData which lets the controller know if their data value has been changed. FirebaseAuthResult allows the controller to abstract the Firebase response and represent it to the User. The GlobalUser is used to model the current user instance by using the Singleton design pattern. The GlobalUser instance is our main way of accessing user information/functionalities( login/logout ) throughout the application. The Singleton pattern ensures correctness by always referencing the same user in the same instance of the application.

## Continuous Integration
For our project, we decided to use CircleCI as our continuous integration platform to iteratively and automatically implement development and release testing thus allowing us to evalutate each potential commit/push/release against previous releases before deployment; greatly increasing efficiency and the ability to push releases much faster. It also provided the security that new features won't break any previous features. CircleCI also lets the developer define specific workflows for tests and dependences between separate tests and certain criteria that must be satisfied before a release can be deployed. The overall workflow can be seen in the diagram below. It also allowed us to automatically run instrumented tests through Google's Firebase Test Lab and generate test reports that were also automatically stored in our Firebase Storage.

<img src="/includes/CI_Workflow.png" align="right" alt="drawing" width="300"/>

The automatically generated report coverage was also uploaded to Codacy, a tool that evaluates and reviews code against 'good' coding standards such as camel case naming conventions, DRY principles, code structure, simplicity and readability. Using this tool allowed us to keep a consistent code base and help remove ambiguity between each developers code whilst removing possibilities of code that was prone to errors later down the line/that might impact new implementations.

## Development Testing

### Test Strategy
Our development testing strategy follows the principle that testing is used to show the presence of bugs and not the absence of bugs. We adhered to this principal by following the protocol of first writing the tests before writing the code, making sure these tests revolve around identifying if the code is conforming to the specification. The correctness of these tests were ensured by using synthetic data that was chosen using equivalence partitioning to provide complete coverage of test cases without needing to try all possibilities.

All unit tests were implemented using JUnit and were ran locally whereas integration/instrumentation tests that require the components of the Android application lifecycle were ran using Firebase Test Lab.

Core components of the application that needed to be tested include the ability for the users to create an account and be able to find, create, delete, update and register for events that are organised by the university Health and Wellbeing Department as well as have an easy way to navigate to University affiliated accounts/websites. Different levels of user privileges were also required and that admins had the ability to create, delete, update and view the data pertaining these events.

### Challenges
Using Google's Firebase API as our main web/cloud service provider created challenges when it came to development testing and implementing specific unit tests. All firebase requests run asynchronously making it difficult to use/evaluate the return data during unit testing. Various workarounds had to be used such as creating UI elements to display the result and test against the UI element instead. We are also unable to mock certain Firebase classes due to their security rules and authentication requirements limiting the capabilities to query the Firestore database. However, these security rules provide a benefit of validation criteria in terms of account creation being predefined for us.

### Test Cases


 #### User Authentication and Creation
  We used the Firebase Authentication API to manage user registration, authentication for logging in and general management. The API itself already has predefined security rules and tests which meant we simply had to provide valid and invalid test cases and make assertions on the API return calls:
  - **Login Authentication:**
      - Instrumentation test with valid and invalid credentials as test inputs.
      - Assertions are based on the UI output such as Toast messages and view changes.  
  - **User Registration and Password Reset:**
      - Instrumentation test with valid and invalid credentials as test inputs. Invalid credentials include existing emails/non-existing emails respectively.
      - Assertions are based on the UI output such as Toast messages and view changes.




 #### Firestore/Database Requests
  We used Firebase Firestore as our main database service provider. Firestore allows us to create predefined database security rules which limits who can write/read to the database and also collection rules such as unique ID's. However, there is currently no way to test database requests directly through unit or integration tests due to the previously described limitations. This means the we have to check if the request was successful via UI assertions.
  - **User Creation:**
    - Instrumentation test: Register user -> Login with user details -> Display UI message if user object is created in database.
    - Assertions are based on the the message contents.
  - **User Management/Updates:**
    - Instrumentation test: Login with existing user -> Navigate to profile page -> update username/password/email -> Logout -> Log back in.
    - Assertions check if the profile page is updated.
  - **Event Subscription/Unsubscription:**
    - Instrumentation test: Login with existing user -> Navigate to events page -> click interested button -> navigate to bookings page -> click unsubscribe button.
    - Assertions check if the 'interested' text box has incremented/decremented and if the bookings page displays/removes the event.
  - **Event Management:**
    - Instrumentation test: Login with admin details -> Navigate to events page -> click manage button -> update event details -> navigate to events page.
    - Assertions check if the event details in the events page has been updated.


 #### Front End:
  Instrumentation and Robo tests are functionalities provided by Firebase Test Lab.Bboth were used to test navigation through the app. Instrumentation tests allow us to apply assertions to UI components during navigation. Robo tests are automated application crawlers that allow us to simulate a user navigating through an app with a predefined script. Instrumentation tests provide feedback via test assertions whereas Robo Tests provide videos of the simulation and sitemaps to show where it has successfully navigated to.
  <img src="/includes/UserRoboSitemap.png" align="right" alt="drawing" width="300"/>


  Using the MVC design pattern allowed us to easily unit test isolated components such as form validation by generating a mock form and test data and implement tests using assertions on the result given back by the FormState model.

  **Login/Registration/Event Form Validation:**
    - Unit Test via JUnit with a mock form and ViewModelController with examples of invalid and valid inputs.
    - Assertions are made on the results of the FormState class given to the ViewModelController.
  - **Menu Navigation:**
    - Robo Test via Test Lab.
    - Test is evaluated via the Test Lab interface and checking if the test results display any bug occurences via the generated video/test logs.
  - **Home Page Weblinks:**
    - Robo Test + Instrumentation via Test Lab.
    - Test is evaluated via the Test Lab interface and checking if the test results display any bug occurences via the generated video/test logs. Assertions are made during the instrumentation test by checking if the web page that is displayed contains the corresponding URL.


# Product Evaluation
  In order to validate our application meets the client specification, we had to ensure that we iteratively evaluated our application when we released a new version with additional functionalities. To achieve this, we made sure that the app was tested by current students of the university through a questionnaire. To produce a fair evaluation, we decided to question two audiences: students who hold a sports membership and students who don’t. This allowed us to find a general consensus of our solution. We evaluated each release based on ease of use and functionality.

  It was important to select an appropriate environment in order to evaluate our application. To target students who hold sports memberships, we believed the Indoor Sports Centre was the most appropriate location. To target non-members, we found users in MVB to test our application. Specifically, we provided an android smartphone which already has the application installed and a questionnaire. We’d allow the user to freely navigate through the application and provide verbal and written feedback via the questionnaire. We aimed to have our implementation tested by at least 15 people (members and non-members).

  The questionnaire mainly highlighted aspects such as how easy it is to use/navigate the app, whether the user interface is appealing, whether they’d use the app again, if they liked the implementation, etc.

  Using the responses from our questionnaire, we then looked into how we can further improve our application. For example, when we first implemented the user interface, we received a lot of responses that mentioned the colour scheme was a bit too dark and unappealing. We therefore opted for a much more appealing colour scheme. We made sure to have this re-evaluated and the majority of the responses stated the UI was much more appealing with the new colour scheme. When we implemented the events page of the app, a lot of the responses stated that they’d like an option to filter the results. We took this response onboard and implemented a filter for searching events. This resulted in more positive responses.

  Upon observation, we also noticed a lot of users were trying to click on the map logo to view the location of that facility. We therefore decided to implement a feature which allows the user to click on a map icon and view where on the map the facility is.

  As well as receiving feedback from students, we held regular scheduled meetings with our clients( approx every two weeks, one if convenient ). These meetings mainly consisted of recieving feedback about the application and how we can further improve it. We recorded notes during each meeting in order to keep track of improvements we needed to make, making note of additional tasks in Jira.

  In conclusion, it is evident that through observation, consistent client communication/feedback and the use of a questionnaire has allowed our final product to fulfill more of the client and users specifications.

### Questionnaire
Link to Questionnaire: [Questionnaire](Questionnaire/Questionnaire.pdf)
