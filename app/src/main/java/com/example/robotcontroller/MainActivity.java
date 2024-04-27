package com.example.robotcontroller;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.robotcontroller.Bluetooth.BtState;

public class MainActivity extends AppCompatActivity {
    MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.InitBtComponents(this);

        TextView BtState = findViewById(R.id.BluetoothStatusText);
        BtState.setText(mainViewModel.getBtState().toString());

        CardView Button1 = findViewById(R.id.Button1);
        CardView Button2 = findViewById(R.id.Button2);
        CardView Button3 = findViewById(R.id.Button3);
        CardView Button4 = findViewById(R.id.Button4);

        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.SendCommand("1");
            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.SendCommand("2");
            }
        });
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.SendCommand("3");
            }
        });
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.SendCommand("4");
            }
        });

    }
}