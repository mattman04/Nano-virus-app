public class GameState {
    private int tumorousCellCount;
    private int redBloodCellCount;
    private int whiteBloodCellCount;
    private int nanoVirusCellCount;
    private int numberOfTurns;

    public GameState(int tumorousCellCount, int redBloodCellCount, int whiteBloodCellCount, int nanoVirusCellCount, int numberOfTurns) {
        this.tumorousCellCount = tumorousCellCount;
        this.redBloodCellCount = redBloodCellCount;
        this.whiteBloodCellCount = whiteBloodCellCount;
        this.nanoVirusCellCount = nanoVirusCellCount;
        this.numberOfTurns = numberOfTurns;
    }

    public void setTumorousCellCount(int tumorousCellCount) {
        this.tumorousCellCount = tumorousCellCount;
    }

    public int getTumorousCellCount() {
        return tumorousCellCount;
    }

    public void setRedBloodCellCount(int redBloodCellCount) {
        this.redBloodCellCount = redBloodCellCount;
    }

    public int getRedBloodCellCount() {
        return redBloodCellCount;
    }

    public void setWhiteBloodCellCount(int whiteBloodCellCount) {
        this.whiteBloodCellCount = whiteBloodCellCount;
    }

    public int getWhiteBloodCellCount() {
        return whiteBloodCellCount;
    }

    public void setNanoVirusCellCount(int nanoVirusCellCount) {
        this.nanoVirusCellCount = nanoVirusCellCount;
    }

    public int getNanoVirusCellCount() {
        return nanoVirusCellCount;
    }

    public void setNumberOfTurns(int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    @Override
    public String toString() {
        return  "GAME STATE:" + " " + '\n' +
                "TUMOROUS CELL COUNT:" + " " + tumorousCellCount + '\n' +
                "RED BLOOD CELL COUNT:" + " " + redBloodCellCount + '\n' +
                "WHITE BLOOD CELL COUNT:" + " " + whiteBloodCellCount + '\n' +
                "NANO-VIRUS CELL COUNT:" + " " + nanoVirusCellCount + '\n' +
                "NUMBER OF TURNS:" + " " + numberOfTurns + '\n';
    }
}
