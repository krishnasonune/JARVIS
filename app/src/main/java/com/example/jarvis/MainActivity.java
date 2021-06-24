package com.example.jarvis;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.example.ai_webza_tec.ai_method.checkForPreviousCallList;
import static com.example.ai_webza_tec.ai_method.clearContactListSavedData;
import static com.example.ai_webza_tec.ai_method.getContactList;
import static com.example.ai_webza_tec.ai_method.makeCall;
import static com.example.ai_webza_tec.ai_method.makeCallFromSavedContactList;
import static com.example.jarvis.Functions.fetchName;
import static com.example.jarvis.Functions.wishMe;

public class MainActivity extends AppCompatActivity {
    private SpeechRecognizer recognizer;
    private TextView tvResult;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Dexter.withContext(this)
                .withPermission(Manifest.permission.RECORD_AUDIO)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {}
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                        System.exit(0);
                    }
                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
        findById();
        initializeTextToSpeech();
        initializeResult();
        fetchName("call to");


    }

    private void initializeTextToSpeech() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(tts.getEngines().size()==0){
                    Toast.makeText(MainActivity.this, "Engine is not available", Toast.LENGTH_SHORT).show();
                }else{
                    String s = wishMe();
                    speak("welcome to navso industries......."+ s);
                }
            }
        });
    }

    private void speak(String msg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null);
        }else{
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private void findById() {
        tvResult = (TextView)findViewById(R.id.tv_result);
    }

    private void initializeResult() {
        if(SpeechRecognizer.isRecognitionAvailable(this)){
            recognizer = SpeechRecognizer.createSpeechRecognizer(this);
            recognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {


                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle bundle) {
                    ArrayList<String> result = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    Toast.makeText(MainActivity.this, ""+result.get(0), Toast.LENGTH_SHORT).show();
                    tvResult.setText(result.get(0));
                    response(result.get(0));

                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }
    }

    private void response(String msg) {
        String msgs = msg.toLowerCase();
        if (msgs.indexOf("hey")!= -1) {
            if (msgs.indexOf("evelyn") != -1) {
                speak("Hello Boss ! how are you ?");
            }

        }
        if (msgs.indexOf("evelyn")!= -1){
            speak("yes boss");
        }

        if (msgs.indexOf("fine")!= -1){
            speak("Its good to know that you are fine .....how can i help you");
        }else if (msgs.indexOf("not good")!= -1){
            speak("can i cheer your mood up by playing your favourite playlist");
        }



        if(msgs.indexOf("what")!= -1){
            if(msgs.indexOf("your")!= -1){
                if(msgs.indexOf("name")!= -1){
                    speak("hi.... i am evelyn");
                }
            }

        }
        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("you")!= -1){
                if(msgs.indexOf("up")!= -1){
                    speak("for you...always boss");
                }
            }

        }
        if(msgs.indexOf("evelyn any")!= -1){
            if(msgs.indexOf("chances of ")!= -1){
                if(msgs.indexOf("my winning")!= -1){
                    speak("you got this boss go ahead");
                }
            }

        }
        if(msgs.indexOf("what's")!= -1){
            if(msgs.indexOf("time")!= -1){
                if(msgs.indexOf("now")!= -1){
                    Date date = new Date();
                    String time = DateUtils.formatDateTime(this,date.getTime(),DateUtils.FORMAT_SHOW_TIME);
                    speak(" the time now is "+time);
                }
            }
        }

        if(msgs.indexOf("what")!= -1){
            if(msgs.indexOf("today's")!= -1){
                if(msgs.indexOf("date")!= -1){
                    SimpleDateFormat df = new SimpleDateFormat("dd mmm yyyy");
                    Calendar cal = Calendar.getInstance();
                    String today_date = df.format(cal.getTime());
                    speak(" today's date is "+today_date);
                }
            }
        }
        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("google")!= -1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
                    startActivity(intent);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("flipkart")!= -1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flipkart.com"));
                    startActivity(intent);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("play")!= -1){
                if(msgs.indexOf("heart stereo")!= -1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/external/downloads/189139"));
                    startActivity(intent);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("play")!= -1){
                if(msgs.indexOf("somebody")!= -1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/external/downloads/187799"));
                    startActivity(intent);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("play")!= -1){
                if(msgs.indexOf("wish list")!= -1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/external/downloads/185651"));
                    startActivity(intent);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("play")!= -1){
                if(msgs.indexOf("divine")!= -1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/external/downloads/185650"));
                    startActivity(intent);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("play")!= -1){
                if(msgs.indexOf("date to you")!= -1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/external/downloads/182585"));
                    startActivity(intent);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("play")!= -1){
                if(msgs.indexOf("radioactive")!= -1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/external/downloads/182072"));
                    startActivity(intent);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("play")!= -1){
                if(msgs.indexOf("reality")!= -1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/external/downloads/181642"));
                    startActivity(intent);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("play")!= -1){
                if(msgs.indexOf("ludo")!= -1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/external/downloads/180137"));
                    startActivity(intent);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("play")!= -1){
                if(msgs.indexOf("bad boys")!= -1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/external/downloads/179398"));
                    startActivity(intent);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("play")!= -1){
                if(msgs.indexOf("one love")!= -1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/external/downloads/177802"));
                    startActivity(intent);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("play")!= -1){
                if(msgs.indexOf("we don't get high")!= -1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/external/downloads/176902"));
                    startActivity(intent);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("play")!= -1){
                if(msgs.indexOf("affection")!= -1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/external/downloads/176901"));
                    startActivity(intent);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("playlist")!= -1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/playlist?list=PLALQNuXpQ7EfL3POc8Dp0U0Jk5g433t0u"));
                    startActivity(intent);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("play")!= -1){
                if(msgs.indexOf("rockstar")!= -1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/external/downloads/173805"));
                    startActivity(intent);
                }
            }
        }


        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("blogger")!= -1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.blogger.com"));
                    startActivity(intent);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("amazon")!= -1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.com"));
                    startActivity(intent);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("browser")!= -1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
                    startActivity(intent);
                }
            }
        }


        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("youtube")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
                    ctx.startActivity(i);
                }
            }
        }


        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("fbook")!= -1){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wwww.facebook.com"));
                    startActivity(intent);
                }
            }
        }


        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("instagram")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.instagram.android");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("play")!= -1){
                if(msgs.indexOf("music")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.google.android.apps.youtube.music");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("make")!= -1){
                if(msgs.indexOf("payment")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.google.android.apps.nbu.paisa.user");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("spotify")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.spotify.music");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("files")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.google.android.apps.nbu.files");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("phonepe")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.phonepe.app");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("grow")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.nextbillion.groww");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("netflix")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.battlebot.dday");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("fitpal")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.xiaomi.hm.health");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("calorie tracker")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.healthifyme.basic");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("facebook")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.facebook.lite");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("telegram")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("org.telegram.messenger");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("pinterest")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.pinterest");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("gradeup")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("co.gradeup.android");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("paytm")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("net.one97.paytm");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("show me")!= -1){
                if(msgs.indexOf("account details")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.msf.kbank.mobile");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("hiragana")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.duolingo");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("rewards")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.google.android.apps.paidtasks");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("check my")!= -1){
                if(msgs.indexOf("mailbox")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("drive")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.google.android.apps.docs");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("i need")!= -1){
                if(msgs.indexOf("direction")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("gallery")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.sec.android.gallery3d");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("notepad")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.samsung.android.app.notes");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("calculator")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.sec.android.app.popupcalculator");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("my space")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.sec.android.app.myfiles");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("clock")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.sec.android.app.clockpackage");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("camera")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.sec.android.app.camera");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("set")!= -1){
                if(msgs.indexOf("reminder")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.samsung.android.app.reminder");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("calender")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.samsung.android.calendar");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("settings")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.android.settings");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("messages")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.samsung.android.messaging");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("play")!= -1){
                if(msgs.indexOf("radio")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.sec.android.app.fm");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("what is")!= -1){
                if(msgs.indexOf("the status")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.samsung.android.lool");
                    ctx.startActivity(i);
                }
            }
        }

        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("whatsapp")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                    ctx.startActivity(i);
                }
            }
        }


        if(msgs.indexOf("evelyn")!= -1){
            if(msgs.indexOf("open")!= -1){
                if(msgs.indexOf("mx player")!= -1){
                    Context ctx = this;
                    Intent i = ctx.getPackageManager().getLaunchIntentForPackage("com.mxtech.videoplayer.ad");
                    ctx.startActivity(i);
                }
            }
        }



        if (msgs.indexOf("call")!= -1) {
            final String[] listName = {""};
            final String name = fetchName(msgs);
            Log.d("Name",name);

            Dexter.withContext(this)
                    .withPermissions(
                            Manifest.permission.READ_CONTACTS,
                            Manifest.permission.CALL_PHONE
                    ).withListener(new MultiplePermissionsListener() {
                @Override public void onPermissionsChecked(MultiplePermissionsReport report) {

                    if (report.areAllPermissionsGranted()) {
                        if (checkForPreviousCallList(MainActivity.this)) {
                            speak(makeCallFromSavedContactList(MainActivity.this,name));

                        }else {
                            HashMap<String , String> list = getContactList(MainActivity.this, name);
                            if (list.size() > 1) {
                                for (String i : list.keySet() ) {
                                    listName[0] = listName[0].concat(".....................................!" +i);
                                }
                                speak("Which one sir?.....there is "+ listName[0]);
                            } else if (list.size() == 1) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    makeCall(MainActivity.this, list.values().stream().findFirst().get());
                                    clearContactListSavedData(MainActivity.this);
                                }
                            } else {
                                speak("No contact found !");
                                clearContactListSavedData(MainActivity.this);
                            }
                        }

                    }

                }
                @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest(); }
            }).check();

        }









    }






    public void startRecording(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);

        recognizer.startListening(intent);
    }
}