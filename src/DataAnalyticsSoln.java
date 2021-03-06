import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.SortedMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.Arrays;
import java.util.ArrayList;


public class DataAnalyticsSoln {
	
	File inFile;
	FileReader inReader;
	BufferedReader inBufReader;
	File outFile;
	FileWriter outWriter;
	BufferedWriter outBufWriter;
	SortedMap<String, Integer> wordCount;

	public void analyseData(String fileSource, String outputDir) {
		
		int lineNumber = 0;
		String line = "";
		String word;
		String[] words;
		ArrayList<Double> medians = new ArrayList<>();
		wordCount = new TreeMap<>();
		int uniqueWordCount;
		int uniqueWordSum = 0;
		
		
		if(fileSource == null || fileSource.isEmpty()) {
			System.out.println("Enter a correct file source");
			return;
		}
		inFile = new File(fileSource);
		
		try{
			inReader = new FileReader(inFile);
		} catch(FileNotFoundException fofExc) {
			System.out.println("File source does not exist");
			return;
		}
		inBufReader = new BufferedReader(inReader);
		
		do {
			try{
				line = inBufReader.readLine();
			}catch(IOException ioExc) {
				System.out.println("IOException occurred while reading file");
				break;
			}
			
			if(line != null && !line.equals("")) {
				lineNumber++;
				words = line.split(" ");
				uniqueWordCount = new HashSet<String>(Arrays.asList(words)).size();
				uniqueWordSum += uniqueWordCount;
				
				medians.add((double)(uniqueWordSum) / lineNumber);
				
				for(int x = 0; x < words.length; x++) {
					word = words[x];
					
					if(wordCount.containsKey(word)) {
						wordCount.put(word, wordCount.get(word) + 1);
					} else {
						wordCount.put(word, 1);
					}
				}
			}
		}while(line != null);
		releaseInputResources();
		
		outFile = new File(outputDir + "ft1.txt");
		try{
			outWriter = new FileWriter(outFile);
		}catch(IOException ioExc) {
			System.out.println("IOException occurred while creating output file");
		}
		
		outBufWriter = new BufferedWriter(outWriter);
		for(String str : wordCount.keySet()) {
			try{
				outBufWriter.write(String.format("%-50s %d",str, wordCount.get(str)) + "\r\n");
			}catch(IOException ioExc) {
				System.out.println("IOException occurred while writing to output file");
			}
		}
		releaseOutputResources();
		outFile = new File(outputDir + "ft2.txt");
		try{
			outWriter = new FileWriter(outFile);
		}catch(IOException ioExc) {
			System.out.println("IOException occurred while creating output file");
		}
		
		outBufWriter = new BufferedWriter(outWriter);
		for(int x = 0; x < medians.size(); x++) {
			try{
				outBufWriter.write(String.valueOf(medians.get(x).toString()) + "\r\n");
			}catch(IOException ioExc) {
				System.out.println("IOException occurred while writing to output file");
			}
		}
		releaseOutputResources();
		
	}
	
	public void releaseOutputResources(){
		
		try{
			outBufWriter.flush();
		}catch(IOException ioExc) {}
		try{
			outBufWriter.close();
		}catch(IOException ioExc) {}
		try{
			outWriter.close();
		}catch(IOException ioExc) {}
	}
	public void releaseInputResources(){
		
		try{
			inBufReader.close();
		}catch(IOException ioExc) {}
		try{
			inReader.close();
		}catch(IOException ioExc) {}
	}
	
	
	public static void main(String[] args) {
		DataAnalyticsSoln dataSoln = new DataAnalyticsSoln();
		dataSoln.analyseData("..\\tweet_input\\tweets.txt", "..\\tweet_output\\");
	}
}
