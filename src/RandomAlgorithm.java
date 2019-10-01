import java.util.Random;

public class RandomAlgorithm {
    private final String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz !.?";
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
        int charactersLength = characters.length();
        for (int i = 0; i < charactersLength; i++) {
            sb.append(characters.charAt(rg.nextInt(charactersLength)));
        }
        return sb.toString();

    }

}
