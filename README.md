목차 
=============

### 1. 소개
* 앱 소개 
* 주제 선정  
* 앱 기능 소개
* 앱 실행 순서

### 2. 앱 설계 전 환경 구축 
* Firebase

### 3. 기능 구현
* 각 액티비티 기능 설명 
* 권한 허용 알림 
* 로그인
* 게시판 
* 보증금 

### 4. 차별성 
> 차별성

### 5. 수익화 방안
> 수익화 방안

### 6. 기대 효과
> 기대 효과

***


1.소개
=============



주제 선정  
-------------

대부분의 대학생들은 자취방을 1년씩 계약 하지만 실제 거주 기간은 6개월 정도 밖에 되지 않는다. 그리하여 대학교 커뮤니티 어플인 에브리 타임을 통하여 방을 단기간 거래가 많아 지고 있다.    
커뮤니티 어플를 통한 거래는 신용성, 안정성에 있어서 문제가 있다. 이러한 문제를 해결하고자 신용성과 안정성이 보장된 단기간 방 임대 서비스 어플리케이션을 제작하였다.  

앱 기능 소개  
-------------
#### 1. 회원가입/ 로그인 / 이메일 인증
* 이메일을 통한 회원가입 
* 가입한 이메일을 통한 로그인
* 이메일 인증 서비스를 이용한 안전한 시스템 구축       

#### 2. 회원 정보 
* 로그인 성공 시 회원정보 작성 화면 출력
* 대학별 게시판 분류를 위해서 목록을 통한 학교 선택    

#### 3. 게시판
* 제목, 내용, 사진 및 영상 작성 후 게시글 등록
* 주소 클릭 시 자동으로 현재 주소 및 변환된 주소 저장 
* 등록된 게시글 삭제 및 수정 가능
* 게시글 클릭 시 클릭한 게시글 화면 출력   

#### 4. 보증금 
* 보증금 서비스를 통한 사용자의 거래간 신용 보장     


앱 실행   
-------------
1. 이메일, 비밀번호를 작성 후 회원가입을 합니다.  
   
2. 로그인 버튼 클릭 후 회원가입시 작성했던 이메일과 비밀번호를 작성하여 로그인을 합니다.   
   
3. 이메일 인증 화면 출력되면, 인증 이메일 보내기 버튼을 클릭 후 메일을 확인하여 URL 클릭하게 되면 이메일 인증이 됩니다.   
   
4. 이메일 인증 성공 후, 회원정보 작성 화면이 출력 되며 회원정보를 작성합니다.   
      * 대학교 작성 칸을 더블 클릭하면 대학교 목록이 출력됩니다.
      * 사용자에 맞게 학교를 선택 후 나머지 정보를 작성합니다    
          
5. 회원정보 작성 완료 후 게시판 화면 출력이 됩니다.   
      * 오른쪽 하단 버튼 클릭 하게 되면 게시글 작성화면 출력됩니다.
      * 게시글 작성을 합니다.
      * 사진 및 영상 첨부할때 사진버튼 클릭 시 사용자의 앨범 권한을 묻습니다. 
      * 권한 승인 후 사진 및 영상 첨부 가능합니다.
      * 주소 작성칸 더블 클릭 시 현재 주소 자동으로 작성, 주소 변환 더블 클릭 시 현재 주소를 데이터로 변환 해줍니다.
      * 게시글 작성 후 확인 버튼 클릭 시 게시판에 사용자가 등록한 게시물 출력 됩니다.  
        
6. 작성한 게시글을 클릭 하면 자세한 내용을 확인 할수 있습니다.

7. 왼쪽 상단 버튼 클릭 시 보증금 거래합니다. 
     
     
2.앱 설계 전 환경 구축
=============  
    
Firebase
-------------

#### Firebase 연동 
  
1. Firebase 사이트 접속하여 Firestore 프로젝트를 만듭니다.
  
2. Firebase에 앱을 등록 합니다. 
   
3. Firebase 구성 파일 추가 합니다. 
    * Download google-services.json(google-services.json 다운로드)을 클릭하여 Firebase Android 구성 파일(google-services,json)을 가져옵니다.
      
    * 앱에서 Firebase 제품을 사용할 수 있도록 google-service 플러그인을 Gradle 파일에 추가합니다. 
