package apps.uncc.com.myapplication;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prek7 on 2/20/2018.
 */

public class QuestionUtil {



    static ArrayList<Question> parseQuestion(ArrayList<String> in) throws JSONException {


        ArrayList<Question> questionArrayList = new ArrayList<>();



        for(int i = 0;i<in.size();i++){

            String fields[]=in.get(i).split(";");
            Question question = new Question();

            question.setId(Integer.parseInt(fields[0]));

            question.setQuestion(fields[1]);
            if (!fields[2].isEmpty())
            {
                question.setImagehref(fields[2]);

            }

            question.setAnswer(Integer.parseInt(fields[fields.length-1]));

            for (int k = 3;k<fields.length-1;k++)
            {
                question.getChoices().add(fields[k]);


            }


            questionArrayList.add(question);
        }

Log.d("7777777777","val"+questionArrayList.toString());
        return questionArrayList;
    }


}
