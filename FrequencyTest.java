import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;

public class FrequencyTest{
	
	int[] counts;
	double[] expected_counts;
	double p_value;
	double test_statistic;

	public FrequencyTest(Dataset data){
		performCounts(data);
		set_expected_counts(data);
		compute_test_stat();
		compute_p_value(data);
	}

	public void performCounts(Dataset data){

		//Create the empty array
		int size = data.get_input_stats().get("Size");
		int range = data.get_input_stats().get("Max") - data.get_input_stats().get("Min") + 1;
		counts = new int[range];
		Arrays.fill(counts, 0);

		//Read through the dataset and increment the values of the array accordingly.
		try{
			BufferedReader reader = data.get_new_data_reader();
			for (int k = 0; k < size;k++){
				counts[Integer.parseInt(reader.readLine())]++;
			}
		} catch(IOException e){	
		    System.err.println("Error: " + e.getMessage()); // handle exception
		}
	}

	public void set_expected_counts(Dataset data){
		int range = data.get_input_stats().get("Max") - data.get_input_stats().get("Min") + 1;
		expected_counts = new double[range];
		Arrays.fill(expected_counts, data.get_input_stats().get("Size")/range);

	}

	public void compute_p_value(Dataset data){

		int range = data.get_input_stats().get("Max") - data.get_input_stats().get("Min") + 1;
		ChiSquaredDistribution chi = new ChiSquaredDistribution(range - 1);
		p_value = 1 - chi.cumulativeProbability(0,test_statistic);

	}

	public void compute_test_stat(){

		test_statistic = 0;
		for (int k = 0; k < expected_counts.length; k++){
			test_statistic += (counts[k] - expected_counts[k])*(counts[k] - expected_counts[k])/expected_counts[k];
		}

	}

	// Have this here for troubleshooting.
	public static void main(String[] args){

		File f = new File(args[0]);
		Dataset dat = new Dataset(f);
		FrequencyTest ft = new FrequencyTest(dat);
		System.out.println(ft.test_statistic);
		System.out.println(ft.p_value);

	}

}