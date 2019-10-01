import java.util.*;

public class GeneticAlgorithm {
    private final int populationSize = 300;
    private final String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz !.?";
    private final String goalString = "Welcome to CS547!";
    private int generationCount;
    private ArrayList<String> population;
    private ArrayList<String> matingPool;
    private Random rg = new Random();
    private String bestMatch;
    private int bestScore;

    public GeneticAlgorithm() {
        bestMatch = "";
        bestScore = 0;
        generationCount = 0;
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
        }
        System.out.println("Fond string: \"" + bestMatch + "\" in generation: " + generationCount);
    }

    private boolean found() {
        for (String s : population) {
            int fitnessScore = fitnessFunction(s);
            if (fitnessScore > bestScore) {
                bestScore = fitnessScore;
                bestMatch = s;
                System.out.println("Best so far: " + bestMatch + " Fitness score: " + fitnessScore + " Generation: " + generationCount);
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
                if (rank > bestScore - 1) { // allow only best to enter the pool, also make sure that the best one has the best chance to mate
                    matingPool.add(dna);
                }
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
                if (rg.nextInt(100) == 1) { // 1 in 100 chance of mutation
                    offspring.append(characters.charAt(rg.nextInt(characters.length())));
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
                sb.append(characters.charAt(rg.nextInt(characters.length())));
            }
            population.add(sb.toString());
        }
        return population;
    }

    private int fitnessFunction(String candidate) {
        int score = 0;
        for (int i = 0; i < goalString.length(); i++) {
            if (candidate.charAt(i) == goalString.charAt(i)) score++;
        }
        return score;
    }
}
