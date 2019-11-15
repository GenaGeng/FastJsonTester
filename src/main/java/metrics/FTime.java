package metrics;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GN
 * @description Record the values of the F-time
 * @date 2019/11/13
 */
public class FTime extends CalculateTime {

    private List<Long> firstSelectTestCaseArray = new ArrayList<Long>();

    private List<Long> firstGenerateTestcaseArray = new ArrayList<Long>();

    private List<Long> firstExecuteTestcaseArray = new ArrayList<Long>();


    /** add element to list*/
    @Override
    public void addSelectTestCaseTime(Long time){
        firstSelectTestCaseArray.add(time);
    }

    @Override
    public void addGenerateTestCaseTime(Long time){
        firstGenerateTestcaseArray.add(time);
    }

    @Override
    public void addExecuteTestCaseTime(Long time){
        firstExecuteTestcaseArray.add(time);
    }



    /** get list*/
    @Override
    public List<Long> getSelectTestCaseTime() {
        return firstSelectTestCaseArray;
    }

    @Override
    public List<Long> getGenerateTestCaseTime() {
        return firstGenerateTestcaseArray;
    }

    @Override
    public List<Long> getExecuteTestCaseTime() {
        return firstExecuteTestcaseArray;
    }
}
