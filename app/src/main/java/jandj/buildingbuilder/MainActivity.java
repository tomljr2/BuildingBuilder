package jandj.buildingbuilder;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    final int STUDDISTANCE = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView lengthTV = findViewById(R.id.LengthTV);
        final EditText lengthET = findViewById(R.id.LengthET);
        final Button b = findViewById(R.id.Button);
        final Spinner units = findViewById(R.id.spinner);
        final TextView studsTV = findViewById(R.id.StudsTV);

        //Dynamically get the value in the EditText and change the color if the value is not valid.
        lengthET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //This regular expression will check to see if the value is a number
                if(charSequence.toString().matches("(\\d+(?:\\.\\d+)?)|(.+(\\d+))"))
                    lengthTV.setTextColor(Color.DKGRAY);
                else
                    lengthTV.setTextColor(Color.RED);

                studsTV.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        //When the button is pressed, it should automatically figure out the position of the studs
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Float length = 0f;

                //If the text is red, an invalid number was inputted
                if(lengthTV.getCurrentTextColor() != Color.RED)
                {
                    length = Float.parseFloat(lengthET.getText().toString());

                    if(units.getSelectedItem().toString().equals("ft"))
                        length = ftToIn(length);
                    else if(units.getSelectedItem().toString().equals("cm"))
                        length = cmToIn(length);
                }
                if(lengthTV.getCurrentTextColor() == Color.RED)
                {
                    studsTV.setText("ERROR: Invalid number.");
                }
                //Easter egg just for funsies
                else if(length >= 535012204.7243)
                {
                    studsTV.setText("ERROR: There is literally nowhere on Earth with that " +
                            "much land to build on.");
                }
                //Set an upper limit to how long the wall can be. This prevents the app from
                //using all resources on your phone with really large numbers.
                else if(length > 12000.0)
                {
                    studsTV.setText("ERROR: Length too large.");
                }
                //The number cannot be smaller than 2 inches because we are assuming the
                //construction worker is using 2x4s
                else if (length < 2.0)
                {
                    lengthTV.setTextColor(Color.RED);
                    studsTV.setText("ERROR: Length too small.");
                }
                //If it is smaller than the width of two 2x4s, then we only want to use one.
                else if(length < 4.0)
                {
                    studsTV.setText("The center of stud 1 should be "
                            + String.format("%.2f",length/2) + " in. along the base.");
                }
                //Otherwise we will go through a loop to calculate and display every stud and its
                //position on the base piece of wood.
                else
                {
                    float currentPosition = 0;
                    int studs = (int)(length / STUDDISTANCE);
                    float excess = (int)(length % STUDDISTANCE);

                    if(length % STUDDISTANCE != 0)
                        studs++;

                    String response = "The center of stud 1 should be 1.0 in. along the base.\n";

                    for(int i = 0; i < studs; i++)
                    {
                        if(i == (studs - 1))
                        {
                            response += "The center of stud " + (i+2) +  " should be "
                                    + String.format("%.2f",length-1) + " in. along the base.\n";
                            break;
                        }
                        else if(length % STUDDISTANCE == 0)
                            currentPosition += STUDDISTANCE;
                        else if(i == 0)
                            currentPosition = (excess + STUDDISTANCE)/2;
                        else
                            currentPosition += STUDDISTANCE;

                        response += "The center of stud " + (i+2) +" should be "
                                + String.format("%.2f",currentPosition) + " in. along the base.\n";
                    }

                    //Display the results
                    studsTV.setText(response);

                }
            }
        });
    }

    //Conversion functions
    float ftToIn(float length)
    { return length * 12; }
    float cmToIn(float length)
    { return length * (float)(0.393701); }
}
