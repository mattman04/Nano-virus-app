public class RedBloodCell extends Cell {
    private String cellType;

    public RedBloodCell() {
        super();
        cellType = "Red blood";
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
