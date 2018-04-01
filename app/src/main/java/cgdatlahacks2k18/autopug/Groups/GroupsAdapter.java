package cgdatlahacks2k18.autopug.Groups;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import cgdatlahacks2k18.autopug.R;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsViewHolders> {
    private List<GroupMemberObject> groupsList;
    private Context context;

    public GroupsAdapter(List <GroupMemberObject> groupsList, Context context)
    {
        this.groupsList = groupsList;
        this.context = context;
    }

    @Override
    public GroupsViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_groupmember, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        GroupsViewHolders rcv = new GroupsViewHolders(layoutView);

        return rcv;
    }

    @Override
    public void onBindViewHolder(GroupsViewHolders holder, int position) {
        holder.mGroupMemberBattleTag.setText("BattleTag: " + groupsList.get(position).getBattleTag());
        holder.mGroupMemberName.setText(groupsList.get(position).getDisplayName());
        if (!groupsList.get(position).getProfileImageUrl().equals("default")) {
            Glide.with(context).load(groupsList.get(position).getProfileImageUrl()).into(holder.mGroupMemberImage);
        }
    }

    @Override
    public int getItemCount() {
        return groupsList.size();
    }
}