```
    dependencies {
    classpath 'com.google.gms:google-services:4.2.0'  // Google Services plugin
  } 
```

4. 앱에 Firebase SDK 추가합니다. 
```
dependencies {
 implementation 'com.google.firebase:firebase-core:17.0.0'
```


3.기능 구현
=============  
  
#### 1. Activity
|Activity|기능|
|------|---|
|MainActivity|작성된 게시판 불러와서 출력|
|LoginActivity|로그인 처리|
|SignUpActivity|회원 가입 처리|
|VerifiedActivity|이메일 인증 처리|
|MemberInfo|입력받은 회원 정보 처리|
|MemberInitActivity|회원 정보 데이터베이스에 저장|
|PostActivity|게시판 출력 형태 처리|
|WritePostActivity|글 작성|
|GalleryActivity|사진,동영상 처리|


#### 2. 회원가입/ 로그인/ 이메일인증
    
    
    
회원가입 SignUpActivity
```
public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signUpButton).setOnClickListener(onClickListener); 
        findViewById(R.id.gotoLoginButton).setOnClickListener(onClickListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1); //어플 종료
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.signUpButton://회원가입 리스터
                    signUp();
                    break;
                case R.id.gotoLoginButton:// 로그인 화면으로 전환 리스너
                    startLoginActivity(); //화면 전환
                    break;
            }
        }
    };

    private void signUp() {
        String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();
        String passwordCheck = ((EditText)findViewById(R.id.passwordCheckEditText)).getText().toString();  //입력한 정보 읽어오기
        
        if(email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0){
            if(password.equals(passwordCheck)){ //password, passwordCheck 똑같을때

                mAuth.createUserWithEmailAndPassword(email, password) //생성
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startToast("회원가입에 성공하였습니다.");
                                    startLoginActivity(); 
                                } else {
                                    if(task.getException() != null){
                                        startToast(task.getException().toString()); //에러
                                    }
                                }
                            }
                        });
            }else{
                startToast("비밀번호가 일치하지 않습니다.");
            }
        }else {
            startToast("이메일 또는 비밀번호를 입력해 주세요.");
        }
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
```



LoginActivity
```
    private FirebaseAuth mAuth;
    private AdView mAdView;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance(); 

        findViewById(R.id.loginButton).setOnClickListener(onClickListener);
        mAdView = findViewById(R.id.adView);// 배너 광고 호출
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
        @Override
    public void onStart() {
        super.onStart();
        
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
    
    //버튼 리스너
    
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.loginButton:
                    Login(); //Login 함수 호출
                    break;
            }
        }
    };

    
    
     private void Login() {
        String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString(); 
        String password = ((EditText)findViewById(R.id.passwordEditText)).getText().toString(); //이메일, 비밀번호를 읽어옴

        if(email.length() > 0 && password.length() > 0){ 
            mAuth.signInWithEmailAndPassword(email, password)//유효하다면
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startToast("로그인에 성공하였습니다.");
                                startVerifiedActivity(); //로그인 성공시 이메일 인증 Activity로 이동
                            } else {
                                if(task.getException() != null){
                                    startToast(task.getException().toString()); // 에러처리
                                }
                            }
                        }
                    });
        }else {
            startToast("이메일 또는 비밀번호를 입력해 주세요.");
        }
    }
        private void startToast(String msg) { //핸드폰에 출력
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    private void startVerifiedActivity() {
        Intent intent = new Intent(this, VerifiedActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent); //인증 화면으로 전환하면서 초기화
    }
```
    VerifiedActivity// 이메일 인증을 위한 액티비티
 
