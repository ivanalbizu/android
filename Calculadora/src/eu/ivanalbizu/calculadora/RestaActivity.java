package eu.ivanalbizu.calculadora;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class RestaActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Se obtienen los valores del Intent "Origen"
		String operando1 = getIntent().getStringExtra("operando1");
		String operando2 = getIntent().getStringExtra("operando2");
		
		//Se declara el Intent de regreso
		Intent returnIntent = new Intent();
		
		try {	
			Float op1 = Float.parseFloat(operando1);
			Float op2 = Float.parseFloat(operando2);
			Float res = op1-op2;
			
			//Si se ha llegado hasta aquí el formato de número es correcto
			String resultado = res.toString();

			returnIntent.putExtra("resultado", resultado);
			setResult(RESULT_OK,returnIntent);
			finish();
			
		} catch(NumberFormatException e) {
			//Si se entra aquí es que formato de número es incorrecto
			//Se envía resultado cancelado
			setResult(RESULT_CANCELED, returnIntent);
			finish();
		}
	}
	
}
