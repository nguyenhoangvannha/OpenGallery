package com.example.nhvn.opengallery.fragments;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.nhvn.opengallery.R;
import com.example.nhvn.opengallery.adapters.PhotosAdapter;
import com.example.nhvn.opengallery.data.Album;
import com.example.nhvn.opengallery.util.MediaSpaceDecoration;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String BUNDLE_ALBUM = "ALBUM";

    // TODO: Rename and change types of parameters

    Context context;
    Album album;

    RecyclerView recyclerView;
    FrameLayout frameLayout;
    PhotosAdapter photosAdapter;
    public AlbumFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static AlbumFragment newInstance(Context context, Album album) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_ALBUM, album);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        if (getArguments() != null) {
            album = (Album) getArguments().getSerializable(BUNDLE_ALBUM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        frameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_album, container, false);
        recyclerView = frameLayout.findViewById(R.id.photos);
        photosAdapter = new PhotosAdapter(context, album);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new MediaSpaceDecoration(8));
        recyclerView.setAdapter(photosAdapter);
        return frameLayout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }
}
