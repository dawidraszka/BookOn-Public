package pl.dawidraszka.bookon.ui.bookview;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import pl.dawidraszka.bookon.R;
import pl.dawidraszka.bookon.data.adapter.BookFragmentPagerAdapter;
import pl.dawidraszka.bookon.data.model.booksearch.Parameter;
import pl.dawidraszka.bookon.viewmodels.BookViewModel;

public class BookActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Switch isbnTitleSwitch;

    private FragmentPagerAdapter adapter;

    private BookViewModel bookViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        isbnTitleSwitch = findViewById(R.id.isbnTitleSwitch);

        bookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        bookViewModel.init(this);

        if (bookViewModel.getBookSearch().getParameters().size() > 1) {
            isbnTitleSwitch.setVisibility(View.VISIBLE);

            isbnTitleSwitch.setOnClickListener(v -> {
                if (((Switch) v).isChecked()) {
                    bookViewModel.setCurrentParameter(Parameter.TITLE);
                } else {
                    bookViewModel.setCurrentParameter(Parameter.ISBN);
                }
            });
        }

        initFragmentPagerAdapter();
    }

    private void initFragmentPagerAdapter() {
        List<String> titles = new ArrayList<>();
        titles.add("GoodReads");
        titles.add("Skąpiec");
        titles.add("Google");
        titles.add("Allegro");

        List<Fragment> bookFragments = new ArrayList<>();
        bookFragments.add(new GoodreadsFragment());
        bookFragments.add(new SkapiecFragment());
        bookFragments.add(new GoogleFragment());
        bookFragments.add(new AllegroFragment());

        adapter = new BookFragmentPagerAdapter(getSupportFragmentManager(), bookFragments, titles);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}

