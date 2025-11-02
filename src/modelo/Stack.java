package modelo;

public class Stack {
    protected Nodo top; // Referencia al tope (último elemento añadido) de la pila

    // Constructor: crea pila vacía
    public Stack() {
        top = null;
    }

    // Metodo para verificar que la pila está vacía
    public boolean isEmpty() {
        return top == null;
    }

    // Push (Apilar): Pone un elemento en el tope de la pila
    public void push(Object element) {
        // 1. Crea un nuevo nodo
        // 2. Este nuevo nodo apunta al 'top' actual.
        //    (Usamos el constructor de dos parámetros de tu clase Nodo)
        Nodo newNodo = new Nodo(element, top); 
        
        // 3. El nuevo nodo se convierte en el nuevo 'top'
        top = newNodo;
    }

    // Pop (Desapilar): Saca y retorna el elemento del tope de la pila
    public Object pop() throws Exception {
        if (isEmpty()) {
            throw new Exception("Error: Eliminar de una pila vacia");
        }
        
        // 1. Guarda el elemento del nodo del tope
        Object aux = top.getElement();
        
        // 2. Mueve 'top' al siguiente nodo (el que estaba debajo)
        //    (Se usa el atributo 'next' del Nodo actual)
        top = top.next;
        
        // 3. Retorna el elemento
        return aux;
    }

    //
    // Peek (Cima): Acceso al elemento del tope sin quitarlo
    public Object peek() throws Exception {
        if (isEmpty()) {
            throw new Exception("Error: Pila vacia");
        }
        // Retorna el elemento del tope
        return top.getElement();
    }
    
    //
    // Libera todos los nodos de la pila
    public void clear() {
        // Recorre la pila moviendo 'top' hasta que es null
        while (top != null) {
            top = top.next;
        }
        System.gc(); 
    }
}
