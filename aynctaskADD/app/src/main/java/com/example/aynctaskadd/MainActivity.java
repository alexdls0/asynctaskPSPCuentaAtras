package com.example.aynctaskadd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvCuentaRegresiva;
    private Hebra hebra;
    private Button btLanzar;
    private boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initcomponents();
    }

    private void initcomponents() {
        btLanzar = findViewById(R.id.btLanzar);
        tvCuentaRegresiva = findViewById(R.id.tvCuentaRegresiva);
        hebra = new Hebra(20);

        btLanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!started){
                    hebra.execute();
                    started = true;
                }
                if (hebra.getStatus() == AsyncTask.Status.FINISHED){
                    hebra = new Hebra(20);
                    hebra.execute();
                }
            }
        });
    }

    class Hebra extends AsyncTask<String, Integer, Boolean>{
        private int valorInicial;

        Hebra(int vi){
            valorInicial = vi;
        }

        Hebra(){
            this(20);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvCuentaRegresiva.setText(""+valorInicial);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            for (int i = valorInicial; i >= 0; i--) {
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return false;
                }
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int value = values[values.length-1];
            tvCuentaRegresiva.setText(""+value);
            Log.v("logprueba",""+value);
            if(value==0){
                Intent i = new Intent(MainActivity.this, SecondAvtivity.class);
                startActivity(i);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onCancelled(Boolean aBoolean) {
            super.onCancelled(aBoolean);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean){
                tvCuentaRegresiva.setText(""+0);
            }else{
                tvCuentaRegresiva.setText(""+20);
            }
        }
    }


}
