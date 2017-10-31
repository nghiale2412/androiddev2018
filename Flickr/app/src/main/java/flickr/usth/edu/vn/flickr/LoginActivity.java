package flickr.usth.edu.vn.flickr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText Username;
    private EditText Password;
    private CardView Login;
    private TextView Warning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = (EditText) findViewById(R.id.editText4);
        Password = (EditText) findViewById(R.id.editText5);
        Login = (CardView) findViewById(R.id.cardView);
        Warning = (TextView) findViewById(R.id.Warning);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validate(Username.getText().toString(), Password.getText().toString());

            }
        });
    }

    private void validate(String name, String pass) {
        if ((name.equals("tuanct1997")) && (pass.equals("123456789"))) {
            Intent intent = new Intent(LoginActivity.this, FlickrActivity.class);
            overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
            startActivity(intent);
            finish();

        } else {

            Warning.setText("Try Again");

        }

    }
}


