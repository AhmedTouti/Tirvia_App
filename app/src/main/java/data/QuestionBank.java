package data;

import android.app.DownloadManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import controller.AppController;
import model.Question;

/**https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json**/
public class QuestionBank {
    private String url="https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
    ArrayList<Question> questionArrayList=new ArrayList<>();

    public QuestionBank() {
    }

    public List<Question> getQuestions(final AnswerListAsyncResponse callBack)
        {


            JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET,url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<response.length();i++) {
                    Question question=new Question();
                    try {
                       question.setAnswer( response.getJSONArray(i).get(0).toString());

                        /**question.setAnswerTrue(Boolean.valueOf( response.getJSONArray(i).getBoolean(1)));**/
                        question.setAnswerTrue(Boolean.valueOf( response.getJSONArray(i).get(1).toString().trim()));
                        questionArrayList.add(question);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                if (callBack != null)callBack.processFinished(questionArrayList);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
            AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        return questionArrayList;

    }

}
