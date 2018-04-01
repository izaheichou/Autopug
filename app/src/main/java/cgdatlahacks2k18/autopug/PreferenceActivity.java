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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
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
        final GameTitle overwatchTeam = new Overwatch();
        // generate check boxes
        List<String> roles = overwatch.getAllRoles();
        List<String> modes = overwatch.getAllModes();
        List<String> platforms = overwatch.getAllPlatforms();
        final LinearLayout layout = (LinearLayout) findViewById(R.id.activity_preference);
        final TextView teamRoleLabel = new TextView(this);
        final TextView roleLabel = new TextView(this);
        final TextView modeLabel = new TextView(this);
        final TextView platformLabel = new TextView(this);
        final RadioGroup platformGroup = new RadioGroup(this);
        final RadioGroup modeGroup = new RadioGroup(this);

        final TextView bioLabel = new TextView(this);
        final EditText bio = new EditText(this);
        bio.setHint("overwatch bio");
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(140);
        bio.setFilters(fArray);

        teamRoleLabel.setText("Select Team Mate(s) You Prefer");
        roleLabel.setText("Select Role(s) You Want Now");
        modeLabel.setText("Select Mode You Want Now");
        platformLabel.setText("Select Platform You Want Now");

        layout.addView(bioLabel,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.addView(bio,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

        layout.addView(teamRoleLabel,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

        for (String role : roles) {  // FOR TEAM!!!
            final CheckBox rolebox = new CheckBox(this);
            rolebox.setId(Overwatch.assignIdToString(role));
            rolebox.setText(role);
            rolebox.setOnClickListener(new View.OnClickListener() {
                public void onClick (View view) {
                    boolean checked = ((CheckBox) view).isChecked();

                    String valueString = Overwatch.idToString(view.getId());
                    overwatchTeam.setRole(valueString, checked);
                }
            });
            layout.addView(rolebox,
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        // FOR CURRENT USER
        layout.addView(roleLabel,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

        for (String role : roles) {  // FOR TEAM!!!
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
            final RadioButton modebtn = new RadioButton(this);
            modebtn.setId(Overwatch.assignIdToString(mode));  // TODO: set this id, needs to be used on onclick
            modebtn.setText(mode);
            modeGroup.addView(modebtn,
                    new RadioGroup.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        layout.addView(modeGroup,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

        // FOR PLATFORM!!
        layout.addView(platformLabel,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

        for (String platform : platforms) {
            final RadioButton platbtn = new RadioButton(this);
            platbtn.setId(Overwatch.assignIdToString(platform));  // TODO: set this id, needs to be used on onclick
            platbtn.setText(platform);
            platformGroup.addView(platbtn,
                    new RadioGroup.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        layout.addView(platformGroup,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

        final Button submitBtn = new Button(this);
        submitBtn.setText("Start Search");
        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int platformId = platformGroup.getCheckedRadioButtonId();
                int modeId = modeGroup.getCheckedRadioButtonId();
                String playerPlatform = Overwatch.idToString(platformId);
                String playerMode = Overwatch.idToString(modeId);
                String userId = mAuth.getCurrentUser().getUid();
                DatabaseReference searchPoolDb = FirebaseDatabase.getInstance().getReference()
                        .child("searchPool").child(overwatch.getName()).child("Individuals").child(userId);

                DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference()
                        .child("users").child(userId).child("Games").child(overwatch.getName());
                // currentUserDb.setValue(null);
                currentUserDb.child("Bio").setValue(bio.getText().toString());
                searchPoolDb.child("Bio").setValue(bio.getText().toString());

                // searchPoolDb.setValue(null);
                List<String> playerRoles = overwatch.getRoles();
                searchPoolDb.child("roles").setValue(null);
                for (String role : playerRoles) {
                    searchPoolDb.child("roles").push().setValue(role);
                }
                List<String> teamRoles = overwatchTeam.getRoles();
                searchPoolDb.child("teamRoles").setValue(null);
                for (String role : teamRoles) {
                    searchPoolDb.child("teamRoles").push().setValue(role);
                }
                searchPoolDb.child("preferredMode").setValue(playerMode);
                searchPoolDb.child("platform").setValue(playerPlatform);

                Intent intent = new Intent(PreferenceActivity.this, MainActivity.class);  // TODO: finalize next page
                intent.putExtra("gameName", overwatch.getName());
                intent.putExtra("user1Platform", playerPlatform);
                intent.putStringArrayListExtra("user1TeamRoles", (ArrayList<String>) teamRoles);
                intent.putExtra("user1Mode", playerMode);
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