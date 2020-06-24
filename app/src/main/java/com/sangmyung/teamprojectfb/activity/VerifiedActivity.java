package com.sangmyung.teamprojectfb.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sangmyung.teamprojectfb.R;

public class VerifiedActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView textView;
    EditText editEmailText;
    Button EmailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verified);
        mAuth = FirebaseAuth.getInstance();
        EmailButton=findViewById(R.id.emailVerifiedButton);
        textView = (TextView) findViewById(R.id.emailVerifiedEditText);
        editEmailText=(EditText)findViewById(R.id.emailEditText);
        findViewById(R.id.reloginButton).setOnClickListener(onClickListener);
        verified();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.reloginButton:
                    startLoginActivity();
                    break;
            }
        }
    };
    private void verified() {
        final FirebaseUser user = mAuth.getCurrentUser();

        if (user.isEmailVerified()) {
            startToast("인증에 성공하였습니다.");
            startMemberActivity();

        } else {
            textView.setText("이메일이 인증되지 않았습니다.");
            EmailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(VerifiedActivity.this, "인증이메일이 발송되었습니다.", Toast.LENGTH_SHORT).show();
                            editEmailText.setText("이메일을 통한 인증 후 다시 로그인해주세요.");
                        }
                    });
                }
            });
        }
    }
    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void startMemberActivity() {
        Intent intent = new Intent(this, MemberInitActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
};