import java.util.*;
import java.io.*;

public class NanoVirusApp implements ConvertCells {
    private ArrayList<Cell> originalCellList = new ArrayList<>();
    private ArrayList<Cell> finalCellList = new ArrayList<>();
    private ArrayList<NanoVirus> nanoVirusList = new ArrayList<>();
    private Scanner scan;
    private int tumorousCellCount;
    private int redBloodCellCount;
    private int whiteBloodCellCount;
    private int nanoVirusCellCount;
    private int numberOfTurns;
    private NanoVirus nanoVirus = new NanoVirus();

    public NanoVirusApp() {
        initializeApp();
    }

    private void initializeApp() {
        displayGameMenu();
    }

    private void displayGameMenu() {
        scan = new Scanner(System.in);
        int input = 0;

        /*USER EITHER ENTERS OPTION 1 OR OPTION 0 TO START OR EXIT THE GAME*/
        do {
            System.out.println("NANO-VIRUS APP:");
            System.out.println("PLEASE SELECT AN OPTION:");
            System.out.println("1: START GAME");
            System.out.println("0: EXIT GAME");

            try {
                input = scan.nextInt();
                scan.nextLine();

                processMenuOption(input);
            }
            catch(InputMismatchException i) {
                System.out.println("VALUE NEEDS TO EITHER BE 0 OR 1");
                System.out.println();
                displayGameMenu();
            }
        }
        while(input != 0);
    }

    private void processMenuOption(int input) {
        /*GAME STARTS WHEN OPTION 1 IS ENTERED, AND GAME CLOSES WHEN OPTION 0 IS ENTERED*/
        switch(input) {
            case 1:
                play();
                break;

            case 0:
                System.out.println("GAME CLOSING...");
                exitGame();
                break;

            default: System.out.println("NEED TO ENTER EITHER 1 OR 0");
        }
    }

    /*GAME FUNCTIONALITY*/
    private void play() {
        System.out.println("GAME STARTING...");
        System.out.println("");

        /*INITIALISE DEFAULT GAME STATE*/
        generateCells();
        generateNanoVirus();
        calculateNanoVirusDistances();

        System.out.println();

        calculateTumorousRedBloodDistance();
        calculateTumorousWhiteBloodDistance();

        Cell tumor = null;

        do {
            System.out.println("TUMOROUS CELL COUNT:" + " " + getTumorousCellCount());
            System.out.println("RED BLOOD CELL COUNT:" + " " + getRedBloodCellCount());
            System.out.println("WHITE BLOOD CELL COUNT:" + " " + getWhiteBloodCellCount());
            System.out.println("NANO-VIRUS CELL COUNT:" + " " + getNanoVirusCellCount());
            System.out.println("NUMBER OF TURNS TAKEN:" + " " + getNumberOfTurns());
            System.out.println();

            System.out.println("SELECT NANO-VIRUS ACTION:");
            System.out.println("1: MOVE");
            System.out.println("2: KILL TUMOR");
            System.out.println("3: REPLICATE");
            System.out.println("4: EXIT");

            processNanoVirusActionOption(validateGameOption(scan));

            //calculateTumorousRedBloodDistance();
            //convertRedBloodCellToTumorousCell();

            //calculateTumorousWhiteBloodDistance();
            //convertWhiteBloodCellToTumorousCell(redBloodCellCount);

            for(Cell c : finalCellList) {
                if(c.getClass().toString().equals("class TumorousCell")) {
                    tumor = c;
                }
            }

            if(tumorousCellCount == 0) {
                System.out.println("ALL TUMOROUS CELLS REMOVED");
                System.out.println();
                exitGame();
            }
            else if(tumorousCellCount == 100) {
                System.out.println("ALL CELLS ARE NOW TUMOROUS");
                System.out.println();
                exitGame();
            }
        }
        /*CONTINUE THE GAME WHILE THERE ARE STILL TUMOROUS CELLS REMAINING*/
        while(finalCellList.contains(tumor));
    }

