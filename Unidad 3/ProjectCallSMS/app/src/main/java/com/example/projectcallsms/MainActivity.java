package com.example.projectcallsms;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {

    private String telefono; //Variable donde se guardara el numero de telefono
    private String nombre; //Variable donde se guardara el nombre de contacto

    private final int codigoRequestContactsTel = 1; //Codigo de respuesta para el requestPermision de Llamada y  Contactos
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button mCallButton = (Button) findViewById(R.id.btn_call); //Obtenemos el boton del view

        checkPhonePermission();
        //Asignamos un evento al boton btn_call
        mCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPhonePermission()==false){
                    Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    pickContact.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); //SOLO MOSTRAR VENTANA CON NOMBRE Y NUMERO
                    startActivityForResult(pickContact, 1);
                }
                else{
                    try {
                        //Pedir que activen el permiso de leer contactos y llamadas
                        if(checkPhonePermission()){
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE}, codigoRequestContactsTel);
                        }
                    }catch (Exception e){
                        Toast.makeText(MainActivity.this, "Falló la aplicación. Intente de nuevo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //Booleano para saber si los permisos han sido concedidos
    public boolean checkPhonePermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) == PERMISSION_GRANTED) | (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }

    //Revisar las peticiones de activacion para los permisos de Llamadas y Contactos
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case codigoRequestContactsTel: {
                if (grantResults.length > 0
                        && grantResults[0] == PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permiso concedido", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Permiso denegado", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    //OnActivityResult del boton btn_call
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri = data.getData();
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst(); //Nos movemos al primer dato
        telefono = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)); //Guardamos el numero encontrado
        cursor.moveToFirst(); //Nos movemos al primer dato
        nombre = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)); //Guardamos el nombre encontrado
        String marcar = "tel:" + telefono; //Concatenamos para completar la URI
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(marcar)));
        Toast.makeText(MainActivity.this, "Lamando a "+nombre+"\n"+telefono, Toast.LENGTH_SHORT).show();
    }
}
