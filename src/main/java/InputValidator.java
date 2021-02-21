import Exceptions.NumberException;

import java.math.BigDecimal;

public class InputValidator {
    public BigDecimal validateWeight(String inputWeight) throws NumberException {
        BigDecimal validWeight;
        try {
            validWeight = new BigDecimal(inputWeight);
        } catch (NumberFormatException e) {
            throw new NumberException("Invalid weight input");
        }
        String stringFormat = validWeight.stripTrailingZeros().toPlainString();
        int index = stringFormat.indexOf(".");
        int decimalNumbers = index < 0 ? 0 : stringFormat.length() - index - 1;
        if (validWeight.compareTo(BigDecimal.ZERO) < 0 || decimalNumbers > 3) {
            throw new NumberException("Invalid weight input!");
        }
        return validWeight;
    }

    public String validatePostalNumber(String inputNumber) throws NumberException {
        if (!inputNumber.matches("\\d{5}")) {
            throw new NumberException("Invalid postal code number");
        }
        return inputNumber;
    }
    public BigDecimal validateFeeNumber(String fee) throws NumberException {
        BigDecimal validWeight;
        try {
            validWeight = new BigDecimal(fee);
        } catch (NumberFormatException e) {
            throw new NumberException("Invalid weight input");
        }
        String stringFormat = validWeight.stripTrailingZeros().toPlainString();
        int index = stringFormat.indexOf(".");
        int decimalNumbers = index < 0 ? 0 : stringFormat.length() - index - 1;
        if (validWeight.compareTo(BigDecimal.ZERO) < 0 || decimalNumbers > 2) {
            throw new NumberException("Invalid weight input!");
        }
        return validWeight;

    }

}
