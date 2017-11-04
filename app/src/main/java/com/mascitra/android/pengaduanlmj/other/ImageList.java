package com.mascitra.android.pengaduanlmj.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mascitra.android.pengaduanlmj.R;
import com.mascitra.android.pengaduanlmj._sliders.FragmentSlider;

/**
 * Created by SONY on 14/09/2017.
 */

public class ImageList extends Fragment {
    private static final String ARG_PARAM1 = "params";
private TextView url;
    private String imageUrls;

    public static ImageList newInstance(String params) {
        ImageList  imageList= new ImageList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, params);
        imageList.setArguments(args);
        return imageList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        imageUrls = getArguments().getString(ARG_PARAM1);
        View view = inflater.inflate(R.layout.list_item, container, false);
//        url = (TextView) view.findViewById(R.id.url);
//        ImageView img = (ImageView) view.findViewById(R.id.imageList);
//        imageUrls = String.valueOf(url.getText());
//
//        Glide.with(this).load(imageUrls)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(img);
        return view;
    }
}
