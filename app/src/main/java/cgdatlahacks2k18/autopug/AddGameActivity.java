package cgdatlahacks2k18.autopug;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class AddGameActivity extends AppCompatActivity {

    private ArrayAdapter<CharSequence> gameArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        Spinner spinner = (Spinner) findViewById(R.id.gameslist_spinner);
        gameArrayAdapter = ArrayAdapter.createFromResource(this, R.array.games_masterlist, android.R.layout.simple_spinner_item);
        gameArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(gameArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = (String) adapterView.getItemAtPosition(i);
                // generate game specific form here
                if (selected.equals("Overwatch")) {
                    generateOverwatchForm();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO: maybe toast?
            }
        });

    }

    private void generateOverwatchForm() {
        Log.d("addgameactivity", "generate overwatch form called");
        final GameTitle overwatch = new Overwatch();
        // generate check boxes
        List<String> roles = overwatch.getRoles();
        List<String> modes = overwatch.getModes();
        final LinearLayout layout = (LinearLayout) findViewById(R.id.activity_add_game);
        final TextView roleLabel = new TextView(this);
        final TextView modeLabel = new TextView(this);
        roleLabel.setText("Select Role(s) You Play (You can change these anytime)");
        modeLabel.setText("Select Mode(s) You Play (You can change these anytime)");

        layout.addView(roleLabel,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

        for (String role : roles) {
            final CheckBox rolebox = new CheckBox(this);
            rolebox.setId(Overwatch.assignIdToString(role));
            // TODO: Layout params??
            rolebox.setText(role);
            rolebox.setOnClickListener(new View.OnClickListener() {
                public void onClick (View view) {
                    boolean checked = ((CheckBox) view).isChecked();

                    String valueString = Overwatch.idToString(view.getId());
                    overwatch.setRole(valueString, checked);
                }
            });
            layout.addView(rolebox,
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        layout.addView(modeLabel,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

        for (String mode : modes) {
            final CheckBox modebox = new CheckBox(this);
            modebox.setId(Overwatch.assignIdToString(mode));  // TODO: set this id, needs to be used on onclick
            // TODO: Layout params??
            modebox.setText(mode);
            modebox.setOnClickListener(new View.OnClickListener() {
                public void onClick (View view) {
                    boolean checked = ((CheckBox) view).isChecked();

                    String valueString = Overwatch.idToString(view.getId());
                    overwatch.setMode(valueString, checked);
                }
            });
            layout.addView(modebox,
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        final Button submitBtn = new Button(this);
        submitBtn.setText("Submit");
        // submitBtn.setId(69);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // TODO: update database, User class? add overwatch instance to their list of games

                /* // TODO: go to next page
                Intent intent = new Intent(AddGameActivity.this, MainActivity.class);  // TODO: finalize next page
                    startActivity(intent);
                    finish();
                    return;
                 */
            }
        });
        layout.addView(submitBtn,
                new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
    }


}
