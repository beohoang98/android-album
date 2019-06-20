package nhom4.dack.albumanh.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import nhom4.dack.albumanh.R;
import nhom4.dack.albumanh.ViewPhoto;
import nhom4.dack.albumanh.adapter.PhotoGridAdapter;

@SuppressLint("ValidFragment")
public class PhotoList extends Fragment
        implements PhotoGridAdapter.OnItemClickListener
{
    Context mContext;
    private OnFragmentInteractionListener mListener;

    RecyclerView photoGrid;
    PhotoGridAdapter adapter;
    final public List<String> listPhotoUri;

    @SuppressLint("ValidFragment")
    public PhotoList(Context context) {
        Log.d("nhom04", "invoked to Photolist() constructor");
        mContext = context;
        listPhotoUri = new ArrayList<>();
        adapter = new PhotoGridAdapter(listPhotoUri);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (listPhotoUri.size() == 0) {
//            (new LoadTestImage()).execute();

            loadImageUriListFromStorage();
        }
    }

    private void loadImageUriListFromStorage() {
        Log.d("nhom04", "invoked to loadImageUriListFromStorage()");
        Uri uri;
        Cursor cursor;
        int column_index;
        String path = null,sortOrder;
//        ArrayList<String> imageList = new ArrayList<>();
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.MediaColumns.DATA };
        //DATA is the path to the corresponding image. We only need this for loading //image into a recyclerview

        sortOrder = MediaStore.Images.ImageColumns.DATE_ADDED + " DESC";
        //This sorts all images such that recent ones appear first

        cursor = mContext.getContentResolver().query(uri, projection, null,null, sortOrder);

        try {
            if (null != cursor) {
                column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                while (cursor.moveToNext()) {
                    path = cursor.getString(column_index);
                    Log.d("Nhom04", "got image path - "+path);
                    listPhotoUri.add(path);
                }

                adapter.notifyDataSetChanged();
                cursor.close();
                //imageList gets populated with paths to images by here
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        photoGrid = view.findViewById(R.id.photoGrid);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        layoutManager.setInitialPrefetchItemCount(10);

        Log.d("nhom04", "invoked to onViewCreated");

        photoGrid.setLayoutManager(layoutManager);
        photoGrid.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        //Todo: i think these bunk of code is useless now
//        photoGrid.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                if (listPhotoUri.size() < 50 && !recyclerView.canScrollVertically(1)) {
////                    (new LoadTestImage()).execute();
//                    Toast.makeText(getContext(), "Load more", Toast.LENGTH_SHORT)
//                            .show();
//                }
//            }
//        });
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
    public void onClick(String uri, ImageView imageView) {
        // https://stackoverflow.com/questions/8407336/how-to-pass-drawable-between-activities

        // Todo: why dont we just pass the image's uri in to intent ????
        Intent intent = new Intent(getContext(), ViewPhoto.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("photo_uri", uri);
        Log.d("nhom04", "Bat dau send " + uri + " cho ViewPhoto");

        startActivity(intent);
    }

//    public class LoadTestImage extends AsyncTask<Void, String, Integer>{
//        int lastSize = 0;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            lastSize = listPhotoUri.size();
//            listPhotoUri.addAll(Arrays.asList(new String[10]));
//            adapter.notifyDataSetChanged();
//        }
//
//        @Override
//        protected void onProgressUpdate(String... values) {
//            super.onProgressUpdate(values);
//            String uri = values[0];
//            int idx = Integer.parseInt(values[1]);
//            listPhotoUri.set(idx + lastSize, uri);
//            adapter.notifyDataSetChanged();
//        }
//
//        int randomW() {
//            return (int)(Math.random() * 200 + 300);
//        }
//        int randomH() {
//            return (int)(Math.random() * 200 + 200);
//        }
//
//        @Override
//        protected Integer doInBackground(Void... voids) {
//            try {
//                for (int i = 0; i < 10; ++i) {
//                    int w = this.randomW();
//                    int h = this.randomH();
//                    String uri = ("https://picsum.photos/" + w + "/" + h + "/");
//                    publishProgress(uri, i + "");
//                }
//            } catch (NullPointerException nullE) {
//                Log.e("LoadTestImage", "NullPointer " + nullE.getMessage());
//            } catch (Exception e) {
//                Log.e("LoadTestImage", e.getMessage());
//            }
//
//            return 0;
//        }
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        protected void onPostExecute(Integer avoid) {
//            super.onPostExecute(avoid);
//            Toast.makeText(getContext(), "Loaded " + listPhotoUri.size() + " photos", Toast.LENGTH_SHORT).show();
//        }
//    }
}
