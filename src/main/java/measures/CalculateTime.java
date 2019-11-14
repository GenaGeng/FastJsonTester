package measures;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author GN
 * @description Template method:calculate average and variance
 * @date 2019/11/13
 */
public abstract class CalculateTime {

    /** get average*/
    public double calculateAverage(List<Long> list){

        int sum = 0;

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        for (int i = 0; i < list.size(); i++) {

            sum += list.get(i);

        }

        return Double.parseDouble(decimalFormat.format(sum / list.size()));
    }



    /** get variance*/
    public double calculateVariance(List<Long> list){

        double average = calculateAverage(list);

        double sum = 0;

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        for (int i = 0 ;i < list.size(); i++){

            sum += Math.pow(list.get(i) - average, 2);

        }

        return Double.parseDouble(decimalFormat.format(sum / (list.size() - 1)));
    }



    /**abstract method to add element to list*/
    public abstract void addSelectTestCaseTime(Long time);

    public abstract void addGenerateTestCaseTime(Long time);

    public abstract void addExecuteTestCaseTime(Long time);



    /** abstract method to get list*/
    public abstract List<Long> getSelectTestCaseTime();

    public abstract  List<Long> getGenerateTestCaseTime();

    public abstract List<Long> getExecuteTestCaseTime();
}
