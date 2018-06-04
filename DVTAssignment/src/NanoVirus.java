public class NanoVirus extends Cell implements CalculateDistance {
    private String cellType;
    private int distance;

    public NanoVirus() {
        super();
        cellType = "Nano-virus";
    }

   public void calculateDistance(Cell cell) {
       this.distance = (int)Math.sqrt(Math.pow(getXCoordinate() - cell.getXCoordinate(), 2) + Math.pow(getYCoordinate() - cell.getYCoordinate(), 2) +
               Math.pow(getZCoordinate() - cell.getZCoordinate(), 2));
   }

    @Override
    public int getDistance() {
        return distance;
    }

    @Override
    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String getCellType() {
        return cellType;
    }

    @Override
    public String toString() {
        return "CELL TYPE:" + " " + cellType + '\n' + super.toString();
    }
}
