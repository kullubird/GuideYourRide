package android.kevin.guideyourride;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class PreferenceSetter extends AppCompatActivity {
    private FirebaseAuth mAuth;

    Button Proceed;
    EditText uName;
    String Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_setter);

        uName=findViewById(R.id.txt_username);

        Proceed=findViewById(R.id.btn_submit);
        Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name= String.valueOf(uName.getText());

                mAuth = FirebaseAuth.getInstance();


                FirebaseUser currentUser = mAuth.getCurrentUser();


                if (currentUser != null && Name.length()>3) {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(String.valueOf(uName.getText())).build();
                    currentUser.updateProfile(profileUpdates);


                    Proceed=findViewById(R.id.btn_submit);
                    Toast.makeText(PreferenceSetter.this, "Name is set as "+currentUser.getDisplayName(), Toast.LENGTH_SHORT).show();

                    Intent i=new Intent(PreferenceSetter.this,SignIn.class);
                    startActivity(i);
                }
                else
                    Toast.makeText(PreferenceSetter.this, "Please enter a vaild name", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
