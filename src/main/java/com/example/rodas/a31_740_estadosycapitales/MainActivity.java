package com.example.rodas.a31_740_estadosycapitales;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mAceptarNPreguntas;
    private View mConfigDialogView;
    private EditText mTotPreg;

    private int cantPreguntas = 10;
    private int randImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.op_config:
                    final AlertDialog alertDialog;
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle(R.string.config_dialog);
                    mConfigDialogView = getLayoutInflater().inflate(R.layout.config_dialog,null);
                    mTotPreg = (EditText) mConfigDialogView.findViewById(R.id.NPreguntas);
                    mAceptarNPreguntas = (Button) mConfigDialogView.findViewById(R.id.NPregAceptar);
                    dialog.setView(mConfigDialogView);
                    alertDialog = dialog.create();
                    alertDialog.show();
                    mAceptarNPreguntas.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!mTotPreg.getText().toString().isEmpty()){
                                cantPreguntas = Integer.parseInt(mTotPreg.getText().toString());
                                alertDialog.dismiss();
                            }else{
                                alertDialog.dismiss();
                            }
                        }
                    });
                break;
            case R.id.op_acerca_de:
                    Intent intentoAcercaDe = new Intent(this,AcercaDe.class);
                    startActivity(intentoAcercaDe);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void iniciar (View v){
        Intent intentoPreguntas = new Intent(this,Preguntas.class);
        randImg = (int) (Math.random()*31);
        intentoPreguntas.putExtra("numeroPreguntas",cantPreguntas);
        intentoPreguntas.putExtra("imagenInicial",randImg);
        startActivity(intentoPreguntas);
    }
}
