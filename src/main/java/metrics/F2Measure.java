package metrics;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GengNing
 * @description Record the values of F2-measure
 * @date 2019/11/13
 */
public class F2Measure extends CalculateMeasure {

    private List<Integer> f2Measure = new ArrayList<Integer>();


    /** add element to list*/
    @Override
    public void addMeasure(int value){
        f2Measure.add(value);
    }



    /** get list*/
    @Override
    public List<Integer> getMeasure() {
        return f2Measure;
    }
}
