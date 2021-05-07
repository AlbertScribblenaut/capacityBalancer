/** An N/P ratio balancing algorithm that determines the best usable
 * combinations of different cathode and anode samples based on their
 * theoretical capacities.
 * This makes use of a heap based minimum priority queue. Based
 *  on code that was written during summer 2020 while taking CS61BL at the
 *  University of California, Berkeley. I made use of the libraries that were
 *  provided by course staff (but did not upload to GitHub) as well as apache
 *  .jar files to help with reading .xlsx and .xls files.
 * @author Albert Wen
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

class CapacityBalancer {

    public static void makeCombinations(HashMap<String, Sample> idToSample,
                                        HashMap<String, Boolean> usedSamples,
                                        boolean simpleRead) {
        Set<Sample> anodes = new HashSet<>();
        Set<Sample> cathodes = new HashSet<>();
        MinHeapPQ<Combination> pq = new MinHeapPQ<>();
        Collection<Sample> samples = idToSample.values();
        for (Sample s : samples) {
            if (s.electrodeType.equals("Anode")) {
                anodes.add(s);
            }
            if (s.electrodeType.equals("Cathode")) {
                cathodes.add(s);
            }
        }
        for (Sample an : anodes) {
            for (Sample cath : cathodes) {
                Combination combo = new Combination(an, cath);
                pq.insert(combo, combo.positiveDeviation);
            }
        }

        while (pq.size() > 0) {
            Combination combo = pq.poll();
            String anId = combo.an.id;
            String cathId = combo.cath.id;
            if (usedSamples.get(anId) || usedSamples.get(cathId)) {
                continue;
            }
            if (combo.deviation < 0) {
                continue;
            } // filters out any that have combos less than idealRatio
            combo.giveData(simpleRead);
            usedSamples.put(anId, true);
            usedSamples.put(cathId, true);
        }
    }

    public static void main(String[] args) throws IOException {
        /*if (args.length != 1) {
            System.out.println("Please input one file name");
        }*/
        /** @source https://www.javatpoint.com/how-to-read-excel-file-in-java
         *
         */
        String fileName = "testSheet.xlsx"; // your file here
        boolean simpleRead = true;

        //obtaining input bytes from a file
        FileInputStream fis = new FileInputStream(new File(fileName));
        //creating workbook instance that refers to .xls file
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        //creating a Sheet object to retrieve the object
        XSSFSheet sheet = wb.getSheetAt(0);

        HashMap<String, Boolean> usedSamples= new HashMap<>();
        HashMap<String, Sample> idToSample = new HashMap<>();
        for(Row row:sheet) {     //iteration over row using for each loop
            Cell cell0 = row.getCell(0);
            Cell cell1 =  row.getCell(1);
            if (cell1 == null) {
                break;
            }
            Cell cell2 = row.getCell(2);
            Cell cell3 = row.getCell(3);
            Cell cell4 = row.getCell(4);

            // note that batchName could be either a set number or its
            // creation date
            String batchName =
                    String.valueOf(cell0.getDateCellValue());
//            String batchName = cell0.getStringCellValue();
            String label = cell1.getStringCellValue();
            double sideLength= cell2.getNumericCellValue();
            String electrodeType = cell3.getStringCellValue();
            double mass = cell4.getNumericCellValue();
            Sample s = new Sample(batchName, label, sideLength, electrodeType,
                    mass);

            usedSamples.put(s.id, false);
            idToSample.put(s.id, s);
        }

        makeCombinations(idToSample, usedSamples, simpleRead);
    }
}

