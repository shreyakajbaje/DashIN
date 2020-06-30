package com.example.dashin.LoginModule.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dashin.CustomerModule.activities.MainActivity;
import com.example.dashin.R;
import com.example.dashin.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignupActivity extends AppCompatActivity {

    EditText name,email,phone,password,cpassword;
    private FirebaseFirestore db;
    ImageView image,register;
    Intent imageData;
    boolean imageSelected=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db= FirebaseFirestore.getInstance();
        name=findViewById(R.id.name);
        email=findViewById(R.id.txt_email);
        password=findViewById(R.id.txt_password);
        cpassword=findViewById(R.id.txt_confirm_password);
        phone=findViewById(R.id.phno);
        register=findViewById(R.id.buttonRegister);
        image = findViewById(R.id.ownerImage);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                {
                   // db.collection("CUSTOMER").document(phone.getText().toString())
                    final ProgressDialog dialog = new ProgressDialog(SignupActivity.this);
                    dialog.setMessage("Uploading...");
                    dialog.setCancelable(false);
                    dialog.show();
                    Constants.CurrentUser.setName(name.getText().toString());
                    Constants.CurrentUser.setEmail(email.getText().toString());
                    Constants.CurrentUser.setPassword(password.getText().toString());
                    Constants.CurrentUser.setContact("+91" + phone.getText().toString());
                    Uri fileUri = imageData.getData();
                    final String fileName = getFileName(fileUri);
                    //set image
                    Constants.CurrentUser.setImage(fileName);

                    //upload currentuser data to firebase
                    Constants.mFirestore.collection("customer").document(Constants.CurrentUser.getContact()).set(Constants.CurrentUser);

                    //image upload code
                    StorageReference mStorageRef;
                    mStorageRef = FirebaseStorage.getInstance().getReference();

                    StorageReference Ref = mStorageRef.child(Constants.CurrentUser.getContact()+"/"+fileName);

                    Ref.putFile(fileUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Get a URL to the uploaded content

                                    dialog.dismiss();

                                    finish();
                                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                    startActivity(intent);

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    // ...
                                }
                            });
                }
            }
        });
    }
    public boolean validate()
    {
        if(name.getText().toString().equals("")||email.getText().toString().equals("")||phone.getText().toString().equals("")||password.getText().toString().equals("")||cpassword.getText().toString().equals(""))
        {
            Toast.makeText(SignupActivity.this,"All fields are required",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!password.getText().toString().equals(cpassword.getText().toString()))
        {
            Toast.makeText(SignupActivity.this,"Passwords not matching",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!imageSelected)
        {
            Toast.makeText(SignupActivity.this,"Select Profile Pic",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void choose(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 101);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK) {

            if (data.getData() != null) {

                Toast.makeText(SignupActivity.this, "Selected Profile picture", Toast.LENGTH_SHORT).show();
                Uri fileUri = data.getData();
                String fileName = getFileName(fileUri);
                imageData=data;
                image.setImageURI(fileUri);
                imageSelected=true;
            }

        }

    }


    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

}