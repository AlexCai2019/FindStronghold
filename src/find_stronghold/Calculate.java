package find_stronghold;

public class Calculate
{
    private final Coordinate[] line1 = new Coordinate[]{ new Coordinate(0, 0),new Coordinate(0, 0) }; //第一條線
    private final Coordinate[] line2 = new Coordinate[]{ new Coordinate(0, 0),new Coordinate(0, 0) }; //第二條線
    private final Coordinate[][] lines = { line1,line2 };
    private boolean startCalculate = false;

    public String findStronghold(String singleXOrZ, int row, int eitherXOrZ)
    {
        if (singleXOrZ.isEmpty())
            return ""; //如果字串是空的 就回傳空字串

        try //將輸入的字串們轉為線上的兩點
        {
            //先判斷是哪一條方程式 此時使用[row >> 1] 決定是line1或line2
            //因為row只會傳入0,1,2,3 因此右位移 0和1會變成0 2和3會變成1
            //就可以找出是line1或是line2 (line1是前兩列連成的線 line2是後兩列連成的線)
            //找到是line1或line2後 使用row & 1 找出是line1[0]或line1[1] (或是line2[0]/line2[1])
            //因為row & 1 會將0和2轉換為0 並將1和3轉換為1
            //抓到正確的那組Coordinate後 呼叫set方法傳入eitherXOrZ和Double.parseDouble(singleXOrZ)
            //因為eitherXOrZ 這個數字是代表要修改的是x或是z
            //而singleXOrZ 當初是傳入那個輸入框的內容
            lines[row >> 1][row & 1].set(eitherXOrZ, Double.parseDouble(singleXOrZ));
        }
        catch (NumberFormatException numberFormatException)
        {
            return "請勿輸入數字以外的內容";
        }

        if (!startCalculate)
            return "";

        Coordinate[] ab = { calculateAB(line1), calculateAB(line2) }; //算出聯立方程式的AB

        double[] stronghold = calculateXZ(ab).getDoubleArray(); //算出聯立方程式的XY 並轉為double array

        return String.format("[%.3f, %.3f]", stronghold[0], stronghold[1]);
    }

    public void setStartCalculate(boolean set)
    {
        startCalculate = set;
    }

    //找出AB
    private Coordinate calculateAB(Coordinate[] line)
    {
        // z0 = a * x0 + b
        // z1 = a * x1 + b
        double a = (line[0].getZ() - line[1].getZ()) / (line[0].getX() - line[1].getX());
        double b = line[0].getZ() - a * line[0].getX();
        return new Coordinate(a, b);
    }

    //找出XZ
    public Coordinate calculateXZ(Coordinate[] ab)
    {
        // z = a0 * x + b0
        // z = a1 * x + b1
        double x = -(ab[0].getB() - ab[1].getB()) / (ab[0].getA() - ab[1].getA());
        double z = ab[0].getA() * x + ab[0].getB();
        return new Coordinate(x, z);
    }
}

class Coordinate
{
    private final double[] coordinate = new double[2];

    public Coordinate(double x, double z)
    {
        coordinate[0] = x;
        coordinate[1] = z;
    }

    public void set(int which, double value)
    {
        coordinate[which] = value;
    }

    public double getX()
    {
        return coordinate[0];
    }
    public double getA()
    {
        return coordinate[0];
    }

    public double getZ()
    {
        return coordinate[1];
    }
    public double getB()
    {
        return coordinate[1];
    }

    public double[] getDoubleArray()
    {
        return coordinate;
    }
}