```
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
        verified();//인증 클래스 호출
    }

    View.OnClickListener onClickListener = new View.OnClickListener() { //리스너
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.reloginButton:
                    startLoginActivity(); //Login 액티비티로 전환
                    break;
            }
        }
    };
    private void verified() {
        final FirebaseUser user = mAuth.getCurrentUser();

        if (user.isEmailVerified()) { //이메일 인증 완료시 회원 정보 입력 액티비티로 전환
            startToast("인증에 성공하였습니다.");
            startMemberActivity();

        } else {
            textView.setText("이메일이 인증되지 않았습니다.");
            EmailButton.setOnClickListener(new View.OnClickListener() { //클릭리스너에서
                @Override
                public void onClick(View view) {//누르면
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {// 인증 이메일 발송
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
        startActivity(intent);//회원정보 입력을 위한 액티비티로 이동
    }
    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent); //로그인 액티비티로 
    }
};
```
#### 2. 입력받은 회원 데이터베이스 저장
MemberInitActivity //입력받은 회원정보를 데이터베이스에 저장
```
    private static final String TAG = "MemberInitActivity";
    EditText univTemp;
    EditText phoneTemp; //변수 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_init);
        check(); //check 함수 호출
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
```
### 게시판 글 작성
  
WritePostActivity.java 
  
게시판 작성 
     
```
 View.OnClickListener onClickListener = new View.OnClickListener() { // 게시판 확인 버튼 클릭 시 게시물 등록 시작 
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.check:
                    storageUpload(univ,address);  // 게시물 확인 버튼 클릭시 대학교와 주소 서버에 등록
                    break;
                case R.id.image: // 이미지 버튼 클릭 시 사용자 앨범 권한 승인 여부 검사 
                    if (ContextCompat.checkSelfPermission(WritePostActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        if (ActivityCompat.shouldShowRequestPermissionRationale(WritePostActivity.this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            ActivityCompat.requestPermissions(WritePostActivity.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1);
                        } else {  // 권한 승언 실패시 다시 승인 여부 검사 
                            ActivityCompat.requestPermissions(WritePostActivity.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1);
                            showToast(WritePostActivity.this, "권한을 허용해주세요."); 
                        }
                    } else {
                        myStartActivity(GalleryActivity.class, "image", 0);
                    }
                    break;
                case R.id.video: // 동영상 클릭 
                    myStartActivity(GalleryActivity.class, "video", 0);
                    break;
                case R.id.buttonsBackgroundLayout:
                    if (buttonBackgroundLayout.getVisibility() == View.VISIBLE) {
                        buttonBackgroundLayout.setVisibility(View.GONE);
                    }
                    break;
                case R.id.imageModify: // 등록한 사진 수정 
                    myStartActivity(GalleryActivity.class, "image", 1);
                    buttonBackgroundLayout.setVisibility(View.GONE);
                    break;
                case R.id.videoModify: // 등록한 동영상 수정 
                    myStartActivity(GalleryActivity.class, "video", 1);
                    buttonBackgroundLayout.setVisibility(View.GONE);
                    break;
                case R.id.delete: // 등록한 사진 및 영상 삭제 
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
                            parent.removeView(selectedView); //  선택한 파일 삭제 
                            buttonBackgroundLayout.setVisibility(View.GONE);
                        }
                    }).addOnFailureListener(new OnFailureListener() { // 파일 삭제 실패시 실패 메세지 출력 
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            showToast(WritePostActivity.this, "파일을 삭제하는데 실패하였습니다.");
                        }
                    });

                    break;
                case R.id.gpsLayout: // 주소 칸 더블 클릭 시 자동으로 현재 위치 저장
                    checkLocationPermission();
                    break;
                case R.id.geoLayout: // 주소 변환 칸 더블 클릭 시 자동으로 변환된 주소 데이터 저장 
                    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    lm.removeUpdates(locationListener);
                    getLocation(latitude, longitude);
                    break;
            }
        }
    };

```
   
위치 서비스 권한 및 주소 자동 계산 
   
```

    private LocationListener locationListener = new LocationListener() { // 주소칸 더블 클릭시 현재 위치 자동으로 계산되어 저장 
        @Override
        public void onLocationChanged(Location location) {

            longitude = location.getLongitude(); // 경도 
            latitude = location.getLatitude(); // 위도 
            gpsLocation.setText(longitude + "/" + latitude);
        }
         private void checkLocationPermission() { // 위치 서비스 권한 승인 여부 검사 
        int checkPermission = ActivityCompat.checkSelfPermission(WritePostActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (checkPermission == PackageManager.PERMISSION_GRANTED) { 
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, locationListener);
        } else {
            ActivityCompat.requestPermissions(WritePostActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
        }
    }

    private String getLocation(Double latitude, Double longitude) { // 위치 서비스 권한 승인 완료 시 위도 경도 계산 하여 한글로 변환하여 저장 
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
```
    
