package com.example.fabrice.gestionlivraison.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.fabrice.gestionlivraison.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewDeliveryActivity extends AppCompatActivity {
    private EditText txtStartDate;
    private Spinner spClient;
    private EditText txtDeliveryReference;
    private EditText txtDeliveryAddress;
    private EditText txtStartDeliveryComments;
    private Button btnStartDeliveryProof;
    private Button btnSave;
    private Button btnCancel;

    private ImageView imgStartDeliveryProof;
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_delivery);

        initComponents();
    }

    private void initComponents(){
        txtStartDate = (EditText) findViewById(R.id.txtStartDate);
        spClient = (Spinner) findViewById(R.id.spClient);
        txtDeliveryReference = (EditText) findViewById(R.id.txtDeliveryReference);
        txtDeliveryAddress = (EditText) findViewById(R.id.txtDeliveryAddress);
        txtStartDeliveryComments = (EditText) findViewById(R.id.txtComments);
        btnStartDeliveryProof = (Button) findViewById(R.id.btnProof);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        imgStartDeliveryProof = (ImageView) findViewById(R.id.imgStartDeliveryProof);
    }


    public void takePicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            // Create the File where the photo should go
            File photoFile = null;
            try
            {
                photoFile = createImageFile();
            }
            catch (IOException ex)
            {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null)
            {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, PhotoActivity.REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp =  new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CANADA).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PhotoActivity.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null)
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgStartDeliveryProof.setImageBitmap(imageBitmap);
        }

        imgStartDeliveryProof = (ImageView) findViewById(R.id.imageView);
        imgStartDeliveryProof.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));

    }
}
