package pl.dawidraszka.bookon.ui.booksearch;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Rational;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageAnalysisConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

import pl.dawidraszka.bookon.R;
import pl.dawidraszka.bookon.data.model.booksearch.BookSearch;
import pl.dawidraszka.bookon.tools.BarcodeAnalyzer;
import pl.dawidraszka.bookon.tools.DataVerifier;
import pl.dawidraszka.bookon.ui.bookview.BookActivity;
import pl.dawidraszka.bookon.ui.savedsearches.SavedSearchesActivity;
import pl.dawidraszka.bookon.viewmodels.BookSearchViewModel;

public class BookSearchActivity extends AppCompatActivity implements LifecycleOwner, BarcodeAnalyzer.BarcodeListener {

    private static final int PERMISSION_REQUEST_CAMERA = 1;
    private EditText titleEditText;
    private EditText isbnEditText;
    private Button saveSearchButton;
    private Button searchButton;
    private Button grantPermissionsButton;
    private TextureView cameraPreview;
    private BookSearchViewModel bookSearchViewModel;

    private HandlerThread handlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_book);

        bookSearchViewModel = ViewModelProviders.of(this).get(BookSearchViewModel.class);
        bookSearchViewModel.init(this);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateUI();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        titleEditText = findViewById(R.id.titleEditText);
        titleEditText.addTextChangedListener(textWatcher);
        isbnEditText = findViewById(R.id.isbnEditText);
        isbnEditText.addTextChangedListener(textWatcher);
        saveSearchButton = findViewById(R.id.saveButton);
        searchButton = findViewById(R.id.searchButton);
        grantPermissionsButton = findViewById(R.id.grantPermissionsButton);
        cameraPreview = findViewById(R.id.viewFinder);

        BookSearch currentBookSearch = bookSearchViewModel.getBookSearch();
        if (currentBookSearch != null) {
            titleEditText.setText(currentBookSearch.getTitle());
            isbnEditText.setText(currentBookSearch.getIsbn());
        }

        grantPermissionsButton.setOnClickListener(v -> ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            cameraPreview.post(this::startCamera);
            grantPermissionsButton.setVisibility(View.GONE);
        }

        searchButton.setOnClickListener(v -> {
            String title = null;
            String isbn = null;
            if (DataVerifier.isTitleCorrect(titleEditText.getText().toString())) {
                title = titleEditText.getText().toString();
            }
            if (DataVerifier.isIsbnCorrect(isbnEditText.getText().toString())) {
                isbn = isbnEditText.getText().toString();
            }

            bookSearchViewModel.setBookSearch(new BookSearch(title, isbn));

            Intent intent = new Intent(this, BookActivity.class);
            startActivity(intent);
        });

        saveSearchButton.setOnClickListener(v -> {
            String title = null;
            String isbn = null;
            if (DataVerifier.isTitleCorrect(titleEditText.getText().toString())) {
                title = titleEditText.getText().toString();
            }
            if (DataVerifier.isIsbnCorrect(isbnEditText.getText().toString())) {
                isbn = isbnEditText.getText().toString();
            }
            bookSearchViewModel.saveBookSearch(new BookSearch(title, isbn));
            Toast.makeText(this, "Zapisano wyszukiwanie", Toast.LENGTH_SHORT).show();
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.book_search_options);
        toolbar.getOverflowIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.saved_searches) {
                Intent intent = new Intent(this, SavedSearchesActivity.class);
                startActivity(intent);
            }

            return true;
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraPreview.post(this::startCamera);
                grantPermissionsButton.setVisibility(View.GONE);
            }
        }
    }

    private void startCamera() {
        PreviewConfig previewConfig = new PreviewConfig.Builder()
                .setTargetAspectRatio(new Rational(1, 1))
                .setTargetResolution(new Size(1000, 1000))
                .build();

        Preview preview = new Preview(previewConfig);

        preview.setOnPreviewOutputUpdateListener(output -> {
            ViewGroup parent = (ViewGroup) cameraPreview.getParent();
            parent.removeView(cameraPreview);
            parent.addView(cameraPreview);

            cameraPreview.setSurfaceTexture(output.getSurfaceTexture());
            updateTransform();
        });

        handlerThread = new HandlerThread("Analyzer");
        handlerThread.start();

        ImageAnalysisConfig imageAnalysisConfig = new ImageAnalysisConfig.Builder()
                .setCallbackHandler(new Handler(handlerThread.getLooper()))
                .setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
                .build();

        ImageAnalysis analyzerUseCase = new ImageAnalysis(imageAnalysisConfig);
        analyzerUseCase.setAnalyzer(new BarcodeAnalyzer(this));

        CameraX.bindToLifecycle(this, preview, analyzerUseCase);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quit();
    }

    private void updateTransform() {
        Matrix matrix = new Matrix();

        int centerX = cameraPreview.getWidth() / 2;
        int centerY = cameraPreview.getHeight() / 2;

        float rotationDegrees = 0;

        switch (getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_0:
                rotationDegrees = 0;
                break;
            case Surface.ROTATION_90:
                rotationDegrees = 90;
                break;
            case Surface.ROTATION_180:
                rotationDegrees = 180;
                break;
            case Surface.ROTATION_270:
                rotationDegrees = 270;
                break;
        }

        matrix.postRotate(-rotationDegrees, centerX, centerY);
        cameraPreview.setTransform(matrix);
    }

    private void updateUI() {
        searchButton.setEnabled(DataVerifier.isTitleCorrect(titleEditText.getText().toString()) || DataVerifier.isIsbnCorrect(isbnEditText.getText().toString()));
        saveSearchButton.setEnabled(DataVerifier.isTitleCorrect(titleEditText.getText().toString()) || DataVerifier.isIsbnCorrect(isbnEditText.getText().toString()));

        int greenColor = getResources().getColor(R.color.colorGreen);
        int redColor = getResources().getColor(R.color.colorRed);
        if (DataVerifier.isTitleCorrect(titleEditText.getText().toString())) {
            titleEditText.setTextColor(greenColor);
        } else {
            titleEditText.setTextColor(redColor);
        }

        if (DataVerifier.isIsbnCorrect(isbnEditText.getText().toString())) {
            isbnEditText.setTextColor(greenColor);
        } else {
            isbnEditText.setTextColor(redColor);
        }
    }

    @Override
    public void barcodeReceived(String barcode) {
        isbnEditText.setText(barcode);
    }
}
