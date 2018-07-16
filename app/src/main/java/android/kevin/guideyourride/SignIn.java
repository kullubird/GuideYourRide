package android.kevin.guideyourride;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignIn extends AppCompatActivity {

    String StrEmail,StrPassword;
    //int emailFlag=0;

    EditText EtUsername,EtPassword,EtCode;
    Button Submit,SubmitF;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        EtUsername=findViewById(R.id.txt_username);
        EtPassword=findViewById(R.id.txt_password);

        Submit=findViewById(R.id.btn_submit);
        SubmitF=findViewById(R.id.btn_submitf);

        mAuth = FirebaseAuth.getInstance();

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(emailFlag==1)
                    signIn();
                //else
                //    Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_LONG).show();
            }
        });

        SubmitF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();

                //if (user != null) {
                  //  Toast.makeText(getApplicationContext(), "Logged in successfully", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(SignIn.this,DetailsActivity.class);
                    startActivity(i);
                //}
                //else {
                //    Toast.makeText(getApplicationContext(), "Log in failed", Toast.LENGTH_SHORT).show();
                //}
            }
        });


       /* EtUsername.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                StrEmail=EtUsername.getText().toString().trim();;

                if (StrEmail.matches(emailPattern) && s.length() > 0)
                {
                    emailFlag=1;
                }
                else {
                    emailFlag=0;
                    Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_LONG).show();
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });*/
    }


    private void signIn() {

        mAuth.signOut();
        StrEmail=EtUsername.getText().toString().trim();;



        StrPassword=EtPassword.getText().toString().trim();;
        mAuth.signInWithEmailAndPassword(StrEmail,StrPassword);
        FirebaseUser currentUser = mAuth.getCurrentUser();
       // if(currentUser!=null)
            Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show();
        //updateUI(user);
    }

}
