package cgdatlahacks2k18.autopug.Groups;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cgdatlahacks2k18.autopug.R;

public class ManageGroupActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mGroupsAdapter;
    private RecyclerView.LayoutManager mGroupLayoutManager;
    private List<GroupMemberObject> groupMembers = new ArrayList<GroupMemberObject>();
    private FirebaseAuth mAuth;
    private String currentUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_group);

        mAuth = FirebaseAuth.getInstance();
        currentUid = mAuth.getCurrentUser().getUid();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mGroupLayoutManager = new LinearLayoutManager(ManageGroupActivity.this);
        mRecyclerView.setLayoutManager(mGroupLayoutManager);
        mGroupsAdapter = new GroupsAdapter(getGroupMembers(), ManageGroupActivity.this);
        mRecyclerView.setAdapter(mGroupsAdapter);

        getGroupMemberIds();
    }

    private void getGroupMemberIds() {
        DatabaseReference currentUserGroupIdDb = FirebaseDatabase.getInstance().getReference().child("users")
                .child(currentUid).child("Games").child("Overwatch").child("currentGroup");

        currentUserGroupIdDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String groupId = dataSnapshot.child("groupId").getValue(String.class);

                    DatabaseReference groupMembersDb = FirebaseDatabase.getInstance().getReference()
                            .child("groups").child(groupId).child("Members");

                    groupMembersDb.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot member : dataSnapshot.getChildren()) {
                                    FetchUserInformation(member.getValue().toString());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) { }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void FetchUserInformation(String key) {
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("users")
                .child(key);
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String userId = dataSnapshot.getKey();
                    String displayName = "";
                    String profileImageUrl = "";
                    String battleTag = "";
                    if (dataSnapshot.child("displayName").getValue() != null) {
                        displayName = dataSnapshot.child("displayName").getValue(String.class);
                    }
                    if (dataSnapshot.child("profileImageUrl").getValue() != null) {
                        profileImageUrl = dataSnapshot.child("profileImageUrl").getValue(String.class);
                    }
                    if (dataSnapshot.child("battleTag").getValue() != null) {
                        battleTag = dataSnapshot.child("battleTag").getValue(String.class);
                    }
                    GroupMemberObject obj = new GroupMemberObject(userId, displayName, profileImageUrl, battleTag);
                    groupMembers.add(obj);
                    mGroupsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public List<GroupMemberObject> getGroupMembers() {
        return groupMembers;
    }
}
