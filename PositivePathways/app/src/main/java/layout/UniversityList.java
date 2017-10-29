package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.positivepathways.R;

/**
 *
 * This fragment will display all the universities, and when a university is selected, it will start
 * the fragment FetchProfile.
 */
public class UniversityList extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_university_list, parent, false);
    }

}
