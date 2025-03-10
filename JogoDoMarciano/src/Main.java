import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        telaInicial();
    }

    static void telaInicial() {
        System.out.println("\n**********");
        System.out.println("Jogo do Marciano");
        System.out.println("Digite 1 para iniciar");
        System.out.println("Digite 2 para sair");
        System.out.print("\nDigite a sua opção: ");

        try {
            int opcaoEscolhidaNumerica = Integer.parseInt(scanner.nextLine());

            switch (opcaoEscolhidaNumerica) {
                case 1:
                    System.out.println("**********");
                    gameMarciano();
                    break;
                case 2:
                    System.out.println("Você digitou a opção " + opcaoEscolhidaNumerica);
                    System.out.println("Tchau tchau :)");
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida! Digite um número.");
        }
    }

    static void gameMarciano() {
        contarHistoria();
        game();
        novaPartida();
    }

    static void contarHistoria() {
        mostrarTexto("O Mistério do Marciano Perdido");
        esperarEnter();
        
        mostrarTexto("Em um planeta distante chamado Xylox, um pequeno marciano chamado Zorp se perdeu enquanto explorava a Floresta das Mil Árvores...");
        esperarEnter();

        mostrarTexto("Você é um explorador intergaláctico e recebeu uma missão muito importante: encontrar Zorp antes que a noite caia...");
        esperarEnter();

        mostrarTexto("A única pista que você tem é que Zorp está escondido em uma árvore numerada de 1 a 100...");
        esperarEnter();

        mostrarTexto("Será que você consegue encontrar o pequeno marciano antes que ele se assuste? Boa sorte, explorador! 🚀👽");
        esperarEnter();
    }

    static void game() {
        Random rand = new Random();
        int arvoreMarciano = rand.nextInt(100) + 1;
        int arvore = 0;
        int numeroTentativa = 0;
        boolean marcianoEncontrado = false;
        int tempoLimite = 10;
        int tempoEsgotadoCount = 0; // Contador de vezes que o tempo esgotou

        mostrarTexto("Adivinhe a Árvore do Marciano");

        while (!marcianoEncontrado) {
            System.out.println("\n**********");
            System.out.println("\nSua última tentativa: " + arvore);
            System.out.println("Número de Tentativas: " + numeroTentativa);
            System.out.println("Você tem " + tempoLimite + " segundos para responder!");
            System.out.print("Digite um número da árvore onde o Marciano possa estar: ");

            String opcaoEscolhida = lerComTimeout(tempoLimite);

            if (opcaoEscolhida == null) {
                tempoEsgotadoCount++;
                System.out.println("\n⏳ Tempo esgotado! O Marciano fugiu para outra árvore!");
                
                // Se o tempo esgotar 3 vezes seguidas, perguntar se o jogador quer continuar
                if (tempoEsgotadoCount >= 3) {
                    System.out.print("\nVocê perdeu muito tempo! Deseja continuar? (s/n): ");
                    
                    scanner = new Scanner(System.in); // Reinicializa o scanner para evitar erro

                    String resposta = scanner.nextLine().toLowerCase();
                    
                    if (resposta.equals("n")) {
                        System.out.println("O Marciano escapou para sempre... Fim de jogo.");
                        return;
                    } else {
                        tempoEsgotadoCount = 0; // Resetar contador
                    }
                }
                continue;
            }

            try {
                arvore = Integer.parseInt(opcaoEscolhida);

                if (arvore < 1 || arvore > 100) {
                    System.out.println("Entrada inválida! Digite um número entre 1 e 100.");
                    continue;
                }

                numeroTentativa++;
                tempoEsgotadoCount = 0; // Resetar contador caso o jogador responda

                if (arvore == arvoreMarciano) {
                    System.out.println("🎉 Parabéns! O Marciano estava na árvore " + arvoreMarciano + ".");
                    System.out.println("Você encontrou em " + numeroTentativa + " tentativas.");
                    marcianoEncontrado = true;
                } else if (arvore > arvoreMarciano) {
                    System.out.println("O Marciano está em uma árvore menor!");
                } else {
                    System.out.println("O Marciano está em uma árvore maior!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número entre 1 e 100.");
            }
        }
    }

    static void novaPartida() {
        boolean iraSair = false;

        while (!iraSair) {
            System.out.print("\nGostaria de Jogar Novamente? s/n: ");
            
            scanner = new Scanner(System.in); // Reinicializa o scanner para evitar erro
            String opcaoEscolhida = scanner.nextLine().toLowerCase();

            if (opcaoEscolhida.equals("s")) {
                System.out.println("\n**********");
                game();
            } else if (opcaoEscolhida.equals("n")) {
                System.out.println("Até um outro dia.");
                iraSair = true;
            } else {
                System.out.println("Opção inválida!");
            }
        }
    }

    static void mostrarTexto(String texto) {
        for (char letra : texto.toCharArray()) {
            System.out.print(letra);
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    static void esperarEnter() {
        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }

    static String lerComTimeout(int tempoLimite) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> scanner.nextLine());

        try {
            return future.get(tempoLimite, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            executor.shutdown();
        }
    }
}
