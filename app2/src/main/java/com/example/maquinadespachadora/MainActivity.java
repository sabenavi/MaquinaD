package com.example.maquinadespachadora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner spinner_medicinas;
    EditText txtPrecio;
    EditText txtStock;
    EditText txtCodigo;
    Button buttonBuscar;
    private DatabaseReference Medicinadb;
    String m_seleccionada="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Medicinadb = FirebaseDatabase.getInstance().getReference();
        txtPrecio = (EditText) findViewById(R.id.editTextPrecio);
        txtStock = (EditText) findViewById(R.id.editTextStorage);
        txtCodigo = (EditText) findViewById(R.id.editTextCodigo);
        spinner_medicinas = (Spinner) findViewById(R.id.spinnermedicinas);
        buttonBuscar = (Button) findViewById(R.id.buttonBuscar);
        cargarMedicinas();
    }

    public void registrarMedicina(View view) {

        //String med=medicinas.getSelectedItem().toString();
        String precio = txtPrecio.getText().toString();
        String stock = txtStock.getText().toString();
        String codigo = txtCodigo.getText().toString();
        if (!(TextUtils.isEmpty(precio) || TextUtils.isEmpty(stock) || TextUtils.isEmpty(codigo))) {
            String id = Medicinadb.push().getKey();
            Medicina m = new Medicina(precio, stock, "Redoxon", codigo);
            Medicinadb.child("Medicinas").child(m.getNombre()).setValue(m);
            Toast.makeText(this, "Medicina Registrada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void buscarMedicina(View view) {
        System.out.println("BOTON");
        Medicinadb.child("Medicinas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    long num=dataSnapshot.getChildrenCount();
                    for (DataSnapshot child: dataSnapshot.getChildren()){
                        System.out.println(child.child("nombre").getValue());

                    }
                    //String name=dataSnapshot.child("Flumocil").child("nombre").getValue().toString();
                    //System.out.println(name);

                }else{
                    System.out.println("F");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void cargarMedicinas(){
        final List<Medicina> medicinas= new ArrayList<>();
        Medicinadb.child("Medicinas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot d:dataSnapshot.getChildren()){
                        String nombrem= d.child("nombre").getValue().toString();
                        String preciom= d.child("precio").getValue().toString();
                        String stockm= d.child("stock").getValue().toString();
                        String codigom= d.child("codigo").getValue().toString();
                        medicinas.add(new Medicina(preciom,stockm,nombrem,codigom));
                    }
                    ArrayAdapter<Medicina> arrayA=new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_dropdown_item_1line,medicinas);
                    spinner_medicinas.setAdapter(arrayA);
                    spinner_medicinas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            m_seleccionada=adapterView.getItemAtPosition(i).toString();
                            for (Medicina m:medicinas){
                                if (m.getNombre().equals(m_seleccionada)) {
                                    txtCodigo.setText(m.getCodigo());
                                    txtPrecio.setText(m.getPrecio());
                                    txtStock.setText(m.getStock());
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void generar(View view){
        Intent i=new Intent(this,MainActivity2.class);
        startActivity(i);
    }

}

