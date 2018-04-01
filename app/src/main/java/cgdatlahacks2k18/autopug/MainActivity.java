package cgdatlahacks2k18.autopug;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> mPotentialMatchUids;
    private ArrayAdapter<String> arrayAdapter;
    private int i;
    private List<GameTitle> mGameList;  // TODO: not sure if we need this here but leaving it here for now
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mGameList = new ArrayList<>();
        mGameList.add(new Overwatch());

        mPotentialMatchUids = new ArrayList<>();  // these are the cards

        arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.helloText, mPotentialMatchUids );

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                mPotentialMatchUids.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {  // TODO
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {  // TODO
                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {  // TODO
                // Ask for more data here
                mPotentialMatchUids.add("XML ".concat(String.valueOf(i)));
                arrayAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
//                View view = flingContainer.getSelectedView();
//                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
//                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });

        // TODO
        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
            }
        });
        if (getIntent().getStringExtra("gameName") != null) {
            GetPotentialMatches();
        }
    }

    public void GetPotentialMatches() {
        final String userId = mAuth.getCurrentUser().getUid();
        String gameName = getIntent().getStringExtra("gameName");  // use this as search pool name
        final DatabaseReference searchPoolDb = FirebaseDatabase.getInstance().getReference()
                .child("searchPool").child(gameName).child("Individuals");
        final List<String> user1TeamRoles = getIntent().getStringArrayListExtra("user1TeamRoles");
        final String user1preferredMode = getIntent().getStringExtra("user1Mode");
        final String user1preferredPlatform = getIntent().getStringExtra("user1Platform");

        Log.d("gameName", gameName);

        searchPoolDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("onChildAdded", "is called!!!");
                if (dataSnapshot.exists()) {
                    if (!userId.equals(dataSnapshot.getKey())) {
                        // TODO: check match, can implement score later
                        List<String> user2Roles = new ArrayList<>();
                        String user2preferredMode = dataSnapshot.child("preferredMode").getValue(String.class);

                        for (DataSnapshot childSnapshot : dataSnapshot.child("roles").getChildren()) {
                            user2Roles.add(childSnapshot.getValue(String.class));
                        }

                        String user2platform = dataSnapshot.child("platform").getValue(String.class);

                        Boolean checkRoles = checkMatch(user2Roles, user1TeamRoles);

                        Boolean modeMatch = checkMode(user1preferredMode, user2preferredMode);

                        if (checkRoles && modeMatch &&
                                user1preferredPlatform.equals(user2platform)) {
                            mPotentialMatchUids.add(dataSnapshot.getKey());
                            arrayAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("onChildChanged", "is called!!!");
                if (dataSnapshot.exists()) {
                    if (!userId.equals(dataSnapshot.getKey())) {
                        // TODO: check match, can implement score later
                        List<String> user2Roles = new ArrayList<>();
                        String user2preferredMode = dataSnapshot.child("preferredMode").getValue(String.class);

                        for (DataSnapshot childSnapshot : dataSnapshot.child("roles").getChildren()) {
                            user2Roles.add(childSnapshot.getValue(String.class));
                        }

                        String user2platform = dataSnapshot.child("platform").getValue(String.class);

                        Boolean checkRoles = checkMatch(user2Roles, user1TeamRoles);

                        Boolean modeMatch = checkMode(user1preferredMode, user2preferredMode);

                        if (checkRoles && modeMatch &&
                                user1preferredPlatform.equals(user2platform)) {
                            mPotentialMatchUids.add(dataSnapshot.getKey());
                            arrayAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    // TODO: Can use for both role and modes, just do normal string matching. Can add score later.
    // TODO: ANY!!!
    public Boolean checkMatch(List<String> user1Info, List<String> user2Preferences) {
        if (user1Info.isEmpty() || user2Preferences.isEmpty()) {
            Log.d("checkMatch", "empty!!!!!!!");
        }
        if (user1Info.contains(Overwatch.Ids.ANYROLE.getName()) ||
                user2Preferences.contains(Overwatch.Ids.ANYROLE.getName())) {
            return true;
        }
        for (String s : user1Info) {
            Log.d("checkMatch", s);
            if (user2Preferences.contains(s)) {
                return true;
            }
        }
        return false;
    }

    public Boolean checkMode(String user1Mode, String user2Mode) {
        if (user1Mode == null || user2Mode == null) {
            Log.d("checkMode", "MODE NULL!!!!.");
            return false;
        }
        if (user1Mode.equals(Overwatch.Ids.ANYMODE.getName()) ||
                user2Mode.equals(Overwatch.Ids.ANYMODE.getName())) {
            return true;
        } else {
            return user1Mode.equals(user2Mode);
        }
    }

    public void logoutUser(View view) {
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this, ChooseLoginRegistrationActivity.class);
        startActivity(intent);
        finish();
        return;
    }

    public void setPreference(View view) {
        Intent intent = new Intent(MainActivity.this, PreferenceActivity.class);
        startActivity(intent);
        finish();
        return;
    }

}
