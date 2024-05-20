package es.pmdm.bibliotecadecontenidomultimedia;

import androidx.appcompat.app.AppCompatActivity;

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
    private EditText edPassword;
    private EditText edPasswordConfirm;
    private Button btnCambiar;
    private FloatingActionButton btnVolverExp3;
    private int idUsuario;
    private UserDto user;
    private ApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        edPassword = findViewById(R.id.edPasswordChange);
        edPasswordConfirm = findViewById(R.id.edPasswordConfirm);
        btnCambiar = findViewById(R.id.btnCambiar);
        btnVolverExp3 = findViewById(R.id.btnVolverExp3);

        apiManager = new ApiManager();
        idUsuario = getIntent().getIntExtra("ID_USUARIO", -1);
        obtenerUsuario(idUsuario);

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

    private void obtenerUsuario(int userId) {
        apiManager.getUserById(userId, new Callback<UserDto>() {
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

        apiManager.updateUsers(idUsuario, user, new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(change_pass.this, "Error al cambiar la contraseña: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(change_pass.this, "Contraseña cambiada con éxito.", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Toast.makeText(change_pass.this, "Error al cambiar la contraseña: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}