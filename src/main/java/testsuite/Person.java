package testsuite;

import java.util.*;

/**
 * @author GengNing
 * @description Bean
 * @date 2019/11/5
 */
public class Person {
    /** height */
    private float height ;

    /** property */
    private double property ;

    /** salary */
    private short salary ;

    private byte age ;

    /** One-time maximum expense */
    private int maxExpense ;

    /** ID */
    private long id ;

    /** ismarried,true or false */
    private boolean married ;

    /** sex */
    private char sex ;

    /** birthday */
    private Date birthday ;

    /** name */
    private String name ;

    /** bowned vehicle */
    private Vehicle vehicle ;

    /** family */
    private Map<String,String> family ;

    /** favourite sports */
    private List<String> favoriteSports ;

    /** favourite food */
    private Set<String> favoriteFoods ;

    public enum Vehicle{
        Car, Motorcycle, Bike, E_Bike
    }

    public Person(float height,double property,short salary,byte age,int maxExpense,long id,boolean married, char sex,Date birthday,String name,
                  Vehicle vehicle,Map<String,String> family,List<String> favoriteSports,Set<String> favoriteFoods){

        super();

        this.height = height;

        this.property = property;

        this.salary = salary;

        this.age = age;

        this.maxExpense = maxExpense;

        this.id = id;

        this.married = married;

        this.sex = sex;

        this.birthday = birthday;

        this.name = name;

        this.vehicle = vehicle;

        this.family = family;

        this.favoriteSports = favoriteSports;

        this.favoriteFoods = favoriteFoods;
    }

    public void setParameters(float height,double property,short salary,byte age,int maxExpense,long id,boolean married, char sex,Date birthday,String name,
                              Vehicle vehicle,Map<String,String> family,List<String> favoriteSports,Set<String> favoriteFoods){

        setHeight(height);

        setProperty(property);

        setSalary(salary);

        setAge(age);

        setMaxExpense(maxExpense);

        setId(id);

        setMarried(married);

        setSex(sex);

        setBirthday(birthday);

        setName(name);

        setVehicle(vehicle);

        setFamily(family);

        setFavoriteSports(favoriteSports);

        setFavoriteFoods(favoriteFoods);

    }

    public void setHeight(float height){
        this.height = height;
    }

    public float getHeight(){
        return height;
    }

    public void setProperty(double property){
        this.property = property;
    }

    public double getProperty() {
        return property;
    }

    public void setSalary(short salary) {
        this.salary = salary;
    }

    public short getSalary() {
        return salary;
    }

    public void setAge(byte age){
        this.age = age;
    }

    public byte getAge() {
        return age;
    }

    public void setMaxExpense(int maxExpense) {
        this.maxExpense = maxExpense;
    }

    public int getMaxExpense() {
        return maxExpense;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public boolean isMarried() {
        return married;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public char getSex() {
        return sex;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setFamily(Map<String, String> family) {
        this.family = family;
    }

    public Map<String,String> getFamily() {
        return family;
    }

    public void setFavoriteSports(List<String> favoriteSports) {
        this.favoriteSports = favoriteSports;
    }

    public List<String> getFavoriteSports() {
        return favoriteSports;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    @Override
    public String toString() {
        return "PersonInfo [ID=" + id + ",name" + name + ",height" + height +",property=" + property + ",salary=" + salary +",age=" + age + ",maxExpense="
                + maxExpense + ",married=" + married + ",sex=" + sex + ",birthday=" + birthday + ",vehicle=" + vehicle
                + ",family=" + family + ",favoriteSports=" + favoriteSports + ",favoriteFoods=" + favoriteFoods + "]";
    }
}
