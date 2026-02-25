## 📝 Resumo do Desenvolvimento - Leitor de PDF

### ✅ O que foi implementado:

#### 1. **Arcitetura em Camadas**
- **Models**: Classes para representar dados (Apolice, Segurado)
- **Services**: Lógica de negócio (PDFReaderService, ApoliceExtratorService)
- **Parsers**: Extração de dados com regex (ApoliceParser)

#### 2. **Classe Segurado**
Armazena informações do segurado:
- Nome, CPF, CNPJ
- Endereço, cidade, estado, CEP
- Telefone, email

#### 3. **Classe Apólice**
Armazena informações da apólice:
- Número, tipo (APÓLICE/ENDOSSO/PROPOSTA)
- Seguradora, datas de vigência
- Prêmio, moeda
-Referência ao Segurado
- Dados adicionais em HashMap

#### 4. **PDFReaderService**
Responsável pela leitura de PDFs:
- `lerPDF(caminho)`: Extrai todo o texto
- `lerPaginaPDF(caminho, página)`: Extrai texto de uma página específica
- `obterNumericoPaginas(caminho)`: Retorna total de páginas
- Tratamento de erros (arquivo não encontrado, PDF criptografado)

#### 5. **ApoliceParser**
Parser com regex para extrair:
- ✅ Número da apólice (6-10 dígitos)
- ✅ Seguradora (busca por nome conhecido)
- ✅ Tipo de documento (APÓLICE, ENDOSSO, PROPOSTA)
- ✅ CPF (com ou sem formatação)
- ✅ CNPJ (com ou sem formatação)
- ✅ Nome do segurado
- ✅ Email
- ✅ Datas de vigência (DD/MM/AAAA)
- ✅ Prêmio (R$ com ou sem formatação)

#### 6. **ApoliceExtratorService**
Orquestra o fluxo:
1. Lê o PDF
2. Extrai texto
3. Faz o parse dos dados
4. Retorna objeto Apolice populado

#### 7. **Aplicação Principal (App.java)**
Menu interativo com 3 opções:
1. Extrair dados de um PDF (com exibição formatada)
2. Obter informações sobre um PDF
3. Sair da aplicação

### 📦 Dependências Adicionadas:
- **Apache PDFBox 2.0.27** (lib/pdfbox-2.0.27.jar)

### 🔧 Configurações:
- VS Code settings.json já estava configurado para incluir arquivos .jar da pasta lib
- Compilação automática com javac
- Classpath inclui a biblioteca PDFBox

### 🚀 Como Executar:

**Terminal:**
```powershell
cd "c:\Users\gusta\OneDrive\Desktop\projetos\java-vs\leitor de PDF"
java -cp "bin;lib\pdfbox-2.0.27.jar" App
```

**VS Code:**
- Abra App.java
- Clique no botão "Run" no topo do arquivo

### 📋 Exemplo de Saída:

```
===== DADOS EXTRAÍDOS =====

--- Informações da Apólice ---
Tipo: APÓLICE
Número: 123456
Seguradora: Porto Seguro
Prêmio: 1200.5 BRL
Vigência: 2024-01-15 até 2025-01-14

--- Informações do Segurado ---
Nome: João Silva Santos
CPF: 123.456.789-00
CNPJ: null
Email: joao@email.com
```

### 🔮 Próximos Passos Recomendados:

1. **Validação de Dados**: Validar CPF, CNPJ, datas, etc.
2. **Parsers por Seguradora**: Criar parsers específicos para cada seguradora
3. **Banco de Dados**: Integrar com banco para persistir dados
4. **API REST**: Expor como serviço web
5. **Melhorias no Parser**: 
   - Suportar mais formatos de data
   - Extrair coberturas e limites
   - Identificar segurado e beneficiários
6. **OCR para PDFs**: Suportar documentos digitalizados
7. **Logs**: Adicionar logging com SLF4J/Logback
8. **Testes Unitários**: JUnit 5 para testes

### 📊 Estrutura de Arquivos Criados:

```
src/
├── App.java (modificado)
├── models/
│   ├── Apolice.java
│   └── Segurado.java
├── services/
│   ├── PDFReaderService.java
│   └── ApoliceExtratorService.java
├── parsers/
│   └── ApoliceParser.java
└── utils/ (vazio - para futuras utilidades)

lib/
└── pdfbox-2.0.27.jar (baixado)

README.md (atualizado)
```

### ✨ Funcionalidades Implementadas:

- [x] Leitura de PDF
- [x] Extração de texto
- [x] Parse com expressões regulares
- [x] Identificação de dados principais
- [x] Menu interativo
- [x] Tratamento de erros
- [x] Estrutura extensível para novos padrões

### 📌 Notas Importantes:

- O parser é genérico e funciona com PDFs de qualquer seguradora
- Usa regex robustas para flexibilidade
- Dados não-encontrados retornam null
- O texto completo é mantido em "dadosAdicionais" para análises futuras
- A arquitetura permite fácil adição de validações e transformações

---

**Desenvolvido para**: SICS - Sistemas para Corretores de Seguros
