package services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.IOException;

public class PDFReaderService {
    
    /**
     * Lê um arquivo PDF e extrai todo o texto copiável
     * @param caminhoArquivo Caminho completo do arquivo PDF
     * @return String contendo todo o texto extraído
     * @throws IOException Se houver erro ao ler o arquivo
     */
    public String lerPDF(String caminhoArquivo) throws IOException {
        File arquivo = new File(caminhoArquivo);
        
        if (!arquivo.exists()) {
            throw new IOException("Arquivo PDF não encontrado: " + caminhoArquivo);
        }
        
        if (!arquivo.getName().toLowerCase().endsWith(".pdf")) {
            throw new IOException("Arquivo não é um PDF: " + caminhoArquivo);
        }
        
        PDDocument documento = null;
        try {
            documento = PDDocument.load(arquivo);
            
            if (documento.isEncrypted()) {
                throw new IOException("PDF está criptografado. Não é possível extrair texto.");
            }
            
            PDFTextStripper stripper = new PDFTextStripper();
            String texto = stripper.getText(documento);
            
            return texto;
            
        } finally {
            if (documento != null) {
                documento.close();
            }
        }
    }
    
    /**
     * Lê um arquivo PDF e extrai o texto de uma página específica
     * @param caminhoArquivo Caminho completo do arquivo PDF
     * @param numeroPagina Número da página (começando em 1)
     * @return String contendo o texto da página
     * @throws IOException Se houver erro ao ler o arquivo
     */
    public String lerPaginaPDF(String caminhoArquivo, int numeroPagina) throws IOException {
        File arquivo = new File(caminhoArquivo);
        
        if (!arquivo.exists()) {
            throw new IOException("Arquivo PDF não encontrado: " + caminhoArquivo);
        }
        
        PDDocument documento = null;
        try {
            documento = PDDocument.load(arquivo);
            
            if (documento.isEncrypted()) {
                throw new IOException("PDF está criptografado. Não é possível extrair texto.");
            }
            
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(numeroPagina);
            stripper.setEndPage(numeroPagina);
            
            String texto = stripper.getText(documento);
            
            return texto;
            
        } finally {
            if (documento != null) {
                documento.close();
            }
        }
    }
    
    /**
     * Obtém o número total de páginas de um PDF
     * @param caminhoArquivo Caminho completo do arquivo PDF
     * @return Número de páginas
     * @throws IOException Se houver erro ao ler o arquivo
     */
    public int obterNumericoPaginas(String caminhoArquivo) throws IOException {
        File arquivo = new File(caminhoArquivo);
        
        if (!arquivo.exists()) {
            throw new IOException("Arquivo PDF não encontrado: " + caminhoArquivo);
        }
        
        PDDocument documento = null;
        try {
            documento = PDDocument.load(arquivo);
            return documento.getNumberOfPages();
            
        } finally {
            if (documento != null) {
                documento.close();
            }
        }
    }
}
