package nhom4.dack.albumanh;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.flexbox.FlexboxLayout;
import com.squareup.picasso.Picasso;

public class ViewPhoto extends AppCompatActivity {
    PhotoView image;
    TabLayout editControl;
    FlexboxLayout viewControl;
    ConstraintLayout viewPhotoFrame;

    ImageButton editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            Bundle extras = getIntent().getExtras();
            String photoURI = extras.getString("photo_uri");
            if (photoURI == null) Log.e("nhom04", "Hinh not found");
            Log.d("nhom04", photoURI);

            image = findViewById(R.id.image);
            image.setImageURI(Uri.parse(photoURI));
        } catch (NullPointerException nullE) {
            Log.e("VIEW_PHOTO", "Cannot get intent extra: " + nullE.getMessage());
            finish();
        }

        mapView();

        handleButton();
    }

    void mapView() {
        editButton = findViewById(R.id.view_edit);
        editControl = findViewById(R.id.editControl);
        viewControl = findViewById(R.id.viewControl);

        viewPhotoFrame = findViewById(R.id.view_photo_frame);
    }

    void handleButton() {
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editControl.animate().translationY(0);
                ((TransitionDrawable)viewPhotoFrame.getBackground()).startTransition(200);
            }
        });
    }
}
