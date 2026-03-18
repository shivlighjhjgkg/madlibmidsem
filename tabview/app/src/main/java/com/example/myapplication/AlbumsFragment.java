package com.example.myapplication;

import android.os.Bundle;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

public class AlbumsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_albums, container, false);

        GridView gridView = view.findViewById(R.id.gridViewAlbums);

        String[] albums = {
                "Album 1",
                "Album 2",
                "Album 3",
                "Album 4",
                "Album 5",
                "Album 6"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(requireContext(),
                        android.R.layout.simple_list_item_1,
                        albums);

        gridView.setAdapter(adapter);

        return view;
    }
}
