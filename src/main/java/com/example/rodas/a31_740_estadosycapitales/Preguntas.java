package com.example.rodas.a31_740_estadosycapitales;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Preguntas extends AppCompatActivity {
    String [] estados = {"Aguascalientes","Baja California","Baja California Sur","Campeche","Chihuahua","Chiapas","Coahuila","Colima","Durango","Guanajuato",
                        "Guerrero","Hidalgo","Jalisco","Ciudad de México","Michoacán","Morelos","Nayarit","Nuevo León","Oaxaca","Puebla","Querétaro","Quintana Roo",
                        "San Luis Potosí","Sinaloa","Sonora","Tabasco","Tamaulipas","Tlaxcala","Veracruz","Yucatán","Zacatecas"};

    String [] capitales = {"Aguascalientes","Mexicali","La Paz","San Francisco de Campeche","Chihuahua","Tuxtla Gutiérrez","Saltillo","Colima","Victoria de Durango",
                            "Guanajuato","Chilpancingo de los Bravo","Pachuca de Soto","Guadalajara","Toluca de Lerdo","Morelia","Cuernavaca","Tepic","Monterrey",
                            "Oaxaca de Juárez","Puebla de Zaragoza","Santiago de Querétaro","Chetumal","San Luis Potosí","Culiacán Rosales","Hermosillo","Villahermosa",
                            "Ciudad Victoria","Tlaxcala de Xicohténcatl","Xalapa-Enríquez","Mérida","Zacatecas"};
    int [] imagenesEstados = {R.drawable.mexico_aguas,R.drawable.mexico_bajac,R.drawable.mexico_bajacas,R.drawable.mexico_camp,R.drawable.mexico_chiu,R.drawable.mexico_chis,
                                R.drawable.mexico_coah,R.drawable.mexico_col,R.drawable.mexico_dur,R.drawable.mexico_guan,R.drawable.mexico_guerr,R.drawable.mexico_hid,R.drawable.mexico_jali,
                                R.drawable.mexico_cdmx,R.drawable.mexico_mich,R.drawable.mexico_more,R.drawable.mexico_naya,R.drawable.mexico_nuev,R.drawable.mexico_oax,R.drawable.mexico_pueb,
                                R.drawable.mexico_quere,R.drawable.mexico_quinta,R.drawable.mexico_san,R.drawable.mexico_sina,R.drawable.mexico_sono,R.drawable.mexico_tab,R.drawable.mexico_tama,
                                R.drawable.mexico_tlax,R.drawable.mexico_vera,R.drawable.mexico_yuca,R.drawable.mexico_zaca};
    private ImageView mEstado;
    private TextView mPuntajeMax, mPuntaje;
    private Button mVerifica,mSiguiente;
    private RadioGroup mGrupoOpc;
    private RadioButton mOpcion;
    private View mEstadoDialog;
    private int respuestaCorrecta,respuestaC,respuestasCorrComp=0,respuestasCorrMedi=0,totPuntos,nuevoRol=0,contador=0,totActual=0;

    private NumerosAleatorios noPregunta,noRespuesta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);
        mEstado = findViewById(R.id.estado);
        mSiguiente = findViewById(R.id.siguiente);
        mPuntaje = findViewById(R.id.puntaje);
        mPuntajeMax = findViewById(R.id.puntajeMax);
        mGrupoOpc = findViewById(R.id.opc_grupo);
        mVerifica = findViewById(R.id.verificar);
        respuestaCorrecta = getIntent().getExtras().getInt("imagenInicial");
        llenarRadioB();
        mEstado.setImageResource(imagenesEstados[getIntent().getExtras().getInt("imagenInicial")]);
        totPuntos = getIntent().getExtras().getInt("numeroPreguntas")*2;
        mPuntajeMax.setText(""+totPuntos);
    }

    public void llenarRadioB(){
        noPregunta = new NumerosAleatorios(0,31);
        noRespuesta = new NumerosAleatorios(0,4);
        respuestaC=noRespuesta.generar();
        for(int i=0;i<4;i++){
            if(i != respuestaC){
                mOpcion = new RadioButton(this);
                mOpcion.setText(estados[noPregunta.generar()]);
                mOpcion.setId(i);
                mOpcion.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                mGrupoOpc.addView(mOpcion,i);
            }else {
                mOpcion = new RadioButton(this);
                mOpcion.setTextColor(getResources().getColor(R.color.colorPrimaryText));
                mOpcion.setText(estados[respuestaCorrecta]);
                mOpcion.setId(i);
                mGrupoOpc.addView(mOpcion,i);
            }
        }
    }

    public void vaciarRadioB(){
        for(int i=0;i<4;i++){
            mGrupoOpc.removeViewAt(0);
        }
    }

    public void siguiente(View v){
        if(!mVerifica.isEnabled()&&contador-1<getIntent().getExtras().getInt("numeroPreguntas")){
            resetearPregunta();
        }else{
            Toast.makeText(this, "Verifique la respuesta primero", Toast.LENGTH_SHORT).show();
        }
        
    }

    public void verifica(View v){
        if(mGrupoOpc.getCheckedRadioButtonId()==respuestaC){
            RadioButton respCapit;
            Button verificarCap;
            final AlertDialog alertDialog;
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            mEstadoDialog = getLayoutInflater().inflate(R.layout.estado_dialog,null);
            noPregunta = new NumerosAleatorios(0,31);
            noRespuesta = new NumerosAleatorios(0,4);
            respuestaC=noRespuesta.generar();
            final RadioGroup grupoCapit = (RadioGroup) mEstadoDialog.findViewById(R.id.opc_capitales);
            for(int i=0;i<4;i++){
                if(i!=respuestaC){
                    respCapit = new RadioButton(this);
                    respCapit.setText(capitales[noPregunta.generar()]);
                    respCapit.setId(i);
                    grupoCapit.addView(respCapit);
                }else{
                    respCapit = new RadioButton(this);
                    respCapit.setText(capitales[respuestaCorrecta]);
                    respCapit.setId(i);
                    grupoCapit.addView(respCapit);
                }
            }
            dialog.setView(mEstadoDialog);
            alertDialog = dialog.create();
            alertDialog.show();
            verificarCap = (Button) mEstadoDialog.findViewById(R.id.verificarCapital);
            verificarCap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(grupoCapit.getCheckedRadioButtonId()==respuestaC && contador<getIntent().getExtras().getInt("numeroPreguntas")){
                        Toast.makeText(v.getContext(), "Correcto, puntos dobles", Toast.LENGTH_SHORT).show();
                        respuestasCorrComp+=2;
                        totActual = respuestasCorrComp+respuestasCorrMedi;
                        mPuntaje.setText(""+totActual);
                        alertDialog.dismiss();
                        mVerifica.setEnabled(false);
                    }else if(contador < getIntent().getExtras().getInt("numeroPreguntas")){
                        Toast.makeText(v.getContext(), "Inorrecto, punto normal", Toast.LENGTH_SHORT).show();
                        respuestasCorrMedi++;
                        alertDialog.dismiss();
                        resetearPregunta();
                    }
                }
            });

        }else{
            Toast.makeText(this, "Respuesta incorrecta", Toast.LENGTH_LONG).show();
            mVerifica.setEnabled(false);
        }
    }

    public void resetearPregunta(){
        contador++;
        if(contador<getIntent().getExtras().getInt("numeroPreguntas")){
            nuevoRol = (int)(Math.random()*31);
            mEstado.setImageResource(imagenesEstados[nuevoRol]);
            totActual = respuestasCorrComp+respuestasCorrMedi;
            mVerifica.setEnabled(true);
            mPuntaje.setText(""+totActual);
            respuestaCorrecta=nuevoRol;
            vaciarRadioB();
            llenarRadioB();
        }else{
            mSiguiente.setEnabled(false);
            mVerifica.setEnabled(false);
            Toast.makeText(this, "JUEGO COMPLETADO!!!", Toast.LENGTH_LONG).show();
        }
    }
}
