import static java.lang.Math.abs;

/**
 * @author Albert Wen
 */
public class Combination implements Comparable<Combination> {
    public Sample an;
    public Sample cath;
    public float ratio;
    public double deviation;

    public Combination(Sample an, Sample cath) {
        // error checking
        if (!an.electrodeType.equals("Anode") && !cath.electrodeType.equals(
                "Cathode")) {
            System.out.println("First parameter must be anode");
            System.out.println("Second parameter must be cathode.");
        }
        if (!an.electrodeType.equals("Anode")) {
            System.out.println("First parameter must be anode");
        }
        if (!cath.electrodeType.equals("Cathode")) {
            System.out.println("Second parameter must be cathode.");
        }

        // creating combinations
        double idealRatio = 1.08;
        this.an = an;
        this.cath = cath;
        this.ratio = findRatio(an, cath);
        double positiveDeviation = this.ratio - idealRatio;
        if (positiveDeviation >= 0) {
            this.deviation = positiveDeviation;
        } else {
            this.deviation = idealRatio;
        }
    }

    public float findRatio(Sample an, Sample cath) {
        float cathCap = (float) (cath.mass * 0.88 * 145 / 1000);
        float anCap = (float) (an.mass * 0.9022 * 372 / 1000);
        return anCap / cathCap;
    }

    public void giveData() {
        System.out.println("Giving combination: ");
        System.out.println("Anode: " + an.label + " (" + an.date + "), " +
                "Cathode" +
                ": " + cath.label + " (" + cath.date +")");
        System.out.println("Ratio of this combination: " + ratio);
        an.giveData();
        cath.giveData();
        System.out.println();
    }

    public int compareTo(Combination other) {
        double idealRatio = 1.08;
        double cmp = abs(idealRatio - this.ratio);
        return cmp == 0 ? 1 : (int) cmp;
    }
}
