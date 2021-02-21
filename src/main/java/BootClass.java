public class BootClass {
    public static void main(String[] args) {

        PostalProcessor postalProcessor = new PostalProcessor(PackageCache.getInstance(), args, System.out);
        postalProcessor.init();
    }
}
