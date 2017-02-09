package com.scalait.bdd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/***
 * Lê um arquivo Excel com definições de funcionalidades
 * 
 * @author tsuru
 *
 */
public class ExcelFeatureSpecParser {

	public static File dirOut;

	public static final int INDICE_PLANILHA_TIPO_1 = 0;
	public static final int INDICE_PLANILHA_TIPO_2 = 1;

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			throw new IllegalArgumentException("O diretório de entrada e saída devem ser informados");
		}

		File dirIn = new File(args[0]);
		dirOut = new File(args[1]);

		Files.walk(dirIn.toPath()).filter(Files::isRegularFile).forEach(ExcelFeatureSpecParser::processarArquivo);

	}

	public static void processarArquivo(Path path) {

		System.out.println("Processando arquivo: " + path.getFileName());
		String UC = path.getFileName().toString().substring(0, 7);

		Workbook wb;
		try {
			wb = WorkbookFactory.create(path.toFile());
			Sheet sheet = wb.getSheetAt(0);
			if (sheet == null || !sheet.getSheetName().equalsIgnoreCase("Identificação")) {
				throw new RuntimeException("A primeira planilha deveria ser 'Identificação', mas foi encontrado: '"
						+ sheet.getSheetName() + "'!");
			}

			sheet = wb.getSheetAt(1);
			if (sheet == null || !sheet.getSheetName().equalsIgnoreCase("Configurações Iniciais")) {
				throw new RuntimeException(
						"A segunda planilha deveria ser 'Configurações Iniciais', mas foi encontrado: '"
								+ sheet.getSheetName() + "'!");
			}

			sheet = wb.getSheetAt(2);
			if (sheet == null || !sheet.getSheetName().equalsIgnoreCase("Cenários")) {
				throw new RuntimeException("A segunda planilha deveria ser 'Cenários', mas foi encontrado: '"
						+ sheet.getSheetName() + "'!");
			}
			for (int sn = 3; sn < wb.getNumberOfSheets(); sn++) {
				if (wb.isSheetHidden(sn)) {
					System.out.println(
							"Planilha está escondida e não será processada " + wb.getSheetAt(sn).getSheetName());
				} else {
					sheet = wb.getSheetAt(sn);
					System.out.println("Sheet #" + sn + " : " + sheet.getSheetName());

					processarPlanilhaCenario(sheet, UC);
				}
			}
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void processarPlanilhaCenario(Sheet sheet, String UC) throws IOException {

		String featureName = sheet.getSheetName();
		System.out.println("Processando planilha " + featureName);
		new File(dirOut.getAbsolutePath() + File.separator + UC).mkdirs();

		FileOutputStream fos = new FileOutputStream(
				dirOut.getAbsolutePath() + File.separator + UC + File.separator + featureName + ".feature");
		StringBuffer sb = new StringBuffer();
		sb.append("# language: pt\n");

		HashMap<String,Integer> indices = getPrimeiraLinhaValida(sheet);

		for (int i = indices.get("CABECALHO"); i < sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				Cell cell = row.getCell(indices.get("FUNCIONALIDADE"));

				if (cell != null && !cell.getStringCellValue().isEmpty()) {
					sb.append("Funcionalidade: ").append(cell.getStringCellValue()).append("\n");
				}
				String CT = row.getCell(indices.get("ID_CASO_TESTE")) == null ? "" : row.getCell(indices.get("ID_CASO_TESTE")).getStringCellValue();
				if (!CT.isEmpty()) {
					sb.append("@").append(CT).append("\n");
					
					String cenario = indices.get("CENARIO") == null ? "" : row.getCell(indices.get("CENARIO")).getStringCellValue();
					
					sb.append("Cenário: ").append(cenario.replace("\n", ""))
							.append("\n");
					sb.append(row.getCell(indices.get("PROCEDIMENTO")).getStringCellValue()).append("\n");
					sb.append(
							row.getCell(indices.get("RESULTADO_ESPERADO")) == null ? "" : row.getCell(indices.get("RESULTADO_ESPERADO")).getStringCellValue())
							.append("\n");
				}
			}
		}
		fos.write(comentarLinhasInvalidas(sb.toString()).getBytes());
		fos.flush();
		fos.close();
	}

	/***
	 * Método que retorna a primeira linha depois do cabeçalho
	 * 
	 * @param sheet
	 * @return
	 */
	public static HashMap<String,Integer> getPrimeiraLinhaValida(Sheet sheet) {
		HashMap<String,Integer> hm = null;
		int indiceCabecalho = -1;
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);

			Cell firstCell = row.getCell(0);
			String primeiraCelula = firstCell.getStringCellValue();
			System.out.println(primeiraCelula);
			if (primeiraCelula.equalsIgnoreCase("Funcionalidade") || primeiraCelula.startsWith("ID")) {
				hm = processaCabecalho(row);
				hm.put("CABECALHO", i + 1);
				indiceCabecalho = i + 1;
				break;
			}
		}
		if (indiceCabecalho > 0)
			return hm;
		else
			throw new RuntimeException("Não foi encontrado o cabeçalho para a planilha " + sheet.getSheetName());
	}
	public static HashMap<String,Integer> processaCabecalho(Row row) {
		HashMap<String,Integer> hm = new HashMap<String,Integer>();
		for (Cell cell : row) {
			String key = null;
			switch (cell.getStringCellValue()) {
			case "Alternativa de Cenário":
				key = "CENARIO";
				break;
			case "Alternativa do Cenário":
				key = "CENARIO";
				break;
			case "Alternativas de Cenário":
				key = "CENARIO";
				break;
			case "Cenário":
				key = "CENARIO";
				break;
			case "ID Caso Teste":
				key = "ID_CASO_TESTE";
				break;
			case "ID Caso de Teste":
				key = "ID_CASO_TESTE";
				break;
			case "ID Procedimento":
				key = "ID_CASO_TESTE";
				break;
			case "Funcionalidade":
				key = "FUNCIONALIDADE";
				break;
			case "PROCEDIMENTO":
				key = "PROCEDIMENTO";
				break;
			case "RESULTADO ESPERADO":
				key = "RESULTADO_ESPERADO";
				break;
			case "Perfil do usuário":
				key = "RESULTADO_ESPERADO";
				break;
			case "Perfil Usuário":
				key = "RESULTADO_ESPERADO";
				break;
			case "":
				key = "VAZIO";
				break;
			case "ONS-21/09/2016":
				key = "ONS-21/09/2016";
				break;
			case "ONS-27/09/2016":
				key = "ONS-27/09/2016";
				break;
			case "ONS-29/09/2016":
				key = "ONS-29/09/2016";
				break;
			case "ONS, 22/09/2016":
				key = "ONS-22/09/2016";
				break;
			case "ONS, 21/09/2016":
				key = "ONS-21/09/2016";
				break;
			case "ONS 29/09/2016 - massa de dados changeset 60047":
				key = "ONS-29/09/2016";
				break;
			case "ONS-20/09/2016 massa de dados change set 59502":
				key = "ONS-20/09/2016";
				break;
			case "ONS-23/09/2016":
				key = "ONS-23/09/2016";
				break;	
			case "ONS-23/09/2016 massa de dados change set 59736":
				key = "ONS-23/09/2016";
				break;	
			case "ONS-26/09/2016 massa de dados change set 59736":
				key = "ONS-26/09/2016";
				break;	
			case "ONS 21/09/2016":
				key = "ONS-21/09/2016";
				break;
			case "ONS 29/09/2016 - massa de dados change set 60047":
				key = "ONS-29/09/2016";
				break;
			case "ID":
				key = "ID_CASO_TESTE";
				break;
			case "ONS, 23/09/2016 massa de dados change set 59502":
				key = "ONS-23/09/2016";
				break;	
			case "ONS, 23/09/2016\nmassa de dados change set 59502":
				key = "ONS-23/09/2016";
				break;	
			case "ONS, 27/09/2016\nmassa de dados change set 59822 e 59365 ":
				key = "ONS-27/09/2016";
				break;	
			default:
				throw new RuntimeException("Coluna não encontrada: " + cell.getStringCellValue());
			}
			hm.put(key,cell.getColumnIndex());
		}
		return hm;
	}

	public static String comentarLinhasInvalidas(String gherkinContent) {
		StringBuffer sb = new StringBuffer();
		String[] lines = gherkinContent.split("\n");
		for (String line : lines) {
			String args[] = line.split(" ");
			if (args != null && args.length > 0) {
				String firstToken = args[0];
				if (firstToken.equals("Funcionalidade:") || firstToken.equals("#") || firstToken.equals("Dado") || firstToken.equals("Quando")
						|| firstToken.equals("E") || firstToken.equals("Então") || firstToken.startsWith("@")
						|| firstToken.equals("Cenário:")) {
					sb.append(line + "\n");
				} else {
					System.err.println("Token inválido: " + firstToken);
					sb.append("#" + line + "\n");
				}
			}
		}
		return sb.toString();
	}
}
