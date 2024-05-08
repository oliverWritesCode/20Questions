
import java.io.Serializable;

public class MyBDTNode implements Serializable
{
    //this class stores a question info, and 2 others nodes for yes and no, as well as a default question
    //and getters and setters plus a constructor

    //this defualt empty question was made because I thought I couldn't check for null values without getting an error.
    public Question defaultEmpty = new Question("X", "X");
    protected MyBDTNode yes;
    protected MyBDTNode no;
    protected Question info;


    public MyBDTNode(Question info)
    {
        this.info = info;
        yes = null;
        no = null;
    }

    //this sets loads a nodes yes path with the default empty question
    public void initializeYes(){
        this.yes = new MyBDTNode(defaultEmpty);
    }

    //this does the same for the no path
    public void initializeNo(){this.no = new MyBDTNode(defaultEmpty);}

    //this does it to both
    public void initializeYesAndNo(){
        initializeYes();
        initializeNo();
    }

    //getters and setters
    public void setInfo(Question info){ this.info = info;}
    public Question getInfo(){ return info; }
    public void setYes(MyBDTNode toLink){
        this.yes = toLink;
    }
    public void setNo(MyBDTNode toLink){
        this.no = toLink;
    }
    public MyBDTNode getYes(){ return yes;}
    public MyBDTNode getNo(){ return no;}
}