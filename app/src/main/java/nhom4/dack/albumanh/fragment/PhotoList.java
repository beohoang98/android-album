package nhom4.dack.albumanh.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import nhom4.dack.albumanh.R;
import nhom4.dack.albumanh.ViewPhoto;
import nhom4.dack.albumanh.adapter.TestPhotoGridAdapter;
import nhom4.dack.albumanh.utils.Fetch;

public class PhotoList extends Fragment
        implements TestPhotoGridAdapter.OnItemClickListener
{

    private OnFragmentInteractionListener mListener;

    RecyclerView photoGrid;
    TestPhotoGridAdapter adapter;
    final public List<String> listPhotoUri;

    public PhotoList() {
        listPhotoUri = new ArrayList<>();
        adapter = new TestPhotoGridAdapter(listPhotoUri);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (listPhotoUri.size() == 0) {
            (new LoadTestImage()).execute();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        photoGrid = view.findViewById(R.id.photoGrid);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        layoutManager.setInitialPrefetchItemCount(10);

        photoGrid.setLayoutManager(layoutManager);
        photoGrid.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        photoGrid.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (listPhotoUri.size() < 50 && !recyclerView.canScrollVertically(1)) {
                    (new LoadTestImage()).execute();
                    Toast.makeText(getContext(), "Load more", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_list, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(ImageView imageView) {
        // https://stackoverflow.com/questions/8407336/how-to-pass-drawable-between-activities

        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();

        Intent intent = new Intent(getContext(), ViewPhoto.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("photo", b);
        startActivity(intent);
    }

    public class LoadTestImage extends AsyncTask<Void, String, Integer>{
        int lastSize = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            lastSize = listPhotoUri.size();
            listPhotoUri.addAll(Arrays.asList(new String[10]));
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String uri = values[0];
            int idx = Integer.parseInt(values[1]);
            listPhotoUri.set(idx + lastSize, uri);
            adapter.notifyDataSetChanged();
        }

        int randomW() {
            return (int)(Math.random() * 200 + 300);
        }
        int randomH() {
            return (int)(Math.random() * 200 + 200);
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                for (int i = 0; i < 10; ++i) {
                    int w = this.randomW();
                    int h = this.randomH();
                    String uri = ("https://picsum.photos/" + w + "/" + h + "/");
                    publishProgress(uri, i + "");
                }
            } catch (NullPointerException nullE) {
                Log.e("LoadTestImage", "NullPointer " + nullE.getMessage());
            } catch (Exception e) {
                Log.e("LoadTestImage", e.getMessage());
            }

            return 0;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Integer avoid) {
            super.onPostExecute(avoid);
            Toast.makeText(getContext(), "Loaded " + listPhotoUri.size() + " photos", Toast.LENGTH_SHORT).show();
        }
    }
}
