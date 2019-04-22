import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Checkpoint {
	public static void main(String[] args) {
		/*
		 * The auto-mgp.data file contains the miles-per-gallon data on various different types of cars.
		 * Each line of the file provides the data for one CarMPGEntry object.
		 * (source: http://archive.ics.uci.edu/ml/datasets)
		 * 
		 * Use the method provided to get an ArrayList of CarMPGEntry objects. Convert the ArrayList into a Stream.
		 * Using streams, perform the following:
		 * 
		 * 1. Print the entire list.
		 * 
		 * 
		 * 2. Print the miles per gallon of each entry.
		 * 
		 * 
		 * 3. Print alphabetized car names of the list.
		 * 
		 * 
		 * 4. Print the list with all 8 cylinder cars removed.
		 * 
		 * 
		 * 5. Print only the cars with "toyota" in the name.
		 */
		
		ArrayList<CarMPGEntry> arr = readCarMPGEntryDataFromFile();
		System.out.println("1.");
		
		Stream<CarMPGEntry> s = arr.stream();
		s.forEach((entry) -> {
			System.out.println(entry.carName);
		});
		System.out.println("\n2.");
		
		s = arr.stream();
		s.forEach((entry) -> {
			System.out.println(entry.carName + " " + entry.mpg);
		});
		System.out.println("\n3.");
		
		s = arr.stream();
		ArrayList<String> names = new ArrayList<String>();
		s.forEach((entry) -> {
			names.add(entry.carName);
		});
		Stream<String> sortedNames = names.stream().sorted();
		sortedNames.forEach((name) -> {
			System.out.println(name);
		});
		System.out.println("\n4.");
		
		s = arr.stream();
		ArrayList<String> removed = new ArrayList<String>();
		s.forEach((entry) -> {
			if (entry.cylinders != 8)
				removed.add(entry.carName);
		});
		for (String str : removed)
			System.out.println(str);
		System.out.println("\n5.");
		
		s = arr.stream();
		ArrayList<String> toyota = new ArrayList<String>();
		s.forEach((entry) -> {
			if (entry.carName.contains("toyota"))
				toyota.add(entry.carName);
		});
		for (String str : toyota)
			System.out.println(str);
		
	}
	
	public static ArrayList<CarMPGEntry> readCarMPGEntryDataFromFile(){
		ArrayList<CarMPGEntry> carList = new ArrayList<CarMPGEntry>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("auto-mpg.data"));
			
			String line = br.readLine();
			while(line != null) {
				String entry = "";
				ArrayList<String> splitEntry = new ArrayList<String>();
				for(int i = 0; i < line.length(); i++) {
					char c = line.charAt(i);
					if(c == ' ' || c == '\t') {
						if(entry.length() > 0) {
							splitEntry.add(entry);
							entry = "";
						}
					}else if(c =='\"') {
						i++;
						c = line.charAt(i);
						while(c != '\"') {
							entry += c;
							c = line.charAt(++i);
						}
						splitEntry.add(entry);
					}else {
						entry += c;
					}
				}
				
				CarMPGEntry cmpg = new CarMPGEntry();
				
				cmpg.mpg = Float.parseFloat(splitEntry.get(0));
				cmpg.cylinders = Integer.parseInt(splitEntry.get(1));
				cmpg.displacement = Float.parseFloat(splitEntry.get(2));
				try {
					cmpg.horsePower = Float.parseFloat(splitEntry.get(3));
					
				}catch(NumberFormatException e) {
					cmpg.horsePower = -1;
				}
				cmpg.weight = Float.parseFloat(splitEntry.get(4));
				cmpg.acceleration = Float.parseFloat(splitEntry.get(5));
				cmpg.modelYear  = Integer.parseInt(splitEntry.get(6));
				cmpg.origin = Integer.parseInt(splitEntry.get(7));
				cmpg.carName = splitEntry.get(8);
				carList.add(cmpg);

				line = br.readLine();
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return carList;
	}
}
