package sample.ga;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Population {

    private List<ChromosomePair> chromosomePairs;

    public Population(int size, boolean createNew) {
        chromosomePairs = new ArrayList<>();
        if (createNew) {
            createNewPopulation(size);
        }
    }

    private void createNewPopulation(int size) {
        for (int i = 0; i < size; i++) {
            Chromosome chromosomeX = new Chromosome();
            Chromosome chromosomeY = new Chromosome();
            ChromosomePair chromosomePair = new ChromosomePair(chromosomeX, chromosomeY);
            chromosomePairs.add(i, chromosomePair);
        }
    }

    public ChromosomePair getChromosomePair(int index) {
        return chromosomePairs.get(index);
    }

    public ChromosomePair getBest() {
        ChromosomePair best = chromosomePairs.get(0);
        for (int i = 1; i < chromosomePairs.size(); i++) {
            if (getChromosomePair(i).getFitness() > best.getFitness()) {
                best = getChromosomePair(i);
            }
        }
        return best;
    }

}
