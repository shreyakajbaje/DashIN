package com.example.dashin.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.chaos.view.PinView;
import com.example.dashin.MainActivity;
import com.example.dashin.R;
import com.example.dashin.VerificationActivity;
import com.example.dashin.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;


public class VerifyOPTFragment extends Fragment {

    String phonenumber;
    EditText et1,et2,et3,et4,et5,et6;
    TextView setphn;
    Button pgnext;

    String TAG = "mapsapp";
    String mVerificationId;
    PinView pinView;

    PhoneAuthProvider.ForceResendingToken mResendToken;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    public VerifyOPTFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_verify_otp, container, false);

        Bundle bundle = getArguments();
        phonenumber = bundle.getString("Number");
        pgnext = view.findViewById(R.id.nexthome);
        onConfirmClicked();

        setphn = view.findViewById(R.id.receivedno);
        setphn.setText(phonenumber);

        pinView = view.findViewById(R.id.firstPinView);
        setPinView(view);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                String code = credential.getSmsCode();
                Log.d(TAG, "onVerificationCompleted:" + credential);
                signInWithPhoneAuthCredential(credential);

                if (code != null) {
                    pinView.setText(code);
                    //verifying the code
                }

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                    Log.e(TAG,"invalid");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                    Log.e(TAG,"sms quota exceeded");
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("vds", "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // ...
            }
        };
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                getActivity(),               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

        return view;
    }

    private void setPinView(View v) {

        pinView.setTextColor(
                ResourcesCompat.getColor(getResources(), R.color.colorAccent, getActivity().getTheme()));
        pinView.setTextColor(
                ResourcesCompat.getColorStateList(getResources(), R.color.colorPrimaryDark, getActivity().getTheme()));
        pinView.setLineColor(
                ResourcesCompat.getColor(getResources(), R.color.colorPrimary, getActivity().getTheme()));
        pinView.setLineColor(
                ResourcesCompat.getColorStateList(getResources(), R.color.colorAccent, getActivity().getTheme()));
        pinView.setAnimationEnable(true);// start animation when adding text
        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        pinView.setHideLineWhenFilled(false);
    }

    private void onConfirmClicked() {

        pgnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!pinView.getText().toString().isEmpty()) {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, pinView.getText().toString());
                    //signing the user
                    signInWithPhoneAuthCredential(credential);
                }else{
                    Toast.makeText(getActivity(), "Creadentials not received!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        Constants.mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                                            getActivity().finish();
                                            Intent intent = new Intent(getActivity(), MainActivity.class);
                                            startActivity(intent);
                                            Log.d(TAG, "signInWithCredential:success");
//                                        DocumentSnapshot ds=task.getResult();
//                                        if(ds.exists())
//                                        {
//                                            getActivity().finish();
//                                            Intent intent = new Intent(getActivity(), MainActivity.class);
//                                            startActivity(intent);
//                                            Log.d(TAG, "signInWithCredential:success");
//                                        }
//                                        else
//                                        {
//                                            getActivity().finish();
//                                            Intent intent = new Intent(getActivity(), UserDetails.class);
//                                            intent.putExtra("NUM", phonenumber);
//                                            startActivity(intent);
//                                            Log.d(TAG, "signInWithCredential:success");
//                                        }
                        }
                        else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });

    }
}