import java.util.*;

abstract class ColaDePrioridad {
    abstract void encolar(int elemento);
    abstract void desencolar();
    abstract int tope();
    abstract boolean esVacia();
    abstract boolean estaLlena();
};

class MinHeap {
    private int[] elementos;
    private int largo;
    private int ultimoLibre;

    public MinHeap(int tamanio) {
        elementos = new int[tamanio + 1];
        largo = tamanio;
        ultimoLibre = 1;
    }

    private int izq(int nodo) {
        return nodo * 2;
    }

    private int der(int nodo) {
        return nodo * 2 + 1;
    }

    private int padre(int nodo) {
        return nodo/2;
    }

    private void intercambiar(int x, int y) {
        int aux = elementos[x];
        elementos[x] = elementos[y];
        elementos[y] = aux;
    }

    public void encolar(int nuevoElemento) {
        if(!estaLleno()) {
            elementos[ultimoLibre] = nuevoElemento;
            flotar(ultimoLibre);
            ultimoLibre++;
        }
    }

    private void flotar(int nodo) {
        if(nodo != 1) {
            int nodoPadre = padre(nodo);
            if(elementos[nodo] < elementos[nodoPadre]) {
                intercambiar(nodo, nodoPadre);
                flotar(nodoPadre);
            }
        }
    }

    public int obtenerMinimo() {
        if(!esVacio()) {
            return elementos[1];
        }
        return -1;
    }

    public void borrarMinimo() {
        if(!esVacio()) {
            elementos[1] = elementos[ultimoLibre - 1];
            ultimoLibre--;
            hundir(1);
        }
    }

    private void hundir(int nodo) {
        // si tiene hijos (al menos 1)
        if(izq(nodo) < ultimoLibre) {
            int izq = izq(nodo);
            int der = der(nodo);
            int hijoMenor = izq;

            if(der < ultimoLibre && elementos[der] < elementos[izq]) {
                hijoMenor = der;
            }

            if(elementos[hijoMenor] < elementos[nodo]) {
                intercambiar(hijoMenor, nodo);
                hundir(hijoMenor);
            }
        }
    }

    public boolean esVacio() {
        return ultimoLibre == 1;
    }

    public boolean estaLleno() {
        return ultimoLibre > largo;
    }

}

class ColaDePrioridadImpHeap extends ColaDePrioridad {
    private MinHeap miHeap;

    public ColaDePrioridadImpHeap(int tamanio) {
        miHeap = new MinHeap(tamanio);
    }
    public void encolar(int el) {
        miHeap.encolar(el);
    }
    public void desencolar() {
        miHeap.borrarMinimo();
    }
    public int tope() {
		return miHeap.obtenerMinimo();
	}
    public boolean esVacia() {
        return miHeap.esVacio();
    }
    public boolean estaLlena() {
        return miHeap.estaLleno();
    }
};



public class Ejercicio1 {
    public static void main(String[] args) {
       
		// leo del archivo externo
		Scanner sc = new Scanner(System.in);
		//leo el tamaño del archivo
        int N = sc.nextInt();
		//Creo la cola de prioridad con este archivo
		ColaDePrioridad cp = new ColaDePrioridadImpHeap(N);
		int elem;
        for (int i = 0; i < N; i++) {
            elem  = sc.nextInt();
			cp.encolar(elem);	
		}	
        while(!cp.esVacia()) {
            System.out.println(cp.tope());
			cp.desencolar();
        }
    }
}