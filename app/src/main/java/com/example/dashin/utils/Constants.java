package com.example.dashin.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class Constants {

    public static final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static final FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    public static final FirebaseStorage mStorage = FirebaseStorage.getInstance();

}
