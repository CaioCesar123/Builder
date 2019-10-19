package pb;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProcessMAIN {
	public static void main(String[] args) throws java.io.IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		ProcessBuilder builder = new ProcessBuilder();
		String comandoLinha;
		int ponteiro = 0;
		String line;
		File diretorio = new File(System.getProperty("user.home"));
		List<String> Historico = new ArrayList<String>();
		List<String> Anterior = new ArrayList<String>();
		/*ZipFile zipFile = new ZipFile(caminhoDoSeuJar);
		String nomedoJar = "Infortunio";
		String caminhoDoSeuJar = System.getProperty("user.dir")+System.getProperty("file.separator")+nomedoJar+".jar";*/

		while (true) {
			System.out.println(diretorio + ">");
			comandoLinha = br.readLine();
			String[] comandos = comandoLinha.split(" ");
			List<String> comandList = new ArrayList<String>();
			if (comandoLinha.isEmpty()) {
				continue;
			}
			for (int i = 0; i < comandos.length; i++) {
				comandList.add(comandos[i]);
			}
			Historico.add(comandoLinha);

			try {
				if (comandList.get(comandList.size() - 1).equals("historico")) {
					for (String c : Historico)
						System.out.println((ponteiro++) + " " + c);
					continue;
				}
				if (comandoLinha.equals("cd")) {
					if (comandList.get(comandList.size() - 1).equals("cd")) {
						diretorio = new File(System.getProperty("user.home"));
						builder.directory(diretorio);
						continue;
					} else {
						String p = comandList.get(1);
						File nDiretorio = new File(p);
						boolean exists = nDiretorio.exists();
						if (exists) {
							diretorio = nDiretorio;
							builder.directory(diretorio);
							continue;
						} else {
							System.out.print(diretorio);
						}
					}
					/*Enumeration<? extends ZipEntry> entries = zipFile.entries();

					while (entries.hasMoreElements()) {
						ZipEntry zipEntry = (ZipEntry) entries.nextElement();
						String name = zipEntry.getName();
						if (!zipEntry.isDirectory() && name.contains("plugins")){
							System.out.println(name);
						}
					}*/
				}
				if (comandoLinha.equals("!!")) {
					if (Anterior.isEmpty()) {
						System.out.println("Não ha comando anterior");
						continue;
					} else {
						builder.command(Anterior);
					}
				} else {
					builder.command(comandList);
					Anterior.clear();
					for (int i = 0; i < comandos.length; i++) {
						Anterior.add(comandos[i]);
					}
				}
				java.lang.Process processo = builder.start();
				InputStream input = processo.getInputStream();
				InputStreamReader Sreader = new InputStreamReader(input);
				BufferedReader Breader = new BufferedReader(Sreader);
				while ((line = Breader.readLine()) != null)
					System.out.println(line);
				Breader.close();
			} catch (IOException e) {
				System.out.println("Comando nao reconhecido");
			}
		}

	}

}
