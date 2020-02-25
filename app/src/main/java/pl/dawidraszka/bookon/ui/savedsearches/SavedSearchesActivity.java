package pl.dawidraszka.bookon.ui.savedsearches;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.dawidraszka.bookon.R;
import pl.dawidraszka.bookon.data.adapter.SavedSearchesRecyclerAdapter;
import pl.dawidraszka.bookon.data.model.booksearch.BookSearch;
import pl.dawidraszka.bookon.ui.booksearch.BookSearchActivity;
import pl.dawidraszka.bookon.viewmodels.SavedSearchesViewModel;

public class SavedSearchesActivity extends AppCompatActivity implements SavedSearchesRecyclerAdapter.OnSavedSearchClickListener, EditSavedSearchDialogListener {

    private RecyclerView savedSearchesRecyclerView;
    private SavedSearchesRecyclerAdapter adapter;
    private SavedSearchesViewModel savedSearchesViewModel;

    private List<BookSearch> savedSearches = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_searches);

        savedSearchesRecyclerView = findViewById(R.id.savedSearchesRecyclerView);
        savedSearchesViewModel = ViewModelProviders.of(this).get(SavedSearchesViewModel.class);
        savedSearchesViewModel.init(this);

        savedSearchesViewModel.getSavedSearches().observe(this, this::updateAdapter);

        initRecyclerView();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.saved_searches_options);
        toolbar.getOverflowIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.find_book) {
                Intent intent = new Intent(this, BookSearchActivity.class);
                startActivity(intent);
            }

            return true;
        });
    }

    private void updateAdapter(List<BookSearch> savedSearches) {
        this.savedSearches.clear();
        this.savedSearches.addAll(savedSearches);
        adapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        adapter = new SavedSearchesRecyclerAdapter(this, this, savedSearches);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        savedSearchesRecyclerView.setLayoutManager(layoutManager);
        savedSearchesRecyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.separator));

        savedSearchesRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onDelete(BookSearch bookSearch) {
        savedSearchesViewModel.delete(bookSearch);
    }

    @Override
    public void onEdit(BookSearch bookSearch) {
        DialogFragment dialog = new EditSavedSearchDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", bookSearch.getId());
        bundle.putString("title", bookSearch.getTitle());
        bundle.putString("isbn", bookSearch.getIsbn());
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "EditSavedSearchDialogFragment");
    }

    @Override
    public void onClick(BookSearch bookSearch) {
        savedSearchesViewModel.setBookSearch(bookSearch);
        startActivity(new Intent(this, BookSearchActivity.class));
    }

    @Override
    public void onSuccessfulEdit(BookSearch bookSearch) {
        savedSearchesViewModel.update(bookSearch);
    }
}
