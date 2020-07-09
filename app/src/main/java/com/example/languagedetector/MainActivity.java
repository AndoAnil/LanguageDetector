package com.example.languagedetector;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentification;

public class MainActivity extends AppCompatActivity {
    private EditText text;
    private TextView language;
    private Button chackLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=(EditText)findViewById(R.id.editTextTextMultiLine);
        language=(TextView)findViewById(R.id.textView);
        chackLang=(Button)findViewById(R.id.button);

        chackLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textLang=text.getText().toString();
                if(textLang.length()!=0)
                {
                    FirebaseLanguageIdentification languageIdentifier =
                            FirebaseNaturalLanguage.getInstance().getLanguageIdentification();
                    languageIdentifier.identifyLanguage(textLang)
                            .addOnSuccessListener(
                                    new OnSuccessListener<String>() {
                                        @Override
                                        public void onSuccess(@Nullable String languageCode) {
                                            if (languageCode != "und") {
                                                language.setText("Your text language is ::"+languageCode);

                                            } else {
                                                language.setText("Your text language is ::"+ " Can't identify language");

                                            }
                                        }
                                    })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Model couldn’t be loaded or other internal error.
                                            // ...
                                            Toast.makeText(MainActivity.this, "Something went wrong! Try again", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                }
            }
        });
    }
}