package com.sangmyung.teamprojectfb.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sangmyung.teamprojectfb.R;


public class MemberInitActivity extends AppCompatActivity {
    private static final String TAG = "MemberInitActivity";
    EditText univTemp;
    EditText phoneTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_init);
        check();
        univTemp=(EditText)findViewById(R.id.univEditText);
        phoneTemp=(EditText)findViewById(R.id.phoneNumberEditText);
        findViewById(R.id.checkButton).setOnClickListener(onClickListener);
        findViewById(R.id.univEditText).setOnClickListener(onClickListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.checkButton:
                    profileUpdate();
                    break;
                case R.id.univEditText:
                    univUpdate();
                    break;
            }
        }
    };

    private void check(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String user=firebaseUser.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document=task.getResult();
                    if(document.exists()){
                        startToast("게시판으로 이동합니다.");
                        myStartActivity(MainActivity.class);
                    }
                }
            }
        });

    }

    private void profileUpdate() {
        String univ = ((EditText)findViewById(R.id.univEditText)).getText().toString();
        String name = ((EditText)findViewById(R.id.nameEditText)).getText().toString();
        String phoneNumber = ((EditText)findViewById(R.id.phoneNumberEditText)).getText().toString();
        String birthDay = ((EditText)findViewById(R.id.birthDayEditText)).getText().toString();
        String address = ((EditText)findViewById(R.id.addressEditText)).getText().toString();

        if(name.length() > 0 && phoneNumber.length() > 9 && birthDay.length() > 5 && address.length() > 0){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            MemberInfo memberInfo = new MemberInfo(univ,name, phoneNumber, birthDay, address);
            if(user != null){
                db.collection("users").document(user.getUid()).set(memberInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startToast("회원정보 등록을 성공하였습니다.");
                                startToast("게시판으로 이동합니다.");
                                myStartActivity(MainActivity.class);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                startToast("회원정보 등록에 실패하였습니다.");
                                Log.w(TAG, "작성 실패", e);
                            }
                        });
            }

        }else {
            startToast("회원정보를 입력해주세요.");
        }
    }
    private void univUpdate(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("대학교를 입력하세요.");

        builder.setItems(R.array.LAN, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String[] items = getResources().getStringArray(R.array.LAN);
                Toast.makeText(getApplicationContext(),items[i],Toast.LENGTH_LONG).show();
                univTemp.setText(items[i]);

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivityForResult(intent, 1);

    }
}