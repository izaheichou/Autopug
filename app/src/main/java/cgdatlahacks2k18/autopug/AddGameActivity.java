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

public class AddGameActivity extends AppCompatActivity {

    private ArrayAdapter<CharSequence> gameArrayAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

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
        List<String> roles = overwatch.getAllRoles();
        List<String> modes = overwatch.getAllModes();
        List<String> platforms = overwatch.getAllPlatforms();
        final LinearLayout layout = (LinearLayout) findViewById(R.id.activity_add_game);
        final TextView roleLabel = new TextView(this);
        final TextView modeLabel = new TextView(this);
        final TextView bioLabel = new TextView(this);
        final TextView platformLabel = new TextView(this);

        final EditText bio = new EditText(this);
        bio.setHint("overwatch bio");
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(140);
        bio.setFilters(fArray);

        roleLabel.setText("Select Role(s) You Play (You can change these anytime)");
        modeLabel.setText("Select Mode(s) You Play (You can change these anytime)");
        bioLabel.setText("Overwatch Specific Bio (max 140 chars)");
        platformLabel.setText("Select your platforms");

        layout.addView(bioLabel,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.addView(bio,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

        layout.addView(roleLabel,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

        for (String role : roles) {
            final CheckBox rolebox = new CheckBox(this);
            rolebox.setId(Overwatch.assignIdToString(role));
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
            modebox.setId(Overwatch.assignIdToString(mode));
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

        layout.addView(platformLabel,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

        for (String platform : platforms) {
            final CheckBox platbox = new CheckBox(this);
            platbox.setId(Overwatch.assignIdToString(platform));
            platbox.setText(platform);
            platbox.setOnClickListener(new View.OnClickListener() {
                public void onClick (View view) {
                    boolean checked = ((CheckBox) view).isChecked();

                    String valueString = Overwatch.idToString(view.getId());
                    overwatch.setPlatform(valueString, checked);
                }
            });
            layout.addView(platbox,
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        final Button submitBtn = new Button(this);
        submitBtn.setText("Submit");
        // submitBtn.setId(69);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String userId = mAuth.getCurrentUser().getUid();
                DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference()
                        .child("users").child(userId).child("Games").child(overwatch.getName());
                currentUserDb.setValue(null);
                currentUserDb.child("Bio").setValue(bio.getText().toString());
                List<String> playerRoles = overwatch.getRoles();
                for (String role : playerRoles) {
                    currentUserDb.child("Roles").push().setValue(role);
                }
                List<String> playerModes = overwatch.getModes();
                for (String mode : playerModes) {
                    currentUserDb.child("Modes").push().setValue(mode);
                }
                List<String> playerPlatforms = overwatch.getPlatforms();
                for (String platform : playerPlatforms) {
                    currentUserDb.child("Platforms").push().setValue(platform);
                }

                Intent intent = new Intent(AddGameActivity.this, MainActivity.class);  // TODO: finalize next page
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
