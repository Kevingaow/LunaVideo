package com.lunavideo.lunavideo.ui.videogrid;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lunavideo.lunavideo.R;
import com.lunavideo.lunavideo.data.entity.LunaVideoThumbEntity;
import com.lunavideo.lunavideo.utils.Constant;
import com.lunavideo.lunavideo.widget.photoview.PhotoView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

/**
 * Created by gaowei on 21/06/2017.
 */

public class VideoGridAdapter extends ArrayAdapter<LunaVideoThumbEntity> {

    LayoutInflater mLayoutInflater;



    public VideoGridAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ViewHolder vh;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.view_video_thumb_entity, parent, false);
            view.setLayoutParams(new RelativeLayout.LayoutParams(
                    Constant.ThumbnailMaxSize, Constant.ThumbnailMaxSize));
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        LunaVideoThumbEntity entity = getItem(position);

        vh.photoView.setImageBitmap(entity.getThumbnail());
        vh.photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        vh.duration.setText(String.valueOf(entity.getDuration()));
        vh.title.setText(entity.getTitle());

        Timber.d("getView, pos:%d", position);
        return view;
    }


    class ViewHolder {
        @BindView(R.id.thumb)
        ImageView photoView;
        @BindView(R.id.duration)
        AppCompatTextView duration;
        @BindView(R.id.title)
        AppCompatTextView title;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
