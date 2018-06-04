import java.util.Random;

public class Cell implements CalculateDistance {
    private int xCoordinate;
    private int yCoordinate;
    private int zCoordinate;
    private Random randomValue = new Random();
    private String cellType;
    private int distance;

    public Cell() {
        /*EACH CELL HAS AN X, Y, AND Z CO-ORDINATE, AND A TYPE*/
        generateXCoordinate();
        generateYCoordinate();
        generateZCoordinate();
        cellType = "Cell";
    }

    /*CO-ORDINATES ARE GENERATED USING RANDOM NUMBERS FROM 1 TO 5000*/
    private void generateXCoordinate() {
        xCoordinate = randomValue.nextInt(5000) + 1;
    }

    private void generateYCoordinate() {
        yCoordinate = randomValue.nextInt(5000) + 1;
    }

    private void generateZCoordinate() {
        zCoordinate = randomValue.nextInt(5000) + 1;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public int getZCoordinate() {
        return zCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public void setZCoordinate(int zCoordinate) {
        this.zCoordinate = zCoordinate;
    }

    public String getCellType() {
        return cellType;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    /*CALCULATE DISTANCES BETWEEN CELLS*/
    @Override
    public void calculateDistance(Cell cell) {
        this.distance = (int)Math.sqrt(Math.pow(getXCoordinate() - cell.getXCoordinate(), 2) + Math.pow(getYCoordinate() - cell.getYCoordinate(), 2) +
                Math.pow(getZCoordinate() - cell.getZCoordinate(), 2));
    }

    @Override
    public String toString() {
        return  "X CO-ORDINATE:" + " " + xCoordinate + '\n' +
                "Y CO-ORDINATE:" + " " + yCoordinate + '\n' +
                "Z CO-ORDINATE:" + " " + zCoordinate + '\n';
    }
}
