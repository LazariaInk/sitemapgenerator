import java.io.*;
import java.net.URISyntaxException;


public class Application {
    public static void main(String[] args) {
        BufferedReader reader;
        BufferedWriter writer;

        try {
            File jarDir = new File(Application.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();

            File[] txtFiles = jarDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

            if (txtFiles != null && txtFiles.length == 1) {
                File file = txtFiles[0];
                System.out.println("Citind din fișierul: " + file.getName());

                File outputFile = new File(jarDir, "sitemap.xml");
                writer = new BufferedWriter(new FileWriter(outputFile));

                writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                writer.write("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");

                reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();

                while (line != null) {
                    writer.write("    <url>\n");
                    writer.write("        <loc>" + line + "</loc>\n");
                    writer.write("        <lastmod>2024-10-20</lastmod>\n");
                    writer.write("        <priority>0.8</priority>\n");
                    writer.write("    </url>\n");

                    line = reader.readLine();
                }

                writer.write("</urlset>\n");

                reader.close();
                writer.close();

                System.out.println("Sitemap-ul a fost generat: " + outputFile.getAbsolutePath());

            } else {
                System.out.println("Nu s-a găsit niciun fișier text sau există mai multe fișiere .txt.");
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
