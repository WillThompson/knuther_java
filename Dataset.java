import java.util.HashMap;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Dataset{
	
	// A Dataset needs to have a filename and some summary information about it
	private HashMap<String,Integer> input_stats = new HashMap<String,Integer>();
	public File datafile;

	public Dataset(File f){

		datafile = f;
		// Perform some preprocessing
		try{
			BufferedReader reader = get_new_data_reader();
			int size = 0;

			String line = reader.readLine();
			int i = Integer.parseInt(line);
			size++;
			int min = i;
			int max = i;
			while((line = reader.readLine()) != null){

				i = Integer.parseInt(line);
				size++;
				min = Math.min(min,i);
				max = Math.max(i,max);
			}

			input_stats.put("Min",min);
			input_stats.put("Max",max);
			input_stats.put("Size",size);

		}
		catch (IOException e)
		{	
		    System.err.println("Error: " + e.getMessage()); // handle exception
		}

	}

	public HashMap<String,Integer> get_input_stats(){
		return input_stats;
	}

	public BufferedReader get_new_data_reader(){
		try{
			return new BufferedReader(new FileReader(datafile));
		}
		catch(FileNotFoundException e){
			System.err.println("Error: " + e.getMessage());
		}
		return null;
	}

	// Have this here for troubleshooting.
	public static void main(String[] args){

		File file = new File(args[0]);
		Dataset dat = new Dataset(file);
		System.out.println(dat.get_input_stats().get("Max"));
		System.out.println(dat.get_input_stats().get("Min"));
		System.out.println(dat.get_input_stats().get("Size"));

	}



}