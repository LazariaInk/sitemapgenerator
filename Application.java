import java.io.*;
import java.net.URISyntaxException;

public class Application {

    private static final String SITEMAP_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n";
    private static final String SITEMAP_FOOTER = "</urlset>\n";
    private static final String LASTMOD = "<lastmod>2024-10-20</lastmod>";
    private static final String PRIORITY = "<priority>0.8</priority>";

    public static void main(String[] args) {
        try {
            File jarDir = getJarDirectory();
            File txtFile = findTxtFile(jarDir);

            if (txtFile != null) {
                System.out.println("Start processing: " + txtFile.getName());
                generateSitemap(txtFile, jarDir);
            } else {
                System.out.println("File not found!");
            }
        } catch (URISyntaxException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static File getJarDirectory() throws URISyntaxException {
        return new File(Application.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
    }

    private static File findTxtFile(File jarDir) {
        File[] txtFiles = jarDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (txtFiles != null && txtFiles.length == 1) {
            return txtFiles[0];
        }
        return null;
    }

    private static void generateSitemap(File inputFile, File jarDir) throws IOException {
        File outputFile = new File(jarDir, "sitemap.xml");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            writer.write(SITEMAP_HEADER);

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(createUrlEntry(line));
            }

            writer.write(SITEMAP_FOOTER);

            System.out.println("Site map was generated: " + outputFile.getAbsolutePath());
        }
    }

    private static String createUrlEntry(String url) {
        return "    <url>\n" +
                "        <loc>" + url + "</loc>\n" +
                "        " + LASTMOD + "\n" +
                "        " + PRIORITY + "\n" +
                "    </url>\n";
    }
}
