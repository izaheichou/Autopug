package cgdatlahacks2k18.autopug.Groups;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cgdatlahacks2k18.autopug.R;

public class GroupsViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView mGroupMemberBattleTag, mGroupMemberName;
    public ImageView mGroupMemberImage;
    public GroupsViewHolders(View itemView)
    {
        super(itemView);
        itemView.setOnClickListener(this);

        mGroupMemberBattleTag = (TextView) itemView.findViewById(R.id.matchbattletag);
        mGroupMemberName = (TextView) itemView.findViewById(R.id.matchname);
        mGroupMemberImage = (ImageView) itemView.findViewById(R.id.matchImage);
    }

    @Override
    public void onClick(View view) {
        // TODO
    }

}
