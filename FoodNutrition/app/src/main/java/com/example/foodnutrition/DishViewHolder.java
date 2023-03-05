package com.example.foodnutrition;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;

public class DishViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView titleTextView;
    public ImageView imageView;
    public String imagePath;
    public ImageButton toggleImageButton;
    public TextView summaryTextView;

    public DishViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image_view);
        titleTextView = itemView.findViewById(R.id.title_text_view);
        summaryTextView = itemView.findViewById(R.id.summary_text_view);
        toggleImageButton = itemView.findViewById(R.id.toggle_image_button);
        toggleImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (summaryTextView.getVisibility() == View.GONE) {
            toggleImageButton.setRotation(180);
            summaryTextView.setVisibility(View.VISIBLE);
        } else {
            toggleImageButton.setRotation(0);
            summaryTextView.setVisibility(View.GONE);
        }
    }

    public void loadImage() {
        if(imagePath == null) return;
        new DownloadImageTask((ImageView) imageView)
                .execute(imagePath);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if(result == null) return;
            bmImage.setImageBitmap(result);
        }
    }

}
