package nhom4.dack.albumanh;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class AlbumAddPhoto extends AppCompatActivity {
    ActionBar actionBar;
    String albumName;
    ArrayList<String> listChoosed = new ArrayList<>();

    public static int CANCEL = 0;
    public static int OK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_add_photo);

        albumName = getIntent().getStringExtra("albumName");
        if (albumName == null || albumName.trim().length() == 0) {
            finish();
        }

        actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.add_album) + " " + albumName);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_delete);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.album_add_photo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_menu_ok:
                Intent intent = new Intent();
                intent.putExtra("listChoosed", listChoosed.toArray());
                setResult(OK, intent);
                finish();
                break;
            case android.R.id.home:
                setResult(CANCEL);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
