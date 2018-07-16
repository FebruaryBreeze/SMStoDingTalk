package februarybreeze.github.io.smstodingtalk;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private SharedPreferences mPreference;

    Preferences(Context context) {
        mPreference = context.getSharedPreferences(Constant.SETTING_FILE_NAME, Context.MODE_PRIVATE);
    }

    public String getDingTalkToken() {
        return mPreference.getString(Constant.Ding_Talk_Robot_Token, "");
    }

    public void setDingTalkToken(String token) {
        mPreference.edit().putString(Constant.Ding_Talk_Robot_Token, token).apply();
    }
}
