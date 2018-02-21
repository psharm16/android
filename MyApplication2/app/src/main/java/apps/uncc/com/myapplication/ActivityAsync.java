package apps.uncc.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by prek7 on 2/20/2018.
 */

public class ActivityAsync extends AsyncTask<RequestParam, Integer, String> {
    static ArrayList<String> data = new ArrayList<String>();
    private Context context;
    private Activity activity;

    @Override
    protected void onPostExecute(String s) {

        Button button = activity.findViewById(R.id.buttonStart);
        ImageView trivia = activity.findViewById(R.id.imageViewLaunch);
        button.setEnabled(true);
        trivia.setImageDrawable(context.getDrawable(R.drawable.trivia));
        trivia.setVisibility(View.VISIBLE);
        ProgressBar pb = activity.findViewById(R.id.progressBarTrivia);
        pb.setVisibility(View.GONE);
        TextView text= activity.findViewById(R.id.textView5);
        text.setText(R.string.triviaReady);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Question> questionDetails = new ArrayList<>();
                try {
                    Log.d("answer", questionDetails.size() + "onclick");
                    questionDetails = QuestionUtil.parseQuestion(data);
                    Log.d("answer", questionDetails.size() + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(context, TriviaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("json", questionDetails);
                i.putExtras(bundle);

                context.startActivity(i);

            }
        });
    }

    public ActivityAsync(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPreExecute() {


    }

    @Override
    protected String doInBackground(RequestParam... strings) {

        BufferedReader reader = null;
        try {
            HttpURLConnection con = strings[0].setUpConnection();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {

              data.add(line);

            }

            Log.d("*********",data.size()+"..");
            return "";

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }
}