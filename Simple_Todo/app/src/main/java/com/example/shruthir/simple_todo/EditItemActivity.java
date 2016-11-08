package com.example.shruthir.simple_todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

    }

    @Override
    protected void onStart() {
        super.onStart();
        final EditText editTextView = (EditText)findViewById(R.id.txtEditView);
        final String editText = this.getIntent().getStringExtra("clickedItemText");
        editTextView.setText(editText);
        editTextView.setSelection(editText.length());
        final int index = this.getIntent().getIntExtra("clickedItemPos", -1);
        final Button saveButton = (Button)findViewById(R.id.btnSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                data.putExtra("clickedItemText", editText);
                data.putExtra("clickedItemPos", index);
                data.putExtra("editedText", editTextView.getText().toString());
                if (getParent() == null) {
                    setResult(Activity.RESULT_OK, data);
                } else {
                    getParent().setResult(Activity.RESULT_OK, data);
                }
                finish();
            }
        });
    }
}
