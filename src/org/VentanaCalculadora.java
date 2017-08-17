package org;

    

	import java.awt.BorderLayout;
	import java.awt.Color;
	import java.awt.Font;
	import java.awt.GridLayout;
	import java.awt.event.MouseAdapter;
	import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;

import javax.swing.JButton;
	import javax.swing.JFrame;
	import javax.swing.JPanel;
	import javax.swing.JTextField;
	import javax.swing.WindowConstants;
	import javax.swing.border.EmptyBorder;

import bsh.EvalError;
import bsh.Interpreter;


	public class VentanaCalculadora extends JFrame {

		/**
		 * generado
		 */
		private static final long serialVersionUID = 1583724102189855698L;

		/** numero tecleado */
		JTextField pantalla;

		/** guarda el resultado de la operacion anterior o el número tecleado */
		double resultado;

		/** para guardar la operacion a realizar */
		String operacion;

		/** Los paneles donde colocaremos los botones */
		JPanel panelNumeros, panelOperaciones;

		/** Indica si estamos iniciando o no una operación */
		boolean nuevaOperacion = true;

		/**
		 * Constructor. Crea los botones y componentes de la calculadora
		 */
		public VentanaCalculadora() {
			super();
			setSize(250, 300);
			setTitle("Calculadora Simple");
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			setResizable(false);

			// Vamos a dibujar sobre el panel
			JPanel panel = (JPanel) this.getContentPane();
			panel.setLayout(new BorderLayout());

			pantalla = new JTextField("0", 20);
			pantalla.setBorder(new EmptyBorder(4, 4, 4, 4));
			pantalla.setFont(new Font("Arial", Font.BOLD, 25));
			pantalla.setHorizontalAlignment(JTextField.RIGHT);
			pantalla.setEditable(false);
			pantalla.setBackground(Color.WHITE);
			panel.add("North", pantalla);

			panelNumeros = new JPanel();
			panelNumeros.setLayout(new GridLayout(4, 3));
			panelNumeros.setBorder(new EmptyBorder(4, 4, 4, 4));

			for (int n = 9; n >= 0; n--) {
				nuevoBotonNumerico("" + n);
			}

			nuevoBotonNumerico(".");

			panel.add("Center", panelNumeros);

			panelOperaciones = new JPanel();
			panelOperaciones.setLayout(new GridLayout(6, 1));
			panelOperaciones.setBorder(new EmptyBorder(4, 4, 4, 4));

			nuevoBotonOperacion("+");
			nuevoBotonOperacion("-");
			nuevoBotonOperacion("*");
			nuevoBotonOperacion("/");
			nuevoBotonOperacion("=");
			nuevoBotonOperacion("CE");

			panel.add("East", panelOperaciones);

			validate();
		}

		/**
		 * Crea un boton del teclado numérico y enlaza sus eventos con el listener
		 * correspondiente
		 * 
		 * @param digito
		 *            boton a crear
		 */
		private void nuevoBotonNumerico(String digito) {
			JButton btn = new JButton();
			btn.setText(digito);
			btn.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent evt) {
					JButton btn = (JButton) evt.getSource();
					numeroPulsado(btn.getText());
				}
			});

			panelNumeros.add(btn);
		}

		/**
		 * Crea un botón de operacion y lo enlaza con sus eventos.
		 * 
		 * @param operacion
		 */
		private void nuevoBotonOperacion(String operacion) {
			JButton btn = new JButton(operacion);
			btn.setForeground(Color.RED);

			btn.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent evt) {
					JButton btn = (JButton) evt.getSource();
					operacionPulsado(btn.getText());
				}
			});

			panelOperaciones.add(btn);
		}

		/**
		 * Gestiona las pulsaciones de teclas numéricas
		 * 
		 * @param digito
		 *            tecla pulsada
		 */
		private void numeroPulsado(String digito) {
			if (pantalla.getText().equals("0") || nuevaOperacion) {
				pantalla.setText(digito);
			} else {
				pantalla.setText(pantalla.getText().concat(digito));
			}
			nuevaOperacion = false;
		}

		/**
		 * Gestiona el gestiona las pulsaciones de teclas de operación
		 * 
		 * @param tecla
		 */
		private void operacionPulsado(String tecla) {
			if (tecla.equals("=")) {
				calcularResultado();
			} else if (tecla.equals("CE")) {
				resultado = 0;
				pantalla.setText("");
				nuevaOperacion = true;
			} else {
				operacion = tecla;
			
					pantalla.setText(pantalla.getText().concat(operacion));
					
				}
		}

	
		
		/**
		 * Calcula el resultado y lo muestra por pantalla
		 */
		private void calcularResultado() {
			String ecuacion = "result =".concat(pantalla.getText());
			try {
				Interpreter interpreter = new Interpreter();
				
				interpreter.eval(ecuacion);
				
				pantalla.setText(interpreter.get("result").toString());
			} catch (EvalError e) {
				e.printStackTrace();
			}
		
			
			guardarResultadoOperacion(ecuacion);
			
			operacion = "";
		}
		
		
		private void guardarResultadoOperacion(String ecuacion){
			
		try {
			byte[] data= ecuacion.concat("  ").concat(pantalla.getText()).getBytes(Charset.forName("UTF-B"));
			File file= new File("resultados.txt");
					if (file.createNewFile()) {
						FileOutputStream out = new FileOutputStream("resultados");
						out.write(data);
						out.close();
					}else{
						FileOutputStream out = new FileOutputStream("resultados");
						out.write(data);
						out.close();
					}
		} catch (Exception e) {
			// TODO: handle exception
		}	
		}	
	}


