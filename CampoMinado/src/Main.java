
public class Main implements Runnable {
	
    Userintfc intfc = new Userintfc(); //Inicializa a classe de user interface
    
    public static void main(String[] args) {
        new Thread (new Main()).start(); //Chama a funcao run
    }

    @Override
    public void run() {            
    	intfc.repaint();
    }
    
}