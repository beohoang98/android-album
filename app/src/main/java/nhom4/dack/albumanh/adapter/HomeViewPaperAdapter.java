package nhom4.dack.albumanh.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import nhom4.dack.albumanh.fragment.AlbumList;
import nhom4.dack.albumanh.fragment.PhotoList;
import nhom4.dack.albumanh.R;

public class HomeViewPaperAdapter extends FragmentPagerAdapter {
    private String pageTitle[] = {"", ""};
//    private Fragment
    Context mContext;

    public HomeViewPaperAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.pageTitle[0] = context.getString(R.string.photo);
        this.pageTitle[1] = context.getString(R.string.album);
        mContext = context;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.pageTitle[position];
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                Log.d("chienpm", "invoked to Fragment.getItem()");
                return new PhotoList(mContext);
            case 1:
                return new AlbumList();
            default:
                throw new Error("HomeViewPagerAdapter just only 2 fragment");
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
