package evolution;

import java.util.ArrayList;
import java.util.Comparator;

public class Evolution {
	static int population = 100;
	static double elitismRate = 0.2;
	static double seedRate = 0.2;
	
	public ArrayList <Genome> genomes;
	
	int inputs;
	int[] hidden;
	int outputs;
	
	public Evolution(int inputs, int[] hidden, int outputs) {
		genomes = new ArrayList <Genome> ();
		
		this.inputs = inputs;
		this.hidden = hidden;
		this.outputs = outputs;
		
		for (int i = 0 ; i < population ; i++) {
			genomes.add(new Genome(inputs, hidden, outputs));
		}
	}
	
	public Genome createGenome() {
		return new Genome(inputs, hidden, outputs);
	}
	public void sort() {
		genomes.sort(new Comparator <Genome> () {
			public int compare(Genome a, Genome b) {
				return b.score - a.score;
			} 
		});
	}
	public void evolve() {
		// Sorting the genomes into order
		genomes.sort(new Comparator <Genome> () {
			public int compare(Genome a, Genome b) {
				return b.score - a.score;
			} 
		});
		
		ArrayList <Genome> next = new ArrayList <Genome> ();
		
		for (int i = 0 ; i < elitismRate * population ; i++) {
			next.add(genomes.get(i));
		}
		genomes = next;
		
		for (int i = 0 ; i < seedRate * population ; i++) {
			next.add(createGenome());
		}
		
		while (next.size() < population) {
			for (int i = 0 ; i < next.size() ; i++) {
				for (int j = 0 ; j <= i ; j++) {
					next.add(new Genome(next.get(i), next.get(j)));
					
					if (next.size() >= population) {
						return;
					}
				}
			}
		}
	}
}