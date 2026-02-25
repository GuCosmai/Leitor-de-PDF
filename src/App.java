import services.ApoliceExtratorService;
import models.Apolice;
import models.Segurado;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("===== LEITOR DE APÓLICES - SICS =====");
        System.out.println("Sistema para extração de dados de PDFs de apólices de seguros\n");
        
        ApoliceExtratorService extrator = new ApoliceExtratorService();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Extrair dados de um PDF");
            System.out.println("2. Informações sobre um PDF");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            
            String opcao = scanner.nextLine().trim();
            
            switch (opcao) {
                case "1":
                    extrairDadosPDF(extrator, scanner);
                    break;
                case "2":
                    informacoesPDF(extrator, scanner);
                    break;
                case "3":
                    System.out.println("\nEncerrando aplicação...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }
    
    private static void extrairDadosPDF(ApoliceExtratorService extrator, Scanner scanner) {
        System.out.print("\nDigite o caminho do arquivo PDF: ");
        String caminhoArquivo = scanner.nextLine().trim();
        
        try {
            Apolice apolice = extrator.extrairApolice(caminhoArquivo);
            
            System.out.println("\n===== DADOS EXTRAÍDOS =====");
            System.out.println("\n--- Informações da Apólice ---");
            System.out.println("Tipo: " + apolice.getTipo());
            System.out.println("Número: " + apolice.getNumero());
            System.out.println("Seguradora: " + apolice.getSeguradora());
            System.out.println("Prêmio: " + apolice.getPremio() + " " + apolice.getMoeda());
            System.out.println("Vigência: " + apolice.getDataVigenciaInicio() + " até " + apolice.getDataVigenciaFim());
            
            if (apolice.getSegurado() != null) {
                Segurado segurado = apolice.getSegurado();
                System.out.println("\n--- Informações do Segurado ---");
                System.out.println("Nome: " + segurado.getNome());
                System.out.println("CPF: " + segurado.getCpf());
                System.out.println("CNPJ: " + segurado.getCnpj());
                System.out.println("Email: " + segurado.getEmail());
            }
            
            System.out.println("\n===== DADOS ADICIONAIS =====");
            System.out.println(apolice.getDadosAdicionais());
            
        } catch (Exception e) {
            System.out.println("Erro ao processar PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void informacoesPDF(ApoliceExtratorService extrator, Scanner scanner) {
        System.out.print("\nDigite o caminho do arquivo PDF: ");
        String caminhoArquivo = scanner.nextLine().trim();
        
        try {
            String informacoes = extrator.obterInformacoesPDF(caminhoArquivo);
            System.out.println("\n" + informacoes);
        } catch (Exception e) {
            System.out.println("Erro ao obter informações: " + e.getMessage());
        }
    }
}