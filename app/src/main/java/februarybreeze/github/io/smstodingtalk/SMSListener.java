package februarybreeze.github.io.smstodingtalk;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class SMSListener extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

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
                startSmsService(context, text);
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

    private void startSmsService(Context context, String message) {
        Intent serviceIntent = new Intent(context, FilterService.class);
        serviceIntent.putExtra(Constant.SMS_Message, message);
        context.startService(serviceIntent);
    }
}
