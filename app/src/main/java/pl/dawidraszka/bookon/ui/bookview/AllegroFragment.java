package pl.dawidraszka.bookon.ui.bookview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.dawidraszka.bookon.R;
import pl.dawidraszka.bookon.data.adapter.AllegroBooksRecyclerAdapter;
import pl.dawidraszka.bookon.data.model.allegro.AllegroBook;
import pl.dawidraszka.bookon.viewmodels.AllegroViewModel;

public class AllegroFragment extends Fragment implements AllegroBooksRecyclerAdapter.OnAllegroClickListener, AdapterView.OnItemSelectedListener {

    private Spinner sortSpinner;
    private RecyclerView allegroBooksRecyclerView;
    private AllegroBooksRecyclerAdapter adapter;
    private AllegroViewModel allegroViewModel;

    private List<AllegroBook> currentBooks = new ArrayList<>();

    private List<String> sortKeys;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_allegro_books, container, false);

        sortSpinner = v.findViewById(R.id.sortSpinner);
        sortSpinner.setOnItemSelectedListener(this);
        sortKeys = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.sort_types_keys)));

        allegroBooksRecyclerView = v.findViewById(R.id.allegroRecyclerView);
        allegroViewModel = ViewModelProviders.of(this).get(AllegroViewModel.class);

        allegroViewModel.init(getContext());

        allegroViewModel.getCurrentParameter().observe(this, parameter -> allegroViewModel.changeBooks(sortKeys.get(sortSpinner.getSelectedItemPosition())));
        allegroViewModel.getCurrentBooks().observe(this, this::updateAdapter);

        initRecyclerView();
        return v;
    }

    private void updateAdapter(List<AllegroBook> allegroBooks) {
        currentBooks.clear();
        currentBooks.addAll(allegroBooks);
        adapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        adapter = new AllegroBooksRecyclerAdapter(getContext(), this, currentBooks);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        allegroBooksRecyclerView.setLayoutManager(layoutManager);
        allegroBooksRecyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.separator));

        allegroBooksRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onAllegroClick(long id) {
        String url = "https://allegro.pl/oferta/" + id;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        allegroViewModel.changeBooks(sortKeys.get(i));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
