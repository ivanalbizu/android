package eu.ivanalbizu.calculadora;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView operando1, operando2, operacion, resultado;
	private Button borrar;
	private final static int TESTIGO = 1234;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		operando1 = (TextView) findViewById(R.id.operando1);
		operando2 = (TextView) findViewById(R.id.operando2);
		operacion = (TextView) findViewById(R.id.operacion);
		resultado = (TextView) findViewById(R.id.resultado);
		borrar = (Button) findViewById(R.id.buttonBorrar);
		
		//Listener para botones numéricos
		OnClickListener marcar = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView txt = (TextView) v;
				//Si no se ha introducido operación a realizar (+ - x %)
				//Se introduce los valores a operando 1
				if (operacion.getText().toString().equals("")) {
					String existente1 = operando1.getText().toString();
					operando1.setText(existente1+txt.getText().toString());
				//Sólo se introduce dígitos a operando 2 cuando
				//Ya existe tipo de operación, 
				//y no se tenga ningún resultado anteriormente
				} else if (resultado.getText().toString().equals("")) {
					String existente2 = operando2.getText().toString();
					operando2.setText(existente2+txt.getText().toString());
				}
			}
		};
		
		//Listener para seleccionar tipo de operación a realizar (+ - x %)
		OnClickListener accion = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView txt = (TextView) v;
				//Dos condiciones para validar la operación
				//1. Que se tenga un resultado de operación anterior.
				//   En este caso, se asigna a operando1 y se valida la operación
				if (!resultado.getText().toString().equals("")){
					//Variable resultado contenía "dígitos extraños": (_=_)
					operando1.setText(resultado.getText().toString().substring(3));
					operacion.setText(txt.getText().toString());
					operando2.setText("");
					resultado.setText("");
				//2. Exista operando 1
				} else if (!operando1.getText().toString().equals("") 
						&& operacion.getText().toString().equals("")) {
					operacion.setText(txt.getText().toString());
				}
			}
		};
		
		//Borrado el último dígito marcado
		borrar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Se permite editar cuando el usuario al menos ha introducido un dígito
				//El orden es de atrás hacía adelante: resultado, operando1, operación, operando1
				if (!resultado.getText().toString().equals("")){
					//Si ya se realizó operación se resetean todos los valores
					//Numéricos y de operaciones
					limpiar(v);
				//Se borra de atrás para adelante
				} else if (!operando2.getText().toString().equals("")) {
					operando2.setText(operando2.getText().subSequence(0, operando2.getText().length() - 1)+"");
				} else if (!operacion.getText().toString().equals("")){
					operacion.setText(operacion.getText().subSequence(0, operacion.getText().length() - 1)+"");
				} else if (!operando1.getText().toString().equals("")){
					operando1.setText(operando1.getText().subSequence(0, operando1.getText().length() - 1)+"");
				}
			}
		});
		
		//Asignación de listener para botones numéricos
		((Button) findViewById(R.id.button0)).setOnClickListener(marcar);
		((Button) findViewById(R.id.button1)).setOnClickListener(marcar);
		((Button) findViewById(R.id.button2)).setOnClickListener(marcar);
		((Button) findViewById(R.id.button3)).setOnClickListener(marcar);
		((Button) findViewById(R.id.button4)).setOnClickListener(marcar);
		((Button) findViewById(R.id.button5)).setOnClickListener(marcar);
		((Button) findViewById(R.id.button6)).setOnClickListener(marcar);
		((Button) findViewById(R.id.button7)).setOnClickListener(marcar);
		((Button) findViewById(R.id.button8)).setOnClickListener(marcar);
		((Button) findViewById(R.id.button9)).setOnClickListener(marcar);
		
		//Asignación de listener para botones de operaciones
		((Button) findViewById(R.id.buttonDividir)).setOnClickListener(accion);
		((Button) findViewById(R.id.buttonMultiplicar)).setOnClickListener(accion);
		((Button) findViewById(R.id.buttonRestar)).setOnClickListener(accion);
		((Button) findViewById(R.id.buttonSumar)).setOnClickListener(accion);

	}
	
	//Método para limpiar los campos: operando1, operando1, operación y resultado
	public void limpiar(View v){
		resultado.setText("");
		operando1.setText("");
		operando2.setText("");
		operacion.setText("");
	}
	
	public void introducirDecimales(View v){
		TextView txt = (TextView) v;
		//Si ya existe un "resultado" no procede insertar decimal
		if (!resultado.getText().toString().equals("")) {
			return;
		}
		//Si no existe operación,
		//el punto decimal se trataría de introducir sobre primer operando
		if (operacion.getText().toString().equals("")){
			String[] partido = operando1.getText().toString().split("\\.");
			//No se ha generado dos elementos, quiere decir que no tiene decimal
			if (partido.length == 1) {
				String existente = operando1.getText().toString();
				operando1.setText(existente+txt.getText().toString());
			}
		} else {
			//Si existe operación,
			//el punto decimal se trataría de introducir sobre segundo operando
			String[] partido = operando2.getText().toString().split("\\.");
			//No se ha generado dos elementos, quiere decir que no tiene decimal
			if (partido.length == 1) {
				String existente = operando2.getText().toString();
				operando2.setText(existente+txt.getText().toString());
			}
		}
	}
	
	public void lanzarOperacion(View v) {
		//Si operando 2 no contiene valor numérico, no se lanza el evento
		if (operando2.getText().toString().equals("")) {
			return;
		}

		Intent intent = null;
		
		//Según el tipo de operación seleccionado (+ - x %)
		//se lanza la actividad correspondiente
		if (operacion.getText().toString().equals("+"))
			intent = new Intent(MainActivity.this, SumaActivity.class);
		else if (operacion.getText().toString().equals("-"))
			intent = new Intent(MainActivity.this, RestaActivity.class);
		else if (operacion.getText().toString().equals("x"))
			intent = new Intent(MainActivity.this, MultiplicacionActivity.class);
		else if (operacion.getText().toString().equals("%"))
			intent = new Intent(MainActivity.this, DivisionActivity.class);
		
		//Se lanza la información extra de los operandos
		intent.putExtra("operando1", operando1.getText().toString());
		intent.putExtra("operando2", operando2.getText().toString());
		startActivityForResult(intent, TESTIGO);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//Si lo recibido corresponde con lo esperado (TESTIGO)
		if (requestCode == TESTIGO) {
			switch (resultCode) {
			case RESULT_OK:
				String res = data.getStringExtra("resultado");
				resultado.setText(" = "+res);
				break;
			case RESULT_CANCELED:
				Toast.makeText(this, "Formato de número no válido", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	}
}
