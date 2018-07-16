package android.kevin.guideyourride;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class BikeLog extends AppCompatActivity {

    Button clearBtn;
    ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_log);

        Button ib = null;
        LinearLayout col1,col2,col3,col4;

        col2=findViewById(R.id.col2);
        col3=findViewById(R.id.col3);
        col4=findViewById(R.id.col4);
        int c1;
        String c2;
        String c3;

        //tv=findViewById(R.id.foodie);
        final DatabaseHandler db = new DatabaseHandler(this);

        backBtn=findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),DetailsActivity.class);
                startActivity(i);
            }
        });


        clearBtn=findViewById(R.id.clear);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Contact> contacts = db.getAllContacts();
                for (final Contact cn : contacts)
                {
                    db.deleteContact(cn);
                }

                refreshActivity();

            }

        });

//tv.setText("Items\n");
        List<Contact> contacts = db.getAllContacts();
        TextView id,name,price;
        int i=0;
        for (final Contact cn : contacts) {

            id=new TextView(this);
            name=new TextView(this);
            price=new TextView(this);

            ib=new Button(this);

//            c1=cn.getID();
            c2=cn.getName();
            c3=cn.getPhoneNumber();

            //           id.setText(""+c1+"");
            name.setText(c2);
            price.setText(c3);

            //           id.setGravity(Gravity.CENTER);
            //         id.setTextSize(20);
//            id.setTextColor(getResources().getColor(android.R.color.white));

            name.setGravity(Gravity.CENTER);
            name.setPadding(0,60,0,0);
            name.setTextSize(15);
            name.setTextColor(getResources().getColor(android.R.color.white));

            price.setGravity(Gravity.CENTER);
            price.setPadding(0,60,0,0);
            price.setTextSize(15);
            price.setTextColor(getResources().getColor(android.R.color.white));


            //          col1.addView(id);
            col2.addView(name);
            col3.addView(price);
            //tv.append( "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " +cn.getPhoneNumber()+"\n");
            // Writing Contacts to log
//tv.append(items[i]);
            // db.deleteContact(cn);

            ib.setText("X");
            ib.setTextSize(10);
            col4.addView(ib);



            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(BikeLog.this, "Removed", Toast.LENGTH_SHORT).show();
                    db.deleteContact(cn);
                    Intent intent = getIntent();
                    intent.setFlags(Intent.

                            FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(intent);
                }
            });


            i++;
        }


    }

    private void refreshActivity() {
        finish();
        startActivity(getIntent());

    }
}
