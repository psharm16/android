package apps.uncc.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by prek7 on 2/20/2018.
 */

public class GetActivity extends AsyncTask<RequestParam,Void,Void> {
    static ArrayList<String> data = new ArrayList<String>();
    Context context;
    private Activity activity;

    public GetActivity(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        ArrayList<Question> questionDetails = new ArrayList<>();
        try {
            questionDetails = QuestionUtil.parseQuestion(data);
            Log.d("ans*****",questionDetails.size()+"");
        }catch(Exception e)
        {
e.printStackTrace();
        }
        Intent i1 = new Intent (context, TriviaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("json",questionDetails);
        i1.putExtras(bundle);
        context.startActivity(i1);
    }

    @Override
    protected Void doInBackground(RequestParam... requestParams) {
        BufferedReader reader = null;
        try {
            HttpURLConnection con = requestParams[0].setUpConnection();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {

                data.add(line);

            }
            return null;
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