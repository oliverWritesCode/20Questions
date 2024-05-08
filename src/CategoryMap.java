
import java.io.Serializable;

public class CategoryMap<K, V> extends ArrayListMap implements Serializable {

    //to string for printing map
    @Override
    public String toString() {
        return "CategoryMap{" +
                "map=" + map +
                '}';
    }
}
