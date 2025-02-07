package com.example.exp6;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


public class MainActivity extends AppCompatActivity {
    private Button selectimage, captureimage;
    private ImageView imageView;
    ActivityResultLauncher<Intent>cameraLauncher;
    ActivityResultLauncher<Intent>galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.imageView);
        selectimage = findViewById(R.id.button);
        captureimage = findViewById(R.id.button2);


        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult activityResult) {
                        Intent data = activityResult.getData();
                        Bundle extras = data.getExtras();
                        Bitmap mBitmap = (Bitmap) extras.get("data");
                        imageView.setImageBitmap(mBitmap);
                    }
                }
        );
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult activityResult) {
                        Intent data=activityResult.getData();
                        Uri chosenImageUri=data.getData();

                        Glide.with(imageView.getContext())
                                .asBitmap()
                                .load(chosenImageUri)
                                .into(imageView);

                    }
                }
        );
    }

    public void selectimage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        galleryLauncher.launch(photoPickerIntent);
        startActivityForResult(Intent.createChooser(photoPickerIntent, "Pick Image"), 1);
    }

    public void captureimage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(Intent.createChooser(intent, "Camera"));
        //startActivityForResult(intent,2);
    }
}
