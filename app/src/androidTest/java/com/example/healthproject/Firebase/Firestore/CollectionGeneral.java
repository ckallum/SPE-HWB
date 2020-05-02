package com.example.healthproject.Firebase.Firestore;

import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;

public class CollectionGeneral {
    //Check if collections exist
    FirebaseFirestore db = FirebaseFirestore.getInstance();



    @Before
    public void setUp(){
    }

    private ArrayList<String> collectionNames = new ArrayList<>(Arrays.asList("events", "users", "venues", "user_event_links"));

    @Test
    public void testCollectionsExist(){
        for( int i = 0; i < collectionNames.size(); i++ ){
            db.collection(collectionNames.get(i)).get().addOnSuccessListener(queryDocumentSnapshots -> {
                assertTrue(queryDocumentSnapshots.size() > 0);
            } );
        }
    }

}

