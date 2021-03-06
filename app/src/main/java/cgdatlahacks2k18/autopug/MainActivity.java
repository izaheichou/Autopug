package cgdatlahacks2k18.autopug;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import cgdatlahacks2k18.autopug.Cards.Card;
import cgdatlahacks2k18.autopug.Cards.CardsArrayAdapter;
import cgdatlahacks2k18.autopug.Games.Overwatch;
import cgdatlahacks2k18.autopug.Groups.ManageGroupActivity;
import cgdatlahacks2k18.autopug.Matches.MatchesActivity;

public class MainActivity extends AppCompatActivity {

    // From tutorial
    private Card cardsData[];  // TODO: remove if unnecessary
    private CardsArrayAdapter cardsArrayAdapter;
    ListView listView;
    List<Card> rowItems;  //  This replaces mPotentialMatchUids
    private DatabaseReference usersDb;
    private String currentUid;

    private int i;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usersDb = FirebaseDatabase.getInstance().getReference().child("users");

        mAuth = FirebaseAuth.getInstance();
        currentUid = mAuth.getCurrentUser().getUid();

        rowItems = new ArrayList<>();
        cardsArrayAdapter = new CardsArrayAdapter(this, R.layout.item, rowItems);

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        flingContainer.setAdapter(cardsArrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                cardsArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {  // TODO
                Card card = (Card) dataObject;
                String userId = card.getUserId();
                usersDb.child(userId).child("Games").child("Overwatch").child("Connections")
                        .child("No").child(currentUid).setValue(true);
                // Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {  // TODO
                Card card = (Card) dataObject;
                String userId = card.getUserId();
                usersDb.child(userId).child("Games").child("Overwatch").child("Connections")
                        .child("Yes").child(currentUid).setValue(true);
                isConnectionMatch(userId);
                // Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {  // TODO
                // Ask for more data here
                // mPotentialMatchUids.add("XML ".concat(String.valueOf(i)));
//                cardsArrayAdapter.notifyDataSetChanged();
//                Log.d("LIST", "notified");
//                i++;
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

    private void isConnectionMatch(final String userId) {  // userId is the one tied to the CARD
        // Check currentUser's connections - if userId exists under Connections - Yes
        final DatabaseReference currentUserConnectionsDb = usersDb.child(currentUid).child("Games")
                .child("Overwatch").child("Connections")
                .child("Yes").child(userId);
        currentUserConnectionsDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {  // Create/update Matches children
                // snapshot is Yes branch for CURRENT USER, whoever just swiped right i guess?
                if (dataSnapshot.exists()) {
                    Toast.makeText(MainActivity.this, "Match made!", Toast.LENGTH_LONG).show();
                    // TODO: I just realized this isn't game specific but we only have OW rn soooooo
                    // TODO: create group/add to current group????
                    // check if users -> uid -> Games -> Overwatch -> CurrentGroup -> groupId
                    // is null or not
                    // if it is null, check if other user's groupid is null
                    // if BOTH null, create new group under Groups and assign group id to both
                    // don't worry about party leader yet
                    // - need to make sure groups ONLY get individuals!!!
                    // individuals can swipe on groups AND indivs
                    // if ONE of users has a team already,
                    // FIRST CHECK if party is full -- if it is just notify user sorry, already filled and just ignore swipe?
                    // add individual to team by assigning
                    // their groupId as existing group's and adding user's id to groups -> groupid
                    // if group full, remove from pool

                    // TODO: temp implementation - currentUserId and userId is other user
                    final DatabaseReference currentUserCurrentGroupDb =
                            FirebaseDatabase.getInstance().getReference().child("users")
                            .child(currentUid).child("Games").child("Overwatch")
                            .child("currentGroup");

                    currentUserCurrentGroupDb.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                final String currUserGroupId = dataSnapshot.child("groupId").getValue(String.class);
                                if (!currUserGroupId.isEmpty()) {
                                    // add other user to group if their group id is null
                                    Log.d("currUserGroup", "is called!!!");
                                    final DatabaseReference otherUserCurrentGroupDb =
                                            FirebaseDatabase.getInstance().getReference().child("users")
                                                    .child(userId).child("Games").child("Overwatch")
                                                    .child("currentGroup");
                                    otherUserCurrentGroupDb.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                Log.d("OTHERUserGroup", "is called!!!");
                                                String otherUserGroupId = dataSnapshot.child("groupId").getValue(String.class);
                                                if (otherUserGroupId.isEmpty()) {
                                                    DatabaseReference membersDb = FirebaseDatabase.getInstance().getReference()
                                                            .child("groups").child(currUserGroupId).child("Members");
                                                    membersDb.push().setValue(userId);
                                                    otherUserCurrentGroupDb.child("groupId").setValue(currUserGroupId);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                } else {  // current user's group id is null
                                    // add current user to group if other group id is null
                                    final DatabaseReference otherUserCurrentGroupDb =
                                            FirebaseDatabase.getInstance().getReference().child("users")
                                                    .child(userId).child("Games").child("Overwatch")
                                                    .child("currentGroup");
                                    otherUserCurrentGroupDb.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                String otherUserGroupId = dataSnapshot.child("groupId").getValue(String.class);
                                                if (!otherUserGroupId.isEmpty()) {
                                                    DatabaseReference membersDb = FirebaseDatabase.getInstance().getReference()
                                                            .child("groups").child(otherUserGroupId).child("Members");
                                                    membersDb.push().setValue(currentUid);
                                                    currentUserCurrentGroupDb.child("groupId").setValue(otherUserGroupId);
                                                } else { // other user's group also null
                                                    // create new group under groups
                                                    Log.d("Last else", "is called!!!");
                                                    DatabaseReference groupsDb = FirebaseDatabase.getInstance().getReference()
                                                            .child("groups");
                                                    DatabaseReference newGroupDb = groupsDb.push();
                                                    newGroupDb.child("Members").push().setValue(currentUid);
                                                    newGroupDb.child("Members").push().setValue(userId);
                                                    currentUserCurrentGroupDb.child("groupId").setValue(newGroupDb.getKey());
                                                    otherUserCurrentGroupDb.child("groupId").setValue(newGroupDb.getKey());
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) { }
                                    });
                                }
                            } else {
                                Log.d("curruserdb", "SNAPSHOT DOESNT EXIST");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) { }
                    });

                    usersDb.child(dataSnapshot.getKey()).child("Games").child("Overwatch").
                            child("Connections").child("Matches").child(currentUid).setValue(true);
                    usersDb.child(currentUid).child("Games").child("Overwatch").child("Connections")
                            .child("Matches").child(dataSnapshot.getKey()).setValue(true);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

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
                        final List<String> user2Roles = new ArrayList<>();
                        final List<String> user2TeamRoles = new ArrayList<>();
                        final String user2preferredMode = dataSnapshot.child("preferredMode").getValue(String.class);
                        final String user2bio = dataSnapshot.child("Bio").getValue(String.class);

                        for (DataSnapshot childSnapshot : dataSnapshot.child("roles").getChildren()) {
                            user2Roles.add(childSnapshot.getValue(String.class));
                        }
                        for (DataSnapshot childSnapshot : dataSnapshot.child("teamRoles").getChildren()) {
                            user2TeamRoles.add(childSnapshot.getValue(String.class));
                        }

                        final String user2platform = dataSnapshot.child("platform").getValue(String.class);
                        Boolean checkRoles = checkMatch(user2Roles, user1TeamRoles);
                        Boolean modeMatch = checkMode(user1preferredMode, user2preferredMode);

                        if (checkRoles && modeMatch && user1preferredPlatform.equals(user2platform)) {
                            DatabaseReference otherUserDb = FirebaseDatabase.getInstance().getReference()
                                    .child("users").child(dataSnapshot.getKey());
                            otherUserDb.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Boolean notAlreadySeen =
                                            !dataSnapshot.child("Games").child("Overwatch")
                                                    .child("Connections").child("Yes")
                                                    .hasChild(currentUid)
                                                    &&
                                            !dataSnapshot.child("Games").child("Overwatch")
                                                    .child("Connections").child("No")
                                                    .hasChild(currentUid);
                                    if (notAlreadySeen) { // only add to cards if not seen yet
                                        String displayName = dataSnapshot.child("displayName").getValue(String.class);
                                        String existingProfileImageUrl = dataSnapshot.child("profileImageUrl").getValue(String.class);
                                        String profileImageUrl = "default";
                                        if (!profileImageUrl.equals(existingProfileImageUrl)) {
                                            profileImageUrl = existingProfileImageUrl;
                                        }
                                        rowItems.add(new Card(dataSnapshot.getKey(), displayName,
                                                profileImageUrl, user2preferredMode,
                                                user2platform, user2bio,user2Roles, user2TeamRoles));
                                        cardsArrayAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) { }
                            });
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("onChildChanged", "is called!!!");
                onChildAdded(dataSnapshot, s);
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

    public void goToMatches(View view) {
        Intent intent = new Intent(MainActivity.this, MatchesActivity.class);
        startActivity(intent);
        return;
    }

    public void goToSettings(View view) {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
        return;
    }

    public void goToGroup(View view) {
        Intent intent = new Intent(MainActivity.this, ManageGroupActivity.class);
        startActivity(intent);
        return;
    }

    public void setPreference(View view) {
        Intent intent = new Intent(MainActivity.this, PreferenceActivity.class);
        startActivity(intent);
        finish();
        return;
    }

}
