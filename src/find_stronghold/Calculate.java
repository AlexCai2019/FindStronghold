package find_stronghold;

public class Calculate
{
    public String findStronghold(String[][] eyePaths)
    {
        Coordinate[] line1; //第一條線
        Coordinate[] line2; //第二條線

        try //將輸入的字串們轉為線上的兩點
        {
            line1 = new Coordinate[]
            {
                    new Coordinate(Double.parseDouble(eyePaths[0][0]), Double.parseDouble(eyePaths[0][1])),
                    new Coordinate(Double.parseDouble(eyePaths[1][0]), Double.parseDouble(eyePaths[1][1]))
            };
            line2 = new Coordinate[]
            {
                    new Coordinate(Double.parseDouble(eyePaths[2][0]), Double.parseDouble(eyePaths[2][1])),
                    new Coordinate(Double.parseDouble(eyePaths[3][0]), Double.parseDouble(eyePaths[3][1]))
            };
        }
        catch (NumberFormatException numberFormatException)
        {
            return "請勿輸入數字以外的內容";
        }

        Coordinate[] ab = { calculateAB(line1), calculateAB(line2) }; //算出聯立方程式的AB

        double[] stronghold = calculateXY(ab).getDoubleArray(); //算出聯立方程式的XY 並轉為double array

        return String.format("[%.3f, %.3f]", stronghold[0], stronghold[1]);
    }

    //找出AB
    private Coordinate calculateAB(Coordinate[] line)
    {
        // y0 = a * x0 + b
        // y1 = a * x1 + b
        double a = (line[0].getY() - line[1].getY()) / (line[0].getX() - line[1].getX());
        double b = line[0].getY() - a * line[0].getX();
        return new Coordinate(a, b);
    }

    //找出XY
    public Coordinate calculateXY(Coordinate[] ab)
    {
        // y = a0 * x + b0
        // y = a1 * x + b1
        double x = -(ab[0].getB() - ab[1].getB()) / (ab[0].getA() - ab[1].getA());
        double y = ab[0].getA() * x + ab[0].getB();
        return new Coordinate(x, y);
    }
}

class Coordinate
{
    private final double[] coordinate = new double[2];

    public Coordinate(double x, double y)
    {
        coordinate[0] = x;
        coordinate[1] = y;
    }

    public double getX()
    {
        return coordinate[0];
    }
    public double getA()
    {
        return coordinate[0];
    }

    public double getY()
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