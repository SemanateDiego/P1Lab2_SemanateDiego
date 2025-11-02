package modelo;

public class Queue {
    protected Nodo front; // Referencia al frente (inicio) de la cola
    protected Nodo end;   // Referencia al final de la cola

    // Constructor: crea cola vacias
    public Queue() {
        front = end = null;
    }

    //
    // Metodo para verificar que la cola esta vacia
    public boolean isEmpty() {
        return front == null;
    }

    //
    // Insertar: pone elemento por el final 
    public void insert(Object element) {
        Nodo a = new Nodo(element); // Creación del nuevo nodo
        
        if(isEmpty()) {
            front = a;
            end = a;
        } else {
            // Se asume que la clase Nodo tiene un método setNext() para enlazar
            end.setNext(a); 
            end = a;
        }
    }

    //
    // Quitar: sale el elemento frente
    public Object remove() throws Exception {
        if (isEmpty()) {
            // Lanza la excepción si la cola está vacía, no hay nada que devolver
            throw new Exception("Error: Eliminar de una cola vacia");
        }
        
        // 1. Guarda el elemento del nodo que se va a eliminar
        Object aux = front.getElement(); // Corregido: asumimos que el campo en Nodo es 'element'
        
        // 2. Mueve 'front' al siguiente nodo
        front = front.next;
        
        // 3. Si al mover 'front' se vuelve nulo, significa que la cola quedó vacía,
        //    por lo que 'end' también debe ser nulo.
        if (front == null) {
            end = null;
        }
        
        // 4. Retorna el elemento
        return aux;
    }

    //
    // Libera todos los nodos de la cola
    public void deleteQueue() {
        // En lugar de un for con punto y coma (;), usamos un while
        while (front != null) {
            front = front.next;
        }
        end = null; // Es buena práctica asegurar que 'end' también sea null
        System.gc(); // Sugerencia de recolección de basura, aunque su uso es opcional
    }

    //
    // Acceso al primero de la cola 
    public Object frontQueue() throws Exception {
        if (isEmpty()) {
            throw new Exception("Error: cola vacia");
        }
        // Corregido: Asumimos que el atributo del nodo se llama 'element' (en minúsculas)
        return front.getElement(); 
    }
}
