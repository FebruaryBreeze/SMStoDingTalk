package februarybreeze.github.io.smstodingtalk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private Preferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preference = new Preferences(this);

        String noticedToken = preference.getDingTalkNoticedToken();
        if (!TextUtils.isEmpty(noticedToken)) {
            TextView tokenView = (TextView) findViewById(R.id.noticedTokenView);
            tokenView.setText(noticedToken);
        }

        String notNoticedToken = preference.getDingTalkNotNoticedToken();
        if (!TextUtils.isEmpty(notNoticedToken)) {
            TextView notNoticedTokenView = (TextView) findViewById(R.id.notNoticedTokenView);
            notNoticedTokenView.setText(notNoticedToken);
        }

        startService(new Intent(getBaseContext(), MainService.class));
    }

    public void setDingTalkNoticedToken(View view) {
        EditText tokenText = (EditText) findViewById(R.id.tokenText);
        String token = tokenText.getText().toString();
        preference.setDingTalkNoticedToken(token);

        TextView tokenView = (TextView) findViewById(R.id.noticedTokenView);
        tokenView.setText(token);
    }

    public void setDingTalkNotNoticedToken(View view) {
        EditText tokenText = (EditText) findViewById(R.id.tokenText);
        String token = tokenText.getText().toString();
        preference.setDingTalkNotNoticedToken(token);

        TextView tokenView = (TextView) findViewById(R.id.notNoticedTokenView);
        tokenView.setText(token);
    }
}
