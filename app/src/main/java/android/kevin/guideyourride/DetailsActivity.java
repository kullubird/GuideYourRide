package android.kevin.guideyourride;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.internal.zzbdd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;


public class DetailsActivity extends AppCompatActivity {
FirebaseDatabase database;
DatabaseReference myRef;
 public TextView tvBikeStatus,tvHelmetStatus,tvSpeed,tvCutCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvBikeStatus=findViewById(R.id.txt_vehicle_status);
        tvHelmetStatus=findViewById(R.id.txt_helmet_status);
        tvSpeed=findViewById(R.id.txt_speed);
        tvCutCount=findViewById(R.id.txt_cut_count);

        startService(new Intent(this, BikeNotificationHandlerService.class));


        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("b1");

        BikeDetails temp=new BikeDetails("Kevin",0, (float) 0.0,0,0,"qwe",0,0);
        myRef.setValue(temp);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                BikeDetails obj = dataSnapshot.getValue(BikeDetails.class);
                updateValue(obj);
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(DetailsActivity.this, "Failed to read data", Toast.LENGTH_SHORT).show();
            }
        });

        Toolbar Lost = findViewById(R.id.myAppBar);
        setSupportActionBar(Lost);
    }

    public void updateValue(BikeDetails obj) {
        tvBikeStatus.setText(" "+obj.getBikeStatus()+" ");
        tvCutCount.setText(" "+obj.cutCount+" ");
        tvSpeed.setText(" "+(int) obj.speed);
        tvHelmetStatus.setText(" "+obj.hemetStatus);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item:
                FirebaseAuth mAuth=FirebaseAuth.getInstance();
                mAuth.signOut();

                stopService(new Intent(this,BikeNotificationHandlerService.class));
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                Toast.makeText(this, "You picked item 1", Toast.LENGTH_SHORT).show();
                break;

        }

        return super.onOptionsItemSelected(item);

    }

    public void buzzRider(View v)
    {
    Intent i = new Intent(getApplicationContext(),BikeLog.class);
    startActivity(i);
    }

    public void showMaps(View view) {
        Uri gmmIntentUri = Uri.parse("google.streetview:cbll=12.934320, 77.606146");

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
    // Make the Intent explicit by setting the Google Maps package
            mapIntent.setPackage("com.google.android.apps.maps");

    // Attempt to start an activity that can handle the Intent
            startActivity(mapIntent);
        }
    }
