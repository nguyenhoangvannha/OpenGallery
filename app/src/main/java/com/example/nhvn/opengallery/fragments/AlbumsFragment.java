package com.example.nhvn.opengallery.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.nhvn.opengallery.R;
import com.example.nhvn.opengallery.adapters.AlbumsAdapter;
import com.example.nhvn.opengallery.data.Album;
import com.example.nhvn.opengallery.data.provider.CPHelper;
import com.example.nhvn.opengallery.util.MediaSpaceDecoration;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AlbumsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumsFragment extends Fragment  {
    public static final String TAG = "AlbumsFragment";
    private RecyclerView recyclerView;
    private AlbumsAdapter albumsAdapter;
    LinearLayout frameLayout;
    private Context context;
    private ArrayList<Album> albums;
    public AlbumsFragment() {

        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AlbumsFragment.
     */
    public static AlbumsFragment newInstance(ArrayList<Album> albums) {
        AlbumsFragment fragment = new AlbumsFragment();
        fragment.albums = albums;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        frameLayout = (LinearLayout) inflater.inflate(R.layout.fragment_albums, container, false);
        recyclerView = frameLayout.findViewById(R.id.albums);
        albumsAdapter = new AlbumsAdapter(context, albums);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MediaSpaceDecoration(8));
        recyclerView.setAdapter(albumsAdapter);
        return frameLayout;
    }

}