게시판을 작성 후 게시판에 등록
   
```
private String getUniv() { // 회원정보에 등록한 대학교 받아오기
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

    private void storageUpload(String univ,String address) { // 작성한 게시글 게시판에 등록하기 
        final String title = ((EditText) findViewById(R.id.titleEditText)).getText().toString();

        if (title.length() > 0) { // 제목 작성 완료 후 
            loaderLayout.setVisibility(View.VISIBLE);
            final ArrayList<String> contentsList = new ArrayList<>();
            user = FirebaseAuth.getInstance().getCurrentUser();
            tempID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            this.univ=univ;
            tempaddress=address;
            FirebaseStorage storage = FirebaseStorage.getInstance(); // 파이어베이스 스토리지 연결 
            StorageReference storageRef = storage.getReference();
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            final DocumentReference documentReference = firebaseFirestore.collection(this.univ).document(); //대학 별 따라 데이터 따로 저장 
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
                                                //게시글 작성 완료
                                                Postinfo postinfo = new Postinfo(title, contentsList, user.getUid(), date, tempaddress);
                                                storeUpload(documentReference, postinfo);
                                                startToast("게시물이 등록되었습니다."); // 게시글 등록 완료.
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
                storeUpload(documentReference, new Postinfo(title, contentsList, user.getUid(), date, address));
            }

        } else { // 제목을 작성하지 않았을때, 작성이 필요하다고 경고하는 메세지
            startToast("내용을 작성해주세요.");
        }
    }

    private void storeUpload(DocumentReference documentReference, Postinfo postinfo) {
        documentReference.set(postinfo.getPostinfo())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) { // 등록 성공 시 종료 
                        Log.e(TAG, "DocumentSnapshot successfully written");
                        loaderLayout.setVisibility(View.GONE);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() { // 등록 실패 
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, " Error writing document.", e);
                        loaderLayout.setVisibility(View.GONE);
                    }
                });

    }
```


4.차별성 
=============   
* 최대 3개월 단기간 방 임대 서비스에만 초점  
    
* 수수료가 없어 사용자 간 부담이 없음 
    
* 보증금 제도 서비스를 통한 임대인 보호
    
* 본인인증 제도를 통하여 거래간 신용 및 안전 확보
    
* 본인인증을 통한 임차인 보호
    

  
5.수익화 방안 
=============   
### 광고/ 보증금 / 앱 내 결제
   
어플에 대한 수익이 없으면 유지하고 보수하기에 힘들다. 그래서 구해방 어플은 앱 내부 광고, 보증금 제도, 앱 내 결제 기능을 추가하여 수익화을 해결하였다.  
   
첫째, 구글 플레이스토어에 앱을 등록 하였다. 어플 등록 시 스토어에서 승인 완료 후 어플에 광고 추가 된다. 승인 완료된 어플은 어플 하단에 광고가 추가 된다    
        
우리가 만든 구해방 어플은 저소득층인 대학생들에게 넓은 수요층을 가질 수 있기에 무료로 배포하며, 앱 내 결제, 보증금 제도 그리고 앱 내 광고 기능을 추가하여 앱 사용자의 발길을 지속할 예정이다.  
    
    
6.기대효과 
============= 
* 장기간 임대가 아닌 단기간 임대 서비스로 주 타깃층인 대학생들의 깊은 인상을 줄 수 있다. 
    
* 직방, 다방과 같은 유명한 부동산 어플과 다르게 보증금 제도 서비스를 추가하여 신용성을 보장 할수 있다.
   
* 복잡한 인증 서비스를 통하여 사용자의 불안감을 감소 시킬수 있다.
   
* 학교 별 게시판을 통하여 쉽게 원하는 지역의 방을 볼 수 있다.

* 복잡하지 않은 인터페이스를 인한 앱 사용자의 접근성을 높일 수 있다
   

   
     
     
     

  

     


