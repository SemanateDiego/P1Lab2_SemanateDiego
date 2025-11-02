package modelo;

public class Nodo {
	private Object element;
	Nodo next;
	
	//Constructor que recibe un elemento
	public Nodo(Object element) {
		this.element = element;
		this.next = null;
	}
	
	//Constructor por defecto
	public Nodo(Object element, Nodo next) {
		this.element = element;
		this.next = next;
	}
	
	//Metodos get y set opcionales
	public Object getElement() {
		return element;
	}
	public void setElement(Object element) {
		this.element = element;
	}
	public Nodo getNext() {
		return next;
	}
	public void setNext(Nodo next) {
		this.next = next;
	}
}
