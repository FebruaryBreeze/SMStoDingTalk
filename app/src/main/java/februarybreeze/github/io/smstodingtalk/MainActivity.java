package februarybreeze.github.io.smstodingtalk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        String token = preference.getDingTalkToken();
        if (token.length() > 0) {
            TextView tokenView = (TextView) findViewById(R.id.tokenView);
            tokenView.setText(token);
        }
    }

    public void setDingTalkToken(View view) {
        EditText tokenText = (EditText) findViewById(R.id.tokenText);
        String token = tokenText.getText().toString();
        preference.setDingTalkToken(token);

        TextView tokenView = (TextView) findViewById(R.id.tokenView);
        tokenView.setText(token);
    }
}
