import Exceptions.FileException;
import Exceptions.NumberException;
import Exceptions.PackageException;
import Services.InputReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.*;

public class PostalProcessor {
    private PackageCache packageCache;
    private InputReader inputReader = new UserInputReader(new InputStreamReader(System.in), System.out);
    private PackageParser packageParser = new PackageParser();
    private String[] filePaths;
    private PrintStream printStream;
    private ConsoleScheduledWriter consoleScheduledWriter = new ConsoleScheduledWriter();

    public PostalProcessor(PackageCache packageCache, String[] filePaths, PrintStream printStream) {
        this.packageCache = packageCache;
        this.filePaths = filePaths;
        this.printStream = printStream;
    }

    public void init() {
        loadFees();
        loadInitPackages();
        consoleScheduledWriter.startService();
        try {
            this.executeApp();
        } catch (Exception e) {

        }
    }

    private void executeApp() throws Exception {

        String newLine;
        while ((newLine = inputReader.readinput()) != null) {
            if (UserInputReader.CMD_QUIT.equals(newLine)) {
                break;
            }
            try {
                PostalSummary newPostalSummary = packageParser.parsePackageFromString(newLine);
                PostalSummary old = packageCache.getPostOffices().get(newPostalSummary.getPostalCode());
                if (old != null) {
                    old.setWeight(old.getWeight().add(newPostalSummary.getWeight()));
                    old.setFee(packageCache.matchFee(old.getWeight()));
                } else {
                    newPostalSummary.setFee(packageCache.matchFee(newPostalSummary.getWeight()));
                    packageCache.getPostOffices().put(newPostalSummary.getPostalCode(), newPostalSummary);
                }


            } catch (NumberException e) {
                System.out.println(e.getMessage());
            } catch (PackageException e) {
                System.out.println(e.getLocalizedMessage());
            }

        }
    }

    private void loadFees() {
        if (filePaths.length < 2) {
            return;
        }
        Map<BigDecimal, BigDecimal> fees = packageCache.getFees();
        try {
            for (String lineFee:
            loadFileLines(UserInputReader.RESOURCE_FOLDER + "" + filePaths[1])) {
                List<BigDecimal> feeLineList = packageParser.parseFeeFromString(lineFee);
                fees.put(feeLineList.get(0), feeLineList.get(1));
            }

        } catch (FileException e) {
            printStream.println("File error: " + filePaths[1]);
        } catch (PackageException e) {
            printStream.println("Package error");
        } catch (NumberException e) {
            printStream.println("Number error");
        }

//        packageCache.getFees().put(new BigDecimal("10"), new BigDecimal("5.00"));
//        packageCache.getFees().put(new BigDecimal("5"), new BigDecimal("2.50"));
//        packageCache.getFees().put(new BigDecimal("3"), new BigDecimal("2.00"));
//        packageCache.getFees().put(new BigDecimal("2"), new BigDecimal("1.50"));
//        packageCache.getFees().put(new BigDecimal("1"), new BigDecimal("1.00"));
//        packageCache.getFees().put(new BigDecimal("0.5"), new BigDecimal("0.70"));
//        packageCache.getFees().put(new BigDecimal("0.2"), new BigDecimal("0.50"));
    }

    private void loadInitPackages() {
        if (filePaths.length < 1) {
            return;
        }
        Map<String, PostalSummary> offices = packageCache.getPostOffices();
        try {
            for (String packageLine:
            loadFileLines(UserInputReader.RESOURCE_FOLDER + "" + filePaths[0])) {
               PostalSummary postalSummary = packageParser.parsePackageFromString(packageLine);
               packageCache.insertPostalSummary(postalSummary);
            }
        } catch (FileException e) {
            printStream.println("File error: " + filePaths[0]);
        } catch (PackageException e) {
            printStream.println("Package error");
        } catch (NumberException e) {
            printStream.println("Number error");
        }

//        HashMap<Integer, PostalSummary> offices = packageCache.getPostOffices();
//        PostalSummary postalSummary = new PostalSummary();
//        postalSummary.setPostalCode(12345);
//        postalSummary.setWeight(new BigDecimal("23.342"));
//        postalSummary.setFee(packageCache.matchFee(postalSummary.getWeight()));
//        offices.put(12345, postalSummary);
//        if (offices.get(12345) != null) {
//            PostalSummary old = offices.get(12345);
//            old.setWeight(old.getWeight().add(new BigDecimal("22")));
//            old.setFee(packageCache.matchFee(old.getWeight()));
////            offices.put(12345, );
//        }
//        PostalSummary postalSummaryTwo = new PostalSummary();
//        postalSummaryTwo.setPostalCode(12346);
//        postalSummaryTwo.setWeight(new BigDecimal("13.342"));
//        postalSummaryTwo.setFee(packageCache.matchFee(postalSummaryTwo.getWeight()));
//        packageCache.getPostOffices().put(postalSummaryTwo.getPostalCode(), postalSummaryTwo);
    }

    private List<String> loadFileLines(String filePath) throws FileException, PackageException {
        List<String> fileLines = new ArrayList<String>();
        try {
            InputReader reader = new UserInputReader(new FileReader(filePath), null);
            String fileLine;
            while((fileLine = reader.readinput()) != null){
                fileLines.add(fileLine);
            }
        } catch (FileNotFoundException e) {
            throw new FileException("File path is not valid!");
        } catch (Exception e) {
            throw new PackageException("Invalid line from file!");
        }
        return fileLines;
    }
}
