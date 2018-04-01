package cgdatlahacks2k18.autopug;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MatchesViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView mMatchID;
    public MatchesViewHolders(View itemView)
    {
        super(itemView);
        itemView.setOnClickListener(this);

        mMatchID = (TextView) itemView.findViewById(R.id.Matchid);
    }

    @Override
    public void onClick(View view)
    {

    }

}
