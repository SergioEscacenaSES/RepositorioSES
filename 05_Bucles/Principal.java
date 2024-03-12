import java.util.Random;
import java.util.Scanner;

public class Principal {
	public static final int NUMERO_ALUMNOS = 8;

	public static final int NOTA_LIMITE_APROBADO = 5;
	public static final int NOTA_MAXIMA=10;
	public static final String[] ASIGNATURAS = 
		{ "PROGRAMACION", "BASE DE DATOS", "LENGUAJES DE MARCA" };
	public static final int NUMERO_ASIGNATURAS = ASIGNATURAS.length;
	private static Scanner teclado=new Scanner(System.in);

	public static void main(String[] args) {
		int matrizNotas[][] = new int[NUMERO_ALUMNOS][NUMERO_ASIGNATURAS];
		String asignatura;
		int numeroAlumno;

		cargarNotasMatriz(matrizNotas);

		imprimirNotas(matrizNotas);
		

		numeroDeSuspensosPorAlumno(matrizNotas);

		System.out.println("Introduce asignatura:");
		asignatura=teclado.nextLine();
		
		notaMediaDeAsignatura(matrizNotas, asignatura);
		
		
		estadisticaDeSuspensos(matrizNotas);
		
		numeroAlumno=solicitarAlumno();
		mejorNotaDeAlumno(matrizNotas, numeroAlumno);
		
		

		

	}

	private static void mejorNotaDeAlumno(int[][] matrizNotas,int numeroAlumno) {

		int posicionAlumno;
		int posicionAsignaturaMasNota;
		if (numeroAlumno <1 || numeroAlumno > NUMERO_ALUMNOS)
			System.out.println("Numero de alumno " + numeroAlumno + " incorrecto");
		else {
			posicionAlumno=numeroAlumno -1;
			posicionAsignaturaMasNota=0;
			
			for (int asignatura = 0; asignatura < matrizNotas[0].length; asignatura++) {
				
				if (matrizNotas[posicionAlumno][asignatura] > 
					matrizNotas[posicionAlumno][posicionAsignaturaMasNota]) {
					posicionAsignaturaMasNota= asignatura;
				}
			}
			
			System.out.println("La mejor nota es " +  matrizNotas[posicionAlumno][posicionAsignaturaMasNota]
					+ " en "+  ASIGNATURAS[posicionAsignaturaMasNota]);
			
			
		}
		
	}

	private static int solicitarAlumno() {
		System.out.println("Introduce el número de alumno (1-" + NUMERO_ALUMNOS + ")");
		return Integer.parseInt(teclado.nextLine());
	}

	private static void notaMediaDeAsignatura(int[][] matrizNotas, String asignatura) {
		
		int indiceAsignatura, sumaNotas = 0;
		
		indiceAsignatura=buscarAsignatura( asignatura);
		
		if ( indiceAsignatura==-1) {
			//asignatura no encontrada
			System.out.println("No existe la asignatura " + asignatura);
		}
		else {
			
			for (int numeroDeAlumno = 0; numeroDeAlumno < matrizNotas.length; numeroDeAlumno++) {
				sumaNotas+= matrizNotas[numeroDeAlumno][indiceAsignatura];
			}
			
			System.out.println("La media en la asignatura " + asignatura + " es " + (double)sumaNotas/NUMERO_ALUMNOS);
		}
		
		
	}

	/**
	 * Método que busca una asignatura en el array ASIGNATURAS	
	 * @param asignatura la asignatura buscada
	 * @return Posición en el array o -1 si no lo encuentra
	 */
	private static int buscarAsignatura(String asignatura) {
		
		boolean encontrada=false;
		int posicion=-1;
		
		asignatura=asignatura.toUpperCase();
		
		for (int i = 0; i < ASIGNATURAS.length && !encontrada; i++) {
			if ( asignatura.equals( ASIGNATURAS[i])){
				encontrada=true;
				posicion=i;
			}
		}
		return posicion;
	}

	private static void numeroDeSuspensosPorAlumno(int[][] matrizNotas) {

		
		int numeroDeSuspensos;
		
		for (int numeroAlumno = 0; numeroAlumno < matrizNotas.length; numeroAlumno++) {
			
			numeroDeSuspensos=0;
			
			for (int numeroAsignatura = 0; numeroAsignatura < matrizNotas[0].length; numeroAsignatura++) {
				
				if (matrizNotas[numeroAlumno][numeroAsignatura] <5)
					numeroDeSuspensos++;
			}
			
			System.out.println("El alumno " + (numeroAlumno +1) + " ha tenido " + numeroDeSuspensos + " suspensos");
		}
		
	}

	/**
	 * Carga los datos de la matriz con notas aleatorias entre 0 y 10
	 * @param matrizNotas
	 */
	private static void cargarNotasMatriz(int matrizNotas[][]) {
		int indiceAlumno, indiceAsignatura;

		Random serie = new Random();
		for (indiceAlumno = 0; indiceAlumno < matrizNotas.length; indiceAlumno++) {

			for (indiceAsignatura = 0; indiceAsignatura < matrizNotas[0].length; indiceAsignatura++) {

				matrizNotas[indiceAlumno][indiceAsignatura] = serie.nextInt(NOTA_MAXIMA+1); // DE
																					// 0
																								// A
																								// NOTA_MAXIMA
			}
		}
	}
	/**
	 * Imprime las notas por pantalla
	 * @param matrizNotas
	 */
	private static void imprimirNotas(int matrizNotas[][]) {
		int indiceAlumno, indiceAsignatura;

		for (int i = 0; i < ASIGNATURAS.length; i++) {
			System.out.print("\t" + ASIGNATURAS[i]);
		}
		
		
		System.out.println();
		for (indiceAlumno = 0; indiceAlumno < matrizNotas.length; indiceAlumno++) {
			System.out.print("Alumno " + (indiceAlumno + 1) + "    ");
			for (indiceAsignatura = 0; indiceAsignatura < matrizNotas[0].length; indiceAsignatura++) {
				
				System.out.print(matrizNotas[indiceAlumno][indiceAsignatura]);
				System.out.print("\t\t");
			}
			System.out.println();
		}
		
	}
	/**
	 * Realiza un listado con la estadística de suspensos. Es decir
	 * cuantos alumnos tienen 0, 1, 2... suspensos.
	 * @param matrizNotas
	 */
	private static void estadisticaDeSuspensos(int matrizNotas[][]) {
		int contadorSuspensos = 0;
		int indiceAlumno, indiceAsignaturas;
		int numeroAsignaturas = matrizNotas[0].length;
		
		// Cuidado, crea un vector con un número más del número
		// de asignaturas, porque también hay que contar los que 
		// tienen 0 suspensos
		int[] vectorSuspensos = new int[numeroAsignaturas + 1];


		for (indiceAlumno = 0; indiceAlumno < matrizNotas.length; indiceAlumno++) {
			contadorSuspensos = 0;
			for (indiceAsignaturas = 0; indiceAsignaturas < matrizNotas[indiceAlumno].length; indiceAsignaturas++) {

				if (matrizNotas[indiceAlumno][indiceAsignaturas] < NOTA_LIMITE_APROBADO) {
					contadorSuspensos++;

				}
			}
			vectorSuspensos[contadorSuspensos]++;
		}

		System.out.println("Estadisticas de suspensos\n");

		for (int i = 0; i < vectorSuspensos.length; i++) {
			System.out.println(" Hay " + vectorSuspensos[i] + " alumnos que tienen " + i + " suspensos.\n");
		}

		

	}

	
	
}
