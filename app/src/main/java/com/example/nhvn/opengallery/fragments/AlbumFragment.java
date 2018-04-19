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

        int state  = recyclerView.getScrollState();
        Log.i("state", state + "");
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i("newState", newState +  " getChildCount"  + recyclerView.getChildCount());
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new MediaSpaceDecoration(8));
        recyclerView.setAdapter(photosAdapter);
        return frameLayout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    public class MediaSpaceDecoration extends RecyclerView.ItemDecoration {
        private final int spacing;

        public MediaSpaceDecoration(int spacing) {
            this.spacing = spacing;
        }

        @Override
        public void getItemOffsets(Rect outRect,
                                   View view,
                                   RecyclerView parent,
                                   RecyclerView.State state) {
            final int position = parent.getChildAdapterPosition(view);

            final int totalSpanCount = getTotalSpanCount(parent);
            int spanSize = getItemSpanSize(parent, position);
            if (totalSpanCount == spanSize) {
                return;
            }

            outRect.top = isInTheFirstRow(position, totalSpanCount) ? 0 : spacing;
            outRect.left = isFirstInRow(position, totalSpanCount) ? 0 : spacing / 2;
            outRect.right = isLastInRow(position, totalSpanCount) ? 0 : spacing / 2;
            outRect.bottom = 0; // don't need
        }

        private boolean isInTheFirstRow(int position, int spanCount) {
            return position < spanCount;
        }

        private boolean isFirstInRow(int position, int spanCount) {
            return position % spanCount == 0;
        }

        private boolean isLastInRow(int position, int spanCount) {
            return isFirstInRow(position + 1, spanCount);
        }

        private int getTotalSpanCount(RecyclerView parent) {
            final RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            return layoutManager instanceof GridLayoutManager
                    ? ((GridLayoutManager) layoutManager).getSpanCount()
                    : 1;
        }

        private int getItemSpanSize(RecyclerView parent, int position) {
            final RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            return layoutManager instanceof GridLayoutManager
                    ? ((GridLayoutManager) layoutManager).getSpanSizeLookup().getSpanSize(position)
                    : 1;
        }

    }
}
