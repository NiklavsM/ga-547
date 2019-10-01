import java.util.Random;

public class RandomAlgorithm {
    private final String goalString = "Welcome to CS547!";
    private Random rg = new Random();

    public RandomAlgorithm() {
        generateGoalString();
    }

    private void generateGoalString() {
        String candidateString = "";
        while (!candidateString.equals(goalString)) {
            candidateString = generateRandomString();
        }
        System.out.println("FOUND");
    }

    private String generateRandomString() {
        StringBuilder sb = new StringBuilder();
        int goalStringLength = goalString.length();
        for (int i = 0; i < goalStringLength; i++) {
            sb.append(goalString.charAt(rg.nextInt(goalStringLength)));
        }
        return sb.toString();

    }

}
