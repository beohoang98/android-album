package nhom4.dack.albumanh;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import nhom4.dack.albumanh.adapter.HomeViewPaperAdapter;
import nhom4.dack.albumanh.dialog.CreateAlbumDialog;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentPagerAdapter adapter;
    View view;

    public static final int REQUEST_ADD_ALBUM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);

        mapViewIntoVariablle();

        adapter = new HomeViewPaperAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_photo:
                break;
            case R.id.add_album:
                addAlbum();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    void addAlbum() {
        CreateAlbumDialog addAlbumDialog = new CreateAlbumDialog(this);

        addAlbumDialog.setOnSubmitAlbumName(new CreateAlbumDialog.OnSubmitAlbumName() {
            @Override
            public void onSubmit(@NonNull String albumName) {
                Intent intent = new Intent(getBaseContext(), AlbumAddPhoto.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("albumName", albumName);
                startActivityForResult(intent, REQUEST_ADD_ALBUM);
            }
        });

        addAlbumDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (REQUEST_ADD_ALBUM):
                if (resultCode == AlbumAddPhoto.OK) {
                    if (data == null) {
                        Log.d("TEST_ADD_ALBUM", "NULL");
                        break;
                    }

                    String[] listPhoto = data.getStringArrayExtra("listChoosed");
                    handleAddedAlbum(listPhoto);
                } else {
                    final Snackbar snackbar = Snackbar.make(((ViewGroup) findViewById(android.R.id.content)).getChildAt(0),
                            "You have cancelled", Snackbar.LENGTH_INDEFINITE);

                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });
                    snackbar.show();
                }
                break;
        }
    }

    void handleAddedAlbum(String[] listPhoto) {
        Log.d("TEST_ADD_ALBUM", listPhoto.toString());
    }

    private void mapViewIntoVariablle() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
    }
}
