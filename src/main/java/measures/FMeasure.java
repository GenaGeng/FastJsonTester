package measures;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GengNing
 * @description Record the values of the F-measure
 * @date 2019/11/13
 */
public class FMeasure extends CalculateMeasure {

    private List<Integer> fMeasureArray = new ArrayList<Integer>();


    /** add element to list*/
    @Override
    public void addMeasure(int value){
        fMeasureArray.add(value);
    }



    /** get list*/
    @Override
    public List<Integer> getMeasure(){
        return fMeasureArray;
    }

}