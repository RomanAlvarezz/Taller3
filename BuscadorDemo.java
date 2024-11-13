package TpBorrador;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TpCgpt2 {
	public static void main(String[] args) {
    File dir = new File("C:\\");  	
    File[] arrFiles = dir.listFiles(); 
    List<String> rutasEncontradas = new ArrayList<>();
    Buscador.setRutas(rutasEncontradas);
    Buscador.setArchivo("textito.txt");
    List<Thread> hilos = new ArrayList<>();

    long start = System.currentTimeMillis();
    for (File file : arrFiles) {
        if (!file.getName().equals("tmp") && !file.getName().equals("sys") && !file.getName().equals("proc")) {
            //System.out.println(file.getAbsolutePath());
            Thread hilo = new Thread(new Buscador(file));
            hilos.add(hilo); 
            hilo.start(); 
        } 
    }

    for (Thread hilo : hilos) {
        try {
            hilo.join(); 
        } catch (InterruptedException e) {
            System.out.println("El hilo fue interrumpido.");
        }
    }
    long end = System.currentTimeMillis(); 

    //System.out.println("-----------------------------------------------------");
    System.out.println("Tiempo de Ejecucion: " + (end - start));
    synchronized (rutasEncontradas) { 
    	System.out.println("Cantidad Rutas Encontradas: " + rutasEncontradas.size());
        for (String ruta : rutasEncontradas) {
            System.out.println(ruta);
        }
    }
  }
}