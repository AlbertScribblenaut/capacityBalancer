import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class Sample {
    public String date;
    public String label;
    public double sideLength;
    public String electrodeType;
    public double mass;
    public float capacity;
    public String id;

    public Sample(String date, String label, double sideLength,
                  String electrodeType, double mass) {
        this.date = date;
        this.label = label;
        this.sideLength = sideLength;
        this.electrodeType = electrodeType;
        this.mass = mass;
        this.capacity = 0;
        if (electrodeType.equals("Anode")) {
            this.capacity = (float) ((float) mass * 0.902 * 372 / 1000);
        }
        if (electrodeType.equals("Cathode")) {
            this.capacity = (float) ((float) mass * 0.88 * 148 / 1000);
        }
        this.id = sha1(date + label + mass);
    }
    public void giveData() {
        System.out.println("    Sample Date: " + date);
        System.out.println("    Label: " + label);
        System.out.println("    Electrode Size: " + sideLength + " mm x " + sideLength + " mm");
        System.out.println("    Electrode Type: " + electrodeType);
        System.out.println("    Electrode Mass (mg): " + mass);
        System.out.println("    Theoretical capacity: " + capacity);
        System.out.println();
    }

    /** Returns the SHA-1 hash of the concatenation of VALS, which may
     *  be any mixture of byte arrays and Strings.
     *  @author P. N. Hilfinger*/
    static String sha1(Object... vals) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            for (Object val : vals) {
                if (val instanceof byte[]) {
                    md.update((byte[]) val);
                } else if (val instanceof String) {
                    md.update(((String) val).getBytes(StandardCharsets.UTF_8));
                } else {
                    throw new IllegalArgumentException("improper type to sha1");
                }
            }
            Formatter result = new Formatter();
            for (byte b : md.digest()) {
                result.format("%02x", b);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException excp) {
            throw new IllegalArgumentException("System does not support SHA-1");
        }
    }
}