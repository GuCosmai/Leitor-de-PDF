## Getting Started - Leitor de Apólices SICS

Este é um leitor de PDFs de apólices, endossos e propostas de seguros. Extrai automaticamente dados principais como número da apólice, segurado, datas e valores.

## Estrutura do Projeto

O workspace contém:

- `src`: Código-fonte
  - `models/`: Classes Apolice e Segurado
  - `services/`: PDFReaderService e ApoliceExtratorService  
  - `parsers/`: ApoliceParser para extração
- `lib`: Dependências (PDFBox 2.0.27)
- `bin`: Arquivos compilados

## Como Usar

1. Abra o arquivo App.java e clique em "Run"
2. Ou execute: `java -cp "bin;lib\pdfbox-2.0.27.jar" App`
3. Selecione opção 1 e forneça o caminho completo do PDF
4. Os dados serão extraídos e exibidos

## Dados Extraídos

- Número da apólice
- Tipo (Apólice/Endosso/Proposta)
- Seguradora
- Nome e CPF/CNPJ do segurado
- Datas de vigência
- Prêmio
- Email

## Próximos Passos

1. Integração com banco de dados
2. Parsers específicos por seguradora
3. API REST para integração
4. Suporte a PDF com imagens (OCR)
