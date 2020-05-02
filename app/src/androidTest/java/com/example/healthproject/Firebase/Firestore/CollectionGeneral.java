package com.example.healthproject.Firebase.Firestore;

import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Rule;

import java.util.ArrayList;
import java.util.Arrays;

public class CollectionGeneral {
    //Check if collections exist
    @Rule
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ArrayList<String> collectionNames = new ArrayList<>(Arrays.asList("events", "users", "venues", "user_event_links"));


}

