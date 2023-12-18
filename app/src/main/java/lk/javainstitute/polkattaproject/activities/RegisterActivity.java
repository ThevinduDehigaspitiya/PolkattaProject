package lk.javainstitute.polkattaproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import lk.javainstitute.polkattaproject.R;

public class RegisterActivity extends AppCompatActivity {

    EditText getFName,getLName,getemail,getpassword,getConfirmPassword;
    Button button;

    FirebaseAuth mAuth;

    SharedPreferences sharedPreferences;

    ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }

        getFName = findViewById(R.id.NameRegister);
        getLName = findViewById(R.id.LastNameRegister);
        getemail = findViewById(R.id.EmailRegister);
        getpassword = findViewById(R.id.PasswordRegister);
        getConfirmPassword = findViewById(R.id.RePasswordRegister);
        button = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.PBar);

        sharedPreferences = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);

        boolean isFirstTime = sharedPreferences.getBoolean("firstTime",true);

        if(isFirstTime){

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime",false);
            editor.commit();

            Intent intent = new Intent(RegisterActivity.this,OnBoardingActivity.class);
            startActivity(intent);
            finish();
        }

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String Fname,Lname,email,password,ConfirmPassword;
                Fname = String.valueOf(getFName.getText());
                Lname = String.valueOf(getLName.getText());
                email = String.valueOf(getemail.getText());
                password = String.valueOf(getpassword.getText());
                ConfirmPassword = String.valueOf(getConfirmPassword.getText());

                if (TextUtils.isEmpty(Fname)) {
                    Toast.makeText(RegisterActivity.this,"Enter Firstname",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                if (TextUtils.isEmpty(Lname)){
                    Toast.makeText(RegisterActivity.this,"Enter Lastname",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this,"Enter an Email",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this,"Enter a Password",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                if (TextUtils.isEmpty(ConfirmPassword)){
                    Toast.makeText(RegisterActivity.this,"Confirm the Password",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                if (!password.equalsIgnoreCase(ConfirmPassword)){
                    Toast.makeText(RegisterActivity.this,"Passwords are not matching",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                if (password.length() < 5){
                    Toast.makeText(RegisterActivity.this,"Password is too small",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;

                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {

                                    Toast.makeText(RegisterActivity.this, "Successfully Registered",
                                            Toast.LENGTH_SHORT).show();
                                    getFName.setText("");
                                    getLName.setText("");
                                    getemail.setText("");
                                    getpassword.setText("");
                                    getConfirmPassword.setText("");

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });


        ImageView view = findViewById(R.id.imageView4);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}