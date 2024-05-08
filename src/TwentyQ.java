
import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class TwentyQ {
    BinaryDecisionTree QuestionTree = new BinaryDecisionTree();

    //this just asks the user if they want to play and returns the int that is their answer
    public int menu(){
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter 1 to play the game and 2 to quit the game");
        return userInput.nextInt();
    }

    //this asks the user if they want to keep playing and returns the int that is their answer
    public int askToContinue(){
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter 1 to play the game again and keep training the game\n" +
                "or 2 to quit the game and sentence it to ignorance. The choice is yours.");
        return userInput.nextInt();
    }

    //this method is called when the program has determined that the current node that it is on is a leaf
    //this method is about getting new questions from the player
    //depending on whether it is a leaf that holds a valid question or the default empty question
    //it will either get a new question from the user and make a new path and node for that question,
    //or it will add that question to the current node that had the default empty question
    //this is also where the user has the ability to say that the program has guessed the final answer of what they were thinking of
    //in this case the program will terminate
    public void learn2(){
        if(!Objects.equals(QuestionTree.getCurrent().getInfo().question, QuestionTree.getCurrent().defaultEmpty.question)){
            int askInt = this.askReturnInt();
            boolean answer;
            if(askInt == 1){
                answer = true;
            }else if(askInt == 2){
                answer = false;
            }else{
                answer = false;
                System.out.println("Game over. I have defeated you and your primitive attempts to thwart me with my extreme intelligence.");
                System.exit(0);
            }
            Scanner userInput2 = new Scanner(System.in);
            System.out.println("Please enter a new question that will help me play better in the future:");
            String newQuestionString = userInput2.nextLine();
            System.out.println("Please enter a subject that fits that question:");
            String newSubject = userInput2.nextLine();
            Question newQuestion = new Question(newQuestionString, newSubject);

            QuestionTree.addPath(newQuestion, answer);
        }else{

            Scanner userInput3 = new Scanner(System.in);
            System.out.println("Please enter a new question that will help me play better in the future:");
            String newQuestionString = userInput3.nextLine();
            System.out.println("Please enter a subject that fits that question:");
            String newSubject = userInput3.nextLine();
            Question newQuestion = new Question(newQuestionString, newSubject);
            QuestionTree.getCurrent().setInfo(newQuestion);
            QuestionTree.getCurrent().initializeYesAndNo();
            QuestionTree.makeMapEntry(newQuestion);
        }

    }

    //this method has a loop that checks if the current node is a leaf, if it isnt, than it asks currents question
    //and then goes down the appropriate path, yes or no, based on the users input, then the loop repeats
    //if it does end up on a leaf, the loop ends, and it calls the learn function, so that it can either receive
    //a new question from the user or end the game
    public void play(){
        deserializeTree();
        QuestionTree.current = QuestionTree.root;
        while(!QuestionTree.isCurrentLeaf()){
            Boolean answer = this.ask();
            if(answer){
                QuestionTree.current = QuestionTree.current.getYes();
                QuestionTree.initializeCurrent();
            }else{
                QuestionTree.current = QuestionTree.current.getNo();
                QuestionTree.initializeCurrent();
            }
        }
        //this.learn();
        this.learn2();
        this.serializeTree(QuestionTree);
    }

    //this is very similar to play exept it initializes the variables, doesnt deserialize the tree becuase on first
    //play that is an option that the user hsa to choose, and it adds the first question that comes with the program
    //to the category map
    public void initializePlay(){
        QuestionTree.initializeRoot();
        QuestionTree.initializeCurrent();
        QuestionTree.makeMapEntry(QuestionTree.firstQuestion);
        while(!QuestionTree.isCurrentLeaf()){
            Boolean answer = this.ask();
            if(answer){
                QuestionTree.current = QuestionTree.current.getYes();
                QuestionTree.initializeCurrent();
            }else{
                QuestionTree.current = QuestionTree.current.getNo();
                QuestionTree.initializeCurrent();
            }
        }
        //this.learn();
        this.learn2();
        this.serializeTree(QuestionTree);
    }

    //this method just takes a tree that it will serialize and save to TwentQ.ser
    public void serializeTree(BinaryDecisionTree toSerialize){
        try {
            FileOutputStream fileOut = new FileOutputStream("TwentyQ.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(toSerialize);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    //and this method just sets the one tree the program has, QuestionTree, to be whatever tree was saved to a file
    public void deserializeTree(){
        try {
            FileInputStream fileIn = new FileInputStream("TwentyQ.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            QuestionTree = (BinaryDecisionTree) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Object not found");
            c.printStackTrace();
        }

    }


    //this just offers the user to load in the old tree from the file
    public void offerToLoadTree(){
        Scanner userInput3 = new Scanner(System.in);
        System.out.println("To load in the question tree, enter 1, or enter any other number to start from scratch: ");
        int choice = userInput3.nextInt();
        if(choice == 1){
            this.deserializeTree();
        }
    }


    //this method asks in the current node and gets an answer, yes or no, from the user
    //it then returns a boolean for their answer which is used to determine which node will become the next current
    //this version of ask is only called when current is not a leaf
    public Boolean ask(){
        Scanner userInput4 = new Scanner(System.in);
        System.out.println(QuestionTree.current.getInfo().question);
        System.out.println("\nEnter 1 for yes or any other integer for no:");
        int answer = userInput4.nextInt();
        if(answer == 1){
            return true;
        }
        return false;
    }


    //this version of ask is similar but is used when current is a leaf, and it gives the user the oportunity to say
    //that the program guessed their final thing, becuase there are 3 options it returns an int
    public int askReturnInt(){
        Scanner userInput4 = new Scanner(System.in);
        System.out.println(QuestionTree.current.getInfo().question);
        System.out.println("\nEnter 1 for yes , 2 for no, or any other integer if this is the exact thing you thought of:");
        int answer = userInput4.nextInt();
        return answer;
    }


    //this asks the user if they want to print a map
    public void printMap(){
        Scanner userInput4 = new Scanner(System.in);
        System.out.println("Enter 1 to print out the map of tree categories or any other number to not do that:");
        int choice = userInput4.nextInt();
        if(choice == 1){
            System.out.println(QuestionTree.TreeCategories.toString());
        }
    }


    //this is the final method, which asks the user if they want to play, do they want to load the tree,
    //do they want to print the map, and then it initializes play, and calls the play function in a loop until the user
    //says that they don't want to play anymore
    public void playGame(){
        int choice = menu();
        if(choice == 1){
            this.offerToLoadTree();
            this.printMap();
            System.out.println("Think of a thing and the game will try to guess it, you will help it learn");
            initializePlay();
            choice = askToContinue();
        }
        while(choice == 1){
            play();
            choice = askToContinue();
        }
        System.out.println("I see.");
    }


}
