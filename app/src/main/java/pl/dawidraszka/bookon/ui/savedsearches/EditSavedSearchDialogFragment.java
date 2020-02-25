package pl.dawidraszka.bookon.ui.savedsearches;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import pl.dawidraszka.bookon.R;
import pl.dawidraszka.bookon.data.model.booksearch.BookSearch;
import pl.dawidraszka.bookon.tools.DataVerifier;

public class EditSavedSearchDialogFragment extends DialogFragment {

    private EditSavedSearchDialogListener editSavedSearchDialogListener;

    private EditText titleEditText;
    private EditText isbnEditText;
    private Button cancelButton;
    private Button saveButton;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        editSavedSearchDialogListener = (EditSavedSearchDialogListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_edit_saved_search, null);
        titleEditText = dialogView.findViewById(R.id.titleEditText);
        isbnEditText = dialogView.findViewById(R.id.isbnEditText);
        cancelButton = dialogView.findViewById(R.id.cancelButton);
        saveButton = dialogView.findViewById(R.id.saveButton);

        Bundle bundle = getArguments();
        titleEditText.setText(bundle.getString("title"));
        isbnEditText.setText(bundle.getString("isbn"));

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

        titleEditText.addTextChangedListener(textWatcher);
        isbnEditText.addTextChangedListener(textWatcher);

        cancelButton.setOnClickListener(v -> this.dismiss());
        saveButton.setOnClickListener(v -> {
            String title = null;
            String isbn = null;
            if (DataVerifier.isTitleCorrect(titleEditText.getText().toString())) {
                title = titleEditText.getText().toString();
            }
            if (DataVerifier.isIsbnCorrect(isbnEditText.getText().toString())) {
                isbn = isbnEditText.getText().toString();
            }
            BookSearch updatedBookSearch = new BookSearch(title, isbn);
            updatedBookSearch.setId(bundle.getInt("id"));
            editSavedSearchDialogListener.onSuccessfulEdit(updatedBookSearch);
            this.dismiss();
        });

        builder.setView(dialogView);

        return builder.create();
    }

    private void updateUI() {
        saveButton.setEnabled(DataVerifier.isTitleCorrect(titleEditText.getText().toString()) || DataVerifier.isIsbnCorrect(isbnEditText.getText().toString()));

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
}