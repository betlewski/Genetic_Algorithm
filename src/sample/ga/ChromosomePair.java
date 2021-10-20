package sample.ga;

import lombok.AllArgsConstructor;
import lombok.Data;
import sample.utils.FunctionUtils;

@Data
@AllArgsConstructor
public class ChromosomePair {

    private Chromosome chromosomeX;
    private Chromosome chromosomeY;

    public double getFitness() {
        double valueX = this.chromosomeX.getDecimalValue();
        double valueY = this.chromosomeY.getDecimalValue();
        return FunctionUtils.getFunctionFitness(valueX, valueY);
    }

    public double getFunctionValue() {
        double valueX = this.chromosomeX.getDecimalValue();
        double valueY = this.chromosomeY.getDecimalValue();
        return FunctionUtils.getValueInPoint(valueX, valueY);
    }

    public ChromosomePair clone() {
        Chromosome chromosomeX = new Chromosome();
        chromosomeX.setGenes(this.chromosomeX.getGenes().clone());
        Chromosome chromosomeY = new Chromosome();
        chromosomeY.setGenes(this.chromosomeY.getGenes().clone());
        return new ChromosomePair(chromosomeX, chromosomeY);
    }

}
