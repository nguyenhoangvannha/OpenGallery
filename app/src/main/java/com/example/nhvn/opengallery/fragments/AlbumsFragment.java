package com.example.nhvn.opengallery.fragments;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.nhvn.opengallery.R;
import com.example.nhvn.opengallery.activities.MainActivity;
import com.example.nhvn.opengallery.adapters.AlbumsAdapter;
import com.example.nhvn.opengallery.data.Album;
import com.example.nhvn.opengallery.data.provider.CPHelper;
import com.example.nhvn.opengallery.interfaces.IFragToMain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AlbumsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlbumsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlbumsFragment newInstance(String param1, String param2) {
        AlbumsFragment fragment = new AlbumsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        frameLayout = (LinearLayout) inflater.inflate(R.layout.fragment_albums, container, false);
        recyclerView = frameLayout.findViewById(R.id.albums);
        albums = CPHelper.getAlbums(context);
        albumsAdapter = new AlbumsAdapter(context, albums);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MediaSpaceDecoration(8));
        recyclerView.setAdapter(albumsAdapter);
        return frameLayout;
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
