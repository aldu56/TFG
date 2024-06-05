package es.pmdm.bibliotecadecontenidomultimedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextUtils;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import es.pmdm.bibliotecadecontenidomultimedia.dto.UserDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class change_pass extends AppCompatActivity {
    private static final String CANAL_ID = "1";
    private EditText edPassword;
    private EditText edPasswordConfirm;
    private Button btnCambiar;
    private FloatingActionButton btnVolverExp3;
    private int idUsuario;
    private UserDto user;
    private ApiManager apiManager;

    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        getSupportActionBar().setTitle("Cambio de contraseña");


        edPassword = findViewById(R.id.edPasswordChange);
        edPasswordConfirm = findViewById(R.id.edPasswordConfirm);
        btnCambiar = findViewById(R.id.btnCambiar);
        btnVolverExp3 = findViewById(R.id.btnVolverExp3);

        apiManager = new ApiManager();
        idUsuario = getIntent().getIntExtra("ID_USUARIO", -1);
        token = getIntent().getStringExtra("TOKEN");

        obtenerUsuario(token, idUsuario);

        btnVolverExp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarContrasena();
            }
        });
    }

    private void obtenerUsuario(String token, int userId) {
        apiManager.getUserById(token, userId, new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(change_pass.this, "Código: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                user = response.body();
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Toast.makeText(change_pass.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cambiarContrasena() {
        String nuevaContrasena = edPassword.getText().toString().trim();
        String confirmarContrasena = edPasswordConfirm.getText().toString().trim();

        if (TextUtils.isEmpty(nuevaContrasena) || TextUtils.isEmpty(confirmarContrasena)) {
            Toast.makeText(this, "Por favor, complete ambos campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!nuevaContrasena.equals(confirmarContrasena)) {
            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
            return;
        }

        user.setPassword(nuevaContrasena);

        apiManager.updateUsers(token, idUsuario, user, new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(change_pass.this, "Error al cambiar la contraseña: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(change_pass.this, "Contraseña cambiada con éxito.", Toast.LENGTH_SHORT).show();
                mostrarNotificacion(false, false);
                finish();
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Toast.makeText(change_pass.this, "Error al cambiar la contraseña: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void mostrarNotificacion(boolean expandible, boolean actividad) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(change_pass.this, CANAL_ID);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);

        if (!expandible) {
            builder.setContentTitle("Biblioteca Multimedia");
            builder.setContentText("Has cambiado tu contraseña.");
        } else {
            NotificationCompat.InboxStyle estilo = new NotificationCompat.InboxStyle();
            estilo.setBigContentTitle("Biblioteca Multimedia");

            estilo.addLine("Has cambiado tu  contraseña.");

            builder.setStyle(estilo);
        }

        builder.setPriority(NotificationCompat.PRIORITY_MAX);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(CANAL_ID, "Biblioteca Multimedia", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(canal);
        }

        Notification notificacion = builder.build();
        notificationManager.notify(Integer.parseInt(CANAL_ID), notificacion);

    }
}