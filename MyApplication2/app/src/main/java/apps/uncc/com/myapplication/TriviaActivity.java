package apps.uncc.com.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prek7 on 2/20/2018.
 */
public class TriviaActivity extends AppCompatActivity {
    static int start = 0;
    static ArrayList<Question> questions;
    ImageView imageView ;
    ProgressBar pb;
    static int rightanswers=0;
    static int totalanswers;
    static int checkedId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        setTitle(getResources().getString(R.string.title));
        start = 0;rightanswers=0;

        final MyCount counter = new MyCount(120000,1);
        counter.start();
        Intent intent = getIntent();
        questions = intent.getExtras().getParcelableArrayList("json");
        Button quit = (Button) findViewById(R.id.buttonQuit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TriviaActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        totalanswers = questions.size();
        imageView = (ImageView) findViewById(R.id.imageViewQues);
        pb = (ProgressBar) findViewById(R.id.progressBarQues);
        TextView load = (TextView)findViewById(R.id.textView6);
        TextView tv = (TextView) findViewById(R.id.textViewQuesIs);
        tv.setText(questions.get(0).getQuestion());
        addRadioButtons(questions.get(0).getChoices().size(),questions.get(0).getChoices());
        Picasso.with(TriviaActivity.this).load(questions.get(0).getImagehref()).into(imageView);
        pb.setVisibility(View.INVISIBLE);

        load.setVisibility(View.INVISIBLE);

        Log.d("taggged", questions.get(0).getImagehref());
        Button button = (Button) findViewById(R.id.buttonNext);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
                start++;
                int no = start+1;
                if (no<=questions.size()) {
                    TextView tvn = (TextView) findViewById(R.id.textViewQuesNo);
                    tvn.setText("Q" + no);
                    TextView tv = (TextView) findViewById(R.id.textViewQuesIs);
                    tv.setText(questions.get(start).getQuestion());
                    addRadioButtons(questions.get(start).getChoices().size(), questions.get(start).getChoices());
                    new ImageLoad(getApplicationContext(),TriviaActivity.this).execute(questions.get(start).getImagehref());

                }else
                {
                    counter.cancel();
                    startintent();
                }
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        rightanswers = 0;
    }

    public static int getRightanswers() {
        return rightanswers;
    }

    public static void setRightanswers(int rightanswers) {
        TriviaActivity.rightanswers = rightanswers;
    }

    public static int getTotalanswers() {
        return totalanswers;
    }

    public static void setTotalanswers(int totalanswers) {
        TriviaActivity.totalanswers = totalanswers;
    }

    public void checkAnswer(){
        if (checkedId== questions.get(start).getAnswer()){
        rightanswers++;
        }
        Log.d("query", "answered"+questions.get(start).getAnswer());
        Log.d("query", "right answers"+rightanswers);

    }
    public void startintent(){
        Intent intent = new Intent (TriviaActivity.this, StatsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("json", questions);
        intent.putExtras(bundle);
        startActivity(intent);

    }
    public void addRadioButtons(int number, List<String> choices) {

        RadioGroup rg = (RadioGroup) findViewById(R.id.RgOption);
        int count = rg.getChildCount();
        if( count> 0)
        {
            for (int i = count; i>=0;i--)
            {
                View o = rg.getChildAt(i);
                if(o instanceof RadioGroup)
                    rg.removeViewAt(i);
            }
        }


        for (int row = 0; row < 1; row++) {
            RadioGroup ll = new RadioGroup(this);
            ll.setOrientation(LinearLayout.VERTICAL);

            for (int i = 0; i < number; i++) {
                final RadioButton rdbtn = new RadioButton(this);
                rdbtn.setId(i);
                rdbtn.setText(choices.get(i));
                rdbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("quey",rdbtn.getId()+"");
                        checkedId = rdbtn.getId();
                    }
                });

                ll.addView(rdbtn);
            }
            ((ViewGroup) findViewById(R.id.RgOption)).addView(ll);
        }

    }

    public class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            TextView tv = (TextView) findViewById(R.id.textViewTime);
            int d= (int) (l/1000);
            int m = d/60; int s = d%60;
            tv.setText("Time Left "+m+":"+s);
        }

        @Override
        public void onFinish() {
            checkAnswer();
            startintent();

        }
    }

}
