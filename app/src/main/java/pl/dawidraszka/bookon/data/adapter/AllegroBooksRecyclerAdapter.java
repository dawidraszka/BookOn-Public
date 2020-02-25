package pl.dawidraszka.bookon.data.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

import pl.dawidraszka.bookon.R;
import pl.dawidraszka.bookon.data.model.allegro.AllegroBook;

public class AllegroBooksRecyclerAdapter extends RecyclerView.Adapter<AllegroBooksRecyclerAdapter.ViewHolder> {

    private final OnAllegroClickListener onAllegroClickListener;
    private final Context context;
    private List<AllegroBook> books;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AllegroBooksRecyclerAdapter(Context context, OnAllegroClickListener onAllegroClickListener, List<AllegroBook> books) {
        this.context = context;
        this.onAllegroClickListener = onAllegroClickListener;
        this.books = books;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allegro_book_row, parent, false);
            /*TextView v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.book_row, parent, false);*/

        return new ViewHolder(view, onAllegroClickListener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        AllegroBook book = books.get(position);

        holder.auctionTitleTextView.setText(book.getTitle());
        holder.priceTextView.setText(String.format(Locale.getDefault(), "%.2f %s", book.getPrice(), "zł"));
        holder.priceWithDeliveryTextView.setText(String.format(Locale.getDefault(), "%.2f %s", book.getDeliveryPrice() + book.getPrice(), "zł"));

        Drawable noCoverImage = context.getDrawable(R.drawable.ic_no_cover_book);
        Glide
                .with(context)
                .load(book.getImageUrl())
                .error(noCoverImage).into(holder.auctionImageView);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return books.size();
    }

    public interface OnAllegroClickListener {
        void onAllegroClick(long id);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case

        private ImageView auctionImageView;
        private TextView auctionTitleTextView;
        private TextView priceTextView;
        private TextView priceWithDeliveryTextView;
        private Button allegroButton;

        private OnAllegroClickListener onAllegroClickListener;

        public ViewHolder(View view, OnAllegroClickListener onAllegroClickListener) {
            super(view);

            auctionImageView = view.findViewById(R.id.auctionImageView);
            auctionTitleTextView = view.findViewById(R.id.auctionTitleTextView);
            priceTextView = view.findViewById(R.id.priceTextView);
            priceWithDeliveryTextView = view.findViewById(R.id.priceWithDeliveryTextView);
            allegroButton = view.findViewById(R.id.allegroButton);
            this.onAllegroClickListener = onAllegroClickListener;

            allegroButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onAllegroClickListener.onAllegroClick(books.get(getAdapterPosition()).getId());
        }
    }
}