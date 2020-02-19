package es.studium;
import java.util.Scanner;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;

public class HarryPotter {

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			int num;
			do {
				//Menú de usuario
				 System.out.println("------------------------------------------------------------");
				 System.out.println("1) Mostrar personajes humanos ");
			     System.out.println("2) Mostrar personajes que nacieron antes de 1979 ");
			     System.out.println("3) Mostrar personajes que tengan varita mágica de tipo 'Holly' ");
			     System.out.println("4) Mostrar estudiantes de Hogwarts que estén vivos ");
			     System.out.println("5) Salir del programa");
			     System.out.println("------------------------------------------------------------");
			     num = sc.nextInt();
			     switch (num) {
			     	//Si el usuario elige 1
			        case 1: {
			        	pjHumanos();
			            break;
			        }
			      //Si el usuario elige 2
			        case 2: {
			        	pj1979();
			            break;
			        }
			      //Si el usuario elige 3
			        case 3: {
			        	pjHollyWoodWand();
			            break;
			        }
			      //Si el usuario elige 4
			        case 4: {
			        	pjHogwartsVivos();
			            break;
			        }
			      //Si el usuario elige 5
			        case 5: {
			            System.out.println("Fin del Programa");
			            break;
			        }
			    }
			} while (num != 5);
		}
    }
	//Función para mostrar personajes humanos
    private static void pjHumanos() {
        MongoClient con = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = con.getDatabase("harrypotter");
        MongoCollection<?> data = database.getCollection("data");
        FindIterable<?> res = data.find(eq("species", "human")).
        		projection(fields(include("name", "species"),excludeId()));
        int i=0;
        for (Object o : res) {
            System.out.println(((Document) o).toJson());
            i++;
        }
        System.out.println("\n-----------------  TOTAL: "+i+" REGISTROS  -----------------");
        con.close();
    }
    
    //Función para mostrar personajes con fecha de nacimiento anterior a 1979
    private static void pj1979() {
        MongoClient con = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = con.getDatabase("harrypotter");
        MongoCollection<?> data = database.getCollection("data");
        FindIterable<?> res = data.find(lt("yearOfBirth", 1979))
                .projection(fields(include("name", "yearOfBirth"), excludeId()));
        int i=0;
        for (Object o : res) {
            System.out.println(((Document) o).toJson());
            i++;
        }
        System.out.println("\n-----------------  TOTAL: "+i+" REGISTROS  -----------------");
        con.close();
    }
    //Función para mostrar personajes con varita de madera
    private static void pjHollyWoodWand() {
        MongoClient con = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = con.getDatabase("harrypotter");
        MongoCollection<?> data = database.getCollection("data");
        FindIterable<?> res = data.find(eq("wand.wood", "holly"))
                .projection(fields(include("name", "wand.wood"), excludeId()));
        int i=0;
        for (Object o : res) {
            System.out.println(((Document) o).toJson());
            i++;
        }
        System.out.println("\n-----------------  TOTAL: "+i+" REGISTROS  -----------------");
        con.close();

    }
    //Función para mostrar personajes vivos y que hayan estudiado en Hogwarts
    private static void pjHogwartsVivos() {
        MongoClient con = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = con.getDatabase("harrypotter");
        MongoCollection<?> data = database.getCollection("data");
        FindIterable<?> res = data.find(and(eq("alive", true),eq("hogwartsStudent", true)))
                .projection(fields(include("name", "alive", "hogwartsStudent"), excludeId()));
        int i=0;
        for (Object o : res) {
            System.out.println(((Document) o).toJson());
            i++;
        }
        System.out.println("\n-----------------  TOTAL: "+i+" REGISTROS  -----------------");
        con.close();
    }
}
