import Exceptions.NumberException;
import Exceptions.PackageException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PackageParser {
    private InputValidator inputValidator = new InputValidator();

    public PostalSummary parsePackageFromString(String input) throws NumberException, PackageException {
        String[] splitInput = input.split(" ");
        PostalSummary postalPostalSummary = new PostalSummary();
        if (splitInput.length < 2){
            throw new PackageException("invalid package input");
        }
        postalPostalSummary.setWeight(inputValidator.validateWeight(splitInput[0]));
        postalPostalSummary.setPostalCode(inputValidator.validatePostalNumber(splitInput[1]));
        return postalPostalSummary;
    }
    public List<BigDecimal> parseFeeFromString(String fee) throws NumberException {
        List<BigDecimal> weightFee = new ArrayList<BigDecimal>();
        String[] splitInput = fee.split(" ");
        weightFee.add(inputValidator.validateWeight(splitInput[0]));
        weightFee.add(inputValidator.validateFeeNumber(splitInput[1]));
        return weightFee;
    }
}
