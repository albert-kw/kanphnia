
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

    }

    public int getSize() {
        return _todoList.size();
    }

} //end class Todo
