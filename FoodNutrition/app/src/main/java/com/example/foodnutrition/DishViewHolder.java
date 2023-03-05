package com.example.foodnutrition;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;

public class DishViewHolder extends RecyclerView.ViewHolder {

    public TextView titleTextView;
    public ImageView imageView;
    public String imagePath;

    public DishViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.title_text_view);
        imageView = itemView.findViewById(R.id.image_view);
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
            bmImage.setImageBitmap(result);
        }
    }

}
