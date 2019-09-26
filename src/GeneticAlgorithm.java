import java.util.*;

public class GeneticAlgorithm {
    private final int populationSize = 100;
    private final String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz !.?";
    private final String goalString = "Welcome to CS547!";
    private final double chanceOfMutation = 0.01;
    private int generationCount = 0;
    private ArrayList<String> population;
    private ArrayList<String> matingPool;
    private Random rg = new Random();
    private String bestMatch = "";
    private int bestScore = 0;

    public GeneticAlgorithm() {
        population = getInitialPopulation();
        evolve();
    }

    private void evolve() {
        while (!found()) {
            generationCount++;
            Map<String, Integer> rankedPop = new HashMap<>();
            population.forEach(s -> rankedPop.put(s, fitnessFunction(s)));
            generateMatingPool(rankedPop);
            generateNewPopulation();

            System.out.println("Best so far: " + bestMatch);
        }
        System.out.println("Fond string: \"" + bestMatch + "\" in generation: " + generationCount);
    }

    private boolean found() {
        for (String s : population) {
            int fitnessScore = fitnessFunction(s);
            if (fitnessScore > bestScore) {
                bestScore = fitnessScore;
                bestMatch = s;
            }
            if (fitnessScore == goalString.length()) return true;
        }
        return false;
    }

    // creates matting pool where the fittest dna has the best chances of getting picked
    private void generateMatingPool(Map<String, Integer> rankedPop) {
        matingPool = new ArrayList<>();
        rankedPop.forEach((dna, rank) -> {
            for (int i = 0; i < rank; i++) {
                matingPool.add(dna);
            }
        });
    }

    private void generateNewPopulation() {
        population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            StringBuilder offspring = new StringBuilder();
            String parentA = matingPool.get(rg.nextInt(matingPool.size()));
            String parentB = matingPool.get(rg.nextInt(matingPool.size()));
            for (int k = 0; k < goalString.length(); k++) {
                if (rg.nextInt(100) == 99) {
                    offspring.append(goalString.charAt(rg.nextInt(goalString.length())));
                } else {
                    if (k < goalString.length() / 2) {
                        offspring.append(parentA.charAt(k));
                    } else {
                        offspring.append(parentB.charAt(k));
                    }
                }
            }
            population.add(offspring.toString());

        }
    }

    private ArrayList<String> getInitialPopulation() {
        ArrayList<String> population = new ArrayList<>();
        Random rg = new Random();
        for (int i = 0; i < populationSize; i++) {
            StringBuilder sb = new StringBuilder();
            for (int k = 0; k < goalString.length(); k++) {
                sb.append(goalString.charAt(rg.nextInt(goalString.length())));
            }
            population.add(sb.toString());
        }
//        population.forEach(p -> System.out.println(p + fitnessFunction(p)));
        return population;
    }

    private ArrayList<String> createNewGeneration(ArrayList<String> parents) {
        ArrayList<String> newGeneration = new ArrayList<>();
        return newGeneration;
    }

    private int fitnessFunction(String candidate) {
        int score = 0;
        for (int i = 0; i < goalString.length(); i++) {
            if (candidate.charAt(i) == goalString.charAt(i)) score++;
        }
        return score;
    }
}
