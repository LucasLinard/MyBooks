package tech.linard.android.mybooks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lucas on 29/11/16.
 */

public class VolumeAdapter extends ArrayAdapter<Volume> {

    public VolumeAdapter(Context context, List<Volume> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.volume_list_item, parent, false);
        }

        Volume currentVolume = getItem(position);
        TextView title  = (TextView) listItemView.findViewById(R.id.title_information);
        title.setText(currentVolume.getmTitle());

        TextView author = (TextView) listItemView.findViewById(R.id.author_information);
        author.setText(currentVolume.getmAuthors());
        return listItemView;
    }
}
