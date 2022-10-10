package com.sample.sajilo.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.sample.sajilo.R;

import java.util.List;

public class SliderAdapter extends PagerAdapter {
    Context context;
    List<Datum> bannerListImages;
    private LayoutInflater inflater;


    public SliderAdapter(Context context, List<Datum> bannerListImages) {
        this.context = context;
        this.bannerListImages = bannerListImages;
        this.inflater = inflater;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View sliderLayout = inflater.inflate(R.layout.single_row_slider, null);
        ImageView allSliderBannerImages = sliderLayout.findViewById(R.id.allSliderBannerImages);
       Glide.with(context)
                .load("http://adminapp.tech/Sajilo/" + bannerListImages.get(position).getImg())
                .into(allSliderBannerImages);

        container.addView(sliderLayout);
        return sliderLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return bannerListImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
}
