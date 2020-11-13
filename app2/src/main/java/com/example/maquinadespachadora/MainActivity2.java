package com.example.maquinadespachadora;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainActivity2 extends AppCompatActivity {
    EditText id_generado;
    Button generar;
    Button volver;
    ImageView codgenerado;
    private DatabaseReference Medicinadb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Medicinadb = FirebaseDatabase.getInstance().getReference();
        generar=findViewById(R.id.buttongenerar);
        volver=findViewById(R.id.buttonregresar);
        codgenerado=findViewById(R.id.imageView);
        id_generado=findViewById(R.id.editTextcod);

        /*generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigo=id_generado.getText().toString();
                if (!codigo.isEmpty()){
                    Toast.makeText(this,"Se necesita ingresar un codigo",Toast.LENGTH_SHORT).show();

                }
                QRGEncoder qrgEncoder= new QRGEncoder(codigo,null, QRGContents.Type.TEXT,100);
                try{
                    Bitmap qrBits= qrgEncoder.getBitmap();
                    codgenerado.setImageBitmap(qrBits);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });*/


    }
    public void volver(View view){
        finish();
    }
    public void generar(View view){
        String codigo=id_generado.getText().toString();
        if (codigo.isEmpty()){
            Toast.makeText(this,"Se necesita ingresar un codigo",Toast.LENGTH_SHORT).show();
        }else{
            QRGEncoder qrgEncoder= new QRGEncoder(codigo,null, QRGContents.Type.TEXT,500);
            try{
                Bitmap qrBits= qrgEncoder.getBitmap();
                codgenerado.setImageBitmap(qrBits);
            } catch(Exception e){
                e.printStackTrace();
            }
        }



    }


}