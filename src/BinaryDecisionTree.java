
import java.io.Serializable;
import java.util.Objects;


public class BinaryDecisionTree<K, V> implements Serializable {
    Question firstQuestion = new Question("Is it a thing that is or was once alive?", "Alive");
    MyBDTNode root = new MyBDTNode(firstQuestion);// root is initialized with the start question above
    MyBDTNode current;
    CategoryMap<K, V> TreeCategories = new CategoryMap<K, V>();


    public MyBDTNode getCurrent() {
        return current;
    }

    public void setCurrentToRoot(){
        this.current = this.root;
    }

    //this was from when I thought i couldn't test for null values, it just sets the roots yes or no if there not already
    //initialized
    public void initializeRoot(){
        if(root.getYes() == null){
            root.initializeYes();
        }
        if(root.getNo() == null){
            root.initializeNo();
        }
        this.current = this.root;
    }

    //this does the same for current
    public void initializeCurrent(){
        if(current.getYes() == null){
            current.initializeYes();
        }
        if(current.getNo() == null){
            current.initializeNo();
        }
    }

    //this returns a boolean if the current node is a leaf, it checks this by checking if its yes
    //and no nodes hold the default empty question
    public boolean isCurrentLeaf(){
        if(Objects.equals(this.current.getYes().getInfo().question, current.defaultEmpty.question) &&
                Objects.equals(this.current.getNo().getInfo().question, current.defaultEmpty.question)){
            return true;
        }
        return false;
    }

    //add a question to the map
    public void makeMapEntry(Question toAdd){
        TreeCategories.put(toAdd.subject, toAdd.question);
    }

    //this adds path method takes a question to add, and a boolean to determine to add the question down the yes or no
    //path that all nodes have
    public void addPath(Question newQuestion, Boolean answer){
        if(answer){
            current.getYes().setInfo(newQuestion);
        }else{
            current.getNo().setInfo(newQuestion);
        }
        makeMapEntry(newQuestion);
    }
}
