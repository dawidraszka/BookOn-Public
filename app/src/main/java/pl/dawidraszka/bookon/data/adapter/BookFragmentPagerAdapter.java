package pl.dawidraszka.bookon.data.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class BookFragmentPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> bookFragments;
    private final List<String> fragmentTitles;

    public BookFragmentPagerAdapter(FragmentManager supportFragmentManager, List<Fragment> bookFragments, List<String> fragmentTitles) {
        super(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        this.fragmentTitles = fragmentTitles;
        this.bookFragments = bookFragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return bookFragments.get(position);
    }

    @Override
    public int getCount() {
        return bookFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }
}