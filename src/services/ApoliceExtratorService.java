package services;

import models.Apolice;
import parsers.ApoliceParser;
import java.io.IOException;

public class ApoliceExtratorService {
    
    private PDFReaderService pdfReaderService;
    
    public ApoliceExtratorService() {
        this.pdfReaderService = new PDFReaderService();
    }
    
    /**
     * Extrai os dados de uma apólice a partir de um arquivo PDF
     * @param caminhoArquivoPDF Caminho completo do arquivo PDF
     * @return Objeto Apolice com dados extraídos
     * @throws IOException Se houver erro ao ler o arquivo PDF
     */
    public Apolice extrairApolice(String caminhoArquivoPDF) throws IOException {
        System.out.println("Lendo arquivo PDF: " + caminhoArquivoPDF);
        
        // Lê o conteúdo do PDF
        String textoExtraido = pdfReaderService.lerPDF(caminhoArquivoPDF);
        
        System.out.println("PDF lido com sucesso. Iniciando parse dos dados...");
        
        // Faz o parse do texto extraído
        ApoliceParser parser = new ApoliceParser(textoExtraido);
        Apolice apolice = parser.parse();
        
        System.out.println("Dados extraídos com sucesso!");
        
        return apolice;
    }
    
    /**
     * Extrai os dados de uma apólice a partir de uma página específica do PDF
     * @param caminhoArquivoPDF Caminho completo do arquivo PDF
     * @param numeroPagina Número da página (começando em 1)
     * @return Objeto Apolice com dados extraídos
     * @throws IOException Se houver erro ao ler o arquivo PDF
     */
    public Apolice extrairApoliceDeUmaPagina(String caminhoArquivoPDF, int numeroPagina) throws IOException {
        System.out.println("Lendo página " + numeroPagina + " do arquivo PDF: " + caminhoArquivoPDF);
        
        String textoExtraido = pdfReaderService.lerPaginaPDF(caminhoArquivoPDF, numeroPagina);
        
        System.out.println("Página lida com sucesso. Iniciando parse dos dados...");
        
        ApoliceParser parser = new ApoliceParser(textoExtraido);
        Apolice apolice = parser.parse();
        
        System.out.println("Dados extraídos com sucesso!");
        
        return apolice;
    }
    
    /**
     * Obtém informações sobre um arquivo PDF
     * @param caminhoArquivoPDF Caminho completo do arquivo PDF
     * @return String com informações do PDF
     * @throws IOException Se houver erro ao ler o arquivo PDF
     */
    public String obterInformacoesPDF(String caminhoArquivoPDF) throws IOException {
        int numeroPaginas = pdfReaderService.obterNumericoPaginas(caminhoArquivoPDF);
        return "Arquivo: " + caminhoArquivoPDF + "\nNúmero de páginas: " + numeroPaginas;
    }
}
