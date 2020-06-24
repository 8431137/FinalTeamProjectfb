package com.sangmyung.teamprojectfb.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.sangmyung.teamprojectfb.R;
import com.sangmyung.teamprojectfb.view.ContentsItemView;
import com.sangmyung.teamprojectfb.view.Postinfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.sangmyung.teamprojectfb.Util.showToast;

public class WritePostActivity extends AppCompatActivity {

    private static final String TAG = "WritePostActivity";
    private FirebaseUser user;
    private ArrayList<String> pathList = new ArrayList<>();
    private LinearLayout parent;
    private RelativeLayout buttonBackgroundLayout;
    private ImageView selectedImageView;
    private RelativeLayout loaderLayout;
    private EditText selectedEditText;
    private EditText contentsEditText;
    private EditText titleEditText;
    private Postinfo postinfo;
    private int pathCount, successCount;
    private StorageReference storageRef;
    EditText gpsLocation;
    EditText geoLocation;
    Double longitude;
    Double latitude;
    String address;
    String tempaddress;
    EditText deposit;
    String tempDeposit;
    private String tempID;
    private String univ;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference univCollect=db.collection("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);
        tempDeposit=null;
        tempaddress=null;
        latitude=null;
        longitude=null;
        address=null;
        gpsLocation=(EditText)findViewById(R.id.gpsLayout);
        geoLocation=(EditText)findViewById(R.id.geoLayout);
        deposit=(EditText)findViewById(R.id.depositEditText);
        getUniv();
        parent = findViewById(R.id.contentsLayout);
        buttonBackgroundLayout = findViewById(R.id.buttonsBackgroundLayout);
        loaderLayout = findViewById(R.id.loderLyout);
        contentsEditText = findViewById(R.id.contentsEditText);
        titleEditText = findViewById(R.id.titleEditText);

        buttonBackgroundLayout.setOnClickListener(onClickListener);
        findViewById(R.id.check).setOnClickListener(onClickListener);
        findViewById(R.id.image).setOnClickListener(onClickListener);
        findViewById(R.id.video).setOnClickListener(onClickListener);
        findViewById(R.id.imageModify).setOnClickListener(onClickListener);
        findViewById(R.id.videoModify).setOnClickListener(onClickListener);
        findViewById(R.id.delete).setOnClickListener(onClickListener);
        findViewById(R.id.gpsLayout).setOnClickListener(onClickListener);
        findViewById(R.id.geoLayout).setOnClickListener(onClickListener);
        buttonBackgroundLayout.setOnClickListener(onClickListener);
        contentsEditText.setOnFocusChangeListener(onFocusChangeListener);

        titleEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    selectedEditText = null;
                }
            }
        });

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef  = storage.getReference();

        postinfo = (Postinfo) getIntent().getSerializableExtra("postinfo");
        postInit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0: {
                if (resultCode == Activity.RESULT_OK) {
                    String profilePath = data.getStringExtra("profilePath");
                    pathList.add(profilePath);
                    ContentsItemView contentsItemView = new ContentsItemView(this);

                    if (selectedEditText == null) {
                        parent.addView(contentsItemView);
                    } else {
                        for (int i = 0; i < parent.getChildCount(); i++) {
                            if (parent.getChildAt(i) == selectedEditText.getParent()) {
                                parent.addView(contentsItemView, i + 1);
                                break;
                            }
                        }
                    }
                    contentsItemView.setImage(profilePath);
                    contentsItemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonBackgroundLayout.setVisibility(View.VISIBLE);
                            selectedImageView = (ImageView) v;
                        }
                    });
                    contentsItemView.setOnFocusChangeListener(onFocusChangeListener);

                }
                break;
            }
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    String profilePath = data.getStringExtra("profilePath");
                    pathList.set(parent.indexOfChild((View)selectedImageView.getParent()) - 1 , profilePath);
                    Glide.with(this).load(profilePath).override(1000).into(selectedImageView);
                }
                break;
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.check:
                    storageUpload(univ,address);
                    break;
                case R.id.image:
                    if (ContextCompat.checkSelfPermission(WritePostActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        if (ActivityCompat.shouldShowRequestPermissionRationale(WritePostActivity.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            ActivityCompat.requestPermissions(WritePostActivity.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1);
                        } else {
                            ActivityCompat.requestPermissions(WritePostActivity.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1);
                            showToast(WritePostActivity.this, "권한을 허용해주세요.");
                        }
                    } else {
                        myStartActivity(GalleryActivity.class, "image", 0);
                    }
                    break;
                case R.id.video:
                    myStartActivity(GalleryActivity.class, "video", 0);
                    break;
                case R.id.buttonsBackgroundLayout:
                    if (buttonBackgroundLayout.getVisibility() == View.VISIBLE) {
                        buttonBackgroundLayout.setVisibility(View.GONE);
                    }
                    break;
                case R.id.imageModify:
                    myStartActivity(GalleryActivity.class, "image", 1);
                    buttonBackgroundLayout.setVisibility(View.GONE);
                    break;
                case R.id.videoModify:
                    myStartActivity(GalleryActivity.class, "video", 1);
                    buttonBackgroundLayout.setVisibility(View.GONE);
                    break;
                case R.id.delete:
                    final View selectedView = (View)selectedImageView.getParent();
                    String[] list = pathList.get(parent.indexOfChild(selectedView) - 1).split("\\?");
                    String[] list2 = list[0].split("%2F");
                    String name = list2[list2.length-1];

                    StorageReference desertRef = storageRef.child("posts/"+ postinfo.getId() +"/"+name);
                    desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            showToast(WritePostActivity.this, "파일을 삭제 하였습니다.");
                            pathList.remove(parent.indexOfChild(selectedView) - 1);
                            parent.removeView(selectedView);
                            buttonBackgroundLayout.setVisibility(View.GONE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            showToast(WritePostActivity.this, "파일을 삭제하는데 실패하였습니다.");
                        }
                    });

                    break;
                case R.id.gpsLayout:
                    checkLocationPermission();
                    break;
                case R.id.geoLayout:
                    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    lm.removeUpdates(locationListener);
                    getLocation(latitude, longitude);
                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    myStartActivity(GalleryActivity.class, "image", 0);
                } else {
                    startToast("권한을 허용해주세요");
                }
            }
            case 2: {
                for (int i = 0; i < permissions.length; i++) {
                    if (Manifest.permission.ACCESS_FINE_LOCATION.equals(permissions[i])) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            checkLocationPermission();
                        } else {
                            startToast("권한을 허용해주세요");
                        }
                        break;
                    }
                }

            }
        }
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            longitude = location.getLongitude();
            latitude = location.getLatitude();
            gpsLocation.setText(longitude + "/" + latitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void checkLocationPermission() {
        int checkPermission = ActivityCompat.checkSelfPermission(WritePostActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (checkPermission == PackageManager.PERMISSION_GRANTED) {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, locationListener);
        } else {
            ActivityCompat.requestPermissions(WritePostActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        }
    }

    private String getLocation(Double latitude, Double longitude) {
        String[] addressString = null;

        Geocoder geocoder = new Geocoder(this, Locale.KOREA);
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                addressString = addresses.get(0).toString().split(",");
                address = addressString[0].substring(addressString[0].indexOf("\"") + 1, addressString[0].length() - 2);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        geoLocation.setText(address);
        return address;
    }


    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                selectedEditText = (EditText) v;
            }
        }
    };

    private String getUniv() {
        tempID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("users").document(tempID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                univ = document.get("univ").toString();
                                Log.d(TAG, "" + univ);
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        }

                    }
                });
        return univ;
    }

    private void storageUpload(String univ, String address){
        final String title = ((EditText) findViewById(R.id.titleEditText)).getText().toString();

        if (title.length() > 0) {
            loaderLayout.setVisibility(View.VISIBLE);
            final ArrayList<String> contentsList = new ArrayList<>();
            user = FirebaseAuth.getInstance().getCurrentUser();
            tempID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            tempDeposit=((EditText)findViewById(R.id.depositEditText)).getText().toString();
            this.univ=univ;
            tempaddress=address;
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            final DocumentReference documentReference = firebaseFirestore.collection(this.univ).document();
            //final DocumentReference documentReference = postinfo == null ? firebaseFirestore.collection(this.univ).document() : firebaseFirestore.collection(this.univ).document(postinfo.getId());
            final Date date = postinfo == null ? new Date() : postinfo.getCreatedAt();

            for (int i = 0; i < parent.getChildCount(); i++) {
                LinearLayout linearLayout = (LinearLayout) parent.getChildAt(i);
                for (int ii = 0; ii < linearLayout.getChildCount(); ii++) {
                    View view = linearLayout.getChildAt(ii);

                    if (view instanceof EditText) {
                        String text = ((EditText) view).getText().toString();
                        if (text.length() > 0) {
                            contentsList.add(text);
                        }
                    } else if(!Patterns.WEB_URL.matcher(pathList.get(pathCount)).matches()){
                        String path = pathList.get(pathCount);
                        successCount++;
                        contentsList.add(path);
                        String[] pathArray = path.split("\\.");
                        final StorageReference mountainImagesRef = storageRef.child("posts/" + documentReference.getId() + "/" + pathCount + "." + pathArray[pathArray.length - 1]);

                        try {
                            InputStream stream = new FileInputStream(new File(pathList.get(pathCount)));
                            StorageMetadata metadata = new StorageMetadata.Builder().setCustomMetadata("index", "" + (contentsList.size() - 1)).build();
                            UploadTask uploadTask = mountainImagesRef.putStream(stream, metadata);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {

                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    final int index = Integer.parseInt(taskSnapshot.getMetadata().getCustomMetadata("index"));
                                    mountainImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            successCount--;
                                            contentsList.set(index, uri.toString());
                                            if (successCount==0) {
                                                //완료
                                                Postinfo postinfo = new Postinfo(title, contentsList, user.getUid(), date, tempaddress, tempDeposit);
                                                storeUpload(documentReference, postinfo);
                                                startToast("게시물이 등록되었습니다.");
                                            }

                                        }
                                    });
                                }
                            });
                        } catch (FileNotFoundException e) {
                            Log.e("로그", "에러: " + e.toString());
                        }
                        pathCount++;
                    }
                }
            }
            if (successCount==0) {  //이미지가 추가 될때마다 추가가 됨
                storeUpload(documentReference, new Postinfo(title, contentsList, user.getUid(), date, tempaddress, tempDeposit));
            }

        } else { // 제목을 작성하지 않았을때, 작성이 필요하다고 경고하는 메세지
            startToast("내용을 작성해주세요.");
        }
    }

    private void storeUpload(DocumentReference documentReference, Postinfo postinfo) {
        documentReference.set(postinfo.getPostinfo())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "DocumentSnapshot successfully written");
                        loaderLayout.setVisibility(View.GONE);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, " Error writing document.", e);
                        loaderLayout.setVisibility(View.GONE);
                    }
                });

    }

    private void postInit() {
        if (postinfo != null) {
            titleEditText.setText(postinfo.getTitle());
            ArrayList<String> contentsList = postinfo.getContents();
            for (int i = 0; i < contentsList.size(); i++) {
                String contents = contentsList.get(i);

                if (Patterns.WEB_URL.matcher(contents).matches() && contents.contains("https://firebasestorage.googleapis.com/v0/b/teamprojectfb-b1a8c.appspot.com/o/posts")) {
                    pathList.add(contents);

                    ContentsItemView contentsItemView = new ContentsItemView(this);

                    parent.addView(contentsItemView);

                    contentsItemView.setImage(contents);
                    contentsItemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            buttonBackgroundLayout.setVisibility(View.VISIBLE);
                            selectedImageView = (ImageView) v;
                        }
                    });
                    contentsItemView.setOnFocusChangeListener(onFocusChangeListener);
                    if(i < contentsList.size() - 1){
                        String nextContents = contentsList.get(i + 1);
                        if(!Patterns.WEB_URL.matcher(nextContents).matches() || !nextContents.contains("https://firebasestorage.googleapis.com/v0/b/teamprojectfb-b1a8c.appspot.com/o/posts")){
                            contentsItemView.setText(nextContents);
                        }
                    }

                }else if(i == 0){
                    contentsEditText.setText(contents);

                }

            }
        }
    }

    private void startToast(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void myStartActivity(Class c, String media, int requestCode){
        Intent intent = new Intent(this, c);
        intent.putExtra("media", media);
        startActivityForResult(intent, 0);
    }
}
