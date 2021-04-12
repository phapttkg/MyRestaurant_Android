package hue.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class StartActivity extends AppCompatActivity {
    ImageView logo;
    Button start;
    Animation translatey,translatex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        inits();
        start.startAnimation(translatey);
        logo.startAnimation(translatex);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(StartActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    public void inits(){
        logo=findViewById(R.id.logo1);
        start=findViewById(R.id.btnStart);
        translatey= AnimationUtils.loadAnimation(this,R.anim.translatey);
        translatex= AnimationUtils.loadAnimation(this,R.anim.translatex);

    }
}
