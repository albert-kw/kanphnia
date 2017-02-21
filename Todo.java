
import java.util.List;
import java.util.ArrayList;

public class Todo {

    private static ArrayList<String> _todoList;
    public ArrayList<String> getList() {
        return _todoList;
    }

    private static String _name;
    public String getName() {
        return _name;
    }

    private Todo(){}

    public Todo (String name) {
        _todoList = new ArrayList<String>();
        _name = name;

    } //end ctor (String)

    public int getSize() {
        return _todoList.size();
    } //end int getSize

    public boolean hasItem (String item) {
        if (_todoList.contains (item)) {
            return true;
        }

        return false;

    } //end bool hasItem (String)

    public String toString() {
        String content = "";
        for (int i=0; i < getSize(); i++) {
            content += _todoList.get (i);

            if (i != getSize()-1) {
                content += System.getProperty ("line.separator");
            }
        }

        return content;

    } //end String toString

} //end class Todo
