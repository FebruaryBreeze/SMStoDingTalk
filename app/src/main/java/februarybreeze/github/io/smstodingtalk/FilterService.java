package februarybreeze.github.io.smstodingtalk;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class FilterService extends IntentService {
    public FilterService() {
        super("FilterService");
    }

    public FilterService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return;
        }

        String message = intent.getStringExtra(Constant.SMS_Message);
        Preferences preferences = new Preferences(this);

        String currentToken;
        if (isSpamMessage(message)) {
            currentToken = preferences.getDingTalkNotNoticedToken();
        } else {
            currentToken = preferences.getDingTalkNoticedToken();
        }

        Intent serviceIntent = new Intent(this.getApplicationContext(), DingTalkService.class);
        serviceIntent.putExtra(Constant.Current_Ding_Talk_Token, currentToken);
        serviceIntent.putExtra(Constant.SMS_Message, message);
        this.startService(serviceIntent);
    }

    protected boolean isSpamMessage(String message) {
        ArrayList<String> filterStringList = new ArrayList<String>();
        filterStringList.add("每日优鲜");
        filterStringList.add("退订");
        filterStringList.add("阿里云");
        filterStringList.add("淘会员");
        filterStringList.add("亚马逊");

        for (String filterString: filterStringList) {
            if (message.contains(filterString)) {
                return true;
            }
        }
        return false;
    }
}
