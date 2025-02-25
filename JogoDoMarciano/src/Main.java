import java.util.Random;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Random rand = new Random();
        Scanner scanner = new Scanner(System.in);

        int arvoreMarciano = rand.nextInt(100) + 1; 
        int arvore = 0;
        int numeroTentativa = 0;
        boolean marcianoEncontrado = false;

        while (!marcianoEncontrado) {
            System.out.println("Adivinhe a Árvore do Marciano");
            System.out.println("Sua última tentativa: " + arvore);
            System.out.println("Número de Tentativas: " + numeroTentativa);
            System.out.print("Digite um número da árvore onde o Marciano possa estar: \n");
            
            
            try {
                arvore = Integer.parseInt(scanner.nextLine());

                if (arvore == arvoreMarciano) {
                    System.out.println("Parabéns! O Marciano estava na árvore: " + arvoreMarciano);
                    marcianoEncontrado = true;
                } else if (arvore > arvoreMarciano) {
                    System.out.println("O Marciano está em uma árvore menor!");
                    System.out.println("*******************************************************");
                    numeroTentativa++;
                    Thread.sleep(2000);                
                } else {
                    System.out.println("O Marciano está em uma árvore maior!");
                    System.out.println("*******************************************************");
                    numeroTentativa++;
                    Thread.sleep(2000);
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        scanner.close();
    }

    

}
