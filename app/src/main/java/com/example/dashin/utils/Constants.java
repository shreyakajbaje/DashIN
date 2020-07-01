package com.example.dashin.utils;

import com.example.dashin.CustomerModule.models.Customer;
import com.example.dashin.CustomerModule.models.SingleEntityOfOrders;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class Constants {

    public static final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static final FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    public static final FirebaseStorage mStorage = FirebaseStorage.getInstance();

    public static Customer CurrentUser = new Customer();

    public static SingleEntityOfOrders order = new SingleEntityOfOrders();
}
