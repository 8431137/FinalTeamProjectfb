package com.sangmyung.teamprojectfb;

import android.app.Activity;
import android.util.Patterns;
import android.widget.Toast;

public class Util{
    public Util(){/**/}



    public static void showToast(Activity activity, String msg){  // 알림 메세지를 출력하는 함수
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    public static boolean isStoregeUrl(String url){ // 게시판 사진 및 영상를 저장한 파이어베이스 스토리지 저장경로인 URL 이면 true 이고, 아니면 false 판단하는 함수
        return Patterns.WEB_URL.matcher(url).matches() && url.contains("https://firebasestorage.googleapis.com/v0/b/teamprojectfb-b1a8c.appspot.com/o/posts");
    }

}
