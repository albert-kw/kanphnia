
import java.util.List;
import java.util.ArrayList;

import java.io.IOException;
import java.io.FileNotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class TodoMain {

    private static Todo _todoList;
    private static String _key;

    private static final String DEFAULT_DIR = "todo";
    private static File _dir = new File (DEFAULT_DIR);

    private static String DIR = "";
    private static String FILENAME = "";

    private static File _file;

    private static boolean _active = false;

    public TodoMain (String name) {
        if (!_dir.exists()) {
            _dir.mkdir();
        }

        FILENAME = name;
        _file = new File (DEFAULT_DIR + File.separator + FILENAME);

        _todoList = new Todo (name);
        System.out.printf ("\n'%s' To-do List made\n", _todoList.getName());
        System.out.flush();

        /** @Debug *
        System.out.print ("Absolute Filepath: " + _file.getAbsolutePath() +
            "\n");
        */

    } //end ctor (String)

    public static void main (String[] args) {

        new TodoMain (TodoUtil.prompt ("Enter a name for this list: "));
        _active = true;

        _key = TodoUtil.prompt ("Enter a password to be used: ");

        if (_file.exists()) {

            /*
            System.out.format ("\nA previously saved Todo List has been " +
                "found!\nLoading '%s' list...\n\n", _todoList.getName());
            System.out.flush();
            */

            System.out.print ("\nA previously saved Todo List has been " +
                "found!\nLoading '" + _todoList.getName() + "' list...\n\n");

            load();
        }

        while (_active) {
            decide();
        }

    } //end main

    private static void decide() {
        String item = null;

        help();
        switch (TodoUtil.prompt ("\nChoose an option... ")) {

        case "a":
        case "add":
            item = TodoUtil.prompt ("Enter a to-do item to add to list... ");
            addItem (item);
            break;

        case "r":
        case "remove":
            item = TodoUtil.prompt ("Enter a to-do item to remove from the list... ");
            removeItem (Integer.parseInt (item));
            break;

        case "f":
        case "find":
            item = TodoUtil.prompt ("Enter a to-do item to find from the list... ");
            findItem (item);
            break;

        case "n":
        case "name":
            getName();
            break;

        case "l":
        case "list":
            listItem();
            break;

        case "save":
            save();
            break;

        case "load":
            load();
            break;

        /*
        case "h":
        case "help":
            help();
            break;
        */

        case "q":
        case "quit":
            _active = false;
            break;

        default:
            //help();

        }

    } //end prv stc void decide

    private static void addItem (String item) {
        if (!_todoList.getList().contains (item)) {
            if (_todoList.getList().add (item)) {
                System.out.printf ("\tItem '%s' added successfully to the " +
                    "list.\n", item);
                System.out.flush();
            }
        } else {
            System.out.printf ("Item '%s' already in the list.\n", item);
            System.out.flush();
        }
    } //end prv stc void addItem (String)

    /** @Deprecated *
    private static void removeItem (String item) {
        if (_todoList.getList().contains (item)) {
            if (_todoList.getList().remove (item)) {
                System.out.print ("Item '" + item + "' removed from the list.\n");
            }
        } else {
            System.out.print ("Item '" + item + "' not found from the list.\n");
        }
    } //end prv stc void removeItem (String)
    */

    private static void removeItem (int itemID) {
        if (itemID < 1 || itemID > _todoList.getList().size()) {

            System.out.print ("Cannot remove item with ID '" +
                itemID + "' that does not exist.\n");

        } else {

            String removedItem = _todoList.getList().remove (itemID-1);

            System.out.print ("Removed item '" + removedItem +
                "' with ID '" + itemID + "'.\n");
        }

    } //end prv stc void removeItem (int)

    private static void findItem (String item) {

        boolean found = false;
        int numFound = 0;

        System.out.print ("\n");

        for (String i : _todoList.getList()) {
            if (i.contains (item)) {

                found = true;
                numFound++;

                System.out.print ("ID: " + _todoList.getList().indexOf (i) +
                    "\t" + "Task: '" + i + "' found in the list.\n");
            }
        }

        System.out.print ("Found '" + numFound + "' item(s) in list.\n");

    } //end prv stc void findItem (String)

    private static void getName() {
        System.out.print ("This Todo List's name is: '" +
            _todoList.getName() + "'.\n");

    } //end prv stc void getName

    private static void listItem() {
        if (_todoList.getList().size() == 0) {
            System.out.print ("There are no items in this list.\n");

        } else {
            System.out.print ("There are " + _todoList.getSize() +
                " items in this list.\n\n");

            for (int i=0; i < _todoList.getSize(); i++) {
                System.out.print ("ID: " + (i+1) + "\t" + "Task: " +
                    _todoList.getList().get (i) + "\n");
            }

        }
    } //end prv stc void listItem

    private static void help() {
        System.out.print (
            "\nUsage:\n" +
            "(a)dd\t\t\tadd a to-do item to the list.\n" +
            "(r)emove\t\tremove a to-do item from the list.\n" +
            "(f)ind\t\t\tfind a to-do item and show its contents.\n" +
            "(n)ame\t\t\tprint current Todo List's name.\n" +
            "(l)ist\t\t\tlist all to-do items in the list.\n" +
            "save\t\t\tsave current list to a file.\n" +
            "load\t\t\tload file into list.\n" +
            "(q)uit\t\t\tquits this program.\n"
        );
    } //end prv stc void help

    private static void save() {
        if (_todoList.getSize() > 0) {
            try {

                if (!_file.exists()) {
                    _file.createNewFile();
                }

                byte listContent[] = listToString().getBytes();

                FileOutputStream fileOutputStream = new FileOutputStream (_file);
                fileOutputStream.write (TodoUtil.encrypt (_key, listContent));

                fileOutputStream.close();

                System.out.printf ("Done saving to file '%s'\n", FILENAME);
                System.out.flush();

            } catch (IOException ioException) {
                System.out.print ("error saving to a file.\n");

            }

        } else {
            System.out.print ("Nothing to be saved.\n");
        }
    } //end prv stc void save

    private static void load() {
        _todoList.getList().clear();

        try {
            byte[] byteInput = new byte[(int) _file.length()];

            FileInputStream fileInputStream = new FileInputStream (_file);
            fileInputStream.read (byteInput);

            String content = new String (TodoUtil.decrypt (_key, byteInput));

            for (String i : content.split (System.getProperty ("line.separator"))) {
                if (!i.isEmpty() || !i.trim().isEmpty() || !i.equals ("")) {
                    /** @Debug *
                    System.out.printf ("Loaded and going to add: '%s'\n", i);
                    System.out.flush();
                    */

                    addItem (i);
                }
            }

            fileInputStream.close();

        } catch (Exception e) {
            System.out.print ("Cannot read the file.\n");
        }

        System.out.print ("\n");
        listItem();

    } //end prv stc void load

    private static String listToString() {
        String content = "";
        for (int i=0; i < _todoList.getSize(); i++) {
            content += _todoList.getList().get (i);

            if (i != _todoList.getSize()-1) {
                content += System.getProperty ("line.separator");
            }
        }

        return content;

    } //end prv stc String toString

} //end class TodoMain
