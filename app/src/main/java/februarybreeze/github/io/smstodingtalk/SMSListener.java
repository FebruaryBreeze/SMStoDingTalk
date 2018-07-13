package februarybreeze.github.io.smstodingtalk;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class SMSListener extends BroadcastReceiver {
    private static final String TAG = "SMSListener";
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String Robot_Url = "https://oapi.dingtalk.com/robot/send?access_token=";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();

            if (bundle != null) {
                SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
                StringBuilder body = new StringBuilder();
                String senderNumber = "";
                String date = "";

                for (SmsMessage message : messages) {
                    senderNumber = message.getDisplayOriginatingAddress();
                    date = getDate(message.getTimestampMillis());
                    body.append(message.getDisplayMessageBody());
                }

                String text = body.toString() + "[" + senderNumber + ", " + date + "]";
                sendMessage(text, context);
            }
        }
    }

    private String getDate(long time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            Date date = new Date(time);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sendMessage(String message, Context context) {
        String dingTalkToken = MySingleton.getInstance(context).getDingTalkToken();
        if (dingTalkToken.equals("")) {
            return;
        }

        final JSONObject root = new JSONObject();
        try {
            JSONObject content = new JSONObject();
            content.put("content", message);

            root.put("msgtype", "text");
            root.put("text", content);
        } catch (JSONException e) {
            Log.d(TAG, e.toString());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                Robot_Url + dingTalkToken,
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

        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
