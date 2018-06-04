public class WhiteBloodCell extends Cell {
    private String cellType;

    public WhiteBloodCell() {
        super();
        cellType = "White blood";
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
