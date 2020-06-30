package com.example.dashin.LoginModule.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.chaos.view.PinView;
import com.example.dashin.CustomerModule.activities.MainActivity;
import com.example.dashin.CustomerModule.models.Customer;
import com.example.dashin.LoginModule.activities.SignupActivity;
import com.example.dashin.R;
import com.example.dashin.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.concurrent.TimeUnit;


public class VerifyOPTFragment extends Fragment {

    private TextView mTextViewCountDown;
    private TextView resend;
    private String phonenumber;
    private TextView setphn;
    private Button pgnext;
    private String TAG = "messapp";
    private String mVerificationId;
    private PinView pinView;

    private ExampleThread thread;
    private Handler mainHandler = new Handler();

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
        mTextViewCountDown = view.findViewById(R.id.text_view_countdown);
        resend = view.findViewById(R.id.resend_tv);
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
                    Toast.makeText(getActivity(), "SMS quota exceeded. Please try again after some time.",
                            Toast.LENGTH_LONG).show();
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
                100,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                getActivity(),               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks


        //start counter
        thread = new ExampleThread(99);
        thread.start();

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phonenumber,        // Phone number to verify
                        100,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        getActivity(),               // Activity (for callback binding)
                        mCallbacks);        // OnVerificationStateChangedCallbacks


            }
        });


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
                    Toast.makeText(getActivity(), "Please enter a valid OTP.", Toast.LENGTH_SHORT).show();
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
                            thread.interrupt();

                            Constants.mFirestore.collection("customer").document(phonenumber)
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot ds=task.getResult();
                                    Constants.CurrentUser.setContact(phonenumber);
                                    if(ds.exists())
                                    {
                                        Constants.CurrentUser = ds.toObject(Customer.class);
                                        getActivity().finish();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        getActivity().finish();
                                        Intent intent = new Intent(getActivity(), SignupActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });

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

    class ExampleThread extends Thread {
        int seconds;
        boolean restart = false;

        ExampleThread(int seconds) {
            this.seconds = seconds;
        }

        void restartThread(){
            restart = true;
        }
        @Override
        public void run() {
            for (int i = seconds; i >= 0; i--) {
                if(restart) {
                    i = seconds;
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            restart = false;
                        }
                    });

                }
                //Log.d(TAG, "startThread: " + i);
                final int finalI = i;
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mTextViewCountDown.setText(String.valueOf(finalI));
                    }
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}