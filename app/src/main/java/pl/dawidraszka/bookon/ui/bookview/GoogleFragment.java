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
import pl.dawidraszka.bookon.viewmodels.GoogleViewModel;

public class GoogleFragment extends Fragment {

    private WebView googleWebView;
    private GoogleViewModel googleViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);

        googleWebView = view.findViewById(R.id.webView);
        googleWebView.getSettings().setLoadWithOverviewMode(true);
        googleWebView.setWebViewClient(new GoogleFragment.Browser());

        googleViewModel = ViewModelProviders.of(this).get(GoogleViewModel.class);
        googleViewModel.init(getContext());

        loadUrl(googleViewModel.getCurrentParameter().getValue());
        googleViewModel.getCurrentParameter().observe(this, this::loadUrl);

        return view;
    }

    private void loadUrl(Parameter parameter) {
        String url = "https://www.google.com/search?q=" + googleViewModel.getBookSearch().getValue(parameter);

        googleWebView.loadUrl(url);
    }

    private class Browser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
