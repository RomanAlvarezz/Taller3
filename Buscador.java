package TpBorrador;
import java.io.File;
import java.util.List;

public class Buscador implements Runnable {
    private File directorio;
    private static String nombreArchivo;
    private static List<String> rutasEncontradas;
    
    public Buscador(File directorio) {
        this.directorio = directorio;
    }
    
    public static List<String> getRutas(){
    	return Buscador.rutasEncontradas;
    }
    
    public static void setRutas(List<String> rutas){
    	Buscador.rutasEncontradas = rutas;
    }
    
    public static String getArchivo(){
    	return Buscador.nombreArchivo;
    }
    
    public static void setArchivo(String archivo){
    	Buscador.nombreArchivo = archivo;
    }

    private void buscarArchivo(File file) {
        if (file.getName().equals(Buscador.nombreArchivo)) {
            synchronized (Buscador.rutasEncontradas) { 
                Buscador.rutasEncontradas.add(file.getAbsolutePath());
            }
        }
    }

    private void explorarDirectorio(File file) {
        if (file.isDirectory()) {
            File[] archivos = file.listFiles();
            if (archivos != null) {
                for (File archivo : archivos) {
                    if (archivo.isDirectory()) {
                        explorarDirectorio(archivo); 
                    } else {
                        buscarArchivo(archivo); 
                    }
                }
            }
        } else {
        	buscarArchivo(file); 
        }
    }

    @Override
    public void run() {
        explorarDirectorio(directorio); 
    }
}
