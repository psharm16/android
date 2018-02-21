package apps.uncc.com.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by prek7 on 2/20/2018.
 */

public class StatsActivity extends AppCompatActivity {
    double percent;
    @Override
    protected void onPostResume() {
        super.onPostResume();
        percent = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        setTitle(getResources().getString(R.string.title));
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBarCorrect);
        pb.setProgress(0);
        percent = (double) TriviaActivity.rightanswers/(double) TriviaActivity.totalanswers;
        percent = percent*100;
        TextView tv = (TextView) findViewById(R.id.textViewPerct);
        tv.setText(percent+"%");
        pb.setMax(100);
        pb.setProgress((int) (percent));

        final Button finish = (Button) findViewById(R.id.buttonQuit);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StatsActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        final Button tryagain = (Button) findViewById(R.id.buttonTry);
        final Context context = this.getApplicationContext();
        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = getIntent();
                    ArrayList<Question> questions = intent.getExtras().getParcelableArrayList("json");
                    Intent i = new Intent(context, TriviaActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("json", questions);
                    i.putExtras(bundle);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);




            }
        });

    }


}
