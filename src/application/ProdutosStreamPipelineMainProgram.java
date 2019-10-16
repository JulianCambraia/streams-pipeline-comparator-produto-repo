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

import model.entities.Produto;

public class ProdutosStreamPipelineMainProgram {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		String path = "/tmp/produto.csv";
		List<Produto> listaProd = new ArrayList<Produto>();
		List<String> nomes = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String linha = br.readLine();
			while (linha != null) {
				String[] campos = linha.split(",");
				String nome = campos[0];
				Double preco = Double.parseDouble(campos[1]);
				listaProd.add(new Produto(nome, preco));
				linha = br.readLine();
			}
			Comparator<String> predProd = (s1, s2) -> s1.compareTo(s2);

			double avg = listaProd.stream().map(p -> p.getPreco()).reduce(0.0, (x, y) -> x + y) / listaProd.size();
			System.out.println("Média dos preços é: " + String.format("%.2f", avg));
			nomes = listaProd.stream().filter(p -> p.getPreco() < avg).map(x -> x.getNome()).sorted(predProd.reversed())
					.collect(Collectors.toList());
			nomes.forEach(p -> {
				System.out.println("Produtos: " + p);
			});

		} catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		sc.close();

	}

}
