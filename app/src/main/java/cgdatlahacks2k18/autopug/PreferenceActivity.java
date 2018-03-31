package cgdatlahacks2k18.autopug;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PreferenceActivity extends AppCompatActivity {

    private ArrayAdapter<CharSequence> gameArrayAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        mAuth = FirebaseAuth.getInstance();
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
                    generateOverwatchPreferenceForm();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO: maybe toast?
            }
        });

    }

    private void generateOverwatchPreferenceForm() {
        Log.d("addpreference", "generate overwatch preference form called");
        final GameTitle overwatch = new Overwatch();
        // generate check boxes
        List<String> roles = overwatch.getAllRoles();
        List<String> modes = overwatch.getAllModes();
        final LinearLayout layout = (LinearLayout) findViewById(R.id.activity_preference);
        final TextView roleLabel = new TextView(this);
        final TextView modeLabel = new TextView(this);

        roleLabel.setText("Select Team Role(s) You Prefer");
        modeLabel.setText("Select Mode(s) You Prefer");

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
        submitBtn.setText("Start Search");
        // submitBtn.setId(69);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String userId = mAuth.getCurrentUser().getUid();
                DatabaseReference searchPoolDb = FirebaseDatabase.getInstance().getReference()
                        .child("searchPool").child(overwatch.getName()).child("Individuals").child(userId);
                searchPoolDb.setValue(null);
                List<String> playerRoles = overwatch.getRoles();
                for (String role : playerRoles) {
                    searchPoolDb.child("preferredRoles").push().setValue(role);
                }
                List<String> playerModes = overwatch.getModes();
                for (String mode : playerModes) {
                    searchPoolDb.child("preferredModes").push().setValue(mode);
                }

                // TODO: go to next page

                Intent intent = new Intent(PreferenceActivity.this, MainActivity.class);  // TODO: finalize next page
                startActivity(intent);
                finish();
                return;
            }
        });
        layout.addView(submitBtn,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
    }


}