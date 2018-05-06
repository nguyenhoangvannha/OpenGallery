package com.example.nhvn.opengallery.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.nhvn.opengallery.R;
import com.example.nhvn.opengallery.adapters.PhotosAdapter;
import com.example.nhvn.opengallery.adapters.VideosAdapter;
import com.example.nhvn.opengallery.data.Album;
import com.example.nhvn.opengallery.util.MediaSpaceDecoration;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link VideosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String BUNDLE_VIDEOS = "VIDEOS";
    public static final String TAG = "VideosFragment";

    // TODO: Rename and change types of parameters
    Context context;
    Album video;

     RecyclerView videosRv;
    FrameLayout frameLayout;
    VideosAdapter videosAdapter;

    public VideosFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static VideosFragment newInstance(Context context, Album videos) {
        VideosFragment fragment = new VideosFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_VIDEOS, videos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            video = (Album) getArguments().getSerializable(BUNDLE_VIDEOS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        frameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_videos, container, false);
        videosRv = frameLayout.findViewById(R.id.videosRv);
        //videosAdapter = new VideosAdapter(context, video);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        videosRv.setLayoutManager(gridLayoutManager);
        videosRv.addItemDecoration(new MediaSpaceDecoration(8));
        videosRv.setAdapter(videosAdapter);
        return frameLayout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
