package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(FragmentActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {
            return new ArtistsFragment();
        }
        else if (position == 1) {
            return new AlbumsFragment();
        }
        else {
            return new SongsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
