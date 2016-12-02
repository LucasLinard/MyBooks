package tech.linard.android.mybooks;

import android.app.Fragment;
import android.os.Bundle;

import java.util.List;

public class RetainedFragment extends Fragment {

    // data object we want to retain
    private List<Volume> volumeList;

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public void setData(List<Volume> data) {
        this.volumeList = data;
    }

    public List<Volume> getData() {
        return volumeList;
    }
}