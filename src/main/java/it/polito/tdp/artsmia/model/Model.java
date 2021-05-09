package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private Graph<ArtObject, DefaultWeightedEdge> grafo;     //defautWeightesEdge per specificare che gli archi sono pesati
	private ArtsmiaDAO dao;        //Serve importare il dao perchè altrimenti non posso fare le query
	//per la id Map
	private Map<Integer, ArtObject> idMap;
	
	
	public Model() {
		dao = new ArtsmiaDAO();   //Il dao si inizializza nel Model
		idMap = new HashMap <Integer, ArtObject> ();
	}
	
	public void creaGrafo() {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);  //grafo semplice, pesato e non orientato 
		
		/**
		 * PRIMA COSA DA FARE:
		 * Aggiungere i vertici (se ho delle richiesta particolari aggiungo dei parametri
		 *                       al metodo creaGrafo(). In questo caso però aggiungerò tutti i vertici quindi non 
		 *                       aggiungo nessun parametro)
		 * 1. --> Recupero tutti gli ArtObject dal db
		 * 2. --> Li inserisco come vertici
		 *                       
		 */
		dao.listObjects(idMap);    
		Graphs.addAllVertices(grafo, idMap.values());    //ATTENZIONE : la classe usata ora è Graphs
		
		/**
		 * E' utile utilizzare una Identity Map:
		 * E' una HashMap che salva al suo interno l'id  di un oggetto e l'oggetto stesso
		 * Questa mappa viene riempita la prima volta e poi riutilizzata 
		 * Qunddi una volta che questa è stata utilizzata la prima volta quando mi servono degli oggetti li andrò a prendere da lì
		 * VEDI SOPRA
		 */
		
		/**
		 * AGGIUNGERE GLI ARCHI
		 * Esistono 3 approcci per creare gli archi:  1 più facile concettualmente ma più difficile dal punto di vista computazionale
		 *                                            2 più complessi concettualmente ma più facili dal punto di vista computazionale 
		 * 
		 * Primo approccio:
		 *          Facciamo fare meno operazioni al db ma facciamo noi più operazioni
		 *          Come si fa?
		 *          Con un doppio ciclo for annidato vado a confrontare ognni coppia possibile di vertici. 
		 *          Per ogni coppia di vertii vado a verificare se questi devono essere collegati e se si, con quale peso.
		 *          
		 *      NON FUNZIONA PERCHE' CI SONO TROPPI VERTICI                                               
		 */
		
		/*for(ArtObject a1 : this.grafo.vertexSet()) {
			for(ArtObject a2 : this.grafo.vertexSet()) {
				if(!a1.equals(a2) &&          //controllo di non confrontare lo stesso vertice(non ha senso collegare lo stesso vertice con se stesso)
						!this.grafo.containsEdge(a1, a2)) {    //controllo che non esiste già un arco tra i due vertici considerati
					//devo collegare a1 ad a2??
					//Siccome nel testo si dice di collegare i vertici solo se questi rappresentano opere che sono state esposte insieme almeno una volta
					// vuol dire che il "peso" di questi archi deve essee almeno 1 per crearlo
					int peso = dao.getPeso(a1, a2);
					if(peso > 0) {
						Graphs.addEdge(this.grafo, a1, a2, peso);    //metodo per aggiungere un arco al grafo pesato
					}
					
				}
			}
			
			
		}
		System.out.println("GRAFO CREATO");
		System.out.println("# VERTICI " + grafo.vertexSet().size());
		System.out.println("# ARCHI " + grafo.edgeSet().size());            */
		
		
		/**
		 * Secondo approccio:
		 * si blocca uno oggetto e controllo gli oggetti che sono a lui collegati
		 * 
		 * ANCORA TROPPO LENTO
		 */
		
		
		
		/**
		 * APPROCCIO 3 -> MIGLIORE
		 */
		
		for(Adiacenza a : dao.getAdiacenze()) {
			if(a.getPeso() > 0) {     //questa if è inutile perchè nella tabella non abbiamo le coppie per cui il peso e' 0
				Graphs.addEdge(this.grafo, idMap.get(a.getId1()), idMap.get(a.getId2()), a.getPeso());
			}
		}
		System.out.println("GRAFO CREATO");
		System.out.println("# VERTICI " + grafo.vertexSet().size());
		System.out.println("# ARCHI " + grafo.edgeSet().size());  

	}

}
