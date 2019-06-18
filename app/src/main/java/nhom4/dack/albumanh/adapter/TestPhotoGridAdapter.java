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

public class TestPhotoGridAdapter extends RecyclerView.Adapter<TestPhotoGridAdapter.TestPhotoViewHolder> {
    List<String> listUri;
    private OnItemClickListener listener;

    public TestPhotoGridAdapter(List<String> listUri) {
        this.listUri = listUri;
    }

    @Override
    public int getItemCount() {
        return this.listUri.size();
    }

    @NonNull
    @Override
    public TestPhotoGridAdapter.TestPhotoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.test_photo_view,
                viewGroup,
                false);

        final TestPhotoViewHolder holder = new TestPhotoViewHolder(view);
        view.setTag(holder);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(holder.imageView);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestPhotoGridAdapter.TestPhotoViewHolder viewHolder, int i) {
        String uri = listUri.get(i);
        Picasso.get().load(new File(uri))
                .placeholder(android.R.color.darker_gray)
                .into(viewHolder.imageView);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class TestPhotoViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public TestPhotoViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image);
        }
    }

    public interface OnItemClickListener {
        public void onClick(ImageView imageView);
    }
}
