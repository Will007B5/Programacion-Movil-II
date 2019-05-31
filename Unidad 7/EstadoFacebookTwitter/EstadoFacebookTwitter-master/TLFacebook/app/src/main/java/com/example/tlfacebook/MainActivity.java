package com.example.tlfacebook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MainActivity extends AppCompatActivity {

    private int REQUEST_VIDEO_CODE=1000;
    EditText etTweet;
    Button btnCompLink, btnCompFoto, btnCompVid,btnTweet;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            SharePhoto sharePhoto = new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .build();

            if (ShareDialog.canShow(SharePhotoContent.class))
            {
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(sharePhoto)
                        .build();
                shareDialog.show(content);
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_main);

        btnCompLink = (Button) findViewById(R.id.btnCompartirLink);
        btnCompFoto = (Button) findViewById(R.id.btnCompartirFoto);
        btnCompVid = (Button) findViewById(R.id.btnCompartirVideo);
        etTweet = (EditText) findViewById(R.id.editTweet);

        btnTweet = (Button) findViewById(R.id.btnTweet);
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etTweet.getText().toString().length()>0){
                    String tw= etTweet.getText().toString();

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, tw);
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Tweet");
                    startActivity(Intent.createChooser(shareIntent.putExtra("Twitter", 3), "Selecciona Twitter"));
                    etTweet.setText("");
                    Toast.makeText(MainActivity.this,"Tweet Enviado!!!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Escribe algo para twittear",Toast.LENGTH_SHORT).show();
                }

            }
        });



        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        btnCompLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(MainActivity.this,"Compartida con Exito!",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this,"Cancelado",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setQuote("Este enlace utilizable")
                        .setContentUrl(Uri.parse("https://developer.android.com"))
                        .build();
                if (ShareDialog.canShow(ShareLinkContent.class))
                {
                    shareDialog.show(linkContent);
                }
            }
        });

        btnCompFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(MainActivity.this,"Compartida con Exito!",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this,"Cancelado",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

                Picasso.with(getBaseContext())
                        .load("https://www.w3schools.com/w3css/img_mountains.jpg")
                        .into(target);
            }
        });


        btnCompVid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //escoger video
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Seleccionar video:"),REQUEST_VIDEO_CODE);

            }
        });


        if (AccessToken.getCurrentAccessToken()==null){
            goLoginScreen();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode== RESULT_OK)
        {
            if (requestCode==REQUEST_VIDEO_CODE)
            {
                Uri selectedVideo = data.getData();

                ShareVideo video = new ShareVideo.Builder()
                        .setLocalUrl(selectedVideo)
                        .build();

                ShareVideoContent videoContent = new ShareVideoContent.Builder()
                        .setContentTitle("Este video es usado")
                        .setContentDescription("Mira este video descargado de YouTube")
                        .setVideo(video)
                        .build();
                if (shareDialog.canShow(ShareVideoContent.class))
                    shareDialog.show(videoContent);
            }
        }
    }

    private void goLoginScreen(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void Logout(View view) {
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }
}
