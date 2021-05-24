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

    public ChromosomePair clone() {
        Chromosome chromosomeX = new Chromosome();
        chromosomeX.setGenes(this.chromosomeX.getGenes());
        Chromosome chromosomeY = new Chromosome();
        chromosomeY.setGenes(this.chromosomeY.getGenes());
        return new ChromosomePair(chromosomeX, chromosomeY);
    }

}
