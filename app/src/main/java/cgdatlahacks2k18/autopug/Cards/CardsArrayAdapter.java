package cgdatlahacks2k18.autopug.Cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cgdatlahacks2k18.autopug.R;

public class CardsArrayAdapter extends ArrayAdapter {

    public CardsArrayAdapter(Context context, int resourceId, List<Card> items) {
        super(context, resourceId, items);
    }

    public View getView(int pos, View convertView, ViewGroup parent) {  // this populates each card
        Card cardItem = (Card) getItem(pos);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView mode = (TextView) convertView.findViewById(R.id.mode);
        TextView roles = (TextView) convertView.findViewById(R.id.roles);
        TextView teamRoles = (TextView) convertView.findViewById(R.id.teamRoles);
        TextView platform = (TextView) convertView.findViewById(R.id.platform);
        TextView bio = (TextView) convertView.findViewById(R.id.bio);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);

        name.setText(cardItem.getName());
        mode.setText(cardItem.getMode());
        roles.setText("Their role(s): " + cardItem.getRolesAsString());
        teamRoles.setText("Looking for: " + cardItem.getTeamRolesAsString());
        platform.setText(cardItem.getPlatform());
        bio.setText("Bio: " + cardItem.getBio());

        switch (cardItem.getProfileImageUrl()) {
            case "default":
                Glide.with(convertView.getContext()).load(R.mipmap.ic_launcher).into(image);
                break;
            default:
                Glide.clear(image);
                Glide.with(convertView.getContext()).load(cardItem.getProfileImageUrl()).into(image);
                break;
        }

        return convertView;
    }
}
