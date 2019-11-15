package metrics;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GN
 * @description Record the values of the F2-time
 * @date 2019/11/13
 */
public class F2Time extends CalculateTime {

    private List<Long> secondSelectTestCaseArray = new ArrayList<Long>();

    private List<Long> secondGenerateTestCaseArray = new ArrayList<Long>();

    private List<Long> secondExecuteTestCaseArray = new ArrayList<Long>();


    /** add element to list*/
    @Override
    public void addSelectTestCaseTime(Long time){
        secondSelectTestCaseArray.add(time);
    }

    @Override
    public void addGenerateTestCaseTime(Long time){
        secondGenerateTestCaseArray.add(time);
    }

    @Override
    public void addExecuteTestCaseTime(Long time){
        secondExecuteTestCaseArray.add(time);
    }



    /** get list*/
    @Override
    public List<Long> getSelectTestCaseTime() {
        return secondSelectTestCaseArray;
    }

    @Override
    public List<Long> getGenerateTestCaseTime() {
        return secondGenerateTestCaseArray;
    }

    @Override
    public List<Long> getExecuteTestCaseTime() {
        return secondExecuteTestCaseArray;
    }
}