    /*INPUT VALIDATION FOR THE NANO VIRUS ACTION OPTIONS*/
    /*ENSURES THAT A NUMBER AS ENTERED*/
    private boolean isNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch(NumberFormatException n) {
            return false;
        }
    }

    /*VALIDATE THAT ONLY OPTIONS 1, 2, 3, AND 4 CAN BE ACCEPTED*/
    private int validateGameOption(Scanner input) {
        String entry = input.next();
        int value = 0;

        while(!isNumber(entry)) {
            System.out.println("NEED TO ENTER A NUMBER FROM 1 TO 4");
            entry = input.next();
        }
        switch(Integer.parseInt(entry)) {
            case 1:
                value = Integer.parseInt(entry);
                break;

            case 2:
                value = Integer.parseInt(entry);
                break;

            case 3:
                value = Integer.parseInt(entry);
                break;

            case 4:
                value = Integer.parseInt(entry);
                break;
        }
        return value;
    }

    private void processNanoVirusActionOption(int action) {
        /*OPTION 1 MOVES THE NANO VIRUS*/
        /*OPTION 2 KILLS A TUMOROUS CELL*/
        /*OPTION 3 REPLICATES THE NANO VIRUS*/
        /*OPTION 4 EXITS THE GAME*/
        switch(action) {
            case 1:
                increaseNumberOfTurns(numberOfTurns);
                moveNanoVirus();
                saveAppState();
                break;

            case 2:
                increaseNumberOfTurns(numberOfTurns);
                killTumorousCell();
                saveAppState();
                break;

            case 3:
                increaseNumberOfTurns(numberOfTurns);
                replicateNanoVirus();
                saveAppState();
                break;

            case 4:
                System.out.println("EXITING GAME:...");
                System.exit(0);
                break;

            default:
                System.out.println("VALUE MUST RANGE FROM 1 TO 4");
                System.out.println();
        }
    }

    /*NANO-VIRUS ACTION METHODS*/

    private void moveNanoVirus() {
        System.out.println("ENTER THE NUMBER OF UNITS TO MOVE (MAX 2000 UNITS):");

        int unitsToMove = 0;
        String input = scan.next();

        /*INPUT VALIDATION- NEEDS TO BE AN INTEGER*/
        if(isNumber(input)) {
            /*THE NUMBER OF UNITS TO MOVE ENTERED BY THE USER*/
            unitsToMove = Integer.parseInt(input);
        }
        else {
            System.out.println("NEED TO ENTER A NUMBER");
        }
        /*THE NANO-VIRUS CAN ONLY MOVE UP TO 2000 UNITS*/
        if(unitsToMove <= 2000) {
            /*FOR EACH CELL IN THE LIST, SET THE NEW DISTANCE VALUE TO THE ORIGINAL DISTANCE VALUE
            MINUS THE NUMBER OF UNITS ENTERED, FOR THE NANO-VIRUS, AND FOR THE REST OF THE CELLS*/
            for(Cell cells : finalCellList) {
                nanoVirus.setDistance(cells.getDistance() - unitsToMove);
                cells.setDistance(nanoVirus.getDistance());

                if(cells.getClass().toString().equals("class TumorousCell")) {
                    System.out.println("DISTANCE FROM" + " " + nanoVirus.getCellType() + " " +  "TO" + " " + cells.getCellType() + " " + "IS:" + " " +
                            nanoVirus.getDistance() + " " + "UNITS");
                }
            }
            System.out.println("");
        }
        else {
            System.out.println("2000 UNITS IS THE LIMIT");
            System.out.println("");
            moveNanoVirus();
        }
    }

    private void killTumorousCell() {
        /*DECLARE A CELL INDEX VARIABLE, INDICATING WHICH TUMOROUS CELL TO KILL*/
        int tumorousCellIndex;

        /*RETRIEVE ALL CELLS WHICH ARE TUMOROUS*/
        for(int i = 0; i < finalCellList.size(); i++) {
            /*IF THE CELL IS A TUMOROUS CELL*/
            if(finalCellList.get(i).getClass().toString().equals("class TumorousCell")) {
                /*IF A PARTICULAR TUMOROUS CELL'S DISTANCE RELATIVE TO THE NANO-VIRUS IS LESS THAN OR EQUAL TO 250 UNITS,
                * THEN REMOVE IT (CAN ONLY REMOVE ONCE PER TURN).*/
                if(finalCellList.get(i).getDistance() <= 250) {
                    /*GET THE INDEX VALUE OF THAT TUMOROUS CELL*/
                    tumorousCellIndex = i;

                    /*REMOVE THE TUMOROUS CELL*/
                    finalCellList.remove(tumorousCellIndex);
                    /*DECREASE THE TUMOROUS CELL COUNTER*/
                    decreaseTumorousCellCount(tumorousCellCount);

                    System.out.println("REMOVED A TUMOROUS CELL");
                }
                else {
                    System.out.println("NO TUMOROUS CELL DETECTED WITHIN 250 UNITS");
                }
            }
        }
        System.out.println("");
    }

    private void replicateNanoVirus() {
        /*INSTANTIATE A NEW NANO-VIRUS*/
        NanoVirus newNanoVirus = new NanoVirus();

        /*THE NEW NANO-VIRUS OBTAINS THE CO-ORDINATES OF THE CURRENT NANO-VIRUS*/
        newNanoVirus.setXCoordinate(nanoVirus.getXCoordinate());
        newNanoVirus.setYCoordinate(nanoVirus.getYCoordinate());
        newNanoVirus.setZCoordinate(nanoVirus.getZCoordinate());

        /*ADD NEW NANO VIRUS TO NANO VIRUS LIST*/
        nanoVirusList.add(newNanoVirus);
        /*INCREASE THE NANO VIRUS CELL COUNT*/
        increaseNanoVirusCellCount(nanoVirusCellCount);

        System.out.println("NANO-VIRUS REPLICATED");
        System.out.println();
    }

    private void convertRedBloodCellToTumorousCell() {
        int numberOfTumours = 0;
        TumorousCell tumorousCell;

        if(getNumberOfTurns() > 0) {
           /*AFTER EVERY 2 TURNS*/
           if(getNumberOfTurns() % 2 == 0) {
                /*RETRIEVE THE TUMOUR COUNT*/
                for(int i = 0; i < finalCellList.size(); i++) {
                    if(finalCellList.get(i).getClass().toString().equals("class TumorousCell")) {
                        numberOfTumours++;
                    }
                }
                /*ITERATE FROM THE POSITION AFTER THE LAST TUMOUR CELL IN THE LIST*/
                for(int j = numberOfTumours + 1; j < finalCellList.size(); j++) {
                    /*ITERATE AND CONVERT RED BLOOD CELL BASED ON THE NUMBER OF TUMOROUS CELLS*/
                    /*I.E., "X" NUMBER OF TUMOROUS CELLS WILL TRANSFORM "X" NUMBER OF RED BLOOD CELLS*/
                    while(!(j > j * 2)) {
                        if(finalCellList.get(j).getClass().toString().equals("class RedBloodCell")) {
                            /*INSTANTIATE A NEW TUMOROUS CELL*/
                            tumorousCell = new TumorousCell();

                            /*SET THE TUMOROUS CELL CO-ORDINATES TO THE CO-ORDINATES OF THE RED BLOOD CELL
                            TO BE TRANSFORMED
                             */
                            tumorousCell.setXCoordinate(finalCellList.get(j).getXCoordinate());
                            tumorousCell.setYCoordinate(finalCellList.get(j).getYCoordinate());
                            tumorousCell.setZCoordinate(finalCellList.get(j).getZCoordinate());

                            /*REMOVE THE RED BLOOD CELL, AND REPLACE IT WITH A TUMOROUS CELL*/
                            finalCellList.set(j, tumorousCell);
                            /*INCREASE THE TUMOROUS CELL COUNT*/
                            increaseTumorousCellCount(tumorousCellCount);
                            /*DECREASE THE RED BLOOD CELL COUNT*/
                            decreaseRedBloodCellCount(redBloodCellCount);

                            System.out.println("RED BLOOD CELL CONVERTED TO TUMOROUS CELL");
                        }
                    }
                }
            }
        }
    }

    private void convertWhiteBloodCellToTumorousCell(int redBloodCellCount) {
        int numberOfTumours = 0;
        TumorousCell tumorousCell;

        /*IF THERE ARE NO MORE RED BLOOD CELLS*/
        if(redBloodCellCount == 0) {
            if(getNumberOfTurns() > 0) {
                /*AFTER EVERY 2 TURNS*/
                if(getNumberOfTurns() % 2 == 0) {
                    /*RETRIEVE THE TUMOUR COUNT*/
                    for(int i = 0; i < finalCellList.size(); i++) {
                        if(finalCellList.get(i).getClass().toString().equals("class TumorousCell")) {
                            numberOfTumours++;
                        }
                    }
                    /*ITERATE FROM THE POSITION AFTER THE LAST TUMOUR CELL IN THE LIST*/
                    for(int j = numberOfTumours + 1; j < finalCellList.size(); j++) {
                        /*ITERATE AND CONVERT WHITE BLOOD CELL BASED ON THE NUMBER OF TUMOROUS CELLS*/
                        while(!(j > j * 2)) {
                            if(finalCellList.get(j).getClass().toString().equals("class WhiteBloodCell")) {
                                /*INSTANTIATE A NEW TUMOROUS CELL*/
                                tumorousCell = new TumorousCell();

                                /*SET THE TUMOROUS CELL CO-ORDINATES TO THE CO-ORDINATES OF THE WHITE BLOOD CELL
                                TO BE TRANSFORMED
                                */
                                tumorousCell.setXCoordinate(finalCellList.get(j).getXCoordinate());
                                tumorousCell.setYCoordinate(finalCellList.get(j).getYCoordinate());
                                tumorousCell.setZCoordinate(finalCellList.get(j).getZCoordinate());

                                /*REMOVE THE WHITE BLOOD CELL, AND REPLACE IT WITH A TUMOROUS CELL*/
                                finalCellList.set(j, tumorousCell);
                                /*INCREASE THE TUMOROUS CELL COUNT*/
                                increaseTumorousCellCount(tumorousCellCount);
                                /*DECREASE THE RED BLOOD CELL COUNT*/
                                decreaseWhiteBloodCellCount(whiteBloodCellCount);

                                System.out.println("WHITE BLOOD CELL CONVERTED TO TUMOROUS CELL");
                            }
                        }
                    }
                }
            }
        }
    }

    private void generateCells() {
        /*CELL SUB-TYPE PROBABILITY VALUES*/
        double tumorousCellProbability = 0.05;
        double redBloodCellProbability = 0.7;
        double whiteBloodCellProbability = 0.25;

        /*ADD 100 BASE CELL OBJECTS TO INITIAL LIST*/
        for(int i = 1; i <= 100; i++) {
            originalCellList.add(new Cell());
        }

        /*GENERATE SUB-TYPES BASED ON THEIR PROBABILITY VALUES*/
        for(int j = 1; j <= originalCellList.size() * tumorousCellProbability; j++) {
            /*CONVERT CELL BASE-TYPE TO TUMOROUS CELL SUB-TYPE*/
            finalCellList.add(convertCellToTumorous(originalCellList.get(j)));
        }
        for(int k = 1; k <= originalCellList.size() * redBloodCellProbability; k++) {
            /*CONVERT CELL BASE-TYPE TO RED BLOOD CELL SUB-TYPE*/
            finalCellList.add(convertCellToRedBlood(originalCellList.get(k)));
        }
        for(int l = 1; l <= originalCellList.size() * whiteBloodCellProbability; l++) {
            /*CONVERT CELL BASE-TYPE TO WHITE BLOOD CELL SUB-TYPE*/
            finalCellList.add(convertCellToWhiteBlood(originalCellList.get(l)));
        }
    }

    private void generateNanoVirus() {
        nanoVirusList.add(new NanoVirus());
    }

    private void calculateNanoVirusDistances() {
        /*CALCULATE DISTANCES BETWEEN NANO-VIRUS AND ALL OTHER CELLS*/
        for(Cell c : finalCellList) {
            nanoVirus.calculateDistance(c);
            c.setDistance(nanoVirus.getDistance());

            if(c.getClass().toString().equals("class TumorousCell")) {
                System.out.println("DISTANCE FROM" + " " + nanoVirus.getCellType() + " " +  "TO" + " " + c.getCellType() + " " +
                        "IS:" + " " + nanoVirus.getDistance() + " " + "UNITS");
            }
        }
    }

    private void calculateTumorousRedBloodDistance() {
       /*CALCULATE DISTANCE VALUES FROM TUMOROUS CELL TO RED BLOOD CELL*/
       for(int i = 0; i < finalCellList.size(); i++) {
            if(finalCellList.get(i).getClass().toString().equals("class TumorousCell")) {
                for(int j = 0; j < finalCellList.size(); j++) {
                    if(finalCellList.get(j).getClass().toString().equals("class RedBloodCell")) {
                        finalCellList.get(j).calculateDistance(finalCellList.get(i));
                         /*SET THE TUMOROUS CELL DISTANCE VALUE RELATIVE TO THE RED BLOOD CELL*/
                        finalCellList.get(j).setDistance(finalCellList.get(j).getDistance());
                    }
                }
            }
       }
    }

    private void calculateTumorousWhiteBloodDistance() {
         /*CALCULATE DISTANCE VALUES FROM TUMOROUS CELL TO WHITE BLOOD CELL*/
        for(int i = 0; i < finalCellList.size(); i++) {
            if(finalCellList.get(i).getClass().toString().equals("class TumorousCell")) {
                for(int j = 0; j < finalCellList.size(); j++) {
                    if(finalCellList.get(j).getClass().toString().equals("class WhiteBloodCell")) {
                        finalCellList.get(j).calculateDistance(finalCellList.get(i));
                         /*SET THE TUMOROUS CELL DISTANCE VALUE RELATIVE TO THE WHITE BLOOD CELL*/
                        finalCellList.get(j).setDistance(finalCellList.get(j).getDistance());
                    }
                }
            }
        }
    }

    /*CONVERT CELL BASE-TYPE INTO EITHER TUMOROUS, RED BLOOD, OR WHITE BLOOD SUB-TYPES*/
    @Override
    public Cell convertCellToTumorous(Cell c) {
        c = new TumorousCell();
        return c;
    }

    @Override
    public Cell convertCellToRedBlood(Cell c) {
        c = new RedBloodCell();
        return c;
    }

    @Override
    public Cell convertCellToWhiteBlood(Cell c) {
        c = new WhiteBloodCell();
        return c;
    }

    /*RETRIEVE THE COUNTER VALUES OF THE CELLS*/
    private int getTumorousCellCount() {
        tumorousCellCount = 0;

        for(Cell cells: finalCellList) {
            if(cells.getClass().toString().equals("class TumorousCell")) {
                tumorousCellCount++;
            }
        }
        return tumorousCellCount;
    }

    private int getRedBloodCellCount() {
        redBloodCellCount = 0;

        for(Cell cells : finalCellList) {
            if(cells.getClass().toString().equals("class RedBloodCell")) {
                redBloodCellCount++;
            }
        }
        return redBloodCellCount;
    }

    private int getWhiteBloodCellCount() {
        whiteBloodCellCount = 0;

        for(Cell cells: finalCellList) {
            if(cells.getClass().toString().equals("class WhiteBloodCell")) {
                whiteBloodCellCount++;
            }
        }
        return whiteBloodCellCount;
    }

    private int getNanoVirusCellCount() {
        nanoVirusCellCount = 0;

        for(NanoVirus n: nanoVirusList) {
            nanoVirusCellCount++;
        }
        return nanoVirusCellCount;
    }

    /*SET THE COUNTER VALUES FOR THE CELLS*/
    private void setTumorousCellCount(int tumorousCellCount) {
        this.tumorousCellCount = tumorousCellCount;
    }

    private void setRedBloodCellCount(int redBloodCellCount) {
        this.redBloodCellCount = redBloodCellCount;
    }

    private void setWhiteBloodCellCount(int whiteBloodCellCount) {
        this.whiteBloodCellCount = whiteBloodCellCount;
    }

    private void setNanoVirusCellCount(int nanoVirusCellCount) {
        this.nanoVirusCellCount = nanoVirusCellCount;
    }

    /*INCREASE THE NANO VIRUS AND TUMOROUS CELL COUNT VALUES*/
    private void increaseNanoVirusCellCount(int nanoVirusCellCount) {
        this.nanoVirusCellCount = nanoVirusCellCount + 1;
    }

    private void increaseTumorousCellCount(int tumorousCellCount) {
        this.tumorousCellCount = tumorousCellCount + 1;
    }

    /*DECREASE THE CELL COUNT VALUES*/
    private void decreaseTumorousCellCount(int tumorousCellCount) {
        this.tumorousCellCount = tumorousCellCount - 1;
    }

    private void decreaseRedBloodCellCount(int redBloodCellCount) {
        this.redBloodCellCount = redBloodCellCount - 1;
    }

    private void decreaseWhiteBloodCellCount(int whiteBloodCellCount) {
        this.whiteBloodCellCount = whiteBloodCellCount - 1;
    }

    /*RETRIEVE THE NUMBER OF TURNS TAKEN*/
    private int getNumberOfTurns() {
        return numberOfTurns;
    }

    /*SET THE NUMBER OF TURNS TAKEN*/
    private void setNumberOfTurns(int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    /*INCREASE THE NUMBER OF TURNS TAKEN*/
    private void increaseNumberOfTurns(int numberOfTurns) {
        this.numberOfTurns = numberOfTurns + 1;
    }

    private void saveAppState() {
        /*LOCATE THE FILE WITH THE CORRESPONDING NAME*/
        File file = new File("GameState.txt");

        /*RETRIEVE ITS FILE PATH*/
        String filePath = file.getAbsolutePath();
        /*REPLACES SINGLE QUOTES IN THE FILE PATH WITH DOUBLE QUOTES, SO THAT IT CAN CORRECTLY WRITE TO IT*/
        String newFilePath = filePath.replace("\\", "\\\\");

        /*CREATE A NEW APP STATE INSTANCE FOR EACH CYCLE/TURN*/
        GameState gameState = new GameState(getTumorousCellCount(), getRedBloodCellCount(), getWhiteBloodCellCount(),
                getNanoVirusCellCount(), getNumberOfTurns());

        try {
            /*INSTANTIATE A FILE WRITER WITH THE FILE PATH SPECIFIED*/
            FileWriter fileWriter = new FileWriter(newFilePath);
            /*WRITE THE GAME STATE CONTENTS TO THE FILE*/
            fileWriter.write(gameState.toString());
            /*CLOSE THE FILE STREAM*/
            fileWriter.close();
        }
        catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void exitGame() {
        System.exit(0);
    }
}
