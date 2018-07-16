package android.kevin.guideyourride;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditPic extends AppCompatActivity {

    TextView tvCurrentUsername;

    FirebaseDatabase database;
    DatabaseReference myRef;

    Button btnUpload,btnSave;
    Uri selectedImage;
    StorageReference mStorageRef;


    private static int RESULT_LOAD_IMG=1;
    String imgDecodableString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pic);

        tvCurrentUsername=findViewById(R.id.tvUsername);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("b1");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                BikeDetails obj = dataSnapshot.getValue(BikeDetails.class);
                tvCurrentUsername.setText(obj.name);
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(EditPic.this, "Failed to read data", Toast.LENGTH_SHORT).show();
            }
        });

        final StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        btnUpload=findViewById(R.id.upload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RESULT_LOAD_IMG);

            }
        });


        btnSave=findViewById(R.id.save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StorageReference storageReference=mStorageRef.child("kevin/images.jpg");
                storageReference.putFile(selectedImage)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(EditPic.this, "Done", Toast.LENGTH_SHORT).show();
                                // Get a URL to the uploaded content
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                // ...
                                Toast.makeText(EditPic.this, "Not Done", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    try {
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
            Toast.makeText(this, "Reached here", Toast.LENGTH_SHORT).show();
            selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imgDecodableString = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = findViewById(R.id.image_view1);
            Bitmap bitmap;

            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
            imageView.setImageBitmap(bitmap);        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }
    catch (Exception e)
    {
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
    }

    }

}
