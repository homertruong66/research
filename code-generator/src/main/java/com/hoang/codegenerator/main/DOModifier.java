package com.hoang.codegenerator.main;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DOModifier {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Domain Object folder is missing !");
            return;
        }
        DOModifier.modifyDOs(args[0]);
    }

    public static void modifyDOs(String domainFolderPath) {
        BufferedReader br;
        DataOutputStream dos;
        try {
            File domainDir = new File(domainFolderPath);
            File[] javaFiles = domainDir.listFiles();
            if (javaFiles == null) {
                System.err.println("Folder " + domainDir.getName() + " does not exist !");
                return;
            }

            System.out.println("\nModifying domain objects in foler " + domainFolderPath + " ...");
            int no = 1;
            for (int i = 0; i < javaFiles.length; i++) {
                File f = javaFiles[i];
                if (f.isDirectory() || !f.getName().contains("java") || f.getName().contains("Abstract")) {
                    continue;
                }

                // parse file
                System.out.print((no++) + ")" + f.getName() + "\n");
                br = new BufferedReader(new InputStreamReader(
                        new FileInputStream(f)));
                StringBuffer sb = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().equals("")) {
                        continue;
                    }

                    if (line.contains("getId") || line.contains("setId")) {
                        boolean isStop = false;
                        while (!isStop) {
                            line = br.readLine();
                            if (line.trim().equals("}")) {
                                isStop = true;
                            }
                        }
                        continue;
                    }

                    if (line.contains("package")) {
                        sb.append(line);
                        sb.append("\n");
                        continue;
                    }

                    if (line.contains("implements java.io.Serializable")) {
                        int index = line.indexOf("implements");
                        String st = line.substring(0, index);
                        if (!line.contains("extends")) {
                            line = st.trim() + " extends AbstractEntity { ";
                        }
                        sb.append(line);
                        sb.append("\n");
                        continue;
                    }

                    if (line.contains("// Fields")) {
                        sb.append("\n     private static final long serialVersionUID = 1L;\n\n");
                        sb.append(line);
                        sb.append("\n");
                        continue;
                    }

                    if (line.contains("private Long id")) {
                        sb.append("\n");
                        continue;
                    }

                    if (line.contains("Constructor */") || line.contains("constructor */") || line.contains("// Property")) {
                        sb.append("\n");
                        sb.append(line);
                        sb.append("\n");
                        continue;
                    }

                    if (line.contains("}")) {
                        sb.append(line);
                        sb.append("\n");
                        continue;
                    }

                    sb.append(line);
                    sb.append("\n");
                }

                // save file
                dos = new DataOutputStream(new BufferedOutputStream(
                        new FileOutputStream(f)));
                dos.writeBytes(sb.toString());
                dos.flush();

                try {
                    br.close();
                    dos.close();
                } catch (IOException ex) {
                }
            }
            System.out.println("Done !\n");
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
