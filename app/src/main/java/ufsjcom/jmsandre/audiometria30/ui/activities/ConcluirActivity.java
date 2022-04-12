package ufsjcom.jmsandre.audiometria30.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.io.OutputStream;

import ufsjcom.jmsandre.audiometria30.R;
import ufsjcom.jmsandre.audiometria30.helper.TesteAudiometricoHelper;
import ufsjcom.jmsandre.audiometria30.model.Usuario;

public class ConcluirActivity extends AppCompatActivity {
    private Usuario usuario;
    private TextView nome;
    private TextView cpf;
    private boolean acessarUsuario;

    TesteAudiometricoHelper viewHelper;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getUsuario();

        if(usuario.getNome() == null)
            setContentView(R.layout.activity_concluir_save_false);

        else {
            setContentView(R.layout.activity_concluir_save_true);
            setUserInfo();
        }

        viewHelper = new TesteAudiometricoHelper(
                this,
                usuario,
                TesteAudiometricoHelper.RESULT);

        viewHelper.setDisplays();
        viewHelper.setButtons();
        viewHelper.setChartSettings();

        viewHelper.getPDFButton().setOnClickListener(gerar->{
            gerarBitMap();
        });
    }

    private void getUsuario(){
        Intent intent = getIntent();
        usuario = intent.getParcelableExtra("usuario");

        if(intent.hasExtra("acessarUsuario"))
            this.acessarUsuario = true;
        else this.acessarUsuario = false;
    }


    private void setUserInfo(){
        nome = findViewById(R.id.print_nome);
        cpf = findViewById(R.id.print_cpf);

        nome.setText(usuario.getNome());
        cpf.setText(usuario.getCpf());
    }

    private Bitmap generateBitMapFromView(View view){

        Bitmap bmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmap);

        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        view.draw(canvas);
        return bmap;
    }

    public void gerarBitMap(){

        View printview = this.getWindow().getDecorView().getRootView();
        Bitmap bmap = generateBitMapFromView(printview);
        if(bmap != null){
            saveBmap(bmap);
        }
    }

    private void saveBmap(Bitmap bmap) {
        // Generating a file name
        final Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        final int quality = 95;

        String filename;
        if(usuario.getNome() != null)
            filename = String.format("Audiometria - Resultado - %s", usuario.getNome());

        else filename = ("Audiometria - Teste Rapido");

        // Output stream
        OutputStream out = null;

        // For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            final ContentValues values = new ContentValues();
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, filename);
                values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

            final ContentResolver resolver = getContentResolver();

            Uri uri = null;
            try {
                final Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                uri = resolver.insert(contentUri, values);

                if (uri == null)
                    throw new IOException("Failed to create new MediaStore record.");

                try (final OutputStream stream = resolver.openOutputStream(uri)) {
                    if (stream == null)
                        throw new IOException("Failed to open output stream.");

                    if (!bmap.compress(format, quality, stream))
                        throw new IOException("Failed to save bitmap.");
                }
                Toast.makeText(this, "Salvo", Toast.LENGTH_SHORT).show();
            }
            catch (IOException e) {

                if (uri != null) {
                    // Don't leave an orphan entry in the MediaStore
                    resolver.delete(uri, null, null);
                }
            }
        }
    }


    private static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}