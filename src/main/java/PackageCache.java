import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class PackageCache {
    private HashMap<String, PostalSummary> postOffices = new HashMap<String, PostalSummary>();
    private TreeMap<BigDecimal, BigDecimal> fees = new TreeMap<BigDecimal, BigDecimal>();
    private static PackageCache packageCache = null;

    synchronized static PackageCache getInstance() {
        if (packageCache == null) {
            packageCache = new PackageCache();
        }
        return packageCache;
    }

    private PackageCache() {
    }

    public HashMap<String, PostalSummary> getPostOffices() {
        return postOffices;
    }

    public TreeMap<BigDecimal, BigDecimal> getFees() {
        return fees;
    }

    public List<String> generateCurrentOutput() {
        List<String> postalEntries = new ArrayList<String>();
        for (String postalNumber : postOffices.keySet()) {
            PostalSummary currentPostalSummary = postOffices.get(postalNumber);
            StringBuilder newEntry = new StringBuilder();
            newEntry.append(postalNumber).append(" ");
            newEntry.append(currentPostalSummary.getWeight().setScale(3, BigDecimal.ROUND_DOWN)).append(" ");
            if (fees.size() > 0) {
                newEntry.append(currentPostalSummary.getFee());
            }
            postalEntries.add(newEntry.toString());
        }
        return postalEntries;
    }

    public BigDecimal matchFee(BigDecimal weight) {
        BigDecimal matchedFee = new BigDecimal("0.00");
        if (fees.isEmpty()) {
            return matchedFee;
        }
//        weight = new BigDecimal("23.234");

        for (BigDecimal entry : fees.keySet()) {
            if (entry.compareTo(weight) > 0) {
                break;
            }
            matchedFee = fees.get(entry);
        }
        return matchedFee;
    }

    public void insertPostalSummary(PostalSummary postalSummary) {
        PostalSummary existing = postOffices.get(postalSummary.getPostalCode());
        if (existing != null) {
            existing.setWeight(existing.getWeight().add(postalSummary.getWeight()));
            existing.setFee(matchFee(existing.getWeight()));
            postOffices.put(existing.getPostalCode(), existing);
        } else{
            postalSummary.setFee(matchFee(postalSummary.getWeight()));
            postOffices.put(postalSummary.getPostalCode(), postalSummary);
        }
    }
}
