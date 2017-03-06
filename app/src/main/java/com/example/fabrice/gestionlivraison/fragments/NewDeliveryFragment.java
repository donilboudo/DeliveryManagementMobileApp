package com.example.fabrice.gestionlivraison.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fabrice.gestionlivraison.R;
import com.example.fabrice.gestionlivraison.util.SaveDataStrategy;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class NewDeliveryFragment extends Fragment implements SaveDataStrategy.AsyncResponse{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private OnFragmentInteractionListener mListener;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;

    private View fragmentView;
    private String mCurrentPhotoPath;

    private TextView mStartDate;
    private Spinner mSenderName;
    private TextView mReceiverReference;
    private TextView mReceiverAddress;
    private TextView mStartComments;
    private Button mBtnSave;
    private Button mBtnStartDeliveryProofPicture;
    private ImageView mImgStartDeliveryProof;

    public NewDeliveryFragment() {
    }

    public static NewDeliveryFragment newInstance(String param1, String param2) {
        NewDeliveryFragment fragment = new NewDeliveryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    private void setStartDate() {
        Date startDate = new Date();
        mStartDate.setText(startDate.toString());

    }

    //TODO make date field non editable
    private void setInitialiseViews() {
        mStartDate = (TextView) fragmentView.findViewById(R.id.txtStartDate);
        mSenderName = (Spinner) fragmentView.findViewById(R.id.spSender);
        mReceiverReference = (TextView) fragmentView.findViewById(R.id.txtReceiverReference);
        mReceiverAddress = (TextView) fragmentView.findViewById(R.id.txtReceiverAddress);
        mStartComments = (TextView) fragmentView.findViewById(R.id.txtStartComments);
        mBtnSave = (Button) fragmentView.findViewById(R.id.btnSave);
        mBtnStartDeliveryProofPicture = (Button) fragmentView.findViewById(R.id.btnProof);
        mImgStartDeliveryProof = (ImageView) fragmentView.findViewById(R.id.imgStartDeliveryProof);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_new_delivery, container, false);

        setInitialiseViews();
        setStartDate();
        setListeners();

        return fragmentView;
    }

    private void setListeners() {
        mBtnStartDeliveryProofPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void saveData(){
        String startDate = mStartDate.getText().toString();
        String senderName = mSenderName.getSelectedItem().toString();
        String receiverName = mReceiverReference.getText().toString();
        String receiverAddress = mReceiverAddress.getText().toString();
        String startComments = mStartComments.getText().toString();

        SaveDataStrategy strategy = new SaveDataStrategy(this);
        strategy.execute(startDate, senderName, receiverName, receiverAddress, startComments);
    }

    @Override
    public void processFinish(String output) {

    }

    public void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null)
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
                Uri photoURI = FileProvider.getUriForFile(this.getActivity(), "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp =  new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CANADA).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null)
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImgStartDeliveryProof.setImageBitmap(imageBitmap);
        }

        mImgStartDeliveryProof.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
