package pl.dawidraszka.bookon.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.dawidraszka.bookon.R;
import pl.dawidraszka.bookon.data.model.booksearch.BookSearch;

public class SavedSearchesRecyclerAdapter extends RecyclerView.Adapter<SavedSearchesRecyclerAdapter.ViewHolder> {

    private final Context context;
    private OnSavedSearchClickListener onSavedSearchClickListener;
    private List<BookSearch> savedSearches;

    public SavedSearchesRecyclerAdapter(Context context, OnSavedSearchClickListener onSavedSearchClickListener, List<BookSearch> savedSearches) {
        this.context = context;
        this.onSavedSearchClickListener = onSavedSearchClickListener;
        this.savedSearches = savedSearches;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_search_row, parent, false);
            /*TextView v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.book_row, parent, false);*/

        return new ViewHolder(view, onSavedSearchClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BookSearch bookSearch = savedSearches.get(position);

        holder.titleTextView.setText(bookSearch.getTitle());
        holder.isbnTextView.setText(bookSearch.getIsbn());
    }

    @Override
    public int getItemCount() {
        return savedSearches.size();
    }

    public interface OnSavedSearchClickListener {
        void onDelete(BookSearch bookSearch);

        void onEdit(BookSearch bookSearch);

        void onClick(BookSearch bookSearch);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView isbnTextView;
        private TextView optionsMenuTextView;

        private OnSavedSearchClickListener onSavedSearchClickListener;

        public ViewHolder(View view, OnSavedSearchClickListener onSavedSearchClickListener) {
            super(view);

            titleTextView = view.findViewById(R.id.titleTextView);
            isbnTextView = view.findViewById(R.id.isbnTextView);
            optionsMenuTextView = view.findViewById(R.id.optionsMenuTextView);
            this.onSavedSearchClickListener = onSavedSearchClickListener;

            optionsMenuTextView.setOnClickListener(v -> {
                PopupMenu popup = new PopupMenu(context, v);
                //inflating menu from xml resource
                popup.inflate(R.menu.saved_search_options);
                //adding click listener
                popup.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.delete_search:
                            onSavedSearchClickListener.onDelete(savedSearches.get(getAdapterPosition()));
                            return true;
                        case R.id.modify_search:
                            onSavedSearchClickListener.onEdit(savedSearches.get(getAdapterPosition()));
                            return true;
                        default:
                            return false;
                    }
                });
                //displaying the popup
                popup.show();
            });

            view.setOnClickListener(v -> onSavedSearchClickListener.onClick(savedSearches.get(getAdapterPosition())));
        }
    }
}