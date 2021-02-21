import Exceptions.PackageException;
import Services.InputReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class UserInputReader implements InputReader {
    public static final String CMD_QUIT = "quit";
    public static final String RESOURCE_FOLDER = "src/main/resources/";
    private BufferedReader reader;
    private PrintStream writer;

    public UserInputReader(InputStreamReader inputStreamReader, PrintStream printStream) {
        this.reader = new BufferedReader(inputStreamReader);
        this.writer = printStream;
    }

    public String readinput() throws PackageException {
        String input;
        try {
            if (writer != null) {
                writer.println("Entry: ");
            }
            input = reader.readLine();
        } catch (IOException e) {
            throw new PackageException("Package reading failed!");
        }
        return input;
    }
}
