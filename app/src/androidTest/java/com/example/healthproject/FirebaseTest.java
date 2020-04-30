package com.example.healthproject;

import androidx.test.espresso.idling.CountingIdlingResource;

public abstract class FirebaseTest {
    private static final String IDLING_NAME = "cl.cutiko.espresofirebase.FireBaseTest.key.IDLING_NAME";
    private static final CountingIdlingResource idlingResource = new CountingIdlingResource(IDLING_NAME);

//    @Before
//    public void prepare() {
//        final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        int apps = FirebaseApp.getApps(context).size();
//        if (apps == 0) {
//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setApiKey(BuildConfig.a)
//                    .setApplicationId(BuildConfig.applicationId)
//                    .setDatabaseUrl(BuildConfig.databaseUrl)
//                    .setProjectId(BuildConfig.projectId)
//                    .build();
//            FirebaseApp.initializeApp(context, options);
//        }
//        if (!new CurrentUser().isLogged()) {
//            IdlingRegistry.getInstance().register(idlingResource);
//            FirebaseAuth.getInstance()
//                    .signInWithEmailAndPassword("test@app.io", "12345678")
//                    .addOnCompleteListener(this);
//            idlingResource.increment();
//            onIdle();
//        }
//    }
//
//
//    @Override
//    public void onComplete(@NonNull Task<AuthResult> task) {
//        if (task.isSuccessful()) {
//            idlingResource.decrement();
//        } else {
//            fail("The user was not logged successfully");
//        }
//    }

}
