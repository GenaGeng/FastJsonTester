package measures;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author GengNing
 * @description Template method:calculate average and variance
 * @date 2019/11/13
 */
public abstract class CalculateMeasure {

    /**
     * get average
     * @param list
     * @return average
     */
    public double calculateAverage(List<Integer> list){

        int sum = 0;

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        for (int i = 0; i < list.size(); i++) {

            sum += list.get(i);

        }

        return Double.parseDouble(decimalFormat.format(sum / list.size()));
    }



    /**
     * get variance
     * @param list
     * @return variance
     */
    public double calculateVariance(List<Integer> list){

        double sum = 0;

        double average = calculateAverage(list);

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        for (int i = 0 ;i < list.size(); i++){

            sum += Math.pow(list.get(i) - average, 2);

        }

        return Double.parseDouble(decimalFormat.format(sum / (list.size() - 1)));
    }



    /**abstract method to add element to list*/
    public abstract void addMeasure(int value);



    /**abstract method to get list*/
    public abstract List<Integer> getMeasure();
}
