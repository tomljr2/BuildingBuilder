package jandj.buildingbuilder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView hw = findViewById(R.id.text);
        final EditText et1 = findViewById(R.id.et1), et2 = findViewById(R.id.et2);
        final Button b = findViewById(R.id.button);

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals(""))
                    b.setText(String.valueOf(Float.parseFloat(charSequence.toString()) *
                        Float.parseFloat(et2.getText().toString())));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals(""))
                    b.setText(String.valueOf(Float.parseFloat(et1.getText().toString()) *
                        Float.parseFloat(charSequence.toString())));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.setText(String.valueOf(Float.parseFloat(et1.getText().toString()) *
                        Float.parseFloat(et2.getText().toString())));
            }
        });
    }
}
