package com.example.myapplication;

import android.os.Bundle;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class ArtistsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_artists, container, false);

        ListView listView = view.findViewById(R.id.listViewArtists);

        String[] artists = {
                "Arijit Singh",
                "Shreya Ghoshal",
                "Sonu Nigam",
                "Neha Kakkar",
                "KK"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(requireContext(),
                        android.R.layout.simple_list_item_1,
                        artists);

        listView.setAdapter(adapter);

        return view;
    }
}
