package com.example.ebibliotek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class act_Menu extends AppCompatActivity {

    Button btnAgregarLib;
    Button btnListaLib;
    Button btnLibrosAlq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_menu);

        btnAgregarLib = findViewById(R.id.btnAgregarLib);
        btnListaLib = findViewById(R.id.btnListaLib);
        btnLibrosAlq = findViewById(R.id.btnLibrosAlq);

        btnAgregarLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(act_Menu.this, act_AgregarLibro.class);
                startActivity(intent);
            }
        });

        btnListaLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnLibrosAlq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }//Fin del OnCreate
}//Fin de la clase
