import static java.lang.Math.abs;

/**
 * @author Albert Wen
 */
public class Combination implements Comparable<Combination> {
    public Sample an;
    public Sample cath;
    public float ratio;
    public double deviation;
    public double idealRatio = 1.08;  // serves as our lower bound
    public double positiveDeviation = 1.08;  // functions as priority value

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
        this.an = an;
        this.cath = cath;
        this.ratio = findRatio(an, cath);
        this.deviation = this.ratio - idealRatio;
        if (this.deviation >= 0) {
            this.positiveDeviation = this.deviation;
        }
    }

    // finds N/P ratio for a combination
    public float findRatio(Sample an, Sample cath) {
        float cathCap = (float) (cath.mass * 0.88 * 145 / 1000);  // [mAh]
        float anCap = (float) (an.mass * 0.9022 * 372 / 1000);  // [mAh]
        return anCap / cathCap;
    }

    public void giveData(boolean simpleRead) {
        if (simpleRead) {
        System.out.println(an.label + " (" + an.date + "), " + cath.label + " (" + cath.date + "), - " + ratio);
        } else {
            System.out.println("Giving combination: ");
            System.out.println("Anode: " + an.label + " (" + an.date + "), " +
                    "Cathode" +
                    ": " + cath.label + " (" + cath.date + ")");
            System.out.println("Ratio of this combination: " + ratio);
            System.out.println("Deviation: " + deviation);
            if (this.deviation < 0) {
                System.out.println("Caution - negative deviation below lower " +
                        "bound of ideal ratio: " + idealRatio);
            }
            an.giveData();
            cath.giveData();
            System.out.println();
        }
    }

    public int compareTo(Combination other) {
        double idealRatio = 1.08;
        double cmp = abs(idealRatio - this.ratio);
        return cmp == 0 ? 1 : (int) cmp;
    }
}
