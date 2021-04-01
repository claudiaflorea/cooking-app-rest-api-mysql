package com.practice.cooking;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import collections.linkedList.CircularLinkedList;
import collections.linkedList.DoublyLinkedList;
import collections.linkedList.SinglyLinkedList;
import com.practice.cooking.model.Chef;
import com.practice.cooking.model.Difficulty;
import com.practice.cooking.model.Ingredient;
import com.practice.cooking.model.Recipe;
import com.practice.cooking.model.RecipeType;
import com.practice.cooking.model.Unit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class CookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CookingApplication.class, args);

        System.out.println("********************************************");
        System.out.println("RUNNING");
        System.out.println("********************************************");

        /********* Set Interface *******/

        //HashSet  allows null values, does not allow duplicates
        Set<Ingredient> ingredientHashSet = new HashSet<>();
        ingredientHashSet.add(new Ingredient(1L, "Garlic", 1, Unit.PIECE));
        ingredientHashSet.add(new Ingredient(2L, "Onion", 2, Unit.PIECE));
        ingredientHashSet.add(new Ingredient(3L, "Tomato sauce", 0.2, Unit.LITER));
        ingredientHashSet.add(new Ingredient(4L, "Chicken breast", 1, Unit.KG));
        ingredientHashSet.add(new Ingredient(4L, "Chicken breast", 1, Unit.KG));
        ingredientHashSet.add(new Ingredient(4L, "Chicken breast", 1, Unit.KG));
        ingredientHashSet.add(null);
        System.out.println("HashSet size: " + ingredientHashSet.size() + " " + ingredientHashSet);
        System.out.println("********************************************");

        //LinkedHashSet allows null values, but does not allow duplicates
        Set<Ingredient> ingredientLinkedHashSet = new LinkedHashSet<>();
        ingredientLinkedHashSet.add(new Ingredient(1L, "Basil", 0.001, Unit.KG));
        ingredientLinkedHashSet.add(new Ingredient(2L, "Shrimps", 1, Unit.KG));
        ingredientLinkedHashSet.add(new Ingredient(3L, "Butter", 0.2, Unit.KG));
        ingredientLinkedHashSet.add(new Ingredient(4L, "White wine", 0.1, Unit.LITER));
        ingredientLinkedHashSet.add(new Ingredient(4L, "White wine", 0.1, Unit.LITER));
        ingredientLinkedHashSet.add(new Ingredient(4L, "White wine", 0.1, Unit.LITER));
        ingredientLinkedHashSet.add(null);
        System.out.println("LinkedHashSet size: " + ingredientLinkedHashSet.size() + " " + ingredientLinkedHashSet);
        System.out.println("********************************************");

        //TreeSet does not allow neither duplicates not null values
        //implements SortedSet
        Set<Ingredient> ingredientTreeSet = new TreeSet<>();
        ingredientTreeSet.add(new Ingredient(1L, "Sour cream", 0.1, Unit.LITER));
        ingredientTreeSet.add(new Ingredient(2L, "Parsley", 2, Unit.PIECE));
        ingredientTreeSet.add(new Ingredient(3L, "Parmesan", 0.2, Unit.KG));
        ingredientTreeSet.add(new Ingredient(4L, "Egg", 1, Unit.PIECE));
        System.out.println("TreeSet: " + ingredientTreeSet);
        System.out.println("********************************************");

        //SinglyLinkedList
        SinglyLinkedList singlyLinkedList = new SinglyLinkedList();
        singlyLinkedList.head = new SinglyLinkedList.Node(1);
        SinglyLinkedList.Node second = new SinglyLinkedList.Node(2);
        SinglyLinkedList.Node third = new SinglyLinkedList.Node(3);
        SinglyLinkedList.Node fourth = new SinglyLinkedList.Node(4);
        singlyLinkedList.head.next = second;
        second.next = third;
        third.next = fourth;
        singlyLinkedList.display();

        //DoublyLinkedList
        DoublyLinkedList doublyLinkedList = new DoublyLinkedList();
        doublyLinkedList.addNode(1);
        doublyLinkedList.addNode(2);
        doublyLinkedList.addNode(3);
        doublyLinkedList.addNode(4);
        doublyLinkedList.display();
        System.out.println("********************************************");

        //CircularLinkedList
        CircularLinkedList circularLinkedList = new CircularLinkedList();
        circularLinkedList.addNode(1);
        circularLinkedList.addNode(2);
        circularLinkedList.addNode(3);
        circularLinkedList.addNode(4);
        circularLinkedList.display();
        System.out.println("********************************************");

        /*************** Map Interface ***************/

        //HashMap allows duplicates and null values
        HashMap<Recipe, List<Ingredient>> recipeIngredientHashMap = new HashMap<>();
        recipeIngredientHashMap.put(
            new Recipe(1L, "Milkshake", Difficulty.EASY, null, 1, RecipeType.DESSERT),
            Arrays.asList(
                new Ingredient(1L, "Milk", 1, Unit.LITER),
                new Ingredient(2L, "Banana", 2, Unit.PIECE))

        );
        recipeIngredientHashMap.put(
            new Recipe(1L, "Ice-cream", Difficulty.EASY, null, 4, RecipeType.DESSERT),
            Arrays.asList(
                new Ingredient(1L, "Yogurt", 1, Unit.KG),
                new Ingredient(2L, "Strawberry", 1, Unit.KG)
            )
        );
        recipeIngredientHashMap.put(
            new Recipe(1L, "Cesar salad", Difficulty.EASY, null, 1, RecipeType.ANTRE),
            null
        );
        recipeIngredientHashMap.put(
            null,
            Arrays.asList(
                new Ingredient(1L, "Mascarpone cheese", 1, Unit.KG),
                new Ingredient(2L, "Egg", 3, Unit.PIECE)
            )
        );
        recipeIngredientHashMap.put(
            new Recipe(1L, "Cesar salad", Difficulty.EASY, null, 1, RecipeType.ANTRE),
            null
        );
        System.out.println("HashMap: " + recipeIngredientHashMap);
        Iterator<Map.Entry<Recipe, List<Ingredient>>> recipeListIterator = recipeIngredientHashMap.entrySet().iterator();
        while (recipeListIterator.hasNext()) {
            Map.Entry<Recipe, List<Ingredient>> element = recipeListIterator.next();
            System.out.println("HashMap iterator .... Element with key "+ element.getKey() + " and value " + element.getValue());
        }
        System.out.println("********************************************");

        //TreeMap does not allow neither duplicates nor null values
        TreeMap<Recipe, List<Ingredient>> recipeListTreeMap = new TreeMap<>();
        recipeListTreeMap.put(
            new Recipe(1L, "Tuna salad", Difficulty.MEDIUM, null, 1, RecipeType.ANTRE),
            Arrays.asList(
                new Ingredient(1L, "Chunked tuna", 0.1, Unit.KG),
                new Ingredient(2L, "Maionese", 0.01, Unit.KG),
                new Ingredient(2L, "Onion", 1, Unit.PIECE)
            )
        );
        recipeListTreeMap.put(
            new Recipe(1L, "Pancakes", Difficulty.MEDIUM, null, 1, RecipeType.DESSERT),
            Arrays.asList(
                new Ingredient(1L, "Flour", 1, Unit.KG),
                new Ingredient(2L, "Milk", 0.5, Unit.LITER),
                new Ingredient(2L, "Egg", 3, Unit.PIECE)
            )
        );
        System.out.println("TreeMap: " + recipeListTreeMap);
        Iterator<Map.Entry<Recipe, List<Ingredient>>> recipeListTreeMapIterator = recipeListTreeMap.entrySet().iterator();
        while (recipeListTreeMapIterator.hasNext()) {
            Map.Entry<Recipe, List<Ingredient>> element = recipeListTreeMapIterator.next();
            System.out.println("TreeMap iterator ..... KEY: " + element.getKey() + " AND VALUE: " + element.getValue());
        }
        System.out.println("********************************************");

        //LinkedHashMap allows null values, but does not allow duplicates
        LinkedHashMap<Recipe, List<Ingredient>> recipeLinkedHashMap = new LinkedHashMap<>();
        recipeLinkedHashMap.put(
            new Recipe(1L, "Tzaziki", Difficulty.EASY, null, 1, RecipeType.SIDE),
            Arrays.asList(
                new Ingredient(1L, "Sour cream", 0.02, Unit.KG),
                new Ingredient(2L, "Dill", 0.01, Unit.KG)
            )
        );
        recipeLinkedHashMap.put(
            new Recipe(1L, "Pouched egg", Difficulty.MEDIUM, null, 1, RecipeType.MAIN_COURSE),
            Arrays.asList(
                new Ingredient(1L, "Egg", 1, Unit.PIECE)
            )
        );
        recipeLinkedHashMap.put(
            new Recipe(1L, "Pouched egg", Difficulty.MEDIUM, null, 1, RecipeType.MAIN_COURSE),
            Arrays.asList(
                new Ingredient(1L, "Egg", 1, Unit.PIECE)
            )
        );
        recipeLinkedHashMap.put(
            null,
            Arrays.asList(
                new Ingredient(1L, "Lemon juice", 0.02, Unit.LITER),
                new Ingredient(2L, "Water", 2, Unit.LITER),
                new Ingredient(3L, "Sugar", 0.02, Unit.KG)
            )
        );
        System.out.println("LinkedHashMap: size " + recipeListTreeMap.size() + "  " + recipeLinkedHashMap);
        Iterator<Map.Entry<Recipe, List<Ingredient>>> recipeListIterator2 = recipeListTreeMap.entrySet().iterator();
        while (recipeListIterator2.hasNext()) {
            Map.Entry<Recipe, List<Ingredient>> element = recipeListIterator2.next();
            System.out.println("LinkedHashMap iterator..... Element with key " + element.getKey() + " and value " + element.getValue());
        }
        
        System.out.println("********************************************");

        /**************** List Interface **************/

        //LinkedList allows duplicates and null values
        LinkedList<Chef> chefs = new LinkedList<>();
        chefs.add(new Chef(1L, "Christian"));
        chefs.add(new Chef(2L, "Boris"));
        chefs.add(new Chef(3L, "Jack"));
        chefs.add(new Chef(4L, "Fred"));
        chefs.add(new Chef(4L, "Fred"));
        chefs.add(null);
        System.out.println("LinkedList: " + chefs);
        System.out.println("********************************************");

        //Vector allows duplicates and null values
        Vector<Chef> chefVector = new Vector<>();
        chefVector.add(new Chef(1L, "Mason"));
        chefVector.add(new Chef(2L, "David"));
        chefVector.add(new Chef(3L, "Clark"));
        chefVector.add(new Chef(4L, "Adam"));
        chefVector.add(new Chef(5L, "Lopez"));
        chefVector.add(new Chef(5L, "Lopez"));
        chefVector.add(null);
        System.out.println("Vector: " + chefVector);
        System.out.println("********************************************");

        //Stack allows duplicates and null values
        Stack<Chef> chefStack = new Stack<>();
        chefStack.add(new Chef(1L, "Rodrigo"));
        chefStack.add(new Chef(2L, "Lorenzo"));
        chefStack.add(new Chef(3L, "Miquel"));
        chefStack.add(new Chef(4L, "Fernando"));
        chefStack.add(new Chef(4L, "Fernando"));
        chefStack.add(null);
        System.out.println("Stack: " + chefStack);
        System.out.println("********************************************");

        //ArrayList allows duplicates and null values
        ArrayList<Chef> chefArrayList = new ArrayList<>();
        chefArrayList.add(new Chef(1L, "Carlos"));
        chefArrayList.add(new Chef(2L, "Quinn"));
        chefArrayList.add(new Chef(3L, "Smith"));
        chefArrayList.add(new Chef(4L, "Yakub"));
        chefArrayList.add(new Chef(4L, "Klein"));
        chefArrayList.add(new Chef(4L, "Klein"));
        chefArrayList.add(null);
        chefArrayList.add(null);
        System.out.println("ArrayList: " + chefArrayList);
        System.out.println("********************************************");

        /*********** Queue interface ********/

        //PriorityQueque does not allow null values, but allows duplicates
        PriorityQueue<Chef> chefPriorityQueue = new PriorityQueue<>();
        chefPriorityQueue.add(new Chef(1L, "Jacob"));
        chefPriorityQueue.add(new Chef(2L, "Michael"));
        chefPriorityQueue.add(new Chef(3L, "Borat"));
        chefPriorityQueue.add(new Chef(4L, "Andrew"));
        chefPriorityQueue.add(new Chef(5L, "Luchian"));
        chefPriorityQueue.add(new Chef(5L, "Luchian"));
        System.out.println("PriorityQueue: " + chefPriorityQueue);
        System.out.println("********************************************");

        //ArrayDeque does not allow null values, but allows duplicates
        ArrayDeque<Chef> arrayDeque = new ArrayDeque<>();
        arrayDeque.add(new Chef(1L, "Gillbert"));
        arrayDeque.add(new Chef(2L, "Laura"));
        arrayDeque.add(new Chef(3L, "Dana"));
        arrayDeque.add(new Chef(4L, "Eva"));
        arrayDeque.add(new Chef(5L, "Eva"));
        arrayDeque.addFirst(new Chef(6L, "Diana"));
        arrayDeque.addLast(new Chef(7L, "North"));
        System.out.println("ArrayDeque: " + arrayDeque);
    }

    /**
     * Conclusions
     *
     * TreeSet, TreeMap, PriorityQueue and ArrayDeque do NOT ALLOW NULL VALUES (because they implement Comparable)
     * TreeSet, HashSet, LinkedHashSet, LinkedHashMap and TreeMap do NOT ALLOW DUPLICATE VALUES
     *
     *
     * Set interface : cannot access an element from the set. only the first and last element
     * List interface: can access any element by its index 
     * Queue interface: cannot access an element by its index. only the first and last element. Usually stores elements in FIFO order
     * DeQue interface: is similar to Queue, but allows access, addition, and remove at both ends
     *
     */
    
}
