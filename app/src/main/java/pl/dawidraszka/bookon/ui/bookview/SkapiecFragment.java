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
import pl.dawidraszka.bookon.viewmodels.SkapiecViewModel;

public class SkapiecFragment extends Fragment {

    private WebView skapiecWebView;
    private SkapiecViewModel skapiecViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);

        skapiecWebView = view.findViewById(R.id.webView);
        skapiecWebView.getSettings().setLoadWithOverviewMode(true);
        skapiecWebView.getSettings().setJavaScriptEnabled(true);
        skapiecWebView.setWebViewClient(new Browser());

        skapiecViewModel = ViewModelProviders.of(this).get(SkapiecViewModel.class);
        skapiecViewModel.init(getContext());

        loadUrl(skapiecViewModel.getCurrentParameter().getValue());
        skapiecViewModel.getCurrentParameter().observe(this, this::loadUrl);

        return view;
    }

    private void loadUrl(Parameter parameter) {
        String url = "https://www.skapiec.pl/site/szukaj/?szukaj=" + skapiecViewModel.getBookSearch().getValue(parameter);
        ;
        skapiecWebView.loadUrl(url);
    }

    private class Browser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
