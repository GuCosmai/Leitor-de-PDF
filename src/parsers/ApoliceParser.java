package parsers;

import models.Apolice;
import models.Segurado;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApoliceParser {
    
    private String textoExtraido;
    private Apolice apolice;
    
    public ApoliceParser(String textoExtraido) {
        this.textoExtraido = textoExtraido;
        this.apolice = new Apolice();
    }
    
    /**
     * Realiza o parse completo do texto extraído
     * @return Objeto Apolice com dados extraídos
     */
    public Apolice parse() {
        extrairNumeroApolice();
        extrairSegurador();
        extrairTipoDocumento();
        extrairDadosSegurado();
        extrairDatasVigencia();
        extrairPremio();
        extrairDadosAdicionais();
        
        return apolice;
    }
    
    /**
     * Extrai o número da apólice usando padrões comuns
     */
    private void extrairNumeroApolice() {
        // Padrões comuns para números de apólice
        String[] padroes = {
                "(?i)apólice[:\\s]+([0-9]{6,10})",  // Apólice: 123456
                "(?i)n[úu]mero\\s+(?:de\\s+)?apólice[:\\s]+([0-9]{6,10})",
                "(?i)policy\\s+number[:\\s]+([0-9]{6,10})",
                "(?i)apolice[:\\s]+([0-9]{6,10})"
        };
        
        for (String padrao : padroes) {
            Pattern pattern = Pattern.compile(padrao);
            Matcher matcher = pattern.matcher(textoExtraido);
            if (matcher.find()) {
                apolice.setNumero(matcher.group(1));
                break;
            }
        }
    }
    
    /**
     * Extrai o nome da seguradora
     */
    private void extrairSegurador() {
        String[] seguradoras = {
                "Allianz", "Porto Seguro", "MAPFRE", "Bradesco", "BB",
                "Sul América", "Seguros Unimed", "HDI", "Itaú", "Zurich",
                "Liberty", "Chubb", "Generali", "Sompo", "AON"
        };
        
        for (String seguradora : seguradoras) {
            if (textoExtraido.toLowerCase().contains(seguradora.toLowerCase())) {
                apolice.setSeguradora(seguradora);
                break;
            }
        }
    }
    
    /**
     * Extrai o tipo de documento (APÓLICE, ENDOSSO ou PROPOSTA)
     */
    private void extrairTipoDocumento() {
        if (textoExtraido.toLowerCase().contains("endosso")) {
            apolice.setTipo("ENDOSSO");
        } else if (textoExtraido.toLowerCase().contains("proposta")) {
            apolice.setTipo("PROPOSTA");
        } else {
            apolice.setTipo("APÓLICE");
        }
    }
    
    /**
     * Extrai dados do segurado (nome, CPF, documento)
     */
    private void extrairDadosSegurado() {
        Segurado segurado = new Segurado();
        
        // Extrai CPF
        String cpf = extrairCPF();
        if (cpf != null) {
            segurado.setCpf(cpf);
        }
        
        // Extrai CNPJ
        String cnpj = extrairCNPJ();
        if (cnpj != null) {
            segurado.setCnpj(cnpj);
        }
        
        // Extrai nome (primeiras linhas após "segurado" ou "cliente")
        String nome = extrairNomeSegurado();
        if (nome != null) {
            segurado.setNome(nome);
        }
        
        // Extrai email
        String email = extrairEmail();
        if (email != null) {
            segurado.setEmail(email);
        }
        
        apolice.setSegurado(segurado);
    }
    
    /**
     * Extrai números de CPF
     */
    private String extrairCPF() {
        Pattern pattern = Pattern.compile("(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11})");
        Matcher matcher = pattern.matcher(textoExtraido);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
    
    /**
     * Extrai números de CNPJ
     */
    private String extrairCNPJ() {
        Pattern pattern = Pattern.compile("(\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}|\\d{14})");
        Matcher matcher = pattern.matcher(textoExtraido);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
    
    /**
     * Extrai o nome do segurado
     */
    private String extrairNomeSegurado() {
        String[] padroes = {
                "(?i)segurado[:\\s]+([^\n]+)",
                "(?i)cliente[:\\s]+([^\n]+)",
                "(?i)nome[:\\s]+([^\n]+)"
        };
        
        for (String padrao : padroes) {
            Pattern pattern = Pattern.compile(padrao);
            Matcher matcher = pattern.matcher(textoExtraido);
            if (matcher.find()) {
                String nome = matcher.group(1).trim();
                if (!nome.isEmpty() && nome.length() > 3) {
                    return nome;
                }
            }
        }
        return null;
    }
    
    /**
     * Extrai endereço de email
     */
    private String extrairEmail() {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        Matcher matcher = pattern.matcher(textoExtraido);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }
    
    /**
     * Extrai datas de vigência (início e fim)
     */
    private void extrairDatasVigencia() {
        String[] padroes = {
                "(?i)vigência[:\\s]+([0-9]{1,2})/([0-9]{1,2})/([0-9]{4})",
                "(?i)vigência[:\\s]+([0-9]{1,2})/([0-9]{1,2})/([0-9]{4})\\s*(?:a|-|até)\\s*([0-9]{1,2})/([0-9]{1,2})/([0-9]{4})"
        };
        
        // Tenta encontrar intervalo de datas
        Pattern patternIntervalo = Pattern.compile(
            "(?i)vigência[:\\s]+(\\d{1,2})/(\\d{1,2})/(\\d{4})\\s*(?:a|-|até)\\s*(\\d{1,2})/(\\d{1,2})/(\\d{4})"
        );
        Matcher matcherIntervalo = patternIntervalo.matcher(textoExtraido);
        if (matcherIntervalo.find()) {
            try {
                int diaI = Integer.parseInt(matcherIntervalo.group(1));
                int mesI = Integer.parseInt(matcherIntervalo.group(2));
                int anoI = Integer.parseInt(matcherIntervalo.group(3));
                LocalDate dataInicio = LocalDate.of(anoI, mesI, diaI);
                apolice.setDataVigenciaInicio(dataInicio);
                
                int diaF = Integer.parseInt(matcherIntervalo.group(4));
                int mesF = Integer.parseInt(matcherIntervalo.group(5));
                int anoF = Integer.parseInt(matcherIntervalo.group(6));
                LocalDate dataFim = LocalDate.of(anoF, mesF, diaF);
                apolice.setDataVigenciaFim(dataFim);
            } catch (Exception e) {
                // Ignora erros de conversão de data
            }
        }
    }
    
    /**
     * Extrai valor do prêmio
     */
    private void extrairPremio() {
        String[] padroes = {
                "(?i)prêmio[:\\s]+[R$]*\\s*([0-9]+[.,][0-9]{2})",
                "(?i)valor[:\\s]+[R$]*\\s*([0-9]+[.,][0-9]{2})",
                "(?i)premium[:\\s]+[R$]*\\s*([0-9]+[.,][0-9]{2})"
        };
        
        for (String padrao : padroes) {
            Pattern pattern = Pattern.compile(padrao);
            Matcher matcher = pattern.matcher(textoExtraido);
            if (matcher.find()) {
                try {
                    String valor = matcher.group(1).replace(".", "").replace(",", ".");
                    apolice.setPremio(Double.parseDouble(valor));
                    apolice.setMoeda("BRL");
                    break;
                } catch (NumberFormatException e) {
                    // Continue procurando
                }
            }
        }
    }
    
    /**
     * Extrai dados adicionais que não foram capturados anteriormente
     */
    private void extrairDadosAdicionais() {
        // Este método permite adicionar mais dados conforme necessário
        // Por enquanto, mantém os dados brutos para futuras análises
        apolice.adicionarDado("textoCompleto", textoExtraido);
    }
}
