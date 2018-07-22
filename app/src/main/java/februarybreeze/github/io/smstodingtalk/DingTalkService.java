package februarybreeze.github.io.smstodingtalk;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.support.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class DingTalkService extends IntentService {
    public DingTalkService() {
        super("DingTalkService");
    }

    public DingTalkService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return;
        }

        String token = intent.getStringExtra(Constant.Current_Ding_Talk_Token);
        String message = intent.getStringExtra(Constant.SMS_Message);
        sendMessage(token, message);
    }

    private void sendMessage(String dingTalkToken, String message) {
        if (TextUtils.isEmpty(dingTalkToken)) {
            return;
        }

        final JSONObject root = new JSONObject();
        try {
            JSONObject content = new JSONObject();
            content.put("content", message);
            root.put("msgtype", "text");
            root.put("text", content);
        } catch (JSONException e) {
            Log.d("DingTalkService", e.toString());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                Constant.Ding_Talk_Robot_Url + dingTalkToken,
                root,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
