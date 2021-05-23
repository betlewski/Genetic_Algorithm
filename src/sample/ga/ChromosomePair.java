package sample.ga;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChromosomePair {

    private Chromosome chromosomeX;
    private Chromosome chromosomeY;

    public double getFunctionValue() {
        return GeneticAlgorithm.getFunctionValue(this);
    }

}
