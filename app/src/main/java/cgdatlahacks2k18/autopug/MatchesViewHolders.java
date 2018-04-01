package cgdatlahacks2k18.autopug;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MatchesViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView mMatchBattleTag, mMatchName;
    public ImageView mMatchImage;
    public MatchesViewHolders(View itemView)
    {
        super(itemView);
        itemView.setOnClickListener(this);

        mMatchBattleTag = (TextView) itemView.findViewById(R.id.matchbattletag);
        mMatchName = (TextView) itemView.findViewById(R.id.matchname);
        mMatchImage = (ImageView) itemView.findViewById(R.id.matchImage);
    }

    @Override
    public void onClick(View view)
    {

    }

}
