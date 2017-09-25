package ana.samplechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ana.managers.ChatbotManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnStartChat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String webURL = ((EditText) findViewById(R.id.edtURL)).getText().toString();
                if (!TextUtils.isEmpty(webURL))
                    ChatbotManager.getInstance(MainActivity.this).startChat(webURL);
                else {
                    Toast.makeText(MainActivity.this, "Please Enter URL", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
