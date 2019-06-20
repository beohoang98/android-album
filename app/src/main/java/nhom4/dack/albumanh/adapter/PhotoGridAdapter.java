package nhom4.dack.albumanh.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import nhom4.dack.albumanh.R;

public class PhotoGridAdapter extends RecyclerView.Adapter<PhotoGridAdapter.PhotoViewHolder> {
    private List<String> listUri;
    private OnItemClickListener listener;

    public PhotoGridAdapter(List<String> listUri) {
        this.listUri = listUri;
    }

    @Override
    public int getItemCount() {
        return this.listUri.size();
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,  int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.test_photo_view,
                viewGroup,
                false);

        final PhotoViewHolder holder = new PhotoViewHolder(view);
        view.setTag(holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder viewHolder, int i) {
        String uri = listUri.get(i);
        viewHolder.uri = uri;
        Picasso.get().load(new File(uri))
                .placeholder(android.R.color.darker_gray)
                .into(viewHolder.imageView);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public String uri;

        public PhotoViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(uri, imageView);
                }
            });
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public interface OnItemClickListener {
        void onClick(String uri, ImageView imageView);
    }
}
