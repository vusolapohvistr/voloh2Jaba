import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        MachineFile machineFile = getParamsFromFile(args);
        FiniteStateAutomation stateAutomation = new FiniteStateAutomation(machineFile);
	    System.out.println("Hello world");
    }

    static class Rule {
        public int sFrom;
        public String through;
        public int sTo;
    }

    static class MachineFile {
        public int lengthA;
        public int lengthS;
        public int s0;
        public int lengthF;
        public ArrayList<Integer> finalStates = new ArrayList<Integer>();
        public ArrayList<Rule> rules = new ArrayList<Rule>();
    }

    static MachineFile getParamsFromFile(String[] args) {
        MachineFile answer = new MachineFile();
        try {
            File file = new File(args[0]);
            Scanner sc = new Scanner(file);

            ArrayList<String> lines = new ArrayList<>();

            while (sc.hasNextLine()) {
                lines.add(sc.nextLine());
            }

            answer.lengthA = Integer.parseInt(lines.get(0));
            answer.lengthS = Integer.parseInt(lines.get(1));
            answer.s0 = Integer.parseInt(lines.get(2));
            String[] lineNumbers = lines.get(3).split(" ");
            answer.lengthF = Integer.parseInt(lineNumbers[0]);
            for (int i = 1; i < lineNumbers.length; i++) {
                answer.finalStates.add(Integer.parseInt(lineNumbers[i]));
            }
            for (int i = 4; i < lines.size(); i++) {
                String[] lineArray = lines.get(i).split(" ");
                Rule tempRule = new Rule();
                tempRule.sFrom = Integer.parseInt(lineArray[0]);
                tempRule.through = lineArray[1];
                tempRule.sTo = Integer.parseInt(lineArray[2]);
                answer.rules.add(tempRule);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return answer;
    }

    static class FiniteStateAutomation {
        ArrayList<HashMap<String, Integer>> stateTable;
        ArrayList<Integer> finalStates;
        Integer s0 = 0;
        Integer currentState = 0;
        FiniteStateAutomation (MachineFile file) {
            System.out.println(file.lengthS);
            stateTable = new ArrayList<HashMap<String, Integer>>(file.lengthS);
            System.out.println(stateTable.size());
            finalStates = file.finalStates;
            s0 = file.s0;
            for (int i = 0; i < file.rules.size(); i++) {
                HashMap<String, Integer> tempDict = new HashMap<String, Integer>();
                tempDict.put(file.rules.get(i).through, file.rules.get(i).sTo);
                stateTable.add(file.rules.get(i).sFrom, tempDict);
            }
        }
    }
}