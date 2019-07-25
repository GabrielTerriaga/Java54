package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.entities.Product;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter the file path: ");
		String path = sc.nextLine();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))){
			
			List<Product> list = new ArrayList<>();
			
			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Product(fields[0], Double.parseDouble(fields[1])));
				line = br.readLine();
			}
			
			//preco medio de todos os produtos
			double avg = list.stream()
					.map(p -> p.getPrice())
					.reduce(0.0, (x,y) -> x + y) / list.size();
			//mostra preco medio
			System.out.println("Average price: " + String.format("%.2f", avg));
			
			//comparar dois strings
			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
			
			//pipeline para realizar a operacao de ordenar os produtos em ordem alfabetica decrescente
			List<String> names = list.stream()
					.filter(p -> p.getPrice() < avg) //preco menor que a media
					.map(p -> p.getName()) //uma nova stream com os nomes
					.sorted(comp.reversed()) //usando o sorted com o comparator de ordem alfabetica e revertendo para decrescente
					.collect(Collectors.toList()); //convertendo para lista
			names.forEach(System.out::println); //mostrando
		}
		catch(IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		sc.close();
	}

}
