import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
/*
 Author:
 Date:
 Description:
 */
public class ElectionResults {

    // the main method works as follows:
    // - provided code (leave this code as is):
    //   -- prompts user for file name containing ballot data
    //   -- reads data into array (one array item per line in file)
    // - code you need to write:
    //   -- execute the Ranked Choice Voting process as outlined
    //     in the project description document by calling the other
    //     methods that you will implement in this project
    public static void main(String[] args) {
        // Establish console Scanner for console input
        Scanner console = new Scanner(System.in);

        // Determine the file name containing the ballot data
        System.out.print("Ballots file: ");
        String fileName = console.nextLine();

        // Read the file contents into an array.  Each array
        // entry corresponds to a line in the file.
        String[] fileContents = getFileContents(fileName);

        // ***********************************************
        // Your code below here: execute the RCV process,
        // ensuring to make use of the remaining methods
        // ***********************************************
        ArrayList<Ballot> ballots = convert(fileContents);
        HashMap<String, Integer> tallies = tallies(ballots);
        while(analyze(tallies).isLoser()){
            printCounts(tallies);
            Result winnerLoser= analyze(tallies);
            if (winnerLoser.isLoser()){
                remove(winnerLoser.getName(),ballots);
            }
            tallies=tallies(ballots);
        }
        printCounts(tallies);
        printPercentages(tallies);

    }

    public static ArrayList<Ballot> convert(String[] inputFileContents) {
        ArrayList<Ballot> ballots = new ArrayList();
        String[] var2 = inputFileContents;
        int var3 = inputFileContents.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String b = var2[var4];
            String[] ballotArray = b.split(",");
            Ballot curBallot = new Ballot();
            String[] var8 = ballotArray;
            int var9 = ballotArray.length;

            for(int var10 = 0; var10 < var9; ++var10) {
                String n = var8[var10];
                curBallot.addCandidate(n);
            }

            ballots.add(curBallot);
        }

        return ballots;
    }

    public static HashMap<String, Integer> tallies(ArrayList<Ballot> ballotsCast) {
        HashMap<String, Integer> candidateCount = new HashMap();
        for (Ballot b: ballotsCast){
            if (!b.isExhausted() && !b.getCurrentChoice().equals("")){
                if (candidateCount.containsKey(b.getCurrentChoice())){
                    candidateCount.put(b.getCurrentChoice(),candidateCount.get(b.getCurrentChoice())+1);
                }
                else{
                    candidateCount.put(b.getCurrentChoice(),1);
                }

            }
        }
        return candidateCount;
    }

    public static Integer countTotalVotes(HashMap<String, Integer> candidateCount) {
        int count = 0;
        for (String k: candidateCount.keySet()){
            count+=candidateCount.get(k);
        }
        return count;
    }

    public static Result analyze(HashMap<String, Integer> candidateCount) {
        Result winnerLoser = new Result();
        int winningNumber=(countTotalVotes(candidateCount)/2)+1;
        int minCount=999999999;
        boolean winner = false;
        for (String k: candidateCount.keySet()){
            if (candidateCount.get(k)>=winningNumber){
                winnerLoser.setName(k);
                winnerLoser.setWin(true);
                winner=true;
            }
        }
        String loser="";
        if (!winner){
            for (String n:candidateCount.keySet()){
                if (candidateCount.get(n) <minCount){
                    minCount=candidateCount.get(n);
                    loser=n;
                }
            }
            winnerLoser.setName(loser);
            winnerLoser.setWin(false);
        }
        return winnerLoser;
    }

    public static void printCounts(HashMap<String, Integer> candidateCount) {
        System.out.println("Vote Tallies");
        for (String k: candidateCount.keySet()){
            System.out.println(k+": "+candidateCount.get(k));
        }
    }

    public static void remove(String candidateName, ArrayList<Ballot> ballotsCast) {
        for (int i=0; i<ballotsCast.size();i++){
            ballotsCast.get(i).removeCandidate(candidateName);
            if (ballotsCast.get(i).isExhausted()){
                ballotsCast.remove(i);
                i--;
            }
            }
    }

    public static void printPercentages(HashMap<String, Integer> candidateCount) {
        System.out.println("Vote Counts");
        double totalVotes=countTotalVotes(candidateCount);
        for (String k: candidateCount.keySet()){
            double percentage=(candidateCount.get(k)/totalVotes)*100;
            System.out.print(k+": ");
            System.out.printf("%.1f",percentage);
            System.out.println("%");
        }
    }

    // DO NOT edit the methods below. These are provided to help you get started.
    public static String[] getFileContents(String fileName) {

        // first pass: determine number of lines in the file
        Scanner file = getFileScanner(fileName);
        int numLines = 0;
        while (file.hasNextLine()) {
            file.nextLine();
            numLines++;
        }

        // create array to hold the number of lines counted
        String[] contents = new String[numLines];

        // second pass: read each line into array
        file = getFileScanner(fileName);
        for (int i = 0; i < numLines; i++) {
            contents[i] = file.nextLine();
        }

        return contents;
    }


    public static Scanner getFileScanner(String fileName) {
        try {
            FileInputStream textFileStream = new FileInputStream(fileName);
            Scanner inputFile = new Scanner(textFileStream);
            return inputFile;
        }
        catch (IOException ex) {
            System.out.println("Warning: could not open " + fileName);
            return null;
        }
    }
}
