package pl.dawidraszka.bookon.ui.bookview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import pl.dawidraszka.bookon.R;
import pl.dawidraszka.bookon.data.model.booksearch.Parameter;
import pl.dawidraszka.bookon.viewmodels.GoodreadsViewModel;

public class GoodreadsFragment extends Fragment {

    private WebView goodreadsWebView;
    private GoodreadsViewModel goodreadsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);

        goodreadsWebView = view.findViewById(R.id.webView);
        goodreadsWebView.getSettings().setLoadWithOverviewMode(true);
        goodreadsWebView.setWebViewClient(new Browser());

        goodreadsViewModel = ViewModelProviders.of(this).get(GoodreadsViewModel.class);
        goodreadsViewModel.init(getContext());

        loadUrl(goodreadsViewModel.getCurrentParameter().getValue());
        goodreadsViewModel.getCurrentParameter().observe(this, this::loadUrl);

        return view;
    }

    private void loadUrl(Parameter parameter) {
        String url = "https://www.goodreads.com/search?q=" + goodreadsViewModel.getBookSearch().getValue(parameter);
        goodreadsWebView.loadUrl(url);
    }

    private class Browser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
