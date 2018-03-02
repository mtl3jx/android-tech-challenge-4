package captechventures.com.techchallenge4.cookbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import captechventures.com.techchallenge4.R;

/**
 * Created by mluansing on 11/8/17.
 */

public class EmptyFragment extends Fragment {

    // tag for logging purposes
    private static final String TAG = EmptyFragment.class.getSimpleName();

    public EmptyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_empty, container, false);
    }

}
