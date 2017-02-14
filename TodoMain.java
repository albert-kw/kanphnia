
import java.util.List;
import java.util.ArrayList;

import java.io.IOException;
import java.io.FileNotFoundException;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class TodoMain {

    private static Todo _todoList;
    private static FileWriter fileWriter;

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
        System.out.printf ("\n'%s' To-do List made\n",
            _todoList.getName());

        /** @Debug *
        System.out.print ("Absolute Filepath: " + _file.getAbsolutePath() +
            "\n");
        */

        if (_file.exists()) {

            System.out.print ("A previously saved Todo List has been found!\n" +
                "Loading '" + name + "' list...\n");

            load();
        }

    } //end ctor (String)

    public static void main (String[] args) {

        new TodoMain (TodoUtil.prompt ("Enter a name for this list: "));
        _active = true;

        while (_active) {
            decide();
        }

    } //end main

    private static void decide() {
        String item = null;

        help();
        switch (TodoUtil.prompt ("Choose an option... ")) {

        case "a":
        case "add":
            item = TodoUtil.prompt ("Enter a to-do item to add to list... ");
            addItem (item);
            break;

        case "r":
        case "remove":
            item = TodoUtil.prompt ("Enter a to-do item to remove from the list... ");
            removeItem (item);
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
                System.out.printf ("\tItem '%s' added successfully to the list.\n",
                    item);
            }
        } else {
            System.out.print ("Item '" + item + "' already in the list.\n");
        }
    } //end prv stc void addItem (String)

    private static void removeItem (String item) {
        if (_todoList.getList().contains (item)) {
            if (_todoList.getList().remove (item)) {
                System.out.print ("Item '" + item + "' removed from the list.\n");
            }
        } else {
            System.out.print ("Item '" + item + "' not found from the list.\n");
        }
    } //end prv stc void removeItem (String)

    private static void findItem (String item) {
        if (_todoList.getList().contains (item)) {
            System.out.print ("Item '" + item + "' found in the list.\n");
        } else {
            System.out.print ("Item '" + item + "' not found in the list.\n");
        }
    } //end prv stc void findItem (String)

    private static void getName() {
        System.out.print ("This Todo List's name is: '" + _todoList.getName() + "'.\n");
    } //end prv stc void getName

    private static void listItem() {
        if (_todoList.getList().size() == 0) {
            System.out.print ("There are no items in this list.\n");

        } else {
            System.out.print ("There are " + _todoList.getSize() +
                " items in this list.\n\n");

            for (int i=0; i < _todoList.getSize(); i++) {
                System.out.print ("ID: " + i + "\tTask: " +
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
        try {

            if (!_file.exists()) {
                _file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter (_file);
            BufferedWriter bufWriter = new BufferedWriter (fileWriter);

            for (String i : _todoList.getList()) {
                bufWriter.write (i);
                bufWriter.newLine();
            }

            bufWriter.close();

            System.out.print ("Done saving to file '" + FILENAME + "'\n");

        } catch (IOException ioException) {
            System.out.print ("error saving to a file.\n");

        }
    } //end prv stc void save

    private static void load() {
        _todoList.getList().clear();

        String line = null;

        try {
            FileReader fileReader = new FileReader (_file);
            BufferedReader bufReader = new BufferedReader (fileReader);

            while ((line = bufReader.readLine()) != null) {
                if (!line.trim().isEmpty() ||
                    !line.isEmpty()) {

                    addItem (line);
                }
            }

            bufReader.close();

            System.out.print ("\n");
            listItem();

        } catch (IOException ioException) {
            System.out.print ("error loading from a file.\n");

        }
    } //end prv stc void load

} //end class TodoMain
