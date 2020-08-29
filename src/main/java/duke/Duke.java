package duke;

import java.util.Scanner;

import duke.commands.EnumCommand;
import duke.commands.CommandExecution;
import duke.exception.DukeException;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.tasks.TaskList;
import duke.ui.Ui;


/**
 * A class that represents the Duke application which contains the main method in the class.
 */
public class Duke {
    private static final String filePath = "duke.txt";
    private Storage storage;
    private TaskList result;
    private Ui ui;

    /**
     * Sets up the user interface and load list from file storage.
     *
     * @param filePath the path of the file storage of list of tasks.
     */
    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            result = new TaskList(storage.readFromFile());
        } catch (DukeException e) {
            ui.showError(e);
            result = new TaskList();
        }
    }


    /**
     * The main method of the application.
     *
     * @param args the arguments of the main method.
     */
    public static void main(String[] args) {
        new Duke(filePath).run();
    }


    /**
     * Scans the user input and responds to command inputs.
     */
    public void run() {
        Scanner sc = new Scanner(System.in);
        ui.greetings();
        while (sc.hasNextLine()) {
            String instruction = sc.nextLine();
            try {
                EnumCommand enumCommand = Parser.parseCommand(instruction);
                CommandExecution.executeCommand(enumCommand, instruction, result);
                storage.storeToFile(result);
            } catch (Exception e) {
                ui.showError(e);
            }
        }
    }


}
