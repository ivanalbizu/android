package eu.ivanalbizu.customdial;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	//Se declaran variables
	TextView numero;
	ImageButton editNumero;
	ImageButton doCall;
			
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Se inician las variables hacen uso de la referencia del archivo XML
        numero = (TextView) findViewById(R.id.numeroLlamada);
        editNumero = (ImageButton) findViewById(R.id.imageButton1);
        doCall = (ImageButton) findViewById(R.id.call);
        
        
        //Se construye un "listener" para ser asignado a los botones de la vista
        OnClickListener listenerAddNumber = new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Se obtiene el botón que produjo el evento
				TextView txt = (TextView) v;
				//Se permite un máximo de 10 dígitos paraun número de teléfono
				if (numero.getText().toString().length() < 10) {
					//Se concatena el teléfono que aparece en en TextView con el
					//texto del número de dígito marcado
					numero.setText(numero.getText().toString()+txt.getText().toString());
				} else {
					//Se muestra mensaje cuando se quiere introducir teléfono de más
					//de 10 dígitos
	                Toast.makeText(
	                		MainActivity.this,
	                		"No puede introducir más dígitos",
	                		Toast.LENGTH_SHORT
	                		).show();
				}
			}
		};
		
		//Se asigna a cada uno de los botones el listener anterior creado
		((Button) findViewById(R.id.button1)).setOnClickListener(listenerAddNumber);
		((Button) findViewById(R.id.button2)).setOnClickListener(listenerAddNumber);
		((Button) findViewById(R.id.button3)).setOnClickListener(listenerAddNumber);
		((Button) findViewById(R.id.button4)).setOnClickListener(listenerAddNumber);
		((Button) findViewById(R.id.button5)).setOnClickListener(listenerAddNumber);
		((Button) findViewById(R.id.button6)).setOnClickListener(listenerAddNumber);
		((Button) findViewById(R.id.button7)).setOnClickListener(listenerAddNumber);
		((Button) findViewById(R.id.button8)).setOnClickListener(listenerAddNumber);
		((Button) findViewById(R.id.button9)).setOnClickListener(listenerAddNumber);
		((Button) findViewById(R.id.button0)).setOnClickListener(listenerAddNumber);

		//Se habilita funcionalidad de editar el número de teléfono marcado
		//Se activa para ello un listener sobre el elemento de borrado
		editNumero.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Se permite editar cuando el usuario al menos ha introducido un dígito
				if (numero.getText().length() > 0 ){
					//Se desprecia el último dígito del teléfono marcado
					numero.setText(numero.getText().subSequence(0, numero.getText().length() - 1)
					+ "");
				}
			}
		});
		
		
		//Se habilita listener para lanzar la llamada
		//Se activa para ello un listener sobre el botón de realizar llamada
		doCall.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Se captura el teléfono introducido
				String llamarNumero = numero.getText().toString();
				//Si el usuario no introducido teléfono, se introduce número de emergencias
				if (llamarNumero.equals("")) {
					llamarNumero = "112";
				}
				//Se parsea el teléfono a URI
				Uri number = Uri.parse("tel:"+llamarNumero);
				
				//Se lanza intent de llamada
				Intent callIntent = new Intent(Intent.ACTION_VIEW, number);
				startActivity(callIntent);				
			}
		});		
		
    }
}
