package com.example.healthproject.Firebase;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import androidx.annotation.NonNull;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.idling.CountingIdlingResource;

import com.example.healthproject.BuildConfig;
import com.example.healthproject.Model.FirebaseDataSource;
import com.example.healthproject.Model.GlobalUser;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;

import static androidx.test.espresso.Espresso.onIdle;
import static com.google.firebase.firestore.util.Assert.fail;

public abstract class FirebaseTest {
    private static final String IDLING_NAME = "IDLER";
    private static final CountingIdlingResource idlingResource = new CountingIdlingResource(IDLING_NAME);

    @Before
    public void prepare() {
        final Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        int apps = FirebaseApp.getApps(context).size();
        if (apps == 0) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setApiKey(BuildConfig.apiKey)
                    .setApplicationId(BuildConfig.applicationId)
                    .setDatabaseUrl(BuildConfig.databaseUrl)
                    .setProjectId(BuildConfig.projectId)
                    .build();
            FirebaseApp.initializeApp(context, options);
        }
        if (!GlobalUser.getInstance(new FirebaseDataSource()).isLoggedIn()) {
            IdlingRegistry.getInstance().register(idlingResource);
            FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword("test@app.io", "12345678")
                    .addOnCompleteListener(this::onComplete);
            idlingResource.increment();
            onIdle();
        }
    }

    private void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            idlingResource.decrement();
        } else {
            fail("The user was not logged successfully");
        }
    }

}